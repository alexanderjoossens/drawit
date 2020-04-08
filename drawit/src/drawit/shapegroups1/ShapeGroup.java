package drawit.shapegroups1;
import drawit.shapegroups1.Extent;

import java.util.ArrayList;

import drawit.DoublePoint;
import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;

public class ShapeGroup {

	private final Extent originalExtent;
	private Extent ownExtent = new Extent();
	private final RoundedPolygon shape;

	private ShapeGroup[] subgroups;
	private ShapeGroup parentGroup;
	private ShapeGroup firstChild;
	private ShapeGroup lastChild;
	private ShapeGroup previousSibling;
	private ShapeGroup nextSibling;
	
	private double horizontalScale = 1;
	private double verticalScale = 1;
	private int horizontalTranslation = 0;
	private int verticalTranslation = 0;

	/**
	 * Initializes this object to represent a leaf shape group that directly
	 * contains the given shape.
	 * 
	 * @param shape
	 */
	public ShapeGroup(RoundedPolygon shape) {
		this.shape = new RoundedPolygon();
		int maxX = 0;
		int maxY = 0;
		int minX = 0;
		int minY = 0;

		IntPoint[] points = shape.getVertices();
		for (int i = 0; i < points.length; i++) {
			int xCoord = points[i].getX();
			int yCoord = points[i].getY();
			if (i == 0) {
				maxX = xCoord;
				minX = xCoord;
				maxY = yCoord;
				minY = yCoord;
			} else {
				maxX = Math.max(maxX, xCoord);
				maxY = Math.max(maxY, yCoord);
				minX = Math.min(minX, xCoord);
				minY = Math.min(minY, yCoord);
			}
		}

		Extent extent = Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY);
		this.originalExtent = extent;
		this.ownExtent = extent;

	}

	/**
	 * Initializes this object to represent a non-leaf shape group that directly
	 * contains the given subgroups, in the given order.
	 * 
	 * @param subgroups
	 */
	public ShapeGroup(ShapeGroup[] subgroups) {
		this.shape = new RoundedPolygon();
		ShapeGroup tempPreviousSibling = null;
		for (ShapeGroup shapeGroup : subgroups) {
			shapeGroup.parentGroup = this;

			if (tempPreviousSibling != null) {
				shapeGroup.previousSibling = tempPreviousSibling;
				tempPreviousSibling.nextSibling = shapeGroup;
			}
			tempPreviousSibling = shapeGroup;
		}

		this.firstChild = subgroups[0];
		this.lastChild = subgroups[subgroups.length];

		int maxX = 0;
		int maxY = 0;
		int minX = 0;
		int minY = 0;
		for (ShapeGroup shapeGroup : subgroups) {
			Extent extent = shapeGroup.getExtent();
			if (maxX == 0 && maxY == 0 && minX == 0 && minY == 0) {
				maxY = extent.getBottom();
				maxX = extent.getRight();
				minY = extent.getTop();
				minX = extent.getLeft();
			} else {
				maxY = Math.max(extent.getBottom(), maxY);
				maxX = Math.max(extent.getRight(), maxX);
				minY = Math.min(extent.getTop(), minY);
				minX = Math.min(extent.getLeft(), minX);
			}

		}

		Extent extent = Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY);
		this.originalExtent = extent;
		this.ownExtent = extent;
	}

	/**
	 * Registers the given extent as this shape group's extent, expressed in this
	 * shape group's outer coordinate system.
	 * 
	 * @param newExtent
	 */
	public void setExtent(Extent newExtent) {
		this.ownExtent = newExtent;
	}

	/**
	 * Returns the extent of this shape group, expressed in its outer coordinate
	 * system.
	 */
	public Extent getExtent() {
		return ownExtent;
	}

	/**
	 * Returns the extent of this shape group, expressed in its inner coordinate
	 * system.
	 */
	public Extent getOriginalExtent() {
		return this.originalExtent;
	}

	/**
	 * Returns the coordinates in the global coordinate system of the point whose
	 * coordinates in this shape group's inner coordinate system are the given
	 * coordinates.
	 */
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		double newX = (((double) this.getOriginalExtent().getLeft()) * (1 - this.horizontalScale)
				+ this.horizontalScale * innerCoordinates.getX()) + this.horizontalTranslation;
		double newY = (((double) this.getOriginalExtent().getTop()) * (1 - this.verticalScale)
				+ this.verticalScale * innerCoordinates.getY()) + this.verticalTranslation;

		IntPoint outerCoordinates = new DoublePoint(newX, newY).round();

		if (this.getParentGroup() != null) {
			return this.getParentGroup().toGlobalCoordinates(outerCoordinates);
		} else {
			return outerCoordinates;
		}
	}

	/**
	 * Returns the coordinates in this shape group's inner coordinate system of the
	 * point whose coordinates in the global coordinate system are the given
	 * coordinates.
	 */
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
//		IntPoint newCoords;
//		if (this.getParentGroup() != null) {
//			newCoords = this.getParentGroup().toInnerCoordinates(globalCoordinates);
//		}
//
//		else {
//			newCoords = globalCoordinates;
//		}
//
//		double newX = (((double) this.getExtent().getLeft()) * (1 - (1 / this.horizontalScale))
//				+ (1 / this.horizontalScale) * newCoords.getX()) - this.horizontalTranslation;
//		double newY = (((double) this.getExtent().getTop()) * (1 - (1 / this.verticalScale))
//				+ (1 / this.verticalScale) * newCoords.getY()) - this.verticalTranslation;
//		IntPoint innerCoordinates = new DoublePoint(newX, newY).round();
//		return innerCoordinates;
		
		if (globalCoordinates == null)
			throw new IllegalArgumentException("The global Coordinates equal null");
		
		IntPoint currentCoord = globalCoordinates;
		if (this.getParentGroup() != null) {
			currentCoord = this.getParentGroup().toInnerCoordinates(currentCoord);
		}
		double InnerX = currentCoord.getX() - (horizontalTranslation)/horizontalScale;
		double InnerY = currentCoord.getY() - (verticalTranslation)/verticalScale;
		DoublePoint result = new DoublePoint(InnerX, InnerY);
		return result.round();
	}

	/**
	 * Returns the coordinates in this shape group's inner coordinate system of the
	 * vector whose coordinates in the global coordinate system are the given
	 * coordinates.
	 */
	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
		IntVector newVector;
		if (this.getParentGroup() != null) {
			newVector = this.getParentGroup().toInnerCoordinates(relativeGlobalCoordinates);
		}

		else {
			newVector = relativeGlobalCoordinates;
		}
		int newX = (int) ((int) newVector.getX() * (1 / this.horizontalScale));
		int newY = (int) ((int) newVector.getY() * (1 / this.verticalScale));

		IntVector innerVector = new IntVector(newX, newY);
		return innerVector;
	}

	/**
	 * Returns the shape directly contained by this shape group, or null if this is
	 * a non-leaf shape group.
	 */
	public RoundedPolygon getShape() {
		return shape;
	}

	/**
	 * Returns the shape group that directly contains this shape group, or null if
	 * no shape group directly contains this shape group.
	 */
	public ShapeGroup getParentGroup() {
		return this.parentGroup;
	}

	/**
	 * Returns the number of subgroups of this non-leaf shape group.
	 */
	public int getSubgroupCount() {
		return this.subgroups.length;
	}

	/**
	 * Returns the subgroup at the given (zero-based) index in this non-leaf shape
	 * group's list of subgroups.
	 * 
	 * @param index
	 */
	public ShapeGroup getSubgroup(int index) {
		return this.getSubgroups().get(index);
	}

	/**
	 * Return the first subgroup in this non-leaf shape group's list of subgroups
	 * whose extent contains the given point, expressed in this shape group's inner
	 * coordinate system.
	 */
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		ShapeGroup returnGroup = null;
		for (int i = 0; i < subgroups.length; i++) {
			if (subgroups[i].getOriginalExtent().contains(innerCoordinates)) {
				returnGroup = subgroups[i];
			}
		}
		return returnGroup;
	}

	/**
	 * Returns the list of subgroups of this shape group, or null if this is a leaf
	 * shape group.
	 */
	public java.util.List<ShapeGroup> getSubgroups() {
		if (firstChild != null) {
			ArrayList<ShapeGroup> subgroups = new ArrayList<ShapeGroup>() ;
			for (ShapeGroup subgroup = firstChild; subgroup !=null; subgroup=subgroup.nextSibling)
				subgroups.add(subgroup);
			return subgroups;
		}
		return null;
	}

	/**
	 * Moves this shape group to the front of its parent's list of subgroups.
	 */
	public void bringToFront() {
		for (int i = 0; i < parentGroup.subgroups.length; i++) {
//			if (parentGroup.getSubgroup(i) == this) {
//				parentGroup.subgroups.append(parentGroup.getSubgroup(i));
//				parentGroup.subgroups.delete(i);
				break;
			}
		}


	/**
	 * Moves this shape group to the back of its parent's list of subgroups.
	 */
	public void sendToBack() {
		for (int i=0; i< parentGroup.subgroups.length; i++) {
//			if (parentGroup.getSubgroup(i) == this) {
//				parentGroup.Subgroups.add(parentGroup.getSubgroup(i));
//				parentGroup.subgroups.remove(i);
				break;
			}
		}


	/**
	 * Returns a textual representation of a sequence of drawing commands for
	 * drawing the shapes contained directly or indirectly by this shape group,
	 * expressed in this shape group's outer coordinate system.
	 */
	public java.lang.String getDrawingCommands() {

		StringBuilder commands = new StringBuilder();

		if (this.firstChild == null) {
			commands.append("pushTranslate "+horizontalTranslation+" "+verticalTranslation+"\n");
			commands.append("pushScale "+horizontalScale+" "+verticalScale+"\n");
			commands.append(this.getShape().getDrawingCommands());
			commands.append("popTransform\n");
			commands.append("popTransform\n");
		}

		else {
			for (ShapeGroup subgroup : subgroups) {
				commands.append("pushTranslate "+horizontalTranslation+" "+verticalTranslation+"\n");
				commands.append("pushScale "+horizontalScale+" "+verticalScale+"\n");
				commands.append(subgroup.getDrawingCommands());
				commands.append("popTransform\n");
				commands.append("popTransform\n");
			}
		}
		return commands.toString();
	}
}





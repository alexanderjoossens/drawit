package drawit.shapegroups2;

import drawit.shapegroups2.Extent;
import drawit.DoublePoint;
import drawit.IntPoint;
import drawit.IntVector;
import drawit.DoubleVector;
import drawit.RoundedPolygon;

public class ShapeGroup {

	private RoundedPolygon shape;
	private ShapeGroup[] subgroups;
	private Extent ownExtent;
	private final Extent originalExtent;
	private ShapeGroup parentGroup;
	private double horizontalScale = 1;
	private double verticalScale = 1;
	private int horizontalTranslation = 0;
	private int verticalTranslation = 0;

	private ShapeGroup firstChild;
	private ShapeGroup lastChild;

	private ShapeGroup previousSibling = null;
	private ShapeGroup nextSibling = null;

	/**
	 * Initializes this object to represent a leaf shape group that directly
	 * contains the given shape.
	 * 
	 * @param shape
	 */
	public ShapeGroup(RoundedPolygon shape) {
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

		this.horizontalScale = ((double) newExtent.getWidth() / (double) this.getOriginalExtent().getWidth());
		this.verticalScale = ((double) newExtent.getHeight() / (double) this.getOriginalExtent().getHeight());
		this.horizontalTranslation = (newExtent.getLeft() - getOriginalExtent().getLeft());
		this.verticalTranslation = (newExtent.getTop() - getOriginalExtent().getTop());
	}

	/**
	 * Returns the extent of this shape group, expressed in its outer coordinate
	 * system.
	 * 
	 * @return
	 */
	public Extent getExtent() {
		return ownExtent;
	}

	/**
	 * Returns the extent of this shape group, expressed in its inner coordinate
	 * system.
	 * 
	 * @return
	 */
	public Extent getOriginalExtent() {
		return this.originalExtent;
	}

	/**
	 * Returns the coordinates in the global coordinate system of the point whose
	 * coordinates in this shape group's inner coordinate system are the given
	 * coordinates.
	 */
	public IntPoint toGLobalCoordinates(IntPoint innerCoordinates) {
		double newX = (((double) this.getOriginalExtent().getLeft()) * (1 - this.horizontalScale)
				+ this.horizontalScale * innerCoordinates.getX()) + this.horizontalTranslation;
		double newY = (((double) this.getOriginalExtent().getTop()) * (1 - this.verticalScale)
				+ this.verticalScale * innerCoordinates.getY()) + this.verticalTranslation;

		IntPoint outerCoordinates = new DoublePoint(newX, newY).round();

		if (this.getParentgroup() != null) {
			return this.getParentgroup().toGLobalCoordinates(outerCoordinates);
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
		IntPoint newCoords;
		if (this.getParentgroup() != null) {
			newCoords = this.getParentgroup().toInnerCoordinates(globalCoordinates);
		}

		else {
			newCoords = globalCoordinates;
		}

		double newX = (((double) this.getExtent().getLeft()) * (1 - (1 / this.horizontalScale))
				+ (1 / this.horizontalScale) * newCoords.getX()) - this.horizontalTranslation;
		double newY = (((double) this.getExtent().getTop()) * (1 - (1 / this.verticalScale))
				+ (1 / this.verticalScale) * newCoords.getY()) - this.verticalTranslation;
		IntPoint innerCoordinates = new DoublePoint(newX, newY).round();
		return innerCoordinates;

	}

	/**
	 * Returns the coordinates in this shape group's inner coordinate system of the
	 * vector whose coordinates in the global coordinate system are the given
	 * coordinates.
	 */
	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
		IntVector newVector;
		if (this.getParentgroup() != null) {
			newVector = this.getParentgroup().toInnerCoordinates(relativeGlobalCoordinates);
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
	 * 
	 * @return
	 */
	public RoundedPolygon getShape() {
		return shape;
	}

	/**
	 * Returns the shape group that directly contains this shape group, or null if
	 * no shape group directly contains this shape group.
	 * 
	 * @return
	 */
	public ShapeGroup getParentgroup() {
		return this.parentGroup;
	}

	/**
	 * Returns the number of subgroups of this non-leaf shape group.
	 * 
	 * @return
	 */
	public int getSubgroupCount() {
		return this.getSubgroups().length;
	}

	/**
	 * Returns the subgroup at the given (zero-based) index in this non-leaf shape
	 * group's list of subgroups.
	 * 
	 * @param index
	 * @return
	 */
	public ShapeGroup getSubgroup(int index) {
		return this.getSubgroups()[index];
	}

	/**
	 * Return the first subgroup in this non-leaf shape group's list of subgroups
	 * whose extent contains the given point, expressed in this shape group's inner
	 * coordinate system.
	 */
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		ShapeGroup[] subgroups = this.getSubgroups();
		boolean foundSubgroup = false;
		ShapeGroup returnGroup = null;
		for (ShapeGroup subgroup : subgroups) {
			if (subgroup.getOriginalExtent().contains(innerCoordinates) && !foundSubgroup) {
				returnGroup = subgroup;
			}
		}
		return returnGroup;
	}

	/**
	 * Returns the list of subgroups of this shape group, or null if this is a leaf
	 * shape group.
	 */
	public ShapeGroup[] getSubgroups() {
		return this.subgroups;
	}

	/**
	 * Moves this shape group to the front of its parent's list of subgroups.
	 */
	public void bringToFront() {
		this.nextSibling = this.getParentgroup().firstChild;
		this.nextSibling.previousSibling = this.previousSibling;
		this.previousSibling.nextSibling = this.nextSibling;
		this.getParentgroup().firstChild.previousSibling = this;
		this.getParentgroup().firstChild = this;
	}

	/**
	 * Moves this shape group to the back of its parent's list of subgroups.
	 */
	public void sendToBack() {

	}

	/**
	 * Returns a textual representation of a sequence of drawing commands for
	 * drawing the shapes contained directly or indirectly by this shape group,
	 * expressed in this shape group's outer coordinate system.
	 */
	public java.lang.String getDrawingCommands() {

		StringBuilder commands = new StringBuilder();

		if (this.subgroups == null) {
			commands.append("pushTranslate");
			commands.append("pushScale");
			commands.append(this.getShape().getDrawingCommands());
			commands.append("popTransform");
			commands.append("popTransform");
		}

		else {
			for (ShapeGroup subgroup : this.subgroups) {
				commands.append("pushTranslate");
				commands.append("pushScale");
				commands.append(subgroup.getDrawingCommands());
				commands.append("popTransform");
				commands.append("popTransform");
			}
		}
		return commands.toString();
	}
}

// Dit is oude code, maar ik hou het bij voor als nieuwe code niet werkt

//public ShapeGroup(RoundedPolygon shape) {
//	this.shape = shape;
//	this.originalExtent = this.getExtent();
//
//}
//
//public ShapeGroup(ShapeGroup[] subgroups) {
//	this.subgroups = subgroups;
//	this.originalExtent = this.getExtent();
//	for (ShapeGroup shapeGroup: subgroups) {
//		shapeGroup.parentGroup = this;
//	}
//}
//
//public Extent getExtent() {
//	int maxX = 0;
//	int maxY = 0;
//	int minX = 0;
//	int minY = 0;
//	if (shape==null) {
//		for(ShapeGroup shapeGroup: this.getSubgroups()) {
//			Extent extent = shapeGroup.getExtent();
//			if (maxX==0 && maxY == 0 && minX == 0 && minY ==0) {
//				maxY = extent.getBottom();
//				maxX = extent.getRight();
//				minY = extent.getTop();
//				minX = extent.getLeft();
//			}
//			else {
//				maxY = Math.max(extent.getBottom(),maxY);
//				maxX = Math.max(extent.getRight(),maxX);
//				minY = Math.min(extent.getTop(),minY);
//				minX = Math.min(extent.getLeft(),minX);
//			}
//			
//		}
//	}
//	
//	else {
//		IntPoint[] points = shape.getVertices();
//		for (int i = 0; i < points.length; i++) {
//			int xCoord = points[i].getX();
//			int yCoord = points[i].getY();
//			if (i == 0) {
//				maxX = xCoord;
//				minX = xCoord;
//				maxY = yCoord;
//				minY = yCoord;
//			} else {
//				maxX = Math.max(maxX, xCoord);
//				maxY = Math.max(maxY, yCoord);
//				minX = Math.min(minX, xCoord);
//				minY = Math.min(minY, yCoord);
//
//			}
//		}
//	}
//
//	Extent extent = Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY);
//	return extent;
//}

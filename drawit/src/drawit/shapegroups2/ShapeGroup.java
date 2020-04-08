package drawit.shapegroups2;
import drawit.shapegroups2.Extent;
import drawit.DoublePoint;
import drawit.IntPoint;
import drawit.IntVector;
import drawit.DoubleVector;
import drawit.RoundedPolygon;

public class ShapeGroup {

	private Extent ownExtent;
	private ShapeGroup[] subgroups;
	private final Extent originalExtent;
	
	private double horizontalScale = 1;
	private double verticalScale = 1;
	private int horizontalTranslation = 0;
	private int verticalTranslation = 0;

	private RoundedPolygon shape;
	private ShapeGroup parentGroup;
	private ShapeGroup firstChild;
	private ShapeGroup lastChild;
	private ShapeGroup previousSibling;
	private ShapeGroup nextSibling;

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
		this.lastChild = subgroups[subgroups.length-1];

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
		IntPoint newCoords;
		if (this.getParentGroup() != null) {
			newCoords = this.getParentGroup().toInnerCoordinates(globalCoordinates);
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
		ShapeGroup[] subgroupList = new ShapeGroup[1];
		subgroupList[0] = this.getParentGroup().firstChild;
		for (int i=1; i<this.getSubgroupCount();i++) {
			ShapeGroup[] tempSubgroupList = new ShapeGroup[subgroupList.length+1];
			for (int j=0; i<subgroupList.length;i++) {
				tempSubgroupList[j] = subgroupList[j];
			}
			tempSubgroupList[subgroupList.length] = subgroupList[subgroupList.length-1].nextSibling;
			subgroupList = tempSubgroupList;
			
		}
		
		return subgroupList;
	}

	/**
	 * Moves this shape group to the front of its parent's list of subgroups.
	 */
	public void bringToFront() {
		this.nextSibling.previousSibling = this.previousSibling;
		this.previousSibling.nextSibling = this.nextSibling;
		this.getParentGroup().firstChild.previousSibling = this;
		this.nextSibling = this.getParentGroup().firstChild;
		this.getParentGroup().firstChild = this;
		this.previousSibling = null;
	}

	/**
	 * Moves this shape group to the back of its parent's list of subgroups.
	 */
	public void sendToBack() {
		this.previousSibling.nextSibling = this.nextSibling;
		this.nextSibling.previousSibling = this.previousSibling;
		this.getParentGroup().lastChild.nextSibling = this;
		this.previousSibling = this.getParentGroup().lastChild;
		this.getParentGroup().lastChild = this;
		this.nextSibling = null;
	}

	/**
	 * Returns a textual representation of a sequence of drawing commands for
	 * drawing the shapes contained directly or indirectly by this shape group,
	 * expressed in this shape group's outer coordinate system.
	 */
	public java.lang.String getDrawingCommands() {
		StringBuilder commands = new StringBuilder();
		commands.append("pushTranslate "+horizontalTranslation+" "+verticalTranslation+"\n");
		commands.append("pushScale "+horizontalScale+" "+verticalScale+"\n");
		if (getShape() != null)
			commands.append(getShape().getDrawingCommands());
		else {
			for (int i = getSubgroupCount() -1; i<=0;i--) {
				ShapeGroup currentSubgroup = getSubgroup(i);
				commands.append(currentSubgroup.getDrawingCommands());
			}
		}
		
		commands.append("popTransform\n");
		commands.append("popTransform\n");
		
		return commands.toString();
		
		
		
		
		
		
//
//		StringBuilder commands = new StringBuilder();
//
//		if (this.subgroups == null) {
//			commands.append("pushTranslate "+horizontalTranslation+" "+verticalTranslation+"\n");
//			commands.append("pushScale "+horizontalScale+" "+verticalScale+"\n");
//			commands.append(this.getShape().getDrawingCommands());
//			commands.append("popTransform\n");
//			commands.append("popTransform\n");
//		}
//
//		else {
//			for (ShapeGroup subgroup : subgroups) {
//				commands.append("pushTranslate "+horizontalTranslation+" "+verticalTranslation+"\n");
//				commands.append("pushScale "+horizontalScale+" "+verticalScale+"\n");
//				commands.append(subgroup.getDrawingCommands());
//				commands.append("popTransform\n");
//				commands.append("popTransform\n");
//			}
//		}
//		return commands.toString();
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

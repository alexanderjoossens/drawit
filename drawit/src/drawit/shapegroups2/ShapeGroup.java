package drawit.shapegroups2;

import drawit.shapegroups2.Extent;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;

public class ShapeGroup {

	private RoundedPolygon shape;
	private ShapeGroup[] subgroups;
	private Extent ownExtent;
	private final Extent originalExtent;
	private ShapeGroup parentGroup;
	private double horizontalScale = 0;
	private double verticalScale = 0;
	private double horizontalTranslate = 0;
	private double verticalTranslate = 0;

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
		for (ShapeGroup shapeGroup : subgroups) {
			shapeGroup.parentGroup = this;
		}

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
		return null;
	}

	/**
	 * Returns the coordinates in this shape group's inner coordinate system of the
	 * point whose coordinates in the global coordinate system are the given
	 * coordinates.
	 */
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		return null;
	}

	/**
	 * Returns the coordinates in this shape group's inner coordinate system of the
	 * vector whose coordinates in the global coordinate system are the given
	 * coordinates.
	 */
	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
		return null;
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
		
		String text = "";

		if (this.getShape() != null) {
			text += String.format("pushTranslate");
			text += String.format("pushScale");
			text += this.getShape().getDrawingCommands();
			text += String.format("popTransform");
			text += String.format("popTransform");
		}
		
		else {
			for (int i = 1; i < this.getSubgroupCount(); i++) {
				text += String.format("pushTranslate");
				text += String.format("pushScale");
				text += this.getSubgroup(i).getShape().getDrawingCommands();
				text += String.format("popTransform");
				text += String.format("popTransform");
				}
		}
		return text;
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

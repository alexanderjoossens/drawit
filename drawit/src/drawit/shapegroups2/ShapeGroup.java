package drawit.shapegroups2;
import drawit.shapegroups1.Extent;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;

public class ShapeGroup {
	private RoundedPolygon shape;
	private ShapeGroup[] subgroups;
	private Extent ownExtent;
	private final Extent originalExtent;
	private ShapeGroup parentGroup; //test
	
	

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

	public ShapeGroup(ShapeGroup[] subgroups) {
		for (ShapeGroup shapeGroup: subgroups) {
			shapeGroup.parentGroup = this;
		}
		
		
		int maxX = 0;
		int maxY = 0;
		int minX = 0;
		int minY = 0;
		for(ShapeGroup shapeGroup: subgroups) {
			Extent extent = shapeGroup.getExtent();
			if (maxX==0 && maxY == 0 && minX == 0 && minY ==0) {
				maxY = extent.getBottom();
				maxX = extent.getRight();
				minY = extent.getTop();
				minX = extent.getLeft();
			}
			else {
				maxY = Math.max(extent.getBottom(),maxY);
				maxX = Math.max(extent.getRight(),maxX);
				minY = Math.min(extent.getTop(),minY);
				minX = Math.min(extent.getLeft(),minX);
			}
			
		}
		
		Extent extent = Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY);
		this.originalExtent = extent;
		this.ownExtent = extent;
	}
	
	public void setExtent(Extent newExtent) {
		this.ownExtent = newExtent;
	}

	public Extent getExtent() {
		return ownExtent;
	}

	public Extent getOriginalExtent() {
		return this.originalExtent;
	}
//
//	public IntPoint toGLobalCoordinates(IntPoint innerCoordinates) {
//		return null;
//	}
//
//	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
//		return null;
//	}
//
//	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
//		return null;
//	}


	public RoundedPolygon getShape() {
		if (shape != null) {
			return shape;
		} else {
			return null;
		}
	}

	public ShapeGroup getParentgroup() {
		return this.parentGroup;
	}

	public ShapeGroup[] getSubgroups() {
		return subgroups;
	}

	public int getSubgroupCount() {
		return this.getSubgroups().length;
	}

	public ShapeGroup getSubgroup(int index) {
		return this.getSubgroups()[index];
	}

//	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
//		return null;
//	}
//
//	public void bringToFront() {
//
//	}
//
//	public void sendToBack() {
//
//	}
//
//	public java.lang.String getDrawingCommands() {
//		return null;
//	}
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

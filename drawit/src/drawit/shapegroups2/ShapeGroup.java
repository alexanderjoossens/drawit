package drawit.shapegroups2;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;

public class ShapeGroup {
	private RoundedPolygon shape;
	private ShapeGroup[] subgroups;
	private float horizontalScale;
	private float verticalScale;
	private Extent setExtent;

	public ShapeGroup(RoundedPolygon shape) {
		this.shape = shape;

	}

	public ShapeGroup(ShapeGroup[] subgroups) {
		this.subgroups = subgroups;
	}

	public Extent getExtent() {
		int maxX = 0;
		int maxY = 0;
		int minX = 0;
		int minY = 0;
		if (shape==null) {
			for(ShapeGroup shapeGroup: this.getSubgroups()) {
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
		}
		
		else {
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
		}

		Extent extent = Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY);
		return extent;
	}

//	public Extent getOriginalExtent() {
//		return null;
//	}
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

	public void setExtent(Extent newExtent) {
		this.setExtent = newExtent;
	}

	public RoundedPolygon getShape() {
		if (shape != null) {
			return shape;
		} else {
			return null;
		}
	}

//	public ShapeGroup getParentgroup() {
//		return null;
//	}

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

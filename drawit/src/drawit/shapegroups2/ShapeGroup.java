package drawit.shapegroups2;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;

public class ShapeGroup {
	RoundedPolygon shape;
	ShapeGroup[] subgroups;

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
		IntPoint[] points = shape.getVertices();
		for (int i = 0 ; i<points.length; i++) {
			int xCoord = points[i].getX();
			int yCoord = points[i].getY();
			if (i ==0) {
				maxX = xCoord;
				minX = xCoord;
				maxY = yCoord;
				minY = yCoord;
			}
			else {
				maxX = Math.max(maxX, xCoord);
				maxY = Math.max(maxY, yCoord);
				minX = Math.min(minX, xCoord);
				minY = Math.min(minY, yCoord);

				
			}
		}
	
		Extent extent = Extent.ofLeftTopRightBottom(minX,minY,maxX,maxY);
		return extent;
	}
	
	public Extent getOriginalExtent() {
		return null;
	}
	
	public IntPoint toGLobalCoordinates (IntPoint innerCoordinates) {
		return null;
	}
	
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		return null;
	}
	
	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
		return null;
	}
	
	public void setExtent(Extent newExtent) {
		
	}
	
	public RoundedPolygon getShape() {
		if (shape != null) {
			return shape;
		}
		else {
			return null;
		}
	}
	
	public ShapeGroup getSubgroup(int index) {
		return null;
	}
	
	public ShapeGroup getParentgroup() {
		return null;
	}
	
	public java.util.List<ShapeGroup> getSubgroups() {
		return null;
	}
	
	public int getSubgroupCount() {
		return 0;
	}
	
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		return null;
	}
	
	public void bringToFront() {
		
	}
	
	public void sendToBack() {
		
	}
	
	public java.lang.String getDrawingCommands() {
		return null;
	}
}

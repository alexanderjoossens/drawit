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
		return null;
		
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
		return null;
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

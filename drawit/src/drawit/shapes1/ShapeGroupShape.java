package drawit.shapes1;

import drawit.shapes1.ControlPoint;

public class ShapeGroupShape implements Shape {

	public ShapeGroupShape(drawit.shapegroups1.ShapeGroup group) {
		return;
	}
	
	public drawit.shapegroups1.ShapeGroup getShapeGroup() {
		return null;
	}
	
	public drawit.shapegroups1.ShapeGroup getParent() {
		return null;
	}
	
	public boolean contains(drawit.IntPoint p) {
		return true;
	}
	
	public String getDrawingCommands() {
		return null;
	}
	
	public ControlPoint[] createControlPoints() {
		return null;
	}
	
	public drawit.IntPoint toShapeCoordinates(drawit.IntPoint p) {
		return null;
	}
	
	public drawit.IntPoint toGlobalCoordinates(drawit.IntPoint p) {
		return null;
	}
}

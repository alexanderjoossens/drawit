package drawit.shapegroups1;

public interface Shape {

	private boolean contains​(drawit.IntPoint p) {
		return true;
	}

	private ControlPoint[] createControlPoints() {
		return null;
	}
	
	private String getDrawingCommands() {
		return null;
	}
	
	private drawit.shapegroups1.ShapeGroup getParent() {
		return null;
	}
	
	private drawit.IntPoint	toGlobalCoordinates​(drawit.IntPoint p) {
		return null;
	}
		
	private drawit.IntPoint	toShapeCoordinates​(drawit.IntPoint p) {
		return null;
	}
}

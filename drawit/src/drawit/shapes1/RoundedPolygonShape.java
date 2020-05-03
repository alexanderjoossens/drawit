package drawit.shapes1;

import drawit.RoundedPolygon;
import drawit.shapegroups1.ShapeGroup;
import drawit.shapes1.ControlPoint;

public class RoundedPolygonShape implements Shape {
	private RoundedPolygon polygon;
	private ShapeGroup parent;

	public RoundedPolygonShape(drawit.shapegroups1.ShapeGroup parent, drawit.RoundedPolygon polygon) {
		this.parent = parent;
		this.polygon = polygon;
	}
	
	public drawit.RoundedPolygon getPolygon() {
		return polygon;
	}
	
	public boolean contains(drawit.IntPoint p) {
		return this.getPolygon().contains(p);
	}
	
	public String getDrawingCommands() {
		return this.getPolygon().getDrawingCommands();
	}
	
	public drawit.shapegroups1.ShapeGroup getParent() {
		return parent;
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

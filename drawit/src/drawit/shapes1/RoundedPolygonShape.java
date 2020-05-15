package drawit.shapes1;

import java.util.List;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.ShapeGroup;
import drawit.shapes1.ControlPoint;
import drawit.shapegroups1.*;

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
		IntPoint[] vertices = this.getPolygon().getVertices();
		ControlPoint[] controlpoints = new ControlPoint[vertices.length];
		
		for (int i=0; i<vertices.length; i++) {
			controlpoints[i] = new ControlPointRoundedPolygon(this.getPolygon(), vertices[i]);
		}
		return controlpoints;
	}
	

	
	public drawit.IntPoint toShapeCoordinates(drawit.IntPoint p) {
		return parent.toInnerCoordinates(p);
	}
	
	public drawit.IntPoint toGlobalCoordinates(drawit.IntPoint p) {
		return parent.toGlobalCoordinates(p);
	}
}

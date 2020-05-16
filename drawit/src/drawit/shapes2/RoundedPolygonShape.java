package drawit.shapes2;

import java.util.List;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups2.ShapeGroup;
import drawit.shapes2.ControlPoint;
import drawit.shapegroups2.*;

public class RoundedPolygonShape implements Shape {
	private RoundedPolygon polygon;
	private ShapeGroup parent;
	

	public RoundedPolygonShape(ShapeGroup parent, RoundedPolygon polygon) {
		this.parent = parent;
		this.polygon = polygon;
	}
	
	public drawit.RoundedPolygon getPolygon() {
		return polygon;
	}
	
	public boolean contains(IntPoint p) {
		return this.getPolygon().contains(p);
	}
	
	public String getDrawingCommands() {
		return this.getPolygon().getDrawingCommands();
	}
	
	public ShapeGroup getParent() {
		return parent;
	}
	
	public ControlPoint[] createControlPoints() {
		IntPoint[] vertices = this.getPolygon().getVertices();
		ControlPoint[] controlpoints = new ControlPoint[vertices.length];
		
		for (int i=0; i<vertices.length; i++) {
			controlpoints[i] = new ControlPointRoundedPolygon(this, vertices[i], i);
		}
		return controlpoints;
	}
	

	
	public IntPoint toShapeCoordinates(IntPoint p) {
		return parent.toInnerCoordinates(p);
	}
	
	public IntPoint toGlobalCoordinates(IntPoint p) {
		return parent.toGlobalCoordinates(p);
	}
}


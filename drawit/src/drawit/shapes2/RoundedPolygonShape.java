package drawit.shapes2;

import java.util.List;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;
import drawit.shapegroups2.ShapeGroup;
import drawit.shapes2.ControlPoint;
import drawit.shapegroups2.*;

public class RoundedPolygonShape implements Shape {
	private RoundedPolygon polygon;
	private ShapeGroup parent;
	
	
	
	
	
	private static class ControlPointRoundedPolygon implements ControlPoint {
		private RoundedPolygonShape polygon;
		private IntPoint point;
		int index;
		
		public ControlPointRoundedPolygon(RoundedPolygonShape polygon, IntPoint point, int index) {
			this.polygon = polygon;
			this.point = point;
			this.index = index;
			
			
		}

		@Override
		public IntPoint getLocation() {
			return this.point;
		}

		@Override
		public void move(IntVector delta) {
			IntPoint globalCP = this.polygon.toGlobalCoordinates(point);
			IntPoint movedCP = globalCP.plus(delta);
			IntPoint shapeCP = this.polygon.toShapeCoordinates(movedCP);
			this.polygon.getPolygon().update(index, shapeCP);
			
			
		}

		@Override
		public void remove() {
			IntPoint cp = this.point;
			boolean duplicateFound = false;
			IntPoint[] vertices = this.polygon.getPolygon().getVertices();
			for (int i = 0; i < vertices.length; i++) {
				IntPoint vertex = vertices[i];
				if (vertex.getX() == cp.getX() & vertex.getY() == cp.getY() & duplicateFound == false) {
					duplicateFound = true;
					this.polygon.getPolygon().remove(i);
					
				}
			}
			if (duplicateFound == false) {
				throw new UnsupportedOperationException("ControlPoint does not equal any of the polygons vertices");
			}
		}
		
	}
	
	
	
	
	
	
	
	

	public RoundedPolygonShape(drawit.shapegroups2.ShapeGroup parent, drawit.RoundedPolygon polygon) {
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
	
	public drawit.shapegroups2.ShapeGroup getParent() {
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
	

	
	public drawit.IntPoint toShapeCoordinates(drawit.IntPoint p) {
		if (this.getParent()==null) {
			return p;
		}
		return parent.toInnerCoordinates(p);
	}
	
	public drawit.IntPoint toGlobalCoordinates(drawit.IntPoint p) {
		if (this.getParent()==null) {
			return p;
		}
		return parent.toGlobalCoordinates(p);
	}
}

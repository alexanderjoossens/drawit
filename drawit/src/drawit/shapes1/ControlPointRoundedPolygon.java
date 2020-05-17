package drawit.shapes1;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;

public class ControlPointRoundedPolygon implements ControlPoint {
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
//		this.point = shapeCP;
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
				this.polygon.getPolygon().remove(i);
				
			}
		}
		if (duplicateFound == false) {
			throw new UnsupportedOperationException("ControlPoint does not equal any of the polygons vertices");
		}
	}
	
}

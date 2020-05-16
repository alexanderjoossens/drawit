package drawit.shapes2;

import drawit.IntPoint;
import drawit.IntVector;

public class ControlPointShapeGroup implements ControlPoint {
	private ShapeGroupShape shape;
	private IntPoint point;
	
	public ControlPointShapeGroup(ShapeGroupShape shape, IntPoint point) {
		this.shape = shape;
		this.point = point;
	}
	
	
	
	@Override
	public IntPoint getLocation() {
		return this.point;
	}

	@Override
	public void move(IntVector delta) {
		
	}

	@Override
	public void remove() {
		
	}
	
	
}
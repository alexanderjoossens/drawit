package drawit.shapes1;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.shapegroups1.Extent;

public class ControlPointShapeGroup implements ControlPoint {
	private ShapeGroupShape shape;
	private final IntPoint point;
	private boolean isTopLeftExtent;
	
	public ControlPointShapeGroup(ShapeGroupShape shape, IntPoint point, Boolean isTopLeftExtent) {
		this.shape = shape;
		this.point = point;
		this.isTopLeftExtent = isTopLeftExtent;
	}
	
	
	
	@Override
	public IntPoint getLocation() {
		return this.point;
	}

	@Override
	public void move(IntVector delta) {
		IntPoint globalCP = this.shape.toGlobalCoordinates(point);
		IntPoint movedCP = globalCP.plus(delta);
		IntPoint shapeCP = this.shape.toShapeCoordinates(movedCP);
		Extent usedExtent = this.shape.getShapeGroup().getExtent();
		Extent newExtent;
		if (isTopLeftExtent) {
			newExtent = Extent.ofLeftTopRightBottom(shapeCP.getX(), shapeCP.getY(), usedExtent.getRight(), usedExtent.getBottom());
		}
		else {
			newExtent = Extent.ofLeftTopRightBottom( usedExtent.getLeft(), usedExtent.getTop(), shapeCP.getX(), shapeCP.getY());

		}
		this.shape.getShapeGroup().setExtent(newExtent);
	}

	@Override
	public void remove() {
		
		
	}
	
	
}

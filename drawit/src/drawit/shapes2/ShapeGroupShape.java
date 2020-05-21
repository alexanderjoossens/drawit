package drawit.shapes2;


import drawit.shapes2.ControlPoint;
import drawit.IntPoint;
import drawit.IntVector;
import drawit.shapegroups2.*;

public class ShapeGroupShape implements Shape {
	private ShapeGroup shapegroup;
	
	
	
	
	private static class ControlPointShapeGroup implements ControlPoint {
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
			throw new UnsupportedOperationException("Can't use this method on object ShapeGroupShape");
			
			
		}
		
		
	}

	
	
	
	
	
	public ShapeGroupShape(drawit.shapegroups2.ShapeGroup group) {
		this.shapegroup = group;
	}
	
	public drawit.shapegroups2.ShapeGroup getShapeGroup() {
		return this.shapegroup;
	}
	
	public drawit.shapegroups2.ShapeGroup getParent() {
		return getShapeGroup().getParentGroup();
	}
	
	public boolean contains(drawit.IntPoint p) {
		return getShapeGroup().getExtent().contains(p);
	}
	
	public String getDrawingCommands() {
		return getShapeGroup().getDrawingCommands();
	}
	
	public ControlPoint[] createControlPoints() {
		Extent extent = this.getShapeGroup().getExtent();
		ControlPoint[] controlpoints = new ControlPoint[2];
		
		controlpoints[0] = new ControlPointShapeGroup(this, extent.getTopLeft(), true);
		controlpoints[1] = new ControlPointShapeGroup(this, extent.getBottomRight(), false);

		return controlpoints;
	}
	
	public drawit.IntPoint toShapeCoordinates(drawit.IntPoint p) {
		if (this.getParent()==null) {
			return p;
		}
		return getShapeGroup().toInnerCoordinates(p);
	}
	
	public drawit.IntPoint toGlobalCoordinates(drawit.IntPoint p) {
		if (this.getParent()==null) {
			return p;
		}
		return this.getParent().toGlobalCoordinates(p);
	}
}
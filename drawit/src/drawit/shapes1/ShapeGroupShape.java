package drawit.shapes1;


import drawit.shapes1.ControlPoint;
import drawit.shapegroups1.*;

public class ShapeGroupShape implements Shape {
	private ShapeGroup shapegroup;
	
	public ShapeGroupShape(drawit.shapegroups1.ShapeGroup group) {
		this.shapegroup = group;
	}
	
	public drawit.shapegroups1.ShapeGroup getShapeGroup() {
		return this.shapegroup;
	}
	
	public drawit.shapegroups1.ShapeGroup getParent() {
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
		return getShapeGroup().toGlobalCoordinates(p);
	}
}

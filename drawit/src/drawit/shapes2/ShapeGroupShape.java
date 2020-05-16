package drawit.shapes2;


import drawit.shapes2.ControlPoint;
import drawit.shapegroups2.*;

public class ShapeGroupShape implements Shape {
	private ShapeGroup shapegroup;
	
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
		
		controlpoints[0] = new ControlPointShapeGroup(this, extent.getTopLeft());
		controlpoints[1] = new ControlPointShapeGroup(this, extent.getBottomRight());

		return controlpoints;
	}
	
	public drawit.IntPoint toShapeCoordinates(drawit.IntPoint p) {
		return getShapeGroup().toInnerCoordinates(p);
	}
	
	public drawit.IntPoint toGlobalCoordinates(drawit.IntPoint p) {
		return getShapeGroup().toGlobalCoordinates(p);
	}
}
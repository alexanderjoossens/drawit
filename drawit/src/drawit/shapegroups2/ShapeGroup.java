package drawit.shapegroups2;

import drawit.RoundedPolygon;

public class ShapeGroup {
	RoundedPolygon shape;
	ShapeGroup[] subgroups;

	public ShapeGroup(RoundedPolygon shape) {
		this.shape = shape;

	}

	public ShapeGroup(ShapeGroup[] subgroups) {
		this.subgroups = subgroups;
	}
	
	public Extent getExtent() {
		Extent extent = new Extent();
		
	}
	
}

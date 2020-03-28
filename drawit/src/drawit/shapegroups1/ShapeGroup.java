package drawit.shapegroups1;
import drawit.RoundedPolygon;
import drawit.IntPoint;
import drawit.shapegroups1.Extent;

public class ShapeGroup {
	
	private RoundedPolygon shape;
	private ShapeGroup[] subgroups;
	
	/**
	 * Initializes this object to represent a leaf shape group that directly contains the given shape.
	 * @param shape
	 */
	
	public ShapeGroup(RoundedPolygon shape) {
		this.shape = shape;
	}
	
	/**
	 * Initializes this object to represent a non-leaf shape group that directly contains the given subgroups, in the given order.
	 * @param subgroups
	 */
	public ShapeGroup(ShapeGroup[] subgroups) {
		this.subgroups = subgroups;
	}

	public Extent getExtent() {
		int smallestX = 100000;
		int largestX = 0;
		int smallestY = 100000;
		int largestY = 0;
		
		IntPoint[] points = shape.getVertices();
		
		for (int i = 0; i < points.length - 1; i++) {
			if (points[i].getX() < smallestX)
				smallestX = points[i].getX();
		}
		
		for (int i = 0; i < points.length - 1; i++) {
			if (points[i].getX() > smallestX)
				largestX = points[i].getX();
		}
		
		for (int i = 0; i < points.length - 1; i++) {
			if (points[i].getY() < smallestY)
				smallestY = points[i].getY();
		}
		
		for (int i = 0; i < points.length - 1; i++) {
			if (points[i].getY() > largestY)
				largestY = points[i].getY();
		}
		
		RoundedPolygon extent = new RoundedPolygon();
		extent.setVertices(new IntPoint[] {new IntPoint(smallestX, smallestY), new IntPoint(largestX, largestY), new IntPoint(largestX, largestY), new IntPoint(smallestX, largestY)});
		return extent;
	}
	
	public Extent getOriginalExtent() {
		
	}
	
	public ShapeGroup getParentGroup() {
		return this.shapegroups;
	}
	
	public RoundedPolygon getShape() {
	
	}
	
	
}

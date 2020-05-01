package drawit.shapegroups1;

import java.util.Arrays;

import drawit.IntPoint;
import drawit.RoundedPolygon;

public class LeafShapeGroup extends ShapeGroup {

	private RoundedPolygon shape;
	
	/**
	 * Initializes this object to represent a leaf shape group that directly contains the given shape.
	 * 
	 * @throws IllegalArgumentException if {@code shape} is null
	 *    | shape == null
	 * @throws IllegalArgumentException if {@code shape} has less than three vertices
	 *    | shape.getVertices().length < 3
	 * @mutates | this
	 * @post | getShape() == shape
	 * @post | getParentGroup() == null
	 * @post | getOriginalExtent().getLeft() == Arrays.stream(shape.getVertices()).mapToInt(p -> p.getX()).min().getAsInt()
	 * @post | getOriginalExtent().getTop() == Arrays.stream(shape.getVertices()).mapToInt(p -> p.getY()).min().getAsInt()
	 * @post | getOriginalExtent().getRight() == Arrays.stream(shape.getVertices()).mapToInt(p -> p.getX()).max().getAsInt()
	 * @post | getOriginalExtent().getBottom() == Arrays.stream(shape.getVertices()).mapToInt(p -> p.getY()).max().getAsInt()
	 * @post | getExtent().equals(getOriginalExtent())
	 */
	public LeafShapeGroup(RoundedPolygon shape) {
		if (shape == null)
			throw new IllegalArgumentException("shape is null");
		if (shape.getVertices().length < 3)
			throw new IllegalArgumentException("shape has less than three vertices");
		
		this.shape = shape;
		
		IntPoint[] vertices = shape.getVertices();
		int left = Integer.MAX_VALUE;
		int top = Integer.MAX_VALUE;
		int right = Integer.MIN_VALUE;
		int bottom = Integer.MIN_VALUE;
		for (int i = 0; i < vertices.length; i++) {
			IntPoint vertex = vertices[i];
			left = Math.min(left, vertex.getX());
			right = Math.max(right, vertex.getX());
			top = Math.min(top, vertex.getY());
			bottom = Math.max(bottom, vertex.getY());
		}
		originalExtent = Extent.ofLeftTopRightBottom(left, top, right, bottom);
		currentExtent = originalExtent;
	}


	/**
	 * Returns the shape directly contained by this shape group, or null if this is
	 * a non-leaf shape group.
	 * @inspects | this
	 */
	public RoundedPolygon getShape() {
		return this.shape;
	}
	
	
}
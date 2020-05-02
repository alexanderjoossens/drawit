package drawit.shapegroups2;
import drawit.shapegroups2.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import logicalcollections.LogicalList;
import logicalcollections.LogicalSet;

import drawit.IntPoint;
import drawit.RoundedPolygon;

public class LeafShapeGroup extends ShapeGroup{

	/**
	 * @invar | (parent == null) == (nextSibling == null)
	 * @invar A non-leaf shape group has at least two subgroups
	 *    | nextSibling != this
	 * @invar | (parent == null) == (previousSibling == null)
	 * @invar | parent == null || nextSibling.parent == parent && nextSibling.previousSibling == this
	 * @invar | parent == null || previousSibling.parent == parent && previousSibling.nextSibling == this
	 * @invar | parent == null || parent.getSubgroups().contains(this)
	 */
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
	 * Returns the shape directly contained by this shape group, or {@code null} if this
	 * is a non-leaf shape group.
	 * 
	 * @immutable
	 */
	public RoundedPolygon getShape() { return shape; }
	
	
	/**
	 * Returns a textual representation of a sequence of drawing commands for drawing
	 * the shapes contained directly or indirectly by this shape group, expressed in this
	 * shape group's outer coordinate system.
	 * 
	 * For the syntax of the drawing commands, see {@code RoundedPolygon.getDrawingCommands()}.
	 * 
	 * @post | result != null
	 */
	public String getDrawingCommands() {
		StringBuilder builder = new StringBuilder();
		
		double xscale = currentExtent.getWidth() * 1.0 / originalExtent.getWidth();
		double yscale = currentExtent.getHeight() * 1.0 / originalExtent.getHeight();
		builder.append("pushTranslate " + currentExtent.getLeft() + " " + currentExtent.getTop() + "\n");
		builder.append("pushScale " + xscale + " " + yscale + "\n");
		builder.append("pushTranslate " + -originalExtent.getLeft() + " " + -originalExtent.getTop() + "\n");
		

		builder.append(shape.getDrawingCommands());

		
		builder.append("popTransform popTransform popTransform\n");
		return builder.toString();
	}
}

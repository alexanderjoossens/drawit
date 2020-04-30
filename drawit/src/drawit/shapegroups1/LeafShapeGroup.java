package drawit.shapegroups1;

import java.util.Arrays;

import drawit.IntPoint;
import drawit.RoundedPolygon;

public class LeafShapeGroup extends ShapeGroup {

	private RoundedPolygon shape;
	
	/**
	 * Initializes this object to represent a leaf shape group that directly
	 * contains the given shape.
	 * 
	 * @param shape
	 * The shape of the new ShapeGroup
	 * @throws IllegalArgumentException if shape is null.
	 *    | shape== null
	 * @throws Vertices are not null
	 * | Arrays.stream(shape.getVertices()).allMatch(v -> v != null)
	 * @mutates | this
	 * @post This object's shape equals the given shape.
     *    | getShape() == shape
     * @inspects | shape
	 */
	public LeafShapeGroup(RoundedPolygon shape) {
		if (shape == null) {
			throw new IllegalArgumentException("Shape is null");
		}
		
		for (IntPoint vertex : shape.getVertices()) {
			if (vertex==null) {
				throw new IllegalArgumentException("One of the vertices is null");
			}
		}
		int maxX = 0;
		int maxY = 0;
		int minX = 0;
		int minY = 0;

		IntPoint[] points = shape.getVertices();
		for (int i = 0; i < points.length; i++) {
			int xCoord = points[i].getX();
			int yCoord = points[i].getY();
			if (i == 0) {
				maxX = xCoord;
				minX = xCoord;
				maxY = yCoord;
				minY = yCoord;
			} else {
				maxX = Math.max(maxX, xCoord);
				maxY = Math.max(maxY, yCoord);
				minX = Math.min(minX, xCoord);
				minY = Math.min(minY, yCoord);
			}
		}

		Extent extent = Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY);
		this.originalExtent = extent;
		this.ownExtent = extent;
		this.shape = shape;

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
package drawit.shapegroups2;

import drawit.IntPoint;
import drawit.RoundedPolygon;

/**
 * @immutable
 */
		
public class Extent {

	private int left;
	private int top;
	private int width;
	private int height;
	
	/**
	 * Returns whether this extent, considered as a closed set of points 
	 * (i.e. including its edges and its vertices), contains the given point.
	 * @param point
	 * The point to check if it is in the extent.
	 * @throws IllegalArgumentException if point is null.
	 *   | point == null
	 * @inspects | this
	 * @throws IllegalArgumentException if the point is null. | point == null
	 */
	public boolean contains(IntPoint point) {
		if (point == null) {
			throw new IllegalArgumentException("Point is null");
		}
		IntPoint bottomleft = new IntPoint(this.getLeft(), this.getBottom());
		IntPoint topright = new IntPoint(this.getRight(), this.getTop());
		
		IntPoint[] vertices = new IntPoint[4];
		vertices[0] = bottomleft;
		vertices[1] = this.getBottomRight();
		vertices[2] = topright;
		vertices[3] = this.getTopLeft();
		
		RoundedPolygon polygon = new RoundedPolygon();
		polygon.setVertices(vertices);
		
		return polygon.contains(point);
	}

	/**
	 * Returns the Y coordinate of the edge parallel to the X axis with the largest
	 * Y coordinate.
	 * @inspects | this
	 */
	public int getBottom() {
		return this.getTop() + this.getHeight();
	}

	public IntPoint getBottomRight() {
		IntPoint bottomright = new IntPoint(this.getRight(), this.getBottom());
		return bottomright;
	}

	/**
	 * Returns the distance between the edges that are parallel to the X axis.
	 * @inspects | this
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Returns the X coordinate of the edge parallel to the Y axis with the smallest
	 * X coordinate.
	 * @inspects | this
	 */
	public int getLeft() {
		return this.left;
	}

	/**
	 * Returns the X coordinate of the edge parallel to the Y axis with the largest
	 * X coordinate.
	 * @inspects | this
	 */
	public int getRight() {
		return this.left + this.width;
	}

	/**
	 * Returns the Y coordinate of the edge parallel to the X axis with the smallest
	 * Y coordinate.
	 * @inspects | this
	 */
	public int getTop() {
		return this.top;
	}

	public IntPoint getTopLeft() {
		IntPoint topleft = new IntPoint(this.getLeft(), this.getTop());
		return topleft;
	}

	/**
	 * Returns the distance between the edges that are parallel to the Y axis.
	 * @inspects | this
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * @creates | result
	 * @param left
	 * The X coordinate of the left side of the extent
	 * @param top
	 * The Y coordinate of the top side of the extent
	 * @param right
	 * The X coordinate of the right side of the extent
	 * @param bottom 
	 * The Y cootdinate of the bottom side of the extent
	 * @post | result.getLeft() == left
	 * @post | result.getTop() == top
	 * @post | result.getRight() == right
	 * @post | result.getBottom() == bottom
	 * @post | result != null
	 */
	public static Extent ofLeftTopRightBottom(int left, int top, int right, int bottom) {
		Extent extent = new Extent();
		extent.left = left;
		extent.top = top;
		extent.width = right - left;
		extent.height = bottom - top;
		return extent;
	}

	/**
	 * @creates | result
	 * @param left 
	 * The X coordinate of the left side of the extent
	 * @param top 
	 * The Y coordinate of the top side of the extent
	 * @param width 
	 * The width of the extent
	 * @param height 
	 * The height of the extent
	 * @post | result.getLeft() == left
	 * @post | result.getTop() == top
	 * @post | result.getWidth() == width
	 * @post | result.getHeight() == height
	 * @post | result != null
	 * @throws IllegalArgumentException if the width is negative | width < 0
	 * @throws IllegalArgumentException if the height is negative | height < 0
	 */
	public static Extent ofLeftTopWidthHeight(int left, int top, int width, int height) {
		Extent extent = new Extent();
		extent.left = left;
		extent.top = top;
		extent.width = width;
		extent.height = height;
		return extent;
	}
	/**
	 * Returns an object that has the given bottom coordinate and the same left,
	 * top, and right coordinate as this object.
	 * 
	 * @param newBottom
	 * @creates | result
	 * @inspects | this
	 * @post | result.getBottom() == newBottom
	 * @post | result != null
	 */
	public Extent withBottom(int newBottom) {
		Extent newExtent = Extent.ofLeftTopRightBottom(this.getLeft(), this.getTop(), this.getRight(), newBottom);
		return newExtent;
	}

	/**
	 * Returns an object that has the given right coordinate and the same left, top,
	 * and bottom coordinate as this object.
	 * 
	 * @param newRight
	 * @creates | result
	 * @inspects | this
	 * @post | result.getRight() == newRight
	 * @post | result != null
	 */
	public Extent withRight(int newRight) {
		Extent newExtent = Extent.ofLeftTopRightBottom(this.getLeft(), this.getTop(), newRight, this.getBottom());
		return newExtent;
	}

	/**
	 * Returns an object that has the given left coordinate and the same right, top,
	 * and bottom coordinate as this object.
	 * 
	 * @param newLeft
	 * @creates | result
	 * @inspects | this
	 * @post | result.getLeft() == newLeft
	 * @post | result != null
	 */
	public Extent withLeft(int newLeft) {
		Extent newExtent = Extent.ofLeftTopRightBottom(newLeft, this.getTop(), this.getRight(), this.getBottom());
		return newExtent;
	}

	/**
	 * Returns an object that has the given top coordinate and the same left, right,
	 * and bottom coordinate as this object.
	 * 
	 * @param newTop
	 * @creates | result
	 * @inspects | this
	 * @post | result.getTop() == newTop
	 * @post | result != null
	 */
	public Extent withTop(int newTop) {
		Extent newExtent = Extent.ofLeftTopRightBottom(this.getLeft(), newTop, this.getRight(), this.getBottom());
		return newExtent;
	}

	/**
	 * Returns an object that has the given height and the same left, top, and right
	 * coordinate as this object.
	 * 
	 * @param NewHeight
	 * @creates | result
	 * @inspects | this
	 * @post | result.getHeight() == newHeight
	 * @post | result != null
	 * @throws IllegalArgumentException if the new height is negative | newHeight < 0
	 */
	public Extent withHeight(int newHeight) {
		if (newHeight < 0) {
			throw new IllegalArgumentException("The given height is negative");
		}
		Extent newExtent = Extent.ofLeftTopWidthHeight(this.getLeft(), this.getTop(), this.getWidth(), newHeight);
		return newExtent;
	}

	/**
	 * Returns an object that has the given width and the same left, top, and bottom
	 * coordinate as this object.
	 * 
	 * @param newWidth
	 * @creates | result
	 * @inspects | this
	 * @post | result.getWidth() == newWidth
	 * @post | result != null
	 * @throws IllegalArgumentException if the new width is negative | newWidth < 0
	 */
	public Extent withWidth(int newWidth) {
		if (newWidth < 0) {
			throw new IllegalArgumentException("The given width is negative");
		}
		Extent newExtent = Extent.ofLeftTopWidthHeight(this.getLeft(), this.getTop(), newWidth, this.getHeight());
		return newExtent;
	}

}

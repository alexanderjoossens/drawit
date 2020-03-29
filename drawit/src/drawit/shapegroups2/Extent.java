package drawit.shapegroups2;

import drawit.IntPoint;
import drawit.RoundedPolygon;

public class Extent {

	private int left;
	private int top;
	private int width;
	private int height;

	public static Extent ofLeftTopRightBottom(int left, int top, int right, int bottom) {
		Extent extent = new Extent();
		extent.left = left;
		extent.top = top;
		extent.width = right - left;
		extent.height = bottom - top;
		return extent;
	}

	public static Extent ofLeftTopWidthHeight(int left, int top, int width, int height) {
		Extent extent = new Extent();
		extent.left = left;
		extent.top = top;
		extent.width = width;
		extent.height = height;
		return extent;
	}

	/**
	 * Returns whether this extent, considered as a closed set of points (i.e.
	 * including its edges and its vertices), contains the given point.
	 * 
	 * @param point
	 * @return
	 */
	public boolean contains(IntPoint point) {
		RoundedPolygon polygon = new RoundedPolygon();
		IntPoint[] vertices = new IntPoint[] { new IntPoint(this.left, this.top),
				new IntPoint(this.left + this.width, this.top),
				new IntPoint(this.left + this.width, this.top + this.height),
				new IntPoint(this.left, this.top + this.height) };
		polygon.setVertices(vertices);
		return polygon.contains(point);
	}

	/**
	 * Returns the Y coordinate of the edge parallel to the X axis with the largest
	 * Y coordinate.
	 * 
	 * @return
	 */
	public int getBottom() {
		return this.getTop() + this.getHeight();
	}

	public IntPoint getBottomRight() {
		int right = this.getRight();
		int bottom = this.getBottom();
		IntPoint bottomright = new IntPoint(right, bottom);
		return bottomright;
	}

	/**
	 * Returns the distance between the edges that are parallel to the X axis.
	 * 
	 * @return
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Returns the X coordinate of the edge parallel to the Y axis with the smallest
	 * X coordinate.
	 * 
	 * @return
	 */
	public int getLeft() {
		return this.left;
	}

	/**
	 * Returns the X coordinate of the edge parallel to the Y axis with the largest
	 * X coordinate.
	 * 
	 * @return
	 */
	public int getRight() {
		return this.left + this.width;
	}

	/**
	 * Returns the Y coordinate of the edge parallel to the X axis with the smallest
	 * Y coordinate.
	 * 
	 * @return
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
	 * 
	 * @return
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Returns an object that has the given bottom coordinate and the same left,
	 * top, and right coordinate as this object.
	 * 
	 * @param newBottom
	 * @return
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
	 * @return
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
	 * @return
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
	 * @return
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
	 * @return
	 */
	public Extent withHeight(int newHeight) {
		Extent newExtent = Extent.ofLeftTopWidthHeight(this.getLeft(), this.getTop(), this.getWidth(), newHeight);
		return newExtent;
	}

	/**
	 * Returns an object that has the given width and the same left, top, and bottom
	 * coordinate as this object.
	 * 
	 * @param newWidth
	 * @return
	 */
	public Extent withWidth(int newWidth) {
		Extent newExtent = Extent.ofLeftTopWidthHeight(this.getLeft(), this.getTop(), newWidth, this.getHeight());
		return newExtent;
	}

}

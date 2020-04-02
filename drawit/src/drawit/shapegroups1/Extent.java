package drawit.shapegroups1;

import drawit.IntPoint;

public class Extent {
	
	private int left;
	private int top;
	private int right;
	private int bottom;

	/**
	 * Returns whether this extent, considered as a closed set of points 
	 * (i.e. including its edges and its vertices), contains the given point.
	 * @param point
	 * @return
	 */
	public boolean contains(IntPoint point) {
		return this.contains(point);
	}
	
	/**
	 * Returns the Y coordinate of the edge parallel to the X axis with the largest Y coordinate.
	 * @return
	 */
	public int getBottom() {
		return this.bottom;
	}
	
	public IntPoint getBottomRight() {
		IntPoint bottomright = new IntPoint(this.getRight(), this.getBottom());
		return bottomright;
	}
	
	/**
	 * Returns the distance between the edges that are parallel to the X axis.
	 * @return
	 */
	public int getHeight() {
		return this.getBottom()-this.getTop();
	}
	
	/**
	 * Returns the X coordinate of the edge parallel to the Y axis with the smallest X coordinate.
	 * @return
	 */
	public int getLeft() {
		return this.left;
	}
	
	/**
	 * Returns the X coordinate of the edge parallel to the Y axis with the largest X coordinate.
	 * @return
	 */
	public int getRight() {
		return this.right;
	}
	
	/**
	 * Returns the Y coordinate of the edge parallel to the X axis with the smallest Y coordinate.
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
	 * @return
	 */
	public int getWidth() {
		return this.getRight()-this.getLeft();
	}
	
	public static Extent ofLeftTopRightBottom(int left, int top, int right, int bottom) {
		Extent extent = new Extent();
		extent.left = left;
		extent.top = top;
		extent.right = right;
		extent.bottom = bottom;
		return extent;
	}
	
	public static Extent ofLeftTopWidthHeight(int left, int top, int width, int height) {
		Extent extent = new Extent();
		extent.left = left;
		extent.top = top;
		extent.right = left+width;
		extent.bottom = top+height;
		return extent;
	}
	
	/**
	 * Returns an object that has the given bottom coordinate and the same left, top, and right coordinate as this object.
	 * @param newBottom
	 * @return
	 */
	public Extent withBottom(int newBottom) {
		Extent newExtent = Extent.ofLeftTopRightBottom(this.getLeft(), this.getTop(), this.getRight(), newBottom);
		return newExtent;
	}
	
	/**
	 * Returns an object that has the given height and the same left, top, and right coordinate as this object.
	 * @param NewHeight
	 * @return
	 */
	public Extent withHeight(int newHeight) {
		Extent newExtent = Extent.ofLeftTopWidthHeight(this.getLeft(), this.getTop(), this.getWidth(), newHeight);
		return newExtent;
	}
	
	/**
	 * Returns an object that has the given left coordinate and the same right, top, and bottom coordinate as this object.
	 * @param newLeft
	 * @return
	 */
	public Extent withLeft(int newLeft) {
		Extent newExtent = Extent.ofLeftTopRightBottom(newLeft, this.getTop(), this.getRight(), this.getBottom());
		return newExtent;
	}
	
	/**
	 * Returns an object that has the given right coordinate and the same left, top, and bottom coordinate as this object.
	 * @param newRight
	 * @return
	 */
	public Extent withRight(int newRight) {
		Extent newExtent = Extent.ofLeftTopRightBottom(this.getLeft(), this.getTop(), newRight, this.getBottom());
		return newExtent;
	}
	
	/**
	 * Returns an object that has the given top coordinate and the same left, right, and bottom coordinate as this object.
	 * @param newTop
	 * @return
	 */
	public Extent withTop(int newTop) {
		Extent newExtent = Extent.ofLeftTopRightBottom(this.getLeft(), newTop, this.getRight(), this.getBottom());
		return newExtent;
	}
	
	/**
	 * Returns an object that has the given width and the same left, top, and bottom coordinate as this object.
	 * @param newWidth
	 * @return
	 */
	public Extent withWidth(int newWidth) {
		Extent newExtent = Extent.ofLeftTopWidthHeight(this.getLeft(), this.getTop(), newWidth, this.getHeight());
		return newExtent;
	}
	
	public void setExtent(int left, int right, int top, int bottom) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}
	
}

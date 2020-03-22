package drawit.shapegroups1;

import drawit.IntPoint;

public class Extent {
	
	private final int left;
	private final int top;
	private final int right;
	private final int bottom;

	/**
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public Extent(int left, int top, int right, int bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	/**
	 * Returns whether this extent, considered as a closed set of points 
	 * (i.e. including its edges and its vertices), contains the given point.
	 * @param point
	 * @return
	 */
	public boolean contains(IntPoint point) {
		return contains(point);
	}
	
	/**
	 * Returns the Y coordinate of the edge parallel to the X axis with the largest Y coordinate.
	 * @return
	 */
	public int getBottom() {
		return this.bottom;
	}
	
	public IntPoint getBottomRight() {
		IntPoint bottomright = new IntPoint(right, bottom);
		return bottomright;
	}
	
	/**
	 * Returns the distance between the edges that are parallel to the X axis.
	 * @return
	 */
	public int getHeight() {
		return this.top-this.bottom;
	}
	
	/**
	 * Returns the X coordinate of the edge parallel to the Y axis with the smallest X coordinate.
	 * @return
	 */
	public int getLeft() {
		return left;
	}
	
	/**
	 * Returns the X coordinate of the edge parallel to the Y axis with the largest X coordinate.
	 * @return
	 */
	public int getRight() {
		return right;
	}
	
	/**
	 * Returns the Y coordinate of the edge parallel to the X axis with the smallest Y coordinate.
	 * @return
	 */
	public int getTop() {
		return top;
	}
	
	public IntPoint getTopLeft() {
		IntPoint topleft = new IntPoint(left, top);
		return topleft;
	}
	
	/**
	 * Returns the distance between the edges that are parallel to the Y axis.
	 * @return
	 */
	public int getWidth() {
		return this.right- this.left;
	}
	
	//ik denk dat ge hier een nieuwe rechthoek moet maken? er staat geen documentatie in de html
	public static Extent ofLeftTopRightBottom(int left, int top, int width, int bottom) {
		int right = left+width;
		Extent shape = new Extent(left, top, right, bottom);
		return shape;
	}
	
	//same?
	public static Extent ofLeftTopWidthHeight(int left, int top, int width, int bottom) {
		int right = left+width;
		Extent shape = new Extent(left, top, right, bottom);
		return shape;
	}
	
	public Extent withBottom(int newBottom) {
		
	}
	
	public Extent withHeight(int NewHeight) {
		
	}
	
	public Extent withLeft(int newLeft) {
		
	}
	
	public Extent withRight(int newRight) {
		
	}
	
	public Extent withTop(int newTop) {
		
	}
	
	public Extent withWidth(int newWidth) {
		
	}
	
}

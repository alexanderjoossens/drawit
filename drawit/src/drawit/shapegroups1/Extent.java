package drawit.shapegroups1;
package drawit

import drawit.IntPoint;//waarom kan ik dit niet importen?? ik denk dat de drawit folder een ander type folder/package is want op github staan die methods ook niet in een mapje maar gwn onder elkaar hier https://github.com/stefanlommaert/drawit/tree/master/drawit/src/drawit

public class Extent {
	
	private final int X1;
	private final int Y1;
	private final int X2;
	private final int Y2;
	private final int X3;
	private final int Y3;
	private final int X4;
	private final int Y4;
	
	/**
	 * 
	 * @param X1 The X coordinate of the left under point.
	 * @param Y1 The Y coordinate of the left under point.
	 * @param X2 The X coordinate of the right under point.
	 * @param Y2 The Y coordinate of the right under point.
	 * @param X3 The X coordinate of the top right point.
	 * @param Y3 The Y coordinate of the top right point.
	 * @param X4 The X coordinate of the top left point.
	 * @param Y4 The Y coordinate of the top left point.
	 */
	public Extent(int X1, int Y1, int X2, int Y2, int X3, int Y3, int X4, int Y4) {
		this.X1 = X1;
		this.Y1 = Y1;
		this.X2 = X2;
		this.Y2 = Y2;
		this.X3 = X3;
		this.Y3 = Y3;
		this.X4 = X4;
		this.Y4 = Y4;
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
		return Y3;
	}
	
	public IntPoint getBottomRIght() {
		IntPoint[] newArray = new IntPoint[4];
		return newArray;
	}
	
	/**
	 * Returns the distance between the edges that are parallel to the X axis.
	 * @return
	 */
	public int getHeight() {
		return Y4-Y1;
	}
	
	/**
	 * Returns the X coordinate of the edge parallel to the Y axis with the smallest X coordinate.
	 * @return
	 */
	public int getLeft() {
		return X1;
	}
	
	/**
	 * Returns the X coordinate of the edge parallel to the Y axis with the largest X coordinate.
	 * @return
	 */
	public int getRight() {
		return X2;
	}
	
	/**
	 * Returns the Y coordinate of the edge parallel to the X axis with the smallest Y coordinate.
	 * @return
	 */
	public int getTop() {
		return Y1;
	}
	
	public IntPoint getTopLeft() {
		IntPoint[] newArray = new IntPoint[4];
		return newArray;
	}
	
	/**
	 * Returns the distance between the edges that are parallel to the Y axis.
	 * @return
	 */
	public int getWidth() {
		return X2-X1;
	}
	
	public static Extent ofLeftTopRightBottom(int left, int top, int width, int bottom) {
		
	}
	
	
	public static Extent ofLeftTopWidthHeight(int left, int top, int width, int bottom) {
		
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

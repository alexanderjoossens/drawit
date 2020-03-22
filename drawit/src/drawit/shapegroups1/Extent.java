package drawit.shapegroups1;
package drawit //waarom kan ik dit niet importen?? ik denk dat de drawit folder een ander type folder/package is want op github staan die methods ook niet in een mapje maar gwn onder elkaar hier https://github.com/stefanlommaert/drawit/tree/master/drawit/src/drawit

public class Extent {
	
	/**
	 * 
	 * @param X1
	 * @param Y1
	 * @param X2
	 * @param Y2
	 * @param X3
	 * @param Y3
	 * @param X4
	 * @param Y4
	 */
	public Extent(double X1, double Y1, double X2, double Y2, double X3, double Y3, double X4, double Y4) {
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
		
	}
	
	public IntPoint getBottomRIght() {
		
	}
	
	public int getHeight() {
		
	}
	
	public int getLeft() {
		
	}
	
	public int getRight() {
		
	}
	
	public int getTop() {
		
	}
	
	public IntPoint getTopLeft() {
		
	}
	
	public int getWidth() {
		
	}
	
}

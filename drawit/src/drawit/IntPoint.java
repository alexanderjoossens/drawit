package drawit;

/**
 * An immutable abstraction for a point in the two-dimensional plane with int coordinates.
 *@author Alexander and Stefan
 *
 */
public class IntPoint {
	private int x;
	private int y;

	public IntPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns this point's X coordinate.
	 * @return
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Returns this point's Y coordinate.
	 * @return
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Returns true if this point has the same coordinates as the given point; returns false otherwise.
	 * @param other
	 * @return
	 */
	boolean equals(IntPoint other) {
		if (this.x == other.getX() && this.y == other.getY()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns a DoublePoint object that represents the same 2D point represented by this IntPoint object.
	 * @return
	 */
	DoublePoint asDoublePoint() {
		DoublePoint point = new DoublePoint(this.x, this.y);
		return point;
	}

	/**
	 * Returns true if this point is on open line segment bc.
	 * @param a
	 * @param b
	 * @return
	 */
	boolean isOnLineSegment(IntPoint a, IntPoint b) {
		double rico = ((double) (b.getY() - a.getY())) / ((double) (b.getX() - a.getX()));
		double function = rico * (this.x -b.getX()) + b.getY();
		if (function == this.getY()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Stefan kunt gij hier iets van documentatie schrijven?
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	static boolean isCounterClockWise(IntPoint a, IntPoint b, IntPoint c) {
		return (c.getY()-a.getY()) * (b.getX()-a.getX()) > (b.getY()-a.getY()) * (c.getX()-a.getX());
		
	}
	
	/**
	 * Returns true iff the open line segment ab intersects the open line segment cd.
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @return
	 */
	public static boolean lineSegmentsIntersect(IntPoint a, IntPoint b, IntPoint c, IntPoint d) {
		if (a.equals(d) || b.equals(c)) {
			return false;
		}
		return isCounterClockWise(a,c,d) != isCounterClockWise(b,c,d) && isCounterClockWise(a,b,c) != isCounterClockWise(a,b,d);
	}
	
	/**
	 * Returns an IntVector object representing the displacement from other to this.
	 * @param other
	 * @return
	 */
	public IntVector minus(IntPoint other) {
		int xCoord = this.x - other.getX();
		int yCoord = this.y - other.getY();
		IntVector vector = new IntVector(xCoord, yCoord);
		return vector;	
	}
	
	/**
	 * Returns an IntPoint object representing the point obtained by displacing this point by the given vector.
	 * @param other
	 * @return
	 */
	public IntPoint plus(IntVector other) {
		int xCoord = this.x + other.getX();
		int yCoord = this.y + other.getY();
		IntPoint point = new IntPoint(xCoord, yCoord);
		return point;
	}
	
	public boolean exitPathIntersect(IntPoint a, IntPoint b) {
		return true;
	}


}

package drawit;//test

/**
 * An immutable abstraction for a point in the two-dimensional plane with int
 * coordinates.
 * 
 * @author Alexander and Stefan
 * @immutable
 */
public class IntPoint {
	private final int x;
	private final int y;

	/**
	 * @mutates this
	 * @post | getX() == x
	 * @post | getY() == y
	 */
	public IntPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns this point's X coordinate.
	 * @return | this.x
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Returns this point's Y coordinate.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Returns true if this point has the same coordinates as the given point;
	 * returns false otherwise.
	 */
	public boolean equals(IntPoint other) {
		if (this.x == other.getX() && this.y == other.getY()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns a DoublePoint object that represents the same 2D point represented by
	 * this IntPoint object.
	 * @post a new object of type DoublePoint was made.
	 * 		| point == DoublePoint
	 */
	public DoublePoint asDoublePoint() {
		DoublePoint point = new DoublePoint(this.x, this.y);
		return point;
	}

	/**
	 * Returns true iff this point is on open line segment bc. An open line segment does not include its endpoints.
	 */
	public boolean isOnLineSegment(IntPoint b, IntPoint c) {
		if (b.getX() == c.getX()) {
			if (((b.getY() <= this.getY() && this.getY() <= c.getY())
					|| (c.getY() <= this.getY() && this.getY() <= b.getY())) && this.getX() == b.getX()) {
				return true;
			}
			return false;
		}
		double rico = ((double) (c.getY() - b.getY())) / ((double) (c.getX() - b.getX()));
		double function = rico * (this.x - c.getX()) + c.getY();
		if (function == this.getY()) {
			if ((b.getY() <= this.getY() && this.getY() <= c.getY())
					|| (c.getY() <= this.getY() && this.getY() <= b.getY())) {
				if ((b.getX() <= this.getX() && this.getX() <= c.getX())
						|| (c.getX() <= this.getX() && this.getX() <= b.getX())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks whether the given points follow in a counterclockwise order if we
	 * connect the points together.
	 */
	private static boolean isCounterClockWise(IntPoint a, IntPoint b, IntPoint c) {
		return (c.getY() - a.getY()) * (b.getX() - a.getX()) > (b.getY() - a.getY()) * (c.getX() - a.getX());

	}

	/** No formal
	 * Returns true iff the open line segment ab intersects the open line segment cd.
	 */
	public static boolean lineSegmentsIntersect(IntPoint a, IntPoint b, IntPoint c, IntPoint d) {
		if (a.equals(d) || b.equals(c)) {
			return false;
		}
		return isCounterClockWise(a, c, d) != isCounterClockWise(b, c, d)
				&& isCounterClockWise(a, b, c) != isCounterClockWise(a, b, d);
	}

	/**
	 * Returns an IntVector object representing the displacement from other to this.
	 * @pre other does not equal null.
	 * 	| other != null
	 * @post the resulting vector is the difference of the given 2 vectors.
	 * 	| vector.getX() == this.getX() - other.getX()
	 * 	| vector.getY() == this.y - other.getY()
	 */
	public IntVector minus(IntPoint other) {
		
		int xCoord = this.x - other.getX();
		int yCoord = this.y - other.getY();
		IntVector vector = new IntVector(xCoord, yCoord);
		return vector;
	}

	/**
	 * Returns an IntPoint object representing the point obtained by displacing this
	 * point by the given vector.
	 *  @pre other does not equal null.
	 * 	| other != null
	 * @post the resulting vector is the sum of the given 2 vectors.
	 * 	| vector.getX() == this.getX() + other.getX()
	 * 	| vector.getY() == this.y + other.getY()
	 * @throw IllegalArgumentException if other equals null.
	 * 	| !(other = null)
	 */
	public IntPoint plus(IntVector other) {
		int xCoord = this.x + other.getX();
		int yCoord = this.y + other.getY();
		IntPoint point = new IntPoint(xCoord, yCoord);
		return point;
	}
	
	/**
	 * Returns true if the exitpath of this point intersects with the open linesegment defined by connecting point a and b.
	 * The exitpath is defined by the line from the given point in the positive X direction.
	 * Returns false otherwise.
	 * @pre IntPoint a and IntPoint b do not equal null.
	 * 	| a != null && b != null
	 * @throw ??
	 */
	public boolean exitPathIntersect(IntPoint a, IntPoint b) {
		if (a.getX() == b.getX()) {
			if (((a.getY() < this.getY() && this.getY() < b.getY())
					|| (b.getY() < this.getY() && this.getY() < a.getY())) && this.getX() < a.getX()) {
				return true;
			}
			return false;

		}
		double rico = ((double) (b.getY() - a.getY())) / ((double) (b.getX() - a.getX()));
		double function = ((double) (this.getY() - b.getY()) / rico) + b.getX();
		if ((a.getX() < function && function < b.getX()) || (b.getX() < function && function < a.getX())) {
			return true;
		} else {
			return false;
		}
	}
}

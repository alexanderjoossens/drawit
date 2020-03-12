package drawit;

/**
 * An immutable abstraction for a point in the two-dimensional plane with int
 * coordinates.
 * 
 * @author Alexander and Stefan
 * @immutable
 */
public class IntPoint {

	/**
	 * @param x The X coordinate of the point.
	 * @param y The Y coordinate of the point
	 * @pre Argument {@code x} is not {@code null}. | x != null
	 * @pre Argument {@code y} is not {@code null}. | y != null
	 */
	private final int x;
	private final int y;

	/**
	 * @param x The X coordinate of the point.
	 * @param y The Y coordinate of the point
	 * @pre Argument {@code x} is not {@code null}. | x != null
	 * @pre Argument {@code y} is not {@code null}. | y != null
	 * @mutates | this
	 * @post the new X coordinate of the point is equal to the given X coordinate. |
	 *       getX() == x
	 * @post the new Y coordinate of the point is equal to the given Y coordinate. |
	 *       getY() == y
	 */
	public IntPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns this point's X coordinate.
	 * 
	 * @return | this.x
	 * @creates result
	 * @post The result is not {@code null} | result != null
	 * @inspects | this
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Returns this point's Y coordinate.
	 * 
	 * @return | this.y
	 * @creates result
	 * @post The result is not {@code null} | result != null
	 * @inspects | this
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Returns true if this point has the same coordinates as the given point;
	 * returns false otherwise.
	 * 
	 * @param other The other IntPoint.
	 * @pre Argument {@code other} is not {@code null}. | other != null
	 * @inspects | other
	 * @return True if and only if this point has the same coordinates as the given
	 *         point. result == ( (this.x == other.getX()) && (this.y ==
	 *         other.getY()) )
	 * @creates result
	 * @post The result is not {@code null} | result != null
	 */
	public boolean equals(IntPoint other) {
		boolean result = ((this.getX() == other.getX()) && (this.getY() == other.getY()));
		return result;
	}

	/**
	 * Returns a DoublePoint object that represents the same 2D point represented by
	 * this IntPoint object.
	 * 
	 * @post a new object of type DoublePoint was made. | point == DoublePoint
	 * @inspects | this
	 */
	public DoublePoint asDoublePoint() {
		DoublePoint point = new DoublePoint(this.getX(), this.getY());
		return point;
	}

	/**
	 * Returns true iff this point is on open line segment bc. An open line segment
	 * does not include its endpoints. Returns false otherwise.
	 * 
	 * @param b The IntPoint b.
	 * @param c The IntPoint c.
	 * @pre Argument {@code b} is not {@code null}. | b != null
	 * @pre Argument {@code c} is not {@code null}. | c != null
	 * @return True if and only if this point is on the open line segment bc.
	 * @inspects | b
	 * @inspects | c
	 */
	public boolean isOnLineSegment(IntPoint b, IntPoint c) {
		IntVector BA = new IntVector(this.getX() - b.getX(), this.getY() - b.getY());
		IntVector BC = new IntVector(c.getX() - b.getX(), c.getY() - b.getY());
		
		if (BA.isCollinearWith(BC)) {
			long dotproductVectors = BA.dotProduct(BC);
			if (0 < dotproductVectors && dotproductVectors < BC.dotProduct(BC)) {
				return true;
			}
			
		}
		
		return false;
	}
	
	/**
	 * Checks whether the given points follow in a counterclockwise order if we
	 * connect the points together.
	 * 
	 * @param a The IntPoint a.
	 * @param b The IntPoint b.
	 * @param c The IntPoint c.
	 * @pre Argument {@code a} is not {@code null}. | a != null
	 * @pre Argument {@code b} is not {@code null}. | b != null
	 * @pre Argument {@code c} is not {@code null}. | c != null
	 * @post | result == (((c.getY() - a.getY()) * (b.getX() - a.getX())) > ((b.getY() - a.getY()) * (c.getX() - a.getX())))
	 */
	private static boolean isCounterClockWise(IntPoint a, IntPoint b, IntPoint c) {
		return (c.getY() - a.getY()) * (b.getX() - a.getX()) > (b.getY() - a.getY()) * (c.getX() - a.getX());

	}

	/**
	 * Returns true if the open line segment ab intersects the open line segment cd.
	 * 
	 * @param a The IntPoint a.
	 * @param b The IntPoint b.
	 * @param c The IntPoint c.
	 * @param d The IntPoint d.
	 * @pre Argument {@code a} is not {@code null}. | a != null
	 * @pre Argument {@code b} is not {@code null}. | b != null
	 * @pre Argument {@code c} is not {@code null}. | c != null
	 * @pre Argument {@code d} is not {@code null}. | d != null
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
	 * @pre Argument {@code other} is not {@code null}. 
	 * 		| other != null
	 * @post the resulting vector is the difference of the given 2 vectors. 
	 * 		 | result.getX() == (this.getX() - other.getX()) && result.getY() == (this.getY() - other.getY())
	 * @inspects | other
	 * @creates result
	 * @post The result is not {@code null} | result != null
	 */
	public IntVector minus(IntPoint other) {

		int xCoord = this.getX() - other.getX();
		int yCoord = this.getY() - other.getY();
		IntVector vector = new IntVector(xCoord, yCoord);
		return vector;
	}

	/**
	 * Returns an IntPoint object representing the point obtained by displacing this
	 * point by the given vector.
	 * 
	 * @pre Argument {@code other} is not {@code null}. 
	 * 		| other != null
	 * @inspects | other
	 * @post the resulting point is the point obtained by displacing this point by
	 *       the given vector.
	 *       | result.getX() == this.getX() + other.getX() && result.getY() == this.getY() + other.getY()
	 * @creates result
	 * @post The result is not {@code null} | result != null
	 */
	public IntPoint plus(IntVector other) {
		int xCoord = this.getX() + other.getX();
		int yCoord = this.getY() + other.getY();
		IntPoint point = new IntPoint(xCoord, yCoord);
		return point;
	}

	/**
	 * Returns true if the exitpath of this point intersects with the open
	 * linesegment defined by connecting point a and b. The exitpath is defined by
	 * the line from the given point in the positive X direction. Returns false
	 * otherwise.
	 * 
	 * @param a The IntPoint a.
	 * @param b The IntPoint b.
	 * @param d The IntPoint d.
	 * @pre Argument {@code a} is not {@code null}. | a != null
	 * @pre Argument {@code b} is not {@code null}. | b != null
	 * @return True if and only if the exitpath of this point intersects with the
	 *         open linesegment defined by connecting point a and b.
	 * @creates result
	 * @post The result is not {@code null} | result != null
	 * @inspects | a
	 * @inspects | b
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
		double function = ((double) (this.getY() - b.getY()) / rico) + (double) b.getX();

		if (((a.getX() < function && function < b.getX()) || (b.getX() < function && function < a.getX()))
				&& this.getY() != a.getY() && this.getY() != b.getY() && this.getX() < function) {
			return true;
		} else {
			return false;
		}
	}
}

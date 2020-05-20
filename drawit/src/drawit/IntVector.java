package drawit;

/**
 * An instance of this class represents a displacement in the two-dimensional plane.
 * 
 * @immutable
 */
public class IntVector {
	
	private final int x;
	private final int y;
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	/**
	 * @mutates | this
	 * @post | getX() == x
	 * @post | getY() == y
	 */
	public IntVector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/** Returns {@code true} if this vector has the same coordinates as the given vector; returns {@code false} otherwise.
	 * 
	 * @pre | other != null
	 * @post | result == (this.getX() == other.getX() && this.getY() == other.getY()) 
	 */
	public boolean equals(IntVector other) {
		return other.x == x && other.y == y;
	}

	/**
	 * Returns the cross product of this vector and the given vector.
	 *
	 * @pre | other != null
	 * @post | result == (long)getX() * other.getY() - (long)getY() * other.getX()
	 */
	public long crossProduct(IntVector other) {
		return (long)x * other.y - (long)y * other.x;
	}
	
	/**
	 * Returns whether this vector is collinear with the given vector.
	 *
	 * @pre | other != null
	 * @post | result == (this.crossProduct(other) == 0)
	 */
	public boolean isCollinearWith(IntVector other) {
		return crossProduct(other) == 0;
	}
	
	/**
	 * Returns the dot product of this vector and the given vector.
	 *
	 * @pre | other != null
	 * @post | result == (long)getX() * other.getX() + (long)getY() * other.getY()
	 */
	public long dotProduct(IntVector other) { return (long)x * other.x + (long)y * other.y; }
	
	/**
	 * Returns a {@code DoubleVector} object that represents the same vector represented by this {@code IntVector} object.
	 * 
	 * @post | result != null
	 */
	public DoubleVector asDoubleVector() {
		return new DoubleVector(this.x, this.y);
	}
	
}
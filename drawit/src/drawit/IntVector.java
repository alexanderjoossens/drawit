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
	
	/** Returns {@code true} if this vector equals the given vector; returns {@code false} otherwise.
	 * @pre | other != null
	 * @post | result == (getX() == other.getX() && getY() == other.getY())
	 */
	public boolean equals(IntVector other) {
		return x == other.x && y == other.y;
	}
	
	/**
	 * @post | result == (other instanceof IntVector && equals((IntVector)other))
	 */
	@Override
	public boolean equals(Object other) {
		return other instanceof IntVector && equals((IntVector)other);
	}
	
	/**
	 * Returns whether {@code v1} and {@code v2} are either both {@code null} or equal vectors.
	 * 
	 * @post | result == (v1 == v2 || v1 != null && v2 != null && v1.equals(v2))
	 */
	public static boolean equals(IntVector v1, IntVector v2) {
		return v1 == v2 || v1 != null && v2 != null && v1.equals(v2);
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
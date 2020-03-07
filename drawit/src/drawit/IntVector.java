package drawit;

/**
 * An instance of this class represents a displacement in the two-dimensional plane.
 * @author Alexander and Stefan
 * @pre This vector's values are integers.
 * @immutable
 */
public class IntVector {
	
	/**
	 * @param x
	 * The X value of the vector
	 * @param y
	 * The Y value of the vector
	 * @pre Argument {@code x} is not {@code null}.
     *    | x != null
     * @pre Argument {@code y} is not {@code null}.
     *    | y != null
	 */
    private int x;
    private int y;

    /**
	 * @param x
	 * The X value of the vector.
	 * @param y
	 * The Y value of the vector.
	 * @pre Argument {@code x} is not {@code null}.
     *    | x != null
     * @pre Argument {@code y} is not {@code null}.
     *    | y != null
	 * @mutates | this
	 * @post the new X value of the vector is equal to the given X value.
	 * 		| getX() == x
	 * @post the new Y value of the vector is equal to the given Y value.
	 * 		| getY() == y
     */
    public IntVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X value of the given vector.
     * @return | this.x
     * @immutable
     */
    public int getX() {
    	return this.x;
    }
    
    /**
     * Returns the Y value of the given vector.
     * @return | this.y
     * @immutable
     */
    public int getY() {
    	return this.y;
    }
    
    /**
     * Returns a DoubleVector object that represents the same vector represented by this IntVector object.
     */
    public DoubleVector asDoubleVector() {
    	DoubleVector vector = new DoubleVector(this.getX(),this.getY());
    	return vector;
    }
    
    /**
     * Returns the cross product of this vector and the given vector.
     * @param other
     * 		The other vector.
     * @pre Argument {@code other} is not {@code null}.
     *    | other != null
     * @post The result equals the cross product of this vector and the given vector.
     *  | result == (long)getX() * other.getY() - (long)getY() * other.getX()
     */
    public long crossProduct(IntVector other) {
    	long crossProd = (this.x * other.getY()) - (this.y * other.getX());
    	return crossProd;
    }
    
    /**
     * Returns the dot product of this vector and the given vector.
     * @param other
     * 		The other vector.
     * @pre Argument {@code other} is not {@code null}.
     *    | other != null
     * @post The result is equal to the dot product of this vector and the given vector.
     * | result == this.getX() * other.getX() + this.getY() * other.getY()
     */
    public long dotProduct(IntVector other) {
    	long dotProd = (this.x * other.getX()) + (this.y * other.getY());
    	return dotProd;
    }

    /**
     * Returns whether this vector is collinear with the given vector.
     * @param other
     * 		The other vector.
     * @pre Argument {@code other} is not {@code null}.
     *    | other != null
     * @return True if the crossProduct of this vector and the other vector equals 0. Otherwise return false.
     * | result == (this.crossProduct(other) == 0)
     */
    public boolean isCollinearWith(IntVector other) {
    	boolean Collinearity = (this.crossProduct(other) == 0);
    	return Collinearity;
    }
}


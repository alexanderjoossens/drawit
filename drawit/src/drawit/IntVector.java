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
	 * @mutates | this
	 * @post the new X value of the vector is equal to the given X value.
	 * 		| getX() == x
	 * @post the new Y value of the vector is equal to the given Y value.
	 * 		| getY() == y
	 */
    private int x;
    private int y;

    public IntVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X value of the given vector.
     * @return | this.x
     */
    public int getX() {
    	return this.x;
    }
    
    /**
     * Returns the Y value of the given vector.
     * @return | this.y
     */
    public int getY() {
    	return this.y;
    }
    
    //(no formal)
    /**
     * Returns a DoubleVector object that represents the same vector represented by this IntVector object.
     */
    public DoubleVector asDoubleVector() {
    	DoubleVector vector = new DoubleVector(this.getX(),this.getY());
    	return vector;
    	
    }
    
    /**
     * Returns the cross product of this vector and the given vector.
     * @post result == (long)getX() * other.getY() - (long)getY() * other.getX()
     */
    public long crossProduct(IntVector other) {
    	long crossProd = (this.x * other.getY()) - (this.y * other.getX());
    	return crossProd;
    }
    
    /**
     * Returns the dot product of this vector and the given vector.
     * @post result == this.getX() * other.getX() + this.getY() * other.getY()
     */
    public long dotProduct(IntVector other) {
    	long dotProd = (this.x * other.getX()) + (this.y * other.getY());
    	return dotProd;
    }

    /**
     * Returns whether this vector is collinear with the given vector.
     * @post result == (this.crossProduct(other) == 0)
     * 	| result == (crossProduct(other) == 0)
     */
    public boolean isCollinearWith(IntVector other) {
    	boolean Collinearity = (this.crossProduct(other) == 0);
    	return Collinearity;
    }
}


package drawit;

/**
 * An instance of this class represents a displacement in the two-dimensional plane.
 * @author Alexander and Stefan
 *
 */
public class IntVector {
	
    private int x;
    private int y;

    public IntVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X value of the given vector.
     * @return
     */
    public int getX() {
    	return this.x;
    }
    
    /**
     * Returns the Y value of the given vector.
     * @return
     */
    public int getY() {
    	return this.y;
    }
    
    //dit is genoeg documentatie staat op toledo
    /**
     * Returns a DoubleVector object that represents the same vector represented by this IntVector object.
     */
    public DoubleVector asDoubleVector() {
    	DoubleVector vector = new DoubleVector(this.getX(),this.getY());
    	return vector;
    	
    }
    
    /**
     * Returns the cross product of this vector and the given vector.
     * 
     * @post result == (long)getX() * other.getY() - (long)getY() * other.getX()
     * 
     */
    public long crossProduct(IntVector other) {
    	long crossProd = (this.x * other.getY()) - (this.y * other.getX());
    	return crossProd;
    }
    
    /**
     * Returns the dot product of this vector and the given vector.
     * @param other
     * @return
     * @post result == this.getX() * other.getX() + this.getY() * other.getY()
     */
    public long dotProduct(IntVector other) {
    	long dotProd = (this.x * other.getX()) + (this.y * other.getY());
    	return dotProd;
    }

    /**
     * Returns whether this vector is collinear with the given vector.
     * 
     * @post result == (this.crossProduct(other) == 0)
     */
    public boolean isCollinearWith(IntVector other) {
    	
    	boolean Collinearity = (this.crossProduct(other) == 0);
    	return Collinearity;
    }

}


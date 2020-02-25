ackage drawit;

public class DoubleVector {
	
    private double x;
    private double y;

    public DoubleVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
    	return this.x;
    }
    
    public int getY() {
    	return this.y;
    }
    
    /**
     * Returns a DoubleVector object that represents the same vector represented by this IntVector object.
     * @return
     * 
     * @post The result ...
     * 
     */
    
    public DoubleVector asDoubleVector() {
    	return Math.atan(this.y/this.x);
    	
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

    public long dotProduct​(IntVector other) {
    	double dotProd = (this.x * other.getX()) + (this.y * other.getY());
    	return dotProd;

    }

    public boolean isCollinearWith​(IntVector other)
    /**
     * Returns whether this vector is collinear with the given vector.
     * 
     * @post result == (this.crossProduct(other) == 0)
     */
    
    Collinearity = (this.crossProduct(other) == 0)
    return Collinearity

}
//test


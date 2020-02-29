package drawit;

/**
 * blabla??
 * @author Alexander and Stefan
 *
 */
public class DoubleVector {
	
    double x;
    double y;

    public DoubleVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X value of the given vector.
     * @return
     */
    public double getX() {
    	return this.x;
    }
    
    /**
     * Returns the Y value of the given vector.
     * @return
     */
    public double getY() {
    	return this.y;
    }
    
    /**
     * Returns the angle from positive X to this vector, in radians.
     * The angle from positive X to positive Y is Math.PI / 2; the angle from positive X to negative Y is -Math.PI / 2.
     * 
     * @return
     * 
     * @post The result ...
     * 
     */
    public double asAngle() {
    	return Math.atan2(this.y, this.x);
    	
    }
    /**
     * Returns the cross product of this vector and the given vector.
     * 
     * @post result == this.getX() * other.getY() - this.getY() * other.getX()
     * 
     */
    public double crossProduct(DoubleVector other) {
    	double crossProd = (this.x * other.y) - (this.y * other.x);
    	return crossProd;
    }
    
    /**
     * Returns the dot product of this vector and the given vector.
     * 
     * @param other
     * @return
     * @post result == this.getX() * other.getX() + this.getY() * other.getY()
     */
    public double dotProduct(DoubleVector other) {
    	double dotProd = (this.x * other.x) + (this.y * other.y);
    	return dotProd;

    }
    /**
     * Returns the size (=norm) of the given vector.
     * 
     * @return
     */
    public double getSize() {
    	double size = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    	return size;
    }
    
    /**
     * Returns the vector that you become after adding the given vector to the other given vector.
     * 
     * @param other
     * @return
     */
    public DoubleVector plus(DoubleVector other) {
    	double xCoord = other.x;
    	double yCoord = other.y;
    	DoubleVector vector = new DoubleVector(xCoord+this.x, yCoord+this.y);
    	return vector;
    }
    /**
     * Returns a DoubleVector object whose coordinates are obtained by multiplying this vector's coordinates by the given scale factor.
     * @param 
     * @return
     */
    public DoubleVector scale(double d) {
    	DoubleVector vector = new DoubleVector(this.x*d, this.y*d);
    	return vector;
    }
}


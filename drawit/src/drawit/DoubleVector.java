package drawit;

public class DoubleVector {
	
    double x;
    double y;

    public DoubleVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
    	return this.x;
    }
    
    public double getY() {
    	return this.y;
    }
    
    /**
     * Returns the angle from positive X to this vector, in radians. The angle from positive X to positive Y is Math.PI / 2; the angle from positive X to negative Y is -Math.PI / 2.
     * @return
     * 
     * @post The result ...
     * 
     */
    
    public double asAngle() {
    	return Math.atan(this.y/this.x);
    	
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
     * @param other
     * @return
     * @post result == this.getX() * other.getX() + this.getY() * other.getY()
     */

    public double dotProduct(DoubleVector other) {
    	double dotProd = (this.x * other.x) + (this.y * other.y);
    	return dotProd;

    }

    public double getSize() {
    	double size = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    	return size;
    }

    public DoubleVector plus(DoubleVector other) {
    	double xCoord = other.x;
    	double yCoord = other.y;
    	DoubleVector vector = new DoubleVector(xCoord+this.x, yCoord+this.y);
    	return vector;
    }

    public DoubleVector scale(double d) {
    	DoubleVector vector = new DoubleVector(this.x*d, this.y*d);
    	return vector;
    }
}


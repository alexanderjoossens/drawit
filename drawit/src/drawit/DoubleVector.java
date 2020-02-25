package drawit;

public class DoubleVector {
	
    private double x;
    private double y;

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
    
    public double asAngle() {
    	return Math.atan(this.y/this.x);
    	
    }

    public double crossProduct(DoubleVector other) {
    	double crossProd = (this.x * other.getY()) - (this.y * other.getX());
    	return crossProd;
    }

    public double dotProduct(DoubleVector other) {
    	double dotProd = (this.x * other.getX()) + (this.y * other.getY());
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


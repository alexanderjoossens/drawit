package drawit;

public class DoublePoint {
	
	double x;
	double y;
	
	public DoublePoint(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public DoublePoint plus(DoubleVector other) {
		this.x += other.getX();
		this.y += other.getY();		  
	}
	
	public DoubleVector minus(DoublePoint other) {
		this.x += other.getX();
		this.y += other.getY();
		
	}
	
	
	public IntPoint round() {
		
		
	}

}

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
		double xCoord = this.x + other.getX();
		double yCoord = this.y + other.getY();
		DoublePoint point = new DoublePoint(xCoord, yCoord);
		return point;
	}
	
	public DoubleVector minus(DoublePoint other) {
		double xCoord = this.x - other.getX();
		double yCoord = this.y - other.getY();
		DoubleVector vector = new DoubleVector(xCoord, yCoord);
		return vector;
		
	}
	
	
//	public IntPoint round() {
// moet nog gemaakt worden
//		
//		
//	}

}

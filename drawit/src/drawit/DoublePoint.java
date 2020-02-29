package drawit;

/**
 * blabla?
 * @author Alexander and Stefan
 *
 */
public class DoublePoint {
	
	private double x;
	private double y;
	
	public DoublePoint(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns this point's X coordinate.
	 * @return
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Returns this point's Y coordinate.
	 * @return
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Returns the point that you become after adding the given vector to the given point (=sum).
	 * @param other
	 * @return
	 */
	public DoublePoint plus(DoubleVector other) {
		double xCoord = this.x + other.getX();
		double yCoord = this.y + other.getY();
		DoublePoint point = new DoublePoint(xCoord, yCoord);
		return point;
	}
	
	/**
	 * Returns the point that you become after distracting the given vector from the given point (=difference).
	 * @param other
	 * @return
	 */
	public DoubleVector minus(DoublePoint other) {
		double xCoord = this.x - other.getX();
		double yCoord = this.y - other.getY();
		DoubleVector vector = new DoubleVector(xCoord, yCoord);
		return vector;	
	}
	
	
	/**
	 * Returns an IntPoint object whose coordinates are obtained by rounding the coordinates of this point to the nearest integer.
	 * @return
	 */
	public IntPoint round() {
		int xCoord = (int) Math.round(this.x);
		int yCoord = (int) Math.round(this.y);
		IntPoint point = new IntPoint(xCoord, yCoord);
		return point;
		
		
	}

}

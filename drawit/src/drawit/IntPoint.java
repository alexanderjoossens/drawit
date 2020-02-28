package drawit;

public class IntPoint {
	private int x;
	private int y;
	//delete dit

	public IntPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	boolean equals(IntPoint other) {
		if (this.x == other.getX() && this.y == other.getY()) {
			return true;
		} else {
			return false;
		}
	}

	DoublePoint asDoublePoint() {
		DoublePoint point = new DoublePoint(this.x, this.y);
		return point;
	}

	boolean isOnLineSegment(IntPoint a, IntPoint b) {
		double rico = (b.getY() - a.getY()) / (b.getX() - a.getX());
		double function = rico * (b.getX() - this.x) + this.y;
		if (function == b.getY()) {
			return true;
		} else {
			return false;
		}
	}
	
	static boolean isCounterClockWise(IntPoint a, IntPoint b, IntPoint c) {
		return (c.getY()-a.getY()) * (b.getX()-a.getX()) > (b.getY()-a.getY()) * (c.getX()-a.getX());
		
	}
	
	public static boolean lineSegmentsIntersect(IntPoint a, IntPoint b, IntPoint c, IntPoint d) {
		return isCounterClockWise(a,c,d) != isCounterClockWise(b,c,d) && isCounterClockWise(a,b,c) != isCounterClockWise(a,b,d);
	}
	
	public IntPoint plus(IntVector other) {
		int xCoord = this.x + other.getX();
		int yCoord = this.y + other.getY();
		IntPoint point = new IntPoint(xCoord, yCoord);
		return point;
	}
	
	public IntVector minus(IntPoint other) {
		int xCoord = this.x - other.getX();
		int yCoord = this.y - other.getY();
		IntVector vector = new IntVector(xCoord, yCoord);
		return vector;	
	}

}

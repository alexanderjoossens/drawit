package drawit;

public class IntPoint {
	private int x;
	private int y;
	
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
		}
		else {
			return false;
		}
	}
	
	DoublePoint asDoublePoint() {
		DoublePoint point = new DoublePoint(this.x, this.y);
		return point;
	}
	
	boolean isOnLineSegment(IntPoint a, IntPoint b) {
		double rico = (b.getY()-a.getY()) / (b.getX()-a.getX());
		double function = rico*(b.getX()-this.x) + this.y;
		if (function == b.getY()) {
			return true;
		}
		else {
			return false;
		}	
	}
	
	static boolean lineSegmentsIntersect(IntPoint a, IntPoint b, IntPoint c, IntPoint d) {
		double rico1 = (b.getY()-a.getY()) / (b.getX()-a.getX());
		double rico2 = (b.getY()-a.getY()) / (b.getX()-a.getX());
		
				
		return true;
	}

}

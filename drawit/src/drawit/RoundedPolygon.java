package drawit;

/**
 * An instance of this class is a mutable abstraction storing a rounded polygon
 * defined by a set of 2D points with integer coordinates and a nonnegative
 * corner radius.
 * 
 */
public class RoundedPolygon {
	int radius;
	IntPoint[] points;

	public RoundedPolygon() {
	}

	public void setVertices(IntPoint[] newVertices) {
		this.points = newVertices;
	}

	public IntPoint[] getVertices() {
		return this.points;

	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getRadius() {
		return this.radius;
	}

	public void insert(int index, IntPoint point) {
		IntPoint[] vertices = this.getVertices();
		this.points = PointArrays.insert(vertices, index, point);
	}

	public void remove(int index) {
		IntPoint[] vertices = this.getVertices();
		this.points = PointArrays.remove(vertices, index);
	}

	public void update(int index, IntPoint point) {
		IntPoint[] vertices = this.getVertices();
		this.points = PointArrays.update(vertices, index, point);
	}
	
	/**
	 * Returns true iff the given point is contained by the (non-rounded) polygon
	 * defined by this rounded polygon's vertices. This method does not take into
	 * account this rounded polygon's corner radius; it assumes a corner radius of
	 * zero.
	 * 
	 * A point is contained by a polygon if it coincides with one of its vertices,
	 * or if it is on one of its edges, or if it is in the polygon's interior.
	 * 
	 */
	public boolean contains(IntPoint point) {
		for (int i = 0; i < this.points.length; i++) {
			if (this.points[i].equals(point)) {
				return false;
			}
		}
		if (point.isOnLineSegment(this.points[0], this.points[this.points.length - 1])) {
			return false;
		}
		for (int i = 0; i < points.length - 1; i++) {
			if (point.isOnLineSegment(this.points[i], this.points[i + 1])) {
				return false;
			}
		}
		int intersectAmount = 0;
		
		for (int i = 0; i < this.points.length; i++) {
			if (!(this.points[i].getX() > point.getX() && this.points[i].getY() == point.getY())) {
				
			}
		}
		
		

		return true;
	}

	public String getDrawingCommands() {
		return "lala";
	}
}

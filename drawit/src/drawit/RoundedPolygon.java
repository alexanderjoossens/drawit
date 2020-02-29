package drawit;

/**
 * An instance of this class is a mutable abstraction storing a rounded polygon
 * defined by a set of 2D points with integer coordinates and a nonnegative
 * corner radius.
 * 
 * @author Alexander and Stefan
 * 
 */
public class RoundedPolygon {
	int radius;
	IntPoint[] points;

	public RoundedPolygon() {
		
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
				return true;
		
			}
		}
		return true;
	}

	/**
	 * Returns a textual representation of a set of drawing commands for drawing this rounded polygon.
	 * @return
	 */
	public String getDrawingCommands() {
		return "lala";
	}
	
	/**
	 * Returns the radius of the corners of this rounded polygon.
	 * @return
	 */
	public int getRadius() {
		return this.radius;
	}

	/**
	 * Returns a new array whose elements are the vertices of this rounded polygon.
	 * @return
	 */
	public IntPoint[] getVertices() {
		return this.points;
	}

	/**
	 * blablabla
	 * @param index
	 * @param point
	 */
	public void insert(int index, IntPoint point) {
		IntPoint[] vertices = this.getVertices();
		this.points = PointArrays.insert(vertices, index, point);
	}

	/**
	 * blablabla
	 * @param index
	 */
	public void remove(int index) {
		IntPoint[] vertices = this.getVertices();
		this.points = PointArrays.remove(vertices, index);
	}

/**
 * Sets this rounded polygon's corner radius to the given value.
 * @param radius
 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * Sets the vertices of this rounded polygon to be equal to the elements of the given array.
	 * @param newVertices
	 */
public void setVertices(IntPoint[] newVertices) {
	this.points = newVertices;
	}

	/**
	 * blablabla
	 * @param index
	 * @param point
	 */
	public void update(int index, IntPoint point) {
		IntPoint[] vertices = this.getVertices();
		this.points = PointArrays.update(vertices, index, point);
	}
}


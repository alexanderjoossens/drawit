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
	private int radius;
	private IntPoint[] points;

	public RoundedPolygon() {
	}

	// dit is genoeg documentatie zie toledo (not formal?)
	/**
	 * Returns true if the given point is contained by the (non-rounded) polygon
	 * defined by this rounded polygon's vertices. This method does not take into
	 * account this rounded polygon's corner radius; it assumes a corner radius of
	 * zero.
	 * 
	 * A point is contained by a polygon if it coincides with one of its vertices,
	 * or if it is on one of its edges, or if it is in the polygon's interior.
	 */
	public boolean contains(IntPoint point) {
		for (int i = 0; i < this.points.length; i++) {
			if (this.points[i].equals(point)) {
				return true;
			}
		}
		if (point.isOnLineSegment(this.points[0], this.points[this.points.length - 1])) {
			return true;
		}
		for (int i = 0; i < points.length - 1; i++) {
			if (point.isOnLineSegment(this.points[i], this.points[i + 1])) {
				return true;
			}
		}

		int intersectAmount = 0;

		for (int i = 0; i < this.points.length; i++) {
			if (!(this.points[i].getX() > point.getX() && this.points[i].getY() == point.getY())) {
				intersectAmount++;
			}
		}

		for (int i = 0; i < this.points.length - 1; i++) {
			boolean intersectsExitPath = point.exitPathIntersect(this.points[i], this.points[i + 1]);
			if (intersectsExitPath) {
				intersectAmount++;
			}
		}

		if ((intersectAmount % 2) == 0) {
			return false;
		}

		return true;
	}

	// dit is genoeg documentatie staat op toledo(not formal).
//	/**
//	 * Returns a textual representation of a set of drawing commands for drawing
//	 * this rounded polygon.
//	 */
//	public String getDrawingCommands() {
////		if (this.getVertices().length < 3) {
////			return "";
////		}
//
//		if (PointArrays.checkDefinesProperPolygon(this.points) != null) {
//			return "";
//		}
//
//		for (IntPoint point : this.points) {
//
//			{
//
//				System.out.print("test");
//
//			}
//
//			// if (this.IntVector.isCollinearWith(other)) {
//			// return "";
//		}
//
//		if (this.radius == 0) {
//			return "";
//		}
//
//		// nie naar kijken
//		// Int[] hoekpunten = Array(hoekpunten);
//		// return (hoekpunten[i]);
//
//		return "";
//	}

	/**
	 * Returns the radius of the corners of this rounded polygon.
	 * 
	 * @post the result equals the given radius | result == getRadius()
	 */
	public int getRadius() {
		return this.radius;
	}

	/**
	 * Returns a new array whose elements are the vertices of this rounded polygon.
	 * 
	 * @post The result equals the points | result == points
	 */
	public IntPoint[] getVertices() {
		return this.points;
	}

	/**
	 * This method adds the given point to the points field of this object.
	 * 
	 * @mutates
	 * @post The length of the points is 1 longer than the length of the old points
	 *       | old(points.length) == points.length -1
	 * @post The vertex at the given index of points, equals point. | points[index]
	 *       == point
	 * 
	 */
	public void insert(int index, IntPoint point) {
		IntPoint[] vertices = this.getVertices();
		this.points = PointArrays.insert(vertices, index, point);
	}

	/**
	 * This method removes the given point to the points field of this object.
	 * 
	 * @mutates
	 * @post The length of the points is 1 shorter than the length of the old points
	 *       | old(points.length) == points.length + 1
	 * @post The vertex at the given index of points, equals point. | points[index]
	 *       == old(points[index+1]
	 */
	public void remove(int index) {
		IntPoint[] vertices = this.getVertices();
		this.points = PointArrays.remove(vertices, index);
	}

	/**
	 * @mutates Sets this rounded polygon's corner radius to the given value.
	 * 
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * Sets the vertices of this rounded polygon to be equal to the elements of the
	 * given array.
	 * 
	 * @mutates
	 */
	public void setVertices(IntPoint[] newVertices) {
		this.points = newVertices;
	}

	/**
	 * This method replaces the vertex at the given index of points, with point.
	 * 
	 * @post The vertex at the given index equals point. | points[index] == point
	 */
	public void update(int index, IntPoint point) {
		IntPoint[] vertices = this.getVertices();
		this.points = PointArrays.update(vertices, index, point);
	}

	/**
	 * This method returns the normalized vector.
	 * 
	 */
	public DoubleVector normalize(DoubleVector vector) {
		double powerComponents = vector.getX() * vector.getX() + vector.getY() * vector.getY();
		double normalizeScale = 1 / Math.sqrt(powerComponents);

		DoubleVector normalizedVector = vector.scale(normalizeScale);
		return normalizedVector;

	}

	public String getDrawingCommands() {
		if (PointArrays.checkDefinesProperPolygon(this.points) != null) {
			return "";
		}
		IntPoint[] originalPoints = this.getVertices();
		IntPoint[] newPointsTemp = PointArrays.insert(originalPoints, 0, originalPoints[originalPoints.length - 1]);
		IntPoint[] newPoints = PointArrays.insert(newPointsTemp, newPointsTemp.length - 1, originalPoints[0]);

		String text = "";

		for (int i = 1; i < newPoints.length - 1; i++) {
			DoubleVector BA = newPoints[i - 1].minus(newPoints[i]).asDoubleVector();
			DoubleVector BC = newPoints[i + 1].minus(newPoints[i]).asDoubleVector();
			DoublePoint BAC = newPoints[i].asDoublePoint().plus(BA.scale(0.5));
			DoublePoint BCC = newPoints[i].asDoublePoint().plus(BC.scale(0.5));

			if (newPoints[i].isOnLineSegment(newPoints[i - 1], newPoints[i + 1])) {
				text += String.format("line %s %s %s %s\n", BAC.getX(), BAC.getY(), newPoints[i].getX(),
						newPoints[i].getY());
				text += String.format("line %s %s %s %s\n", BCC.getX(), BCC.getY(), newPoints[i].getX(),
						newPoints[i].getY());

			} else {
				DoubleVector BAU = normalize(BA);
				DoubleVector BCU = normalize(BC);
				DoubleVector BSU = normalize(BAU.plus(BCU));
				double BAUcuttoff = BAU.dotProduct(BSU);
				double unitRadius = Math.abs(BSU.crossProduct(BAU));
				double lengthScale;
				if (BAU.getSize() <= BCU.getSize()) {
					lengthScale = BA.scale(0.5).getSize() / (BAUcuttoff);
				} else {
					lengthScale = BC.scale(0.5).getSize() / (BAUcuttoff);
				}

				double radiusScale = ((double) this.radius) / unitRadius;
				double scale;
				if (radiusScale <= lengthScale) {
					scale = radiusScale;
				} else {
					scale = lengthScale;
				}

				double theRadius = scale * unitRadius;
				System.out.println(theRadius);
				double theLineLength = BAUcuttoff * scale;
				DoubleVector radiusVector = BSU.scale(theRadius);
				DoublePoint radiusCenter = newPoints[i].asDoublePoint().plus(radiusVector);
				DoublePoint endPoint1 = (newPoints[i].asDoublePoint()).plus(BAU.scale(theLineLength));
				DoublePoint endPoint2 = (newPoints[i].asDoublePoint()).plus(BCU.scale(theLineLength));
				DoubleVector startAngleVector = endPoint1.minus(radiusCenter);
				DoubleVector endAngleVector = endPoint2.minus(radiusCenter);
				Double startAngle = startAngleVector.asAngle();
				Double endAngle = endAngleVector.asAngle();
				Double angleExtent = startAngle - endAngle;
				if (angleExtent>(Math.PI)) {
					angleExtent -= ((Math.PI)*2);
				}
				if (angleExtent < -(Math.PI)) {
					angleExtent += ((Math.PI)*2);

				}
				text += String.format("line %s %s %s %s\n", BAC.getX(), BAC.getY(), endPoint1.getX(), endPoint1.getY());
				text += String.format("line %s %s %s %s\n", BCC.getX(), BCC.getY(), endPoint2.getX(), endPoint2.getY());
				text += String.format("arc %s %s %s %s %s\n", radiusCenter.getX(), radiusCenter.getY(), theRadius,
						startAngle, Math.abs(angleExtent));
			}

		}
		System.out.println(text);
		return text;
	}

}

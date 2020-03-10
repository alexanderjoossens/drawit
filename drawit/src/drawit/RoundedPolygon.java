package drawit;

/**
 * An instance of this class is a mutable abstraction storing a rounded polygon
 * defined by a set of 2D points with integer coordinates and a nonnegative
 * corner radius.
 * 
 * @author Alexander and Stefan
 * @invar This object's radius is positive | radius >= 0
 */
public class RoundedPolygon {

	/**
	 * @invar | radius >= 0
	 */
	private int radius;
	private IntPoint[] points;

	public RoundedPolygon() {
		IntPoint point1 = new IntPoint(0, 0);
		IntPoint point2 = new IntPoint(105, 0);
		IntPoint point3 = new IntPoint(100, 100);
		IntPoint point4 = new IntPoint(0, 100);
		IntPoint[] vertices = { point1, point2, point3, point4 };
		this.radius = 0;
		this.points = vertices;
	}

	/**
	 * Returns true if the given point is contained by the (non-rounded) polygon
	 * defined by this rounded polygon's vertices. This method does not take into
	 * account this rounded polygon's corner radius; it assumes a corner radius of
	 * zero.
	 * 
	 * A point is contained by a polygon if it coincides with one of its vertices,
	 * or if it is on one of its edges, or if it is in the polygon's interior.
	 * 
	 * @param point The point to check if it is in the polygon.
	 * @inspects | this
	 * @throws IllegalArgumentException if the point is null. | point == null
	 */
	public boolean contains(IntPoint point) {
		if (point == null) {
			throw new IllegalArgumentException("The point equals null");
		}

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
			if (this.points[i].getX() > point.getX() && this.points[i].getY() == point.getY()) {

				intersectAmount++;
			}
		}
		if (point.exitPathIntersect(this.points[0], this.points[points.length - 1])) {
			intersectAmount++;
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

	/**
	 * Returns a textual representation of a set of drawing commands for drawing
	 * this rounded polygon.
	 * 
	 * @inspects | this
	 * @post Because of Pythagora's theorem, the length of radiusVector squared =
	 *       radius squared + theLineLength squared. |
	 *       (radiusVector.getSize()*radiusVector.getSize() - (theRadius*theRadius +
	 *       theLineLength*theLineLength) <= 0.01 | &&
	 *       radiusVector.getSize()*radiusVector.getSize() - (theRadius*theRadius +
	 *       theLineLength*theLineLength) >= -0.01)
	 * @post The drawn radius can not be longer then this rounded polygons radius. |
	 *       this.radius >= theRadius
	 */
	public String getDrawingCommands() {
		if (this.points.length <= 2) {
			return "";
		}
		IntPoint[] originalPoints = this.getVertices();
		IntPoint[] newPointsTemp = PointArrays.insert(originalPoints, 0, originalPoints[originalPoints.length - 1]);
		IntPoint[] newPoints = PointArrays.insert(newPointsTemp, newPointsTemp.length, originalPoints[0]);

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
				DoubleVector radiusVector = BSU.scale(scale);
				DoublePoint radiusCenter = newPoints[i].asDoublePoint().plus(radiusVector);
				double theLineLength = radiusVector.dotProduct(BAU);
				DoublePoint endPoint1 = (newPoints[i].asDoublePoint()).plus(BAU.scale(theLineLength));
				DoublePoint endPoint2 = (newPoints[i].asDoublePoint()).plus(BCU.scale(theLineLength));
				DoubleVector startAngleVector = endPoint1.minus(radiusCenter);
				DoubleVector endAngleVector = endPoint2.minus(radiusCenter);

				Double startAngle = startAngleVector.asAngle();
				Double endAngle = endAngleVector.asAngle();
				Double angleExtent = startAngle - endAngle;
				if (angleExtent > (Math.PI)) {
					angleExtent -= ((Math.PI) * 2);
				}
				if (angleExtent < -(Math.PI)) {
					angleExtent += ((Math.PI) * 2);

				}
				text += String.format("line %s %s %s %s\n", BAC.getX(), BAC.getY(), endPoint1.getX(), endPoint1.getY());
				text += String.format("line %s %s %s %s\n", BCC.getX(), BCC.getY(), endPoint2.getX(), endPoint2.getY());
				text += String.format("arc %s %s %s %s %s\n", radiusCenter.getX(), radiusCenter.getY(), theRadius,
						startAngle, (-angleExtent));
			}

		}
		return text;
	}

	/**
	 * Returns the radius of the corners of this rounded polygon.
	 * 
	 * @inspects | this
	 * @post the result equals the given radius | result == getRadius()
	 */
	public int getRadius() {
		return this.radius;
	}

	/**
	 * Returns a new array whose elements are the vertices of this rounded polygon.
	 * 
	 * @inspects | this
	 * @post The result equals the points | result == points
	 */
	public IntPoint[] getVertices() {
		IntPoint[] newArray = PointArrays.copy(points);
		return newArray;
	}

	/**
	 * This method adds the given point to the points field of this object.
	 * 
	 * @mutates this.points
	 * @post The length of the points is 1 longer than the length of the old points
	 *       | old(points.length) == points.length -1
	 * @post The vertex at the given index of points, equals point. | points[index]
	 *       == point
	 * @throws IllegalArgumentException if the index is not in the range of the
	 *                                  length of the polygon or if negative. | 0 >
	 *                                  index || index < this.points.length
	 * @throws IllegalArgumentException if the given point is null. | point == null
	 * @throws IllegalArgumentException if the resulting polygon is not proper.
	 * @inspects | this
	 */
	public void insert(int index, IntPoint point) {
		if (point == null) {
			throw new IllegalArgumentException("Point does not exist!");
		}
		if (index < 0 || this.points.length < index) {
			throw new IllegalArgumentException("Index out of bounds exception!");
		}
		
		IntPoint[] vertices = this.getVertices();
		IntPoint[] newArray = PointArrays.insert(vertices, index, point);
		
		if (PointArrays.checkDefinesProperPolygon(newArray) != null) {
			throw new IllegalArgumentException("The resulting polygon is not proper!");
		}
		
		this.setVertices(newArray);
	}

	/**
	 * This method removes the given point to the points field of this object.
	 * 
	 * @param index The index of the point to be removed.
	 * @mutates | this
	 * @post The length of the points is 1 shorter than the length of the old points
	 *       | old(points.length) == points.length + 1
	 * @post The vertex at the given index of points, equals point. | points[index]
	 *       == old(points[index+1])
	 * @throws IllegalArgumentException if the index is null. | index == null
	 * @throws IllegalArgumentException if the resulting polygon is not proper.
	 * @throws IllegalArgumentException if the index is not in the range of the
	 *                                  length of the polygon. | 0 > index || index
	 *                                  <= this.points.length
	 */
	public void remove(int index) {
		if (index < 0 || this.points.length <= index) {
			throw new IllegalArgumentException("Index out of bounds exception!");
		}
		
		IntPoint[] vertices = this.getVertices();
		IntPoint[] newArray = PointArrays.remove(vertices, index);
		
		if (PointArrays.checkDefinesProperPolygon(newArray) != null) {
			throw new IllegalArgumentException("The resulting polygon is not proper!");
		}
		
		this.setVertices(newArray);
	}

	/**
	 * Sets this rounded polygon's corner radius to the given value.
	 * 
	 * @param radius The new radius.
	 * @mutates this.radius
	 * @post the new radius equals the given radius. | this.radius == radius
	 * @throws IllegalArgumentException if the radius is smaller than 0. | 0 > radius
	 * @mutates | this
	 */
	public void setRadius(int radius) {
		if (radius < 0) {
			throw new IllegalArgumentException("Radius is smaller then 0");
		}
		this.radius = radius;
	}

	/**
	 * Sets the vertices of this rounded polygon to be equal to the elements of the
	 * given array.
	 * 
	 * @param newVertices The new vertices.
	 * @throws IllegalArgumentException if the given vertices do not define a proper polygon. | PointArrays.checkDefinesProperPolygon(newVertices) != null
	 * @mutates | this
	 */
	public void setVertices(IntPoint[] newVertices) {
		if (PointArrays.checkDefinesProperPolygon(newVertices) != null) {
			throw new IllegalArgumentException(PointArrays.checkDefinesProperPolygon(newVertices));
		}
		this.points = newVertices;
	}

	/**
	 * This method replaces the vertex at the given index of points, with point.
	 * 
	 * @param point The new point that you want to update the old point with.
	 * @param index The index of the point to be updated.
	 * @throws IllegalArgumentException if the given point is null | point == null
	 * @throws IllegalArgumentException if the new polygon is not proper
	 *  | 
	 * @throws IllegalArgumentException if the given index is not in the range of the points. | index < 0 || this.points.length >= index
	 * @post The vertex at the given index equals point. | points[index] == point
	 * @mutates this.points
	 * @inspects | this
	 */
	public void update(int index, IntPoint point) {
		if (point == null) {
			throw new IllegalArgumentException("The given point equals null!");
		}

		if (index < 0 || this.points.length <= index) {
			throw new IllegalArgumentException("Index out of bounds exception!");
		}
		IntPoint[] vertices = this.getVertices();
		IntPoint[] newArray = PointArrays.update(vertices, index, point);
		if (PointArrays.checkDefinesProperPolygon(newArray) != null) {
			throw new IllegalArgumentException("The resulting polygon is not proper!");
		}
		
		this.setVertices(newArray);
	}

	/**
	 * This method returns the normalized vector.
	 * 
	 * @param vector The vector to be normalized.
	 * @throws IllegalArgumentException if the given vector equals null | vector == null
	 * @post The size of the resulting vector is 1. | result.getSize() <
	 *       1.01 && result.getSize() > 0.99
	 * @inspects | vector
	 */
	public static DoubleVector normalize(DoubleVector vector) {

		if (vector == null) {
			throw new IllegalArgumentException("The given vector equals null!");
		}

		double powerComponents = vector.getX() * vector.getX() + vector.getY() * vector.getY();
		double normalizeScale = 1 / Math.sqrt(powerComponents);
		DoubleVector normalizedVector = vector.scale(normalizeScale);
		return normalizedVector;

	}

}

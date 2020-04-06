package drawit;

import java.awt.Color;

/**
 * An instance of this class is a mutable abstraction storing a rounded polygon
 * defined by a set of 2D points with integer coordinates and a nonnegative
 * corner radius.
 * 
 * @author Alexander and Stefan
 * @invar This object's radius is positive | getRadius() >= 0
 */
public class RoundedPolygon {

	/**
	 * @invar | radius >= 0
	 */
	private int radius;
	private IntPoint[] points;
	private Color color;
	
	public RoundedPolygon() {
		IntPoint point1 = new IntPoint(0, 0);
		IntPoint point2 = new IntPoint(100, 0);
		IntPoint point3 = new IntPoint(100, 100);
		IntPoint point4 = new IntPoint(0, 100);
		IntPoint[] vertices = { point1, point2, point3, point4 };
		this.radius = 0;
		this.points = vertices;
		this.color = Color.yellow;
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
	 * @param point
	 * The point to check if it is in the polygon.
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
	
	/** Returns the color of this rounder polygon.
	 * @inspects	|	this
	 */
	public java.awt.Color getColor() {
		return this.color;
	}
	
	/**	Sets the color of this rounded polygon to the given color.
	 * @mutates		|	this
	 */
	public void setColor(java.awt.Color color) {
		this.color = color;
	}

	/**
	 * Returns a textual representation of a set of drawing commands for drawing
	 * this rounded polygon.
	 * 
	 * @inspects | this
	 */
	public String getDrawingCommands() {
		if (points.length < 3)
			return "";
		StringBuilder commands = new StringBuilder();
		for (int index = 0; index < points.length; index++) {
			IntPoint a = points[(index + points.length - 1) % points.length];
			IntPoint b = points[index];
			IntPoint c = points[(index + 1) % points.length];
			DoubleVector ba = a.minus(b).asDoubleVector();
			DoubleVector bc = c.minus(b).asDoubleVector();
			DoublePoint baCenter = b.asDoublePoint().plus(ba.scale(0.5));
			DoublePoint bcCenter = b.asDoublePoint().plus(bc.scale(0.5));
			double baSize = ba.getSize();
			double bcSize = bc.getSize();
			if (ba.crossProduct(bc) == 0) {
				commands.append("line " + bcCenter.getX() + " " + bcCenter.getY() + " " + b.getX() + " " + b.getY() + "\n");
				commands.append("line " + b.getX() + " " + b.getY() + " " + baCenter.getX() + " " + baCenter.getY() + "\n");
			} else {
				DoubleVector baUnit = ba.scale(1/baSize);
				DoubleVector bcUnit = bc.scale(1/bcSize);
				DoubleVector bisector = baUnit.plus(bcUnit);
				bisector = bisector.scale(1/bisector.getSize());
				double unitEdgeDistance = baUnit.dotProduct(bisector);
				double unitRadius = Math.abs(bisector.crossProduct(baUnit));
				double scaleFactor = Math.min(this.radius / unitRadius, Math.min(baSize, bcSize) / 2 / unitEdgeDistance);
				DoublePoint center = b.asDoublePoint().plus(bisector.scale(scaleFactor));
				double radius = unitRadius * scaleFactor;
				DoublePoint bcCornerStart = b.asDoublePoint().plus(bcUnit.scale(unitEdgeDistance * scaleFactor));
				DoublePoint baCornerStart = b.asDoublePoint().plus(baUnit.scale(unitEdgeDistance * scaleFactor));
				double baAngle = baCornerStart.minus(center).asAngle();
				double bcAngle = bcCornerStart.minus(center).asAngle();
				double angleExtent = bcAngle - baAngle;
				if (angleExtent < -Math.PI)
					angleExtent += 2 * Math.PI;
				else if (Math.PI < angleExtent)
					angleExtent -= 2 * Math.PI;
				commands.append("line " + baCenter.getX() + " " + baCenter.getY() + " " + baCornerStart.getX() + " " + baCornerStart.getY() + "\n");
				commands.append("arc " + center.getX() + " " + center.getY() + " " + radius + " " + baAngle + " " + angleExtent + "\n");
				commands.append("line " + bcCornerStart.getX() + " " + bcCornerStart.getY() + " " + bcCenter.getX() + " " + bcCenter.getY() + "\n");
			}
		}
		if (color == Color.yellow)
			commands.append("fill " + "255 " + "255 " + "0  " + "\n");
		if (color == Color.red)
			commands.append("fill " + "255 " + "0 " + "0 " + "\n");
		if (color == Color.green)
			commands.append("fill " + "0 " + "255 " + "0 " + "\n");
		if (color == Color.blue)
			commands.append("fill " + "0 " + "0 " + "255 " + "\n");
		return commands.toString();
	}


	/**
	 * Returns the radius of the corners of this rounded polygon.
	 * 
	 * @inspects | this
	 */
	public int getRadius() {
		return this.radius;
	}

	/**
	 * Returns a new array whose elements are the vertices of this rounded polygon.
	 * 
	 * @inspects | this
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
	 *       | old(getVertices().length) == getVertices().length -1
	 * @post The vertex at the given index of points, equals point. 
	 * | getVertices()[index]== point
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
	 *       | old(getVertices().length) == getVertices().length + 1
	 * @post The vertex at the given index of points, equals point. 
	 * 		| getVertices()[index] == old(getVertices()[index+1])
	 * @throws IllegalArgumentException if the resulting polygon is not proper.
	 * @throws IllegalArgumentException if the index is not in the range of the length of the polygon. 
	 * 	| 0 > index || index >= getVertices().length
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
	 * @throws IllegalArgumentException if the given index is not in the range of the points. | index < 0 || getVertices().length <= index
	 * @post The vertex at the given index equals point. | getVertices()[index] == point
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
	 * @post The size of the resulting vector is 1. 
	 * | result.getSize() < 1.01 && result.getSize() > 0.99
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

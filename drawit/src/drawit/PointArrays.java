package drawit;

import drawit.IntPoint;

/**
 * Declares a number of methods useful for working with arrays of IntPoint
 * objects.
 * 
 * @author Alexander and Stefan
 * @pre immutable
 */
public class PointArrays {
	/**
	 * Returns a new array with the same contents as the given array.
	 * 
	 * @param points
	 * 		The points are the content of the given array.
	 * @pre Argument {@code points} is not {@code null}.
     *    | points != null
	 * @post the content of the new array equals the given points.
	 * 	| this.equals(newArray) == true
	 * @post the length of the new array is equal to the length of the points.
	 * 		| newArray.length == points.length
	 * @inspects | points
     * @creates result
	 * @post The result is not {@code null}
     *    | result != null
	 */
	public static IntPoint[] copy(IntPoint[] points) {
		IntPoint[] newArray = new IntPoint[points.length];

		for (int i = 0; i < points.length; i++) {
			newArray[i] = points[i];
		}
		return newArray;
	}

	/**
	 * Returns a new array whose elements are the elements of the given array with
	 * the given point inserted at the given index.
	 * @param points
	 * 		The content of the given array.
	 * @param index
	 * 		The position (index) where the point will be inserted.
	 * @param point
	 * 		The point that will be inserted.
	 * @pre Argument {@code points} is not {@code null}.
     *    | points != null
     * @pre Argument {@code index} is not {@code null}.
     *    | index != null
	 * @pre Argument {@code point} is not {@code null}.
     *    | point != null
	 * @post The length of the newArray is 1 more than the given points |
	 *       points.length+1 == newArray.length
     * @creates result
	 * @post The result is not {@code null}
     *    | result != null
	 */
	public static IntPoint[] insert(IntPoint[] points, int index, IntPoint point) {
		IntPoint[] newArray = new IntPoint[points.length + 1];
		int pointsIndex = 0;
		for (int i = 0; i < points.length + 1; i++) {
			if (i == index) {
				newArray[i] = point;
			} else {
				newArray[i] = points[pointsIndex];
				pointsIndex++;
			}
		}
		return newArray;

	}

	/**
	 * Returns a new array whose elements are the elements of the given array with
	 * the element at the given index removed.
	 * @param pointss
	 * 		The content of the given array.
	 * @param index
	 * 		The position (index) of the point that will be removed.
	 * @pre Argument {@code points} is not {@code null}.
     *    | points != null
     * @pre Argument {@code index} is not {@code null}.
     *    | index != null
	 * @post The length of the newArray is 1 less than the given points |
	 *       points.length()-1 == newArray.length()
     * @creates result
	 * @post The result is not {@code null}
     *    | result != null
	 */
	public static IntPoint[] remove(IntPoint[] points, int index) {
		IntPoint[] newArray = new IntPoint[points.length - 1];
		int pointsIndex = 0;
		for (int i = 0; i < points.length - 1; i++) {
			if (i == index) {
				pointsIndex++;
			}
			newArray[i] = points[pointsIndex];
			pointsIndex++;
		}
		return newArray;

	}

	/**
	 * Returns a new array whose elements are the elements of the given array with
	 * the element at the given index replaced by the given point.
	 * @param points
	 * 		The content of the given array.
	 * @param index
	 * 		The position (index) of the point that will be replaced.
	 * @param value
	 * 		The value of the new point.
	 * @pre Argument {@code points} is not {@code null}.
     *    | points != null
	 * @pre Argument {@code index} is not {@code null}.
     *    | index != null
	 * @pre Argument {@code value} is not {@code null}.
     *    | value != null
	 * @post the value of the new array at position i equals the given value.
	 * 		| newArray[i] == value
     * @creates result
	 * @post The result is not {@code null}
     *    | result != null
	 */
	public static IntPoint[] update(IntPoint[] points, int index, IntPoint value) {
		IntPoint[] newArray = new IntPoint[points.length];
		for (int i = 0; i < points.length; i++) {
			if (i == index) {
				newArray[i] = value;
			} else {
				newArray[i] = points[i];
			}
		}
		return newArray;
	}

	/**
	 * Returns null if the given array of points defines a proper polygon;
	 * otherwise, returns a string describing why it does not.
	 * @param points
	 * 		The points to check if they make up a polygon.
	 * @pre Argument {@code points} is not {@code null}.
     *    | points != null
	 */
	public static String checkDefinesProperPolygon(IntPoint[] points) {
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points.length; j++) {
				if (points[i].equals(points[j]) && i != j) {
					return "2 vertices coincide!";
				}
			}
		}

		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points.length - 1; j++) {
				if (points[i].isOnLineSegment(points[j], points[j + 1]) && i != j + 1 && i != j) {
					return "There is a vertex on an edge!";

				}
				if (points[i].isOnLineSegment(points[0], points[points.length - 1]) && i != j + 1 && i != j && i != 0
						&& i != points.length - 1) {
					return "There is a vertex on an edge!";

				}
			}
		}

		for (int i = 0; i < points.length - 1; i++) {
			for (int j = 0; j < points.length - 1; j++) {
				if (IntPoint.lineSegmentsIntersect(points[i], points[i + 1], points[j], points[j + 1]) && i != j) {
					return "Lines intersect!";
				}
			}
		}
		for (int i = 0; i < points.length - 1; i++) {
			if (IntPoint.lineSegmentsIntersect(points[i], points[i + 1], points[0], points[points.length - 1])) {
				return "Lines intersect!";
			}
		}
		return null;
	}
}
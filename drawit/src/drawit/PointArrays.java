package drawit;

import drawit.IntPoint;

public class PointArrays {

	public static IntPoint[] copy(IntPoint[] points) {
		IntPoint[] newArray = new IntPoint[points.length];

		for (int i = 0; i < points.length; i++) {
			newArray[i] = points[i];

		}
		return newArray;

	}

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
	 * 
	 * @param points
	 * @param index
	 * @param value
	 * @return
	 */

	static String checkDefinesProperPolygon(IntPoint[] points) {
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points.length; j++) {
				if (points[i].equals(points[j]) && i != j) {
					return "2 vertices coincide!";
				}
			}
		}
		if (points[0].isOnLineSegment(points[1], points[points.length - 1])) {
			return "There is a vertex on an edge!";
		}
		if (points[points.length - 1].isOnLineSegment(points[points.length - 2], points[0])) {
			return "There is a vertex on an edge!";
		}
		for (int i = 1; i < points.length - 1; i++) {
			if (points[i].isOnLineSegment(points[i - 1], points[i + 1])) {
				return "There is a vertex on an edge!";
			}
		}

		for (int i = 0; i < points.length - 1; i++) {
			for (int j = 0; j < points.length - 1; j++) {
				if (IntPoint.lineSegmentsIntersect(points[i], points[i + 1], points[j], points[j + 1]) && i != j) {
					return ("Lines intersect!");
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
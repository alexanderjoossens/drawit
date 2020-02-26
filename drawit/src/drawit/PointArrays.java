package drawit;

public class PointArrays {

	// moet ik dit deze intarray zelf aanmaken? want ik gebruik die nergens
	private IntPoint[] points;

	public static IntPoint[] copy(IntPoint[] points) {
		IntPoint[] newArray = new IntPoint[points.length];

		for (int i = 0; i < points.length; i++) {
			newArray[i] = points[i];

		}
		return newArray;

	}

	public static IntPoint[] insert(IntPoint[] points, int index, IntPoint point) {
		IntPoint[] newArray = new IntPoint[points.length+1];
		int pointsIndex = 0
		for (int i = 0; i < points.length+1; i++ ) {
			if (i == index) {
				newArray[i] = point;
				pointsIndex++;
			}
			else {
				newArray[i] = points[pointsIndex];
		
			}
			pointsIndex++;
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

}
package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IntPointTest {

	@Test
	void test() {
		IntPoint point1 = new IntPoint(-1, -1);
		assert point1.getX() == -1 && point1.getY() == -1;

		DoublePoint point0 = point1.asDoublePoint();
		assert point0.getX() == -1.0 && point0.getY() == -1.0;

		IntPoint point2 = new IntPoint(-1, -1);
		assert point2.equals(point1);

		IntPoint point3 = new IntPoint(3, 3);
		IntPoint point4 = new IntPoint(2, 2);
		assert point4.isOnLineSegment(point1, point3);

		IntPoint point5 = new IntPoint(1, 2);
		IntPoint point6 = new IntPoint(2, 1);
		assert IntPoint.lineSegmentsIntersect(point1, point2, point3, point4) == false;
		assert IntPoint.lineSegmentsIntersect(point1, point4, point4, point3) == false;
		assert IntPoint.lineSegmentsIntersect(point5, point6, point1, point3) == true;

		IntVector vector1 = new IntVector(-10, -10);
		point6 = point1.plus(vector1);
		assert point6.getX() == -11 && point6.getY() == -11;

		vector1 = point1.minus(point6);
		assert vector1.getX() == 10 && vector1.getY() == 10;

		// exitPathIntersect_Test
		IntPoint point7 = new IntPoint(1, 0);
		IntPoint point8 = new IntPoint(1, 2);
		IntPoint point9 = new IntPoint(0, 1);
		IntPoint point10 = new IntPoint(0, 2);
		IntPoint point11 = new IntPoint(0, 0);
		IntPoint point12 = new IntPoint(2, 1);

		assert point9.exitPathIntersect(point7, point8) == true;
		assert point10.exitPathIntersect(point7, point8) == false;
		assert point11.exitPathIntersect(point7, point8) == false;
		assert point12.exitPathIntersect(point7, point8) == false;

	}

}

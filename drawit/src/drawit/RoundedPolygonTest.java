package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RoundedPolygonTest {

	@Test
	void test() {
		// contains_Test
		RoundedPolygon poly1 = new RoundedPolygon();
		IntPoint point1 = new IntPoint(2, 0);
		IntPoint point2 = new IntPoint(2, 2);
		IntPoint point3 = new IntPoint(0, 2);
		IntPoint point4 = new IntPoint(0, 0);

		IntPoint point5 = new IntPoint(-1, 0);
		IntPoint point6 = new IntPoint(2, 3);
		IntPoint point7 = new IntPoint(-1, 1);
		
		IntPoint point8 = new IntPoint(1, 1);
		IntPoint point9 = new IntPoint(2, 0);
		IntPoint point10 = new IntPoint(1, 0);
		IntPoint point11 = new IntPoint(2, 1);

		IntPoint[] pointArray = new IntPoint[4];

		pointArray[0] = point1;
		pointArray[1] = point2;
		pointArray[2] = point3;
		pointArray[3] = point4;
		poly1.setRadius(10);
		poly1.setVertices(pointArray);

		assert poly1.contains(point5) == false;
		assert poly1.contains(point6) == false;
		assert poly1.contains(point7) == false;
		assert poly1.contains(point8) == true;
		assert poly1.contains(point9) == true;
		assert poly1.contains(point10) == true;
		assert poly1.contains(point11) == true;

		//Tests for getDrawingCommands
		RoundedPolygon poly2 = new RoundedPolygon();
		IntPoint point12 = new IntPoint(0, 0);
		IntPoint point13 = new IntPoint(10, 0);
		IntPoint point14 = new IntPoint(0, 10);
		IntPoint point15 = new IntPoint(10, 10);
		IntPoint[] pointArray2 = new IntPoint[4];

		pointArray[0] = point12;
		pointArray[1] = point13;
		pointArray[2] = point14;
		pointArray[3] = point15;
		poly2.setRadius(10);
		poly2.setVertices(pointArray2);
		
		assert poly2.getDrawingCommands() == "";
	}

}

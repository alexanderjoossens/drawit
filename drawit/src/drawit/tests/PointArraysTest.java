package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.PointArrays;

public class PointArraysTest {

	@Test
	void test() {
		IntPoint point1 = new IntPoint(1, 1);
		IntPoint point2 = new IntPoint(3, 2);
		IntPoint point3 = new IntPoint(2, 3);
		IntPoint point4 = new IntPoint(1, 3);
		IntPoint point5 = new IntPoint(0, -2);
		IntPoint point6 = new IntPoint(0, 0);
		IntPoint point7 = new IntPoint(0, 2);
		IntPoint point8 = new IntPoint(2, 2);
		IntPoint point9 = new IntPoint(2, 0);
		IntPoint point10 = new IntPoint(2, 0);
		IntPoint point11 = new IntPoint(1, 1);
		IntPoint point12 = new IntPoint(2, 1);
		IntPoint point13 = new IntPoint(1, 0);
		
		IntPoint point14 = new IntPoint(0, 0);
		IntPoint point15 = new IntPoint(2, 0);
		IntPoint point16 = new IntPoint(2, 2);
		IntPoint point17 = new IntPoint(1, 0);
		IntPoint point18 = new IntPoint(2, 1);
		IntPoint point19 = new IntPoint(1, 1);

		
		IntPoint[] pointArray = new IntPoint[4];

		pointArray[0] = point1;
		pointArray[1] = point2;
		pointArray[2] = point3;
		pointArray[3] = point4;

		IntPoint[] pointArray6 = new IntPoint[4];

		pointArray6[0] = point6;
		pointArray6[1] = point7;
		pointArray6[2] = point8;
		pointArray6[3] = point9;

		IntPoint[] pointArray7 = new IntPoint[4];

		pointArray7[0] = point7;
		pointArray7[1] = point8;
		pointArray7[2] = point9;
		pointArray7[3] = point10;

		IntPoint[] pointArray8 = PointArrays.update(pointArray7, 3, point11);
		
		IntPoint[] pointArray9 = new IntPoint[5];

		pointArray9[0] = point6;
		pointArray9[1] = point8;
		pointArray9[2] = point12;
		pointArray9[3] = point11;
		pointArray9[4] = point13;
		
		IntPoint[] pointArray10 = new IntPoint[3];

		pointArray10[0] = point14;
		pointArray10[1] = point15;
		pointArray10[2] = point16;
		
		IntPoint[] pointArray11 = new IntPoint[3];

		pointArray11[0] = point14;
		pointArray11[1] = point15;
		pointArray11[2] = point17;
		
		IntPoint[] pointArray12 = new IntPoint[3];

		pointArray12[0] = point14;
		pointArray12[1] = point15;
		pointArray12[2] = point18;
		
		IntPoint[] pointArray13 = new IntPoint[3];

		pointArray13[0] = point14;
		pointArray13[1] = point15;
		pointArray13[2] = point19;
		

		// copy_Test
		IntPoint[] pointArray2 = PointArrays.copy(pointArray);

		assert pointArray[0].getX() == 1;
		assert pointArray2[0].getX() == 1;
		assert pointArray2[3].getY() == 3;

		// insert_Test
		IntPoint[] pointArray3 = PointArrays.insert(pointArray, 2, point5);
		assert pointArray3.length == 5;
		assert pointArray3[2] == point5;
		assert pointArray3[3] == point3;

		// remove_Test
		IntPoint[] pointArray4 = PointArrays.remove(pointArray3, 2);
		assert pointArray4.length == 4;
		assert pointArray4[2] == point3;
		assert pointArray4[3] == point4;

		// update_Test
		IntPoint[] pointArray5 = PointArrays.update(pointArray, 2, point5);
		assert pointArray5.length == 4;
		assert pointArray5[1] == point2;
		assert pointArray5[2] == point5;
		assert pointArray5[3] == point4;

		// checkDefinesProperPolygon_Test
		assert PointArrays.checkDefinesProperPolygon(pointArray) == null;
		assert PointArrays.checkDefinesProperPolygon(pointArray6) == null;
		assert PointArrays.checkDefinesProperPolygon(pointArray3) == "Lines intersect!";
		assert PointArrays.checkDefinesProperPolygon(pointArray7) == "2 vertices coincide!";
		assert PointArrays.checkDefinesProperPolygon(pointArray8) == null;
		System.out.println(PointArrays.checkDefinesProperPolygon(pointArray9));
		assert PointArrays.checkDefinesProperPolygon(pointArray9) == "There is a vertex on an edge!";
		assert PointArrays.checkDefinesProperPolygon(pointArray10) == null;
		assert PointArrays.checkDefinesProperPolygon(pointArray11) == "There is a vertex on an edge!";
		assert PointArrays.checkDefinesProperPolygon(pointArray12) == "There is a vertex on an edge!";
		assert PointArrays.checkDefinesProperPolygon(pointArray13) == "There is a vertex on an edge!";



		
		
	}

}

package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DoublePointTest {

	@Test
	void test() {
		DoublePoint point1= new DoublePoint(4.4 , 3.6);
		assert point1.getX() == 4.4 && point1.getY() == 3.6;
		
		IntPoint point2 = point1.round();
		assert point2.getX() == 4 && point2.getY() == 4;
		
		DoublePoint point3 = new DoublePoint(3.5, -1.5);
		assert point3.getY() == -1.5;
		IntPoint point4 = point3.round();
		assert point4.getX() == 4 && point4.getY() == -1;
		
		DoubleVector point5 = new DoubleVector(3.5, -1.5);
		point3 = point1.plus(point5);
		assert point3.getX() == 7.9 && point3.getY() == 2.1;
		point5 = point1.minus(point3);
		assert point5.getX() == -3.5 && point5.getY() == 1.5;
		
	}

}



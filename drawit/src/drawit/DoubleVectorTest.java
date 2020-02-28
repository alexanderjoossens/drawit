package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DoubleVectorTest {

	@Test
	void test() {
		DoubleVector point1 = new DoubleVector(2.1, 2.1);
		assert point1.getX() == 2.1 && point1.getY() == 2.1;
		assert point1.asAngle() == Math.PI/4;
		
		DoubleVector point2 = new DoubleVector(2, 3);
		double x = point1.crossProduct(point2);
		//System.out.println(p);
		assert x == 2.1000000000000005; //wat doen we hiermee?
		
		x = point1.dotProduct(point2);
		assert x == 10.5;
		
		
	}
}

package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DoubleVectorTest {

	@Test
	void test() {
		DoubleVector vector1 = new DoubleVector(2.5, 2.5);
		assert vector1.getX() == 2.5 && vector1.getY() == 2.5;
		assert vector1.asAngle() == Math.PI/4;
		
		DoubleVector vector2 = new DoubleVector(2, 3);
		double product = vector1.crossProduct(vector2);
		assert product == 2.5;
		
		product = vector1.dotProduct(vector2);
		assert product == 12.5;
		
		double size = vector1.getSize();
		assert size == Math.sqrt(2.5*2.5*2);
		
		DoubleVector vector3 = new DoubleVector(0,0);
		vector3 = vector1.plus(vector2);
		assert vector3.getX() == 4.5 && vector3.getY() == 5.5;
		
		
		
		
	}
}

package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.DoubleVector;
import drawit.IntVector;

public class IntVectorTest {

	@Test
	void test() {
		
		IntVector vector1 = new IntVector(2, 2);
		assert vector1.getX() == 2 && vector1.getY() == 2;
		
		DoubleVector vector2 = vector1.asDoubleVector();
		assert vector2.getX() == 2 && vector2.getY() == 2;
		
		IntVector vector3 = new IntVector(1,3);
		long product = vector1.crossProduct(vector3);
		assert product == 4;
		
		product = vector1.dotProduct(vector3);
		assert product == 8;
		
		IntVector vector5 = new IntVector(1,1);
		boolean bool = vector1.isCollinearWith(vector5);
		assert bool == true;
		bool = vector3.isCollinearWith(vector5);
		assert bool == false;
		
	}

}

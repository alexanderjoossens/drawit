package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DoublePointTest {

	@Test
	void test() {
		DoublePoint point1= new DoublePoint(4.4 , 3.4);
		assert point1.getX() == 4.4;
		
		IntPoint point2 = point1.round();
		
		assert point2.getX() == 4;
		
		DoublePoint point3 = new DoublePoint(3.5, -1.5);
		assert point3.getY() == -1.5;
		IntPoint point4 = point3.round();
		assert point4.getX() == 4 && point4.getY() == -1;
		
		
		
		
	}

}



package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DoublePointTest {

	@Test
	void test() {
		DoublePoint point = new DoublePoint(4.4 , 3.4);
//		assert point.getX() == 4;
		
		IntPoint point2 = new DoublePoint(4 , 3).round();
		
		assert point2.getX() == 4;
		
		
	}

}



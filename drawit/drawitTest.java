import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class drawitTest {

	@Test
	void test() {
		double x = 2;
		double y = 3;
		DoublePoint point1 = new DoublePoint(x, y);
		assert point1.getX() == x;
	}

}


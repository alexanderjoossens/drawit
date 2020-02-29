package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IntPointTest {

	@Test
	void test() {
		IntPoint point1 = new IntPoint(1,1);
		IntPoint point2 = new IntPoint(3,2);
		IntPoint point3 = new IntPoint(2,3);
		IntPoint point4 = new IntPoint(1,3);
		assert point1.getX() == 1;
		System.out.println(IntPoint.lineSegmentsIntersect(point1,point2, point2,point3));
		assert point3.isOnLineSegment(point2, point4) == false;
		
		
		
	}

}

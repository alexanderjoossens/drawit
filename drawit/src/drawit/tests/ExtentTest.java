package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.shapegroups1.Extent;

class ExtentTest {

	// tests voor Override methods
	
	@Test
	void test() {
		Extent extent1 = new Extent();
		Extent extent2 = new Extent();
		
		
		extent1.left = 10;
		extent1.right = 20;
		extent1.top = 10;
		extent1.bottom = 20;
		
		extent2.left = 10;
		extent2.right = 20;
		extent2.top = 10;
		extent2.bottom = 20;
		
		assert extent1.equals(extent2);
		
		
		
	}

}

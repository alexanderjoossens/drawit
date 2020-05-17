package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import drawit.shapegroups1.Extent;

class ExtentTest {

	// tests voor Override methods
	
	@Test
	void test() {
		Extent extent1 = new Extent();
		Extent extent2 = new Extent();
		
		//dit is fout, hoe zet ik die fields naar die waarden?
//		extent1.left = 10;
//		extent1.right = 20;
//		extent1.top = 10;
//		extent1.bottom = 20;
//		
//		extent2.left = 10;
//		extent2.right = 20;
//		extent2.top = 10;
//		extent2.bottom = 20;
		
		// test voor overridden method equals()
		assert extent1.equals(extent2);
		assertTrue(extent1.getClass() == Extent.class);
		
		// test voor overridden method hashCode()
		HashSet<Object> set = new HashSet<>();
		set.add(extent1);
		assertTrue(set.contains(extent2));
		
		// test voor overridden method toString()
		assertEquals("10 10 20 20", extent1);
		
	}

}

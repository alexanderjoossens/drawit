package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.ShapeGroup;

class Part5Test {

	// tests voor Override methods
	
	@Test
	void test() {
		Extent extent1 = new Extent();
		Extent extent2 = new Extent();
		
		// test voor overridden method equals()
		assert extent1.equals(extent2);
		assertTrue(extent1.getClass() == Extent.class);
		
		// test voor overridden method hashCode()
		HashSet<Object> set = new HashSet<>();
		set.add(extent1);
		assertTrue(set.contains(extent2));
		
		// test voor overridden method toString()
		RoundedPolygon triangle = new RoundedPolygon();
		triangle.setVertices(new IntPoint[] {new IntPoint(10, 10), new IntPoint(30, 10), new IntPoint(20, 20)});
		ShapeGroup leaf1 = new LeafShapeGroup(triangle);
		leaf1.setExtent(Extent.ofLeftTopRightBottom(10, 10, 40, 40));
		ShapeGroup leaf2 = new LeafShapeGroup(triangle);
		leaf2.setExtent(Extent.ofLeftTopWidthHeight(10, 10, 30, 30));

		assertEquals("Extent [left=10, top=10, right=40, bottom=40]", leaf1.getExtent().toString());
		assertEquals(leaf2.getExtent().toString(), leaf1.getExtent().toString());
		
	}

}

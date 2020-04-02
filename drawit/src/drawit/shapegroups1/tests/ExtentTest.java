package drawit.shapegroups1.tests;

import static org.junit.jupiter.api.Assertions.*;
import drawit.shapegroups2.*;

import drawit.*;
import drawit.shapegroups1.Extent;

import org.junit.jupiter.api.Test;

class ExtentTest {

	@Test
	void test() {
		
		RoundedPolygon triangle = new RoundedPolygon();
		triangle.setVertices(new IntPoint[] {new IntPoint(10, 10), new IntPoint(30, 10), new IntPoint(20, 20)});

		ShapeGroup leaf = new ShapeGroup(triangle);
		assert leaf.getExtent().getTopLeft().equals(new IntPoint(10, 10));
		assert leaf.getExtent().getBottomRight().equals(new IntPoint(30, 20));
		leaf.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 20, 10));

		// For simplicity, we here ignore the constraint that a non-leaf ShapeGroup shall have at least two subgroups.
		ShapeGroup nonLeaf = new ShapeGroup(new ShapeGroup[] {leaf});
		assert nonLeaf.getExtent().getTopLeft().equals(new IntPoint(0, 0)) && nonLeaf.getExtent().getBottomRight().equals(new IntPoint(20, 10));
		nonLeaf.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 10, 5));
		
		
		
		
////		Extent extent1 = new Extent();
////		extent1.setExtent(10, 20, 10, 20);
////		
////		IntPoint point1 = new IntPoint (15, 15);
////		IntPoint point2 = new IntPoint (10, 10);
////		IntPoint point3 = new IntPoint (25, 25);
////		IntPoint bottomright1 = new IntPoint(20, 20);
////		IntPoint topleft1 = new IntPoint(10, 10);
//		
//		
////		assert extent1.contains(point1);
////		assert extent1.contains(point2);
////		assert !extent1.contains(point3);
//		
//		assert extent1.getBottom() == 20;
////		assert extent1.getBottomRight() == bottomright1;
//		assert extent1.getHeight() == 20-10;
//		assert extent1.getLeft() == 10;
//		assert extent1.getRight() == 20;
//		assert extent1.getTop() == 10;
////		assert extent1.getTopLeft() == topleft1;
//		assert extent1.getWidth() == 20-10;
//		
//		Extent extent2 = extent1.withBottom(5);
//		Extent extent3 = extent1.withHeight(5);
//		Extent extent4 = extent1.withLeft(5);
//		Extent extent5 = extent1.withRight(5);
//		Extent extent6 = extent1.withTop(80);
//		Extent extent7 = extent1.withWidth(80);
//		
//		assert extent2.getBottom() == 5;
//		assert extent3.getHeight() == 5;
//		assert extent4.getLeft() == 5;
//		assert extent5.getRight() == 5;
//		assert extent6.getTop() == 80;
//		assert extent7.getRight() == 90;
//		
	}

}

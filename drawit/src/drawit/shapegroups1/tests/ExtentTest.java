package drawit.shapegroups1.tests;

import static org.junit.jupiter.api.Assertions.*;
import drawit.shapegroups1.*;

import drawit.shapegroups1.Extent;
import drawit.shapegroups1.ShapeGroup;

import drawit.*;

import org.junit.jupiter.api.Test;

class ExtentTest {

	@Test
	void test() {

		RoundedPolygon triangle = new RoundedPolygon();
		triangle.setVertices(new IntPoint[] {new IntPoint(10, 10), new IntPoint(30, 10), new IntPoint(20, 20)});

		ShapeGroup leaf1 = new LeafShapeGroup(triangle);
		assert leaf1.getExtent().getTopLeft().equals(new IntPoint(10, 10)) && leaf1.getExtent().getBottomRight().equals(new IntPoint(30, 20));
		leaf1.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 20, 10));
		
		ShapeGroup leaf2 = new LeafShapeGroup(triangle);
		assert leaf2.getExtent().getTopLeft().equals(new IntPoint(10, 10)) && leaf2.getExtent().getBottomRight().equals(new IntPoint(30, 20));
		leaf2.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 20, 10));

		// For simplicity, we here ignore the constraint that a non-leaf ShapeGroup shall have at least two subgroups.
		ShapeGroup nonLeaf = new NonleafShapeGroup(new ShapeGroup[] {leaf1, leaf2});
		assert nonLeaf.getExtent().getTopLeft().equals(new IntPoint(0, 0));
		assert nonLeaf.getExtent().getBottomRight().equals(new IntPoint(20, 10));
		nonLeaf.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 10, 5));
		
		//
		RoundedPolygon square = new RoundedPolygon();
		square.setVertices(new IntPoint[] {new IntPoint(10, 10), new IntPoint(20, 10), new IntPoint(20, 20), new IntPoint(10, 20)});
		RoundedPolygon rectangle = new RoundedPolygon();
		rectangle.setVertices(new IntPoint[] {new IntPoint(10, 20), new IntPoint(30, 20), new IntPoint(30, 10), new IntPoint(10, 10)});
		
		ShapeGroup leaf22 = new LeafShapeGroup(square);
		assert leaf22.getExtent().getTopLeft().equals(new IntPoint(10, 10)) && leaf22.getExtent().getBottomRight().equals(new IntPoint(20, 20));
		leaf22.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 50, 50));
		ShapeGroup leaf3 = new LeafShapeGroup(rectangle);
		assert leaf3.getExtent().getTopLeft().equals(new IntPoint(10, 10));
		assert leaf3.getExtent().getBottomRight().equals(new IntPoint(30, 20));
		leaf3.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 60, 60));
		
		ShapeGroup nonLeaf2 = new NonleafShapeGroup(new ShapeGroup[] {leaf22, leaf3});
		assert nonLeaf.getExtent().getTopLeft().equals(new IntPoint(0, 0));
		assert nonLeaf2.getExtent().getBottomRight().equals(new IntPoint(60, 60));
		nonLeaf2.setExtent(Extent.ofLeftTopRightBottom(0, 0, 70, 70));
		assert nonLeaf2.getExtent().getBottomRight().equals(new IntPoint(70, 70));
		
		//tests for getters
		IntPoint point1 = new IntPoint(10,10);
		IntPoint point2 = new IntPoint(15,15);
		assert leaf22.getExtent().contains(point1);
		assert leaf22.getExtent().contains(point2);
		assert leaf22.getExtent().withBottom(80).getBottom() == 80;
		assert leaf22.getExtent().withHeight(90).getHeight() == 90;
		assert leaf22.getExtent().withLeft(5).getLeft() == 5;
		assert leaf22.getExtent().withRight(45).getRight() == 45;
		assert leaf22.getExtent().withTop(15).getTop() == 15;
		assert leaf22.getExtent().withWidth(50).getWidth() == 50;
		assert leaf22.getExtent().withHeight(65).getHeight() == 65;
		
		//test voor shapegroup
		assert nonLeaf.getOriginalExtent().getBottomRight().equals(new IntPoint(20, 10));
		
	}

}

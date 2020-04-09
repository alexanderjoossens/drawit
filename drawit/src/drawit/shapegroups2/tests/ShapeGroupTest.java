package drawit.shapegroups2.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups2.Extent;
import drawit.shapegroups2.ShapeGroup;

class ShapeGroupTest {

	@Test
	void test() {
		RoundedPolygon triangle = new RoundedPolygon();
		triangle.setVertices(new IntPoint[] { new IntPoint(8, 10), new IntPoint(9, 8), new IntPoint(10, 10) });

		ShapeGroup leaf = new ShapeGroup(triangle);
		assert leaf.getExtent().getTopLeft().equals(new IntPoint(8, 8))
				&& leaf.getExtent().getBottomRight().equals(new IntPoint(10, 10));
		leaf.setExtent(Extent.ofLeftTopWidthHeight(2, 2, 3, 3));

		ShapeGroup nonLeaf = new ShapeGroup(new ShapeGroup[] { leaf });
		assert nonLeaf.getExtent().getTopLeft().equals(new IntPoint(2, 2));
		assert nonLeaf.getExtent().getBottomRight().equals(new IntPoint(5, 5));
		nonLeaf.setExtent(Extent.ofLeftTopWidthHeight(0, 9, 3, 1));
		assert nonLeaf.getExtent().getTopLeft().equals(new IntPoint(0, 9));
		assert nonLeaf.getExtent().getBottomRight().equals(new IntPoint(3, 10));

		RoundedPolygon polygon1 = new RoundedPolygon();
		polygon1.setVertices(
				new IntPoint[] { new IntPoint(10, 10), new IntPoint(10, 8), new IntPoint(9, 8), new IntPoint(9, 10) });

		RoundedPolygon polygon2 = new RoundedPolygon();
		polygon2.setVertices(
				new IntPoint[] { new IntPoint(8, 10), new IntPoint(10, 10), new IntPoint(10, 9), new IntPoint(8, 9) });

		ShapeGroup leaf1 = new ShapeGroup(polygon1);
		ShapeGroup leaf2 = new ShapeGroup(polygon2);
		
		ShapeGroup[] tempArray = new ShapeGroup[] { leaf1, leaf2 };
		ShapeGroup nonLeaf1 = new ShapeGroup(tempArray);
		assert nonLeaf1.getExtent().getTopLeft().equals(new IntPoint(8, 8));
		assert nonLeaf1.getExtent().getBottomRight().equals(new IntPoint(10, 10));
		nonLeaf1.setExtent(Extent.ofLeftTopWidthHeight(1, 4, 4, 4));
		assert nonLeaf1.getExtent().getTopLeft().equals(new IntPoint(1, 4));
		assert nonLeaf1.getExtent().getBottomRight().equals(new IntPoint(5, 8));
		
		assert leaf2.getParentGroup().equals(nonLeaf1);
		
		assert nonLeaf1.getSubgroupCount() == 2;
		assert nonLeaf1.getSubgroups()[0].equals(leaf1);
		assert nonLeaf1.getSubgroups()[1].equals(leaf2);
		assert nonLeaf1.getSubgroup(0).equals(leaf1);
		assert nonLeaf1.getSubgroup(1).equals(leaf2);
		assert nonLeaf.getSubgroupAt(new IntPoint(8,10)).equals(leaf2);
		
		
		leaf2.bringToFront();
		assert nonLeaf1.getSubgroupCount() == 2;
		assert nonLeaf1.getSubgroups()[0].equals(leaf2);
		assert nonLeaf1.getSubgroups()[1].equals(leaf1);
		
		leaf2.sendToBack();
//		assert nonLeaf1.getSubgroupCount() == 2;
//		assert nonLeaf1.getSubgroups()[0].equals(leaf1);
//		assert nonLeaf1.getSubgroup(1).equals(leaf2);
//		
		
		


		
		
		
//		
//		//
//		RoundedPolygon square = new RoundedPolygon();
//		square.setVertices(new IntPoint[] {new IntPoint(10, 10), new IntPoint(20, 10), new IntPoint(20, 20), new IntPoint(10, 20)});
//		RoundedPolygon rectangle = new RoundedPolygon();
//		rectangle.setVertices(new IntPoint[] {new IntPoint(10, 20), new IntPoint(30, 20), new IntPoint(30, 10), new IntPoint(10, 10)});
//		
//		ShapeGroup leaf2 = new ShapeGroup(square);
//		assert leaf2.getExtent().getTopLeft().equals(new IntPoint(10, 10)) && leaf2.getExtent().getBottomRight().equals(new IntPoint(20, 20));
//		leaf2.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 50, 50));
//		ShapeGroup leaf3 = new ShapeGroup(rectangle);
//		assert leaf3.getExtent().getTopLeft().equals(new IntPoint(10, 10));
//		assert leaf3.getExtent().getBottomRight().equals(new IntPoint(30, 20));
//		leaf3.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 60, 60));
//		
//		ShapeGroup nonLeaf2 = new ShapeGroup(new ShapeGroup[] {leaf2, leaf3});
//		assert nonLeaf.getExtent().getTopLeft().equals(new IntPoint(0, 0));
//		assert nonLeaf2.getExtent().getBottomRight().equals(new IntPoint(60, 60));
//		nonLeaf2.setExtent(Extent.ofLeftTopRightBottom(0, 0, 70, 70));
//		assert nonLeaf2.getExtent().getBottomRight().equals(new IntPoint(70, 70));
//		
//
//		
//		//test voor shapegroup
//		assert nonLeaf.getOriginalExtent().getBottomRight().equals(new IntPoint(20, 10));
//		
//		//test die alexander gestolen heeft van iemand
//		RoundedPolygon triangle88 = new RoundedPolygon();
//		triangle88.setVertices(new IntPoint[] {new IntPoint(10, 10), new IntPoint(30, 10), new IntPoint(20, 20)});
//		
//		ShapeGroup leaf88 = new ShapeGroup(triangle88);
//		ShapeGroup nonLeaf88 = new ShapeGroup(new ShapeGroup[] {leaf88});
//		nonLeaf88.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 10, 5));
//		
//		//deze test faalt, is onze functie dan fout??
//		assert leaf88.toInnerCoordinates(new IntPoint(500,1000)).equals(new IntPoint(10,10));
//		assert leaf88.toInnerCoordinates(new IntPoint(600,1300)).equals(new IntPoint(210,610));
//		assert leaf88.toGlobalCoordinates(new IntPoint(10, 10)).equals(new IntPoint(500, 1000));
	}

}

package drawit.shapegroups1.tests;

import static org.junit.jupiter.api.Assertions.*;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.NonleafShapeGroup;
import drawit.shapegroups1.ShapeGroup;

import org.junit.jupiter.api.Test;

class ShapeGroupTest {

	@Test
	void test() {
		RoundedPolygon triangle = new RoundedPolygon();
		triangle.setVertices(new IntPoint[] { new IntPoint(8, 10), new IntPoint(9, 8), new IntPoint(10, 10) });

		ShapeGroup leaf = new LeafShapeGroup(triangle);
		assert leaf.getExtent().getTopLeft().equals(new IntPoint(8, 8))
				&& leaf.getExtent().getBottomRight().equals(new IntPoint(10, 10));
		leaf.setExtent(Extent.ofLeftTopWidthHeight(2, 2, 3, 3));

		ShapeGroup nonLeaf = new NonleafShapeGroup(new ShapeGroup[] { leaf });
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

		ShapeGroup leaf1 = new LeafShapeGroup(polygon1);
		ShapeGroup leaf2 = new LeafShapeGroup(polygon2);

		ShapeGroup[] tempArray = new ShapeGroup[] { leaf1, leaf2 };
		ShapeGroup nonLeaf1 = new NonleafShapeGroup(tempArray);
		assert nonLeaf1.getExtent().getTopLeft().equals(new IntPoint(8, 8));
		assert nonLeaf1.getExtent().getBottomRight().equals(new IntPoint(10, 10));
		nonLeaf1.setExtent(Extent.ofLeftTopWidthHeight(1, 4, 4, 4));
		assert nonLeaf1.getExtent().getTopLeft().equals(new IntPoint(1, 4));
		assert nonLeaf1.getExtent().getBottomRight().equals(new IntPoint(5, 8));

		assert leaf2.getParentGroup().equals(nonLeaf1);

		assert ((NonleafShapeGroup)nonLeaf1).getSubgroup(0).equals(leaf1);
		assert ((NonleafShapeGroup)nonLeaf1).getSubgroup(1).equals(leaf2);

//		((LeafShapeGroup)leaf2).bringToFront();
//		assert nonLeaf1.getSubgroupCount() == 2;
//		assert nonLeaf1.getSubgroup(0).equals(leaf2);
//		assert nonLeaf1.getSubgroup(1).equals(leaf1);
//
//		leaf2.sendToBack();
//		assert nonLeaf1.getSubgroupCount() == 2;
//		assert nonLeaf1.getSubgroup(1).equals(leaf2);
//		assert nonLeaf1.getSubgroup(0).equals(leaf1);

		RoundedPolygon polygon3 = new RoundedPolygon();
		polygon1.setVertices(
				new IntPoint[] { new IntPoint(10, 10), new IntPoint(10, 8), new IntPoint(9, 8)});

		RoundedPolygon polygon4 = new RoundedPolygon();
		polygon2.setVertices(
				new IntPoint[] { new IntPoint(8, 10), new IntPoint(10, 10), new IntPoint(10, 9)});
		
		RoundedPolygon polygon5 = new RoundedPolygon();
		polygon1.setVertices(
				new IntPoint[] { new IntPoint(0, 10), new IntPoint(0, 8), new IntPoint(9, 8)});

		ShapeGroup leaf3 = new LeafShapeGroup(polygon3);
		ShapeGroup leaf4 = new LeafShapeGroup(polygon4);
		ShapeGroup leaf5 = new LeafShapeGroup(polygon5);

		
		
		ShapeGroup[] tempArray2 = new ShapeGroup[] { leaf3, leaf4, leaf5 };
		ShapeGroup nonLeaf2 = new ShapeGroup(tempArray2);
		
		assert nonLeaf2.getSubgroupCount() == 3;
		assert nonLeaf2.getSubgroup(0).equals(leaf3);
		assert nonLeaf2.getSubgroup(1).equals(leaf4);
		assert nonLeaf2.getSubgroup(2).equals(leaf5);

		leaf4.bringToFront();
		assert nonLeaf2.getSubgroup(0).equals(leaf4);
		assert nonLeaf2.getSubgroup(1).equals(leaf3);
		assert nonLeaf2.getSubgroup(2).equals(leaf5);
		
		leaf5.bringToFront();
		assert nonLeaf2.getSubgroup(0).equals(leaf5);
		assert nonLeaf2.getSubgroup(1).equals(leaf4);
		assert nonLeaf2.getSubgroup(2).equals(leaf3);
		
		leaf4.sendToBack();
		assert nonLeaf2.getSubgroup(0).equals(leaf5);
		assert nonLeaf2.getSubgroup(1).equals(leaf3);
		assert nonLeaf2.getSubgroup(2).equals(leaf4);
		
		
	}

}

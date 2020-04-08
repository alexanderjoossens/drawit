package drawit.shapegroups1.tests;

import static org.junit.jupiter.api.Assertions.*;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.*;

import org.junit.jupiter.api.Test;

class ShapeGroupTest {

	@Test
	void test() {
		
		//test die alexander gestolen heeft van iemand
		RoundedPolygon triangle = new RoundedPolygon();
		triangle.setVertices(new IntPoint[] {new IntPoint(10, 10), new IntPoint(30, 10), new IntPoint(20, 20)});
		
		ShapeGroup leaf = new ShapeGroup(triangle);
		ShapeGroup nonLeaf = new ShapeGroup(new ShapeGroup[] {leaf});
		nonLeaf.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 10, 5));
		
		//deze test faalt, is onze functie dan fout??
		assert leaf.toInnerCoordinates(new IntPoint(500,1000)).equals(new IntPoint(10,10));
		assert leaf.toInnerCoordinates(new IntPoint(600,1300)).equals(new IntPoint(210,610));
		assert leaf.toGlobalCoordinates(new IntPoint(10, 10)).equals(new IntPoint(500, 1000));
		
		
	}

}

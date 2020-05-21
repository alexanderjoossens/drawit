package drawit.shapes1.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.NonleafShapeGroup;
import drawit.shapegroups1.ShapeGroup;
import drawit.shapes1.ControlPoint;
import drawit.shapes1.Shape;
import drawit.shapes1.ShapeGroupShape;

class ShapeTest {

	@Test
	void test() {
		RoundedPolygon polygon1 = new RoundedPolygon();
		polygon1.setVertices(
				new IntPoint[] { new IntPoint(0, 10), new IntPoint(10, 10), new IntPoint(10, 0), new IntPoint(0, 0) });

		RoundedPolygon polygon2 = new RoundedPolygon();
		polygon2.setVertices(
				new IntPoint[] { new IntPoint(0, 5), new IntPoint(5, 5), new IntPoint(5, 0), new IntPoint(0, 0) });

		ShapeGroup leaf1 = new LeafShapeGroup(polygon1);
		ShapeGroup leaf2 = new LeafShapeGroup(polygon2);

		ShapeGroup[] tempArray = new ShapeGroup[] { leaf1, leaf2 };
		ShapeGroup nonLeaf1 = new NonleafShapeGroup(tempArray);
		
		Shape sgs1 = new ShapeGroupShape(nonLeaf1);
		Shape sgs2 = new ShapeGroupShape(leaf1);
		Shape sgs3 = new ShapeGroupShape(leaf1);

		
		// getParent
		assert sgs1.getParent()==null;
		assert sgs2.getParent() == nonLeaf1;
		
		//getShapeggroup
		assert ((ShapeGroupShape) sgs1).getShapeGroup() == nonLeaf1;
		
		//contains
		assert sgs1.contains(new IntPoint(5, 5)) == true;
		assert sgs1.contains(new IntPoint(50, 5)) == false;
		
		
		leaf2.setExtent(Extent.ofLeftTopWidthHeight(0, 0, 10, 10));
		assert sgs3.toGlobalCoordinates(new IntPoint(5,5)).getX()==5;
		assert sgs3.toGlobalCoordinates(new IntPoint(5,5)).getY()==5;
		
		assert sgs3.toShapeCoordinates(new IntPoint(10,10)).getX()==10;
		assert sgs3.toShapeCoordinates(new IntPoint(10,10)).getY()==10;
		
		
		ControlPoint[] cp = sgs1.createControlPoints();
		assert cp[0].getLocation().getX() == 0;
		assert cp[0].getLocation().getY() == 0;

		assert cp[1].getLocation().getX() == 10;
		assert cp[1].getLocation().getY() == 10;
		
		ControlPoint[] cp3 = sgs3.createControlPoints();
		assert cp3[0].getLocation().getX() == 0;
		assert cp3[0].getLocation().getY() == 0;

		assert cp3[1].getLocation().getX() == 10;
		assert cp3[1].getLocation().getY() == 10;

		
		


		
		


		
		

		
		
		

		
		
	}

}

package drawit.shapegroups1.exporter;
import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.*;
import drawit.shapegroups1.exporter.*;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.awt.Color;
import java.util.Arrays;


import org.junit.jupiter.api.Test;

class testje {

	@Test
	void test() {
		RoundedPolygon polygon1 = new RoundedPolygon();
		polygon1.setVertices(new IntPoint[] { new IntPoint(40, 40), new IntPoint(50, 40), new IntPoint(40, 50) });
		ShapeGroup leaf1 = new LeafShapeGroup(polygon1);
		leaf1.setExtent(Extent.ofLeftTopRightBottom(40, 50, 60, 70));
		
		
		RoundedPolygon polygon2 = new RoundedPolygon();
		polygon2.setVertices(new IntPoint[] { new IntPoint(45, 45), new IntPoint(55, 45), new IntPoint(45, 55) });
		ShapeGroup leaf2 = new LeafShapeGroup(polygon2);
		leaf2.setExtent(Extent.ofLeftTopRightBottom(45, 55, 65, 75));
//
//
//		
//		ShapeGroup[] leafs = new ShapeGroup[] {leaf1, leaf2};
//		ShapeGroup nonleaf = new NonleafShapeGroup(leafs);
//		nonleaf.setExtent(Extent.ofLeftTopRightBottom(5, 7, 99, 88));
		
		System.out.print(ShapeGroupExporter.toPlainData(((LeafShapeGroup)leaf2)));
		
		assertEquals(ShapeGroupExporter.toPlainData(((LeafShapeGroup)leaf2)),
				
		        Map.of(
			            "originalExtent", Map.of("left", 45, "top", 45, "right", 55, "bottom", 55),
			            "extent", Map.of("left", 45, "top", 55, "right", 65, "bottom", 75),
			            "shape", Map.of(
			                "vertices", List.of(
			                    Map.of("x", 45, "y", 45),
			                    Map.of("x", 55, "y", 45),
			                    Map.of("x", 45, "y", 55)),
			                "radius", 0,
			                "color", Map.of("red", 255, "green", 255, "blue", 0))));
	}

}
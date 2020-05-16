package drawit.shapegroups1.exporter;
import drawit.IntPoint;
import drawit.RoundedPolygon;
import drawit.shapegroups1.*;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExporterTest {

	@Test
	void test() {
		RoundedPolygon leaf1 = new RoundedPolygon();
		leaf1.setVertices(new IntPoint[] { new IntPoint(40, 40), new IntPoint(50, 40), new IntPoint(40, 50) });
		RoundedPolygon leaf2 = new RoundedPolygon();
		leaf2.setVertices(new IntPoint[] { new IntPoint(45, 45), new IntPoint(55, 45), new IntPoint(45, 55) });

		ShapeGroup nonleaf = new ShapeGroup(leaf1, leaf2);
		leaf1.setExtent(Extent.ofLeftTopWidthHeight(2, 2, 3, 3));
		
	}

}

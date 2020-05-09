package drawit.shapegroups1.exporter;

import java.util.Map;

import drawit.shapegroups1.ShapeGroup;

public class ShapeGroupExporter {

	public static Object toPlainData(ShapeGroup shapeGroup) {
		
		return Map.of(
				"originalExtent", Map.of(
						"left", shapeGroup.getOriginalExtent().getLeft(), 
						"top", shapeGroup.getOriginalExtent().getTop(),
						"right", shapeGroup.getOriginalExtent().getRight(),
						"bottom", shapeGroup.getOriginalExtent().getBottom()));
		
	}
	
}

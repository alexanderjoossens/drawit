package drawit.shapegroups1.exporter;

import java.util.Map;
import drawit.shapegroups1.*;

import drawit.shapegroups1.ShapeGroup;

public class ShapeGroupExporter {

	public static Object toPlainData(ShapeGroup shapeGroup) {
		
		// base case: leaf group
		if (shapeGroup.getParentGroup() == null) {
			
			String result = "";
			for (int i; shapeGroup.getVertices(); i++)
				result += Map.of("x", getVertices()[i].getX,
								 "y", getVertices()[i].getY);
		
		return result;
				
		}
		
		return Map.of(
				"originalExtent", Map.of(
						"left", shapeGroup.getOriginalExtent().getLeft(), 
						"top", shapeGroup.getOriginalExtent().getTop(),
						"right", shapeGroup.getOriginalExtent().getRight(),
						"bottom", shapeGroup.getOriginalExtent().getBottom())
				, "extent", Map.of(	"left", shapeGroup.getOriginalExtent().getLeft(), 
									"top", shapeGroup.getOriginalExtent().getTop(),
									"right", shapeGroup.getOriginalExtent().getRight(),
									"bottom", shapeGroup.getOriginalExtent().getBottom())
				, "subgroups", List.of(shapeGroup.getParentGroup().toPlainData)
				
	}
	
}

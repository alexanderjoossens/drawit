package drawit.shapegroups1.exporter;

import java.util.List;
import java.util.Map;
import drawit.shapegroups1.*;

import drawit.shapegroups1.ShapeGroup;

public class ShapeGroupExporter {

	// recursieve functie, moet van alle extents van leafgroups de coordinaten printen
	public static Object toPlainData(ShapeGroup shapeGroup) {
		
		// base case: leaf group
		if (shapeGroup.getParentGroup() == null) {
			
			String result = "";
			for (int i; shapeGroup.getVertices(); i++)
				result += Map.of("x", getVertices()[i].getX,
								 "y", getVertices()[i].getY);
		
		return result;
				
		}
		
		//recursieve oproep voor 1 verder in de boom
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
				, "subgroups", List.of(shapeGroup.getParentGroup().toPlainData());
				
	}
	
}

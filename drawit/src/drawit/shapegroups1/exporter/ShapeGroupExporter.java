package drawit.shapegroups1.exporter;

import java.util.List;
import java.util.Map;
import drawit.shapegroups1.*;

public class ShapeGroupExporter {

	// recursieve functie
	public static Object toPlainData(ShapeGroup shapeGroup) {
		
		// base case: leaf group
		if (shapeGroup.getParentGroup() == null) {
			
			String result = "";
			result += "originalExtent" + Map.of(
					  		"left", shapeGroup.getOriginalExtent().getLeft(), 
					  		"top", shapeGroup.getOriginalExtent().getTop(),
					  		"right", shapeGroup.getOriginalExtent().getRight(),
					  		"bottom", shapeGroup.getOriginalExtent().getBottom());
			result += "extent" + Map.of("left", shapeGroup.getExtent().getLeft(), 
			        		 			"top", shapeGroup.getExtent().getTop(),
			        		 			"right", shapeGroup.getExtent().getRight(),
			        		 			"bottom", shapeGroup.getExtent().getBottom());
			result += "shape";
			for (int i; ((LeafShapeGroup)shapeGroup).getShape().getVertices()[i] != null; i++)
					result += Map.of("x", ((LeafShapeGroup)shapeGroup).getShape().getVertices()[i].getX(),
									 "y", ((LeafShapeGroup)shapeGroup).getShape().getVertices()[i].getY());
		
			result += "radius" + ((LeafShapeGroup)shapeGroup).getRadius();
			result += "color" + ((LeafShapeGroup)shapeGroup).getColor();
			
			return result;
				
		}
		
		//recursieve oproep voor 1 nonleafgroup verder in de boom
		String result = "";
		result += "originalExtent" + Map.of(
				  		"left", shapeGroup.getOriginalExtent().getLeft(), 
				  		"top", shapeGroup.getOriginalExtent().getTop(),
				  		"right", shapeGroup.getOriginalExtent().getRight(),
				  		"bottom", shapeGroup.getOriginalExtent().getBottom());
		result += "extent" + Map.of("left", shapeGroup.getExtent().getLeft(), 
		        		 			"top", shapeGroup.getExtent().getTop(),
		        		 			"right", shapeGroup.getExtent().getRight(),
		        		 			"bottom", shapeGroup.getExtent().getBottom());
		result += "subgroups";
		for (int i; ((NonleafShapeGroup)shapeGroup).getSubgroup(i) != null; i++)
				result += toPlainData(((NonleafShapeGroup)shapeGroup).getSubgroup(i));
		
		return result;
				
	}
	
}

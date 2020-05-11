package drawit.shapegroups1.exporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import drawit.IntPoint;
import drawit.shapegroups1.*;

public class ShapeGroupExporter {

	// recursieve functie
	public static Object toPlainData(ShapeGroup shapeGroup) {
		
		// base case: leaf group
		if (shapeGroup.getParentGroup() == null) {
			
			Map<String,Object> result = new HashMap<String,Object>();
			
			result.put("originalExtent",Map.of(
					  		"left", shapeGroup.getOriginalExtent().getLeft(), 
					  		"top", shapeGroup.getOriginalExtent().getTop(),
					  		"right", shapeGroup.getOriginalExtent().getRight(),
					  		"bottom", shapeGroup.getOriginalExtent().getBottom()));
			result.put("extent" ,Map.of("left", shapeGroup.getExtent().getLeft(), 
			        		 			"top", shapeGroup.getExtent().getTop(),
			        		 			"right", shapeGroup.getExtent().getRight(),
			        		 			"bottom", shapeGroup.getExtent().getBottom()));
			
			Map<String,Object> tussenresultaat = new HashMap<String,Object>();
			List<Map<String, Object>> vertices= new ArrayList<>();
			
			for (int i=0; ((LeafShapeGroup)shapeGroup).getShape().getVertices()[i] != null; i++)
					vertices.add(Map.of("x", ((LeafShapeGroup)shapeGroup).getShape().getVertices()[i].getX(),
									 	"y", ((LeafShapeGroup)shapeGroup).getShape().getVertices()[i].getY()));
			
			tussenresultaat.put("vertices", vertices);
			tussenresultaat.put("radius" ,((LeafShapeGroup)shapeGroup).getShape().getRadius());
			tussenresultaat.put("color", Map.of(
					"red", ((LeafShapeGroup)shapeGroup).getShape().getColor().getRed(),
					"green", ((LeafShapeGroup)shapeGroup).getShape().getColor().getGreen(),
					"blue", ((LeafShapeGroup)shapeGroup).getShape().getColor().getBlue()));
			
			result.put("shape", tussenresultaat);

			return result;
				
		}
		
		//recursieve oproep voor 1 nonleafgroup verder in de boom
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put("originalExtent",Map.of(
		  		"left", shapeGroup.getOriginalExtent().getLeft(), 
		  		"top", shapeGroup.getOriginalExtent().getTop(),
		  		"right", shapeGroup.getOriginalExtent().getRight(),
		  		"bottom", shapeGroup.getOriginalExtent().getBottom()));
		result.put("extent" ,Map.of("left", shapeGroup.getExtent().getLeft(), 
        		 					"top", shapeGroup.getExtent().getTop(),
        		 					"right", shapeGroup.getExtent().getRight(),
        		 					"bottom", shapeGroup.getExtent().getBottom()));
		
		List<Object> children= new ArrayList<>();
		for (int i=0; ((NonleafShapeGroup)shapeGroup).getSubgroup(i) != null; i++)
				children.add(toPlainData(((NonleafShapeGroup)shapeGroup).getSubgroup(i)));
		
		result.put("subgroups", children);
		return result;
				
	}
	
}

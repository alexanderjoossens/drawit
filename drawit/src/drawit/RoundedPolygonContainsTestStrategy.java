package drawit;

public interface RoundedPolygonContainsTestStrategy {

	/*
	 * Returns {@code true} iff the given point is contained by the (non-rounded) polygon.
	 * 
	 * @pre | point != null
	 * @inspects | polygon
	 * @mutates nothing |
	 * @post result != null
	 */
	public boolean contains(RoundedPolygon polygon, IntPoint point);
		
}

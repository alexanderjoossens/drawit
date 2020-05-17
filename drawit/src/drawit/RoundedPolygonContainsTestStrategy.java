package drawit;

public interface RoundedPolygonContainsTestStrategy {

	/*
	 * @post result != null
	 */
	public boolean contains(RoundedPolygon polygon, IntPoint point);
		
}

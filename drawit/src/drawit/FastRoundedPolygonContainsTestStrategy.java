package drawit;

public class FastRoundedPolygonContainsTestStrategy implements RoundedPolygonContainsTestStrategy {

	@Override
	/**
	 * @pre | polygon != null
	 * @pre | point != null
	 * @post | result == polygon.getBoundingBox().contains(point)
	 */
	public boolean contains(RoundedPolygon polygon, IntPoint point) {
		return polygon.getBoundingBox().contains(point);
	}
	
}

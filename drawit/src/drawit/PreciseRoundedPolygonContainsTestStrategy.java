package drawit;

public class PreciseRoundedPolygonContainsTestStrategy implements RoundedPolygonContainsTestStrategy {

	@Override
	/**
	 * @pre | polygon != null
	 * @pre | point != null
	 * @post | result == polygon.contains(point)
	 */
	public boolean contains(RoundedPolygon polygon, IntPoint point) {
		return polygon.contains(point);
	}

}

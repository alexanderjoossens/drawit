package drawit;

public class PreciseRoundedPolygonContainsTestStrategy implements RoundedPolygonContainsTestStrategy {

	@Override
	/**
	 * @post | result == polygon.contains(point)
	 * @throws IllegalArgumentException | polygon == null || point == null
	 */
	public boolean contains(RoundedPolygon polygon, IntPoint point) {
		if (polygon == null)
			throw new IllegalArgumentException("The polygon equals null");
		if (point == null)
			throw new IllegalArgumentException("The point equals null");
		return polygon.contains(point);
	}

}

package drawit;

public class PreciseRoundedPolygonContainsTestStrategy implements RoundedPolygonContainsTestStrategy {

	@Override
	public boolean contains(RoundedPolygon polygon, IntPoint point) {
		return polygon.contains(point);
	}

}

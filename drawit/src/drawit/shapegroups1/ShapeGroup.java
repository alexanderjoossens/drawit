package drawit.shapegroups1;
import drawit.RoundedPolygon;
import drawit.IntPoint;
import drawit.shapegroups1.Extent;
import drawit.shapegroups2.ShapeGroup;

public class ShapeGroup {
	
	private RoundedPolygon shape;
	private ShapeGroup[] subgroups;
	private Extent ownExtent;
	private final Extent originalExtent;
	private ShapeGroup parentGroup;

	/**
	 * Initializes this object to represent a leaf shape group that directly contains the given shape.
	 * @param shape
	 */
	public ShapeGroup(RoundedPolygon shape) {
		this.shape = shape;
		this.originalExtent = this.getExtent();
	}

	/**
	 * Initializes this object to represent a non-leaf shape group that directly contains the given subgroups, in the given order.
	 * @param subgroups
	 */
	public ShapeGroup(ShapeGroup[] subgroups) {
		this.subgroups = subgroups;
		this.originalExtent = this.getExtent();
		for (ShapeGroup shapeGroup: subgroups) {
			shapeGroup.parentGroup = this;
		}
	}

	public Extent getExtent() {
		
		int maxX = 0;
		int maxY = 0;
		int minX = 0;
		int minY = 0;
		if (shape==null) {
			for(ShapeGroup shapeGroup: this.getSubgroups()) {
				Extent extent = shapeGroup.getExtent();
				if (maxX==0 && maxY == 0 && minX == 0 && minY ==0) {
					maxY = extent.getBottom();
					maxX = extent.getRight();
					minY = extent.getTop();
					minX = extent.getLeft();
				}
				else {
					maxY = Math.max(extent.getBottom(),maxY);
					maxX = Math.max(extent.getRight(),maxX);
					minY = Math.min(extent.getTop(),minY);
					minX = Math.min(extent.getLeft(),minX);
				}
				
			}
		}
		
		else {
			IntPoint[] points = shape.getVertices();
			for (int i = 0; i < points.length; i++) {
				int xCoord = points[i].getX();
				int yCoord = points[i].getY();
				if (i == 0) {
					maxX = xCoord;
					minX = xCoord;
					maxY = yCoord;
					minY = yCoord;
				} else {
					maxX = Math.max(maxX, xCoord);
					maxY = Math.max(maxY, yCoord);
					minX = Math.min(minX, xCoord);
					minY = Math.min(minY, yCoord);
	
				}
			}
		}

		Extent extent = Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY);
		return extent;
	}
	
//		int smallestX = 100000;
//		int largestX = 0;
//		int smallestY = 100000;
//		int largestY = 0;
//		
//		IntPoint[] points = shape.getVertices();
//		
//		for (int i = 0; i < points.length - 1; i++) {
//			if (points[i].getX() < smallestX)
//				smallestX = points[i].getX();
//		}
//		
//		for (int i = 0; i < points.length - 1; i++) {
//			if (points[i].getX() > smallestX)
//				largestX = points[i].getX();
//		}
//		
//		for (int i = 0; i < points.length - 1; i++) {
//			if (points[i].getY() < smallestY)
//				smallestY = points[i].getY();
//		}
//		
//		for (int i = 0; i < points.length - 1; i++) {
//			if (points[i].getY() > largestY)
//				largestY = points[i].getY();
//		}
//		
//		RoundedPolygon extent = new RoundedPolygon();
//		extent.setVertices(new IntPoint[] {new IntPoint(smallestX, smallestY), new IntPoint(largestX, largestY), new IntPoint(largestX, largestY), new IntPoint(smallestX, largestY)});
//		return extent;
//	}
//	
	public Extent getOriginalExtent() {
		return this.originalExtent;
	}
	
	public void setExtent(Extent newExtent) {
		this.ownExtent = newExtent;
	}

	public RoundedPolygon getShape() {
		if (shape != null) {
			return shape;
		} else {
			return null;
		}
	}

	public ShapeGroup getParentgroup() {
		return this.parentGroup;
	}

	public ShapeGroup[] getSubgroups() {
		return subgroups;
	}

	public int getSubgroupCount() {
		return this.getSubgroups().length;
	}

	public ShapeGroup getSubgroup(int index) {
		return this.getSubgroups()[index];
	}
	
}

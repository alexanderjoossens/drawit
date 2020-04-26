package drawit.shapegroups1;
import drawit.shapegroups1.Extent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import drawit.DoublePoint;
import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;

public class LeafShapeGroup {

	private Extent ownExtent;
	/**
	 * @representationObject
	 * @invar | Arrays.stream(subgroups).allMatch(v -> v != null)
	 */
	private ShapeGroup[] subgroups;
	private final Extent originalExtent;
	
	/**
	 * @invar | 0 <= horizontalScale
	 */
	private double horizontalScale = 1;
	/**
	 * @invar | 0 <= verticalScale
	 */
	private double verticalScale = 1;
	private int horizontalTranslation = 0;
	private int verticalTranslation = 0;

	private RoundedPolygon shape;
	private ShapeGroup parentGroup;

	/**
	 * Initializes this object to represent a leaf shape group that directly
	 * contains the given shape.
	 * 
	 * @param shape
	 * The shape of the new ShapeGroup
	 * @throws IllegalArgumentException if shape is null.
	 *    | shape== null
	 * @throws Vertices are not null
	 * | Arrays.stream(shape.getVertices()).allMatch(v -> v != null)
	 * @mutates | this
	 * @post This object's shape equals the given shape.
     *    | getShape() == shape
     * @inspects | shape
	 */
	public ShapeGroup(RoundedPolygon shape) {
		if (shape == null) {
			throw new IllegalArgumentException("Shape is null");
		}
		
		for (IntPoint vertex : shape.getVertices()) {
			if (vertex==null) {
				throw new IllegalArgumentException("One of the vertices is null");
			}
		}
		int maxX = 0;
		int maxY = 0;
		int minX = 0;
		int minY = 0;

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

		Extent extent = Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY);
		this.originalExtent = extent;
		this.ownExtent = extent;
		this.shape = shape;

	}

	/**
	 * Registers the given extent as this shape group's extent, expressed in this
	 * shape group's outer coordinate system.
	 * 
	 * @param newExtent
	 * The new extent of the shapegroup
	 * @throws IllegalArgumentException if the extent is null.
	 *    | newExtent == null
	 * @mutates | this
	 * @post This object's extent equals the given extent
     *    | getExtent() == newExtent
     * @inspects | newExtent
	 */
	public void setExtent(Extent newExtent) {
		if (newExtent == null) {
			throw new IllegalArgumentException("The given extent is null!");
		}
		this.ownExtent = newExtent;

		this.horizontalScale = ((double) newExtent.getWidth() / (double) this.getOriginalExtent().getWidth());
		this.verticalScale = ((double) newExtent.getHeight() / (double) this.getOriginalExtent().getHeight());
		this.horizontalTranslation = (int)(newExtent.getLeft() - getOriginalExtent().getLeft()*this.horizontalScale);
		this.verticalTranslation = (int)(newExtent.getTop() - getOriginalExtent().getTop()*this.verticalScale);
	}

	/**
	 * Returns the extent of this shape group, expressed in its outer coordinate
	 * system.
	 * @inspects | this
	 */
	public Extent getExtent() {
		return this.ownExtent;
	}

	/**
	 * Returns the extent of this shape group, expressed in its inner coordinate
	 * system.
	 * @inspects | this
	 */
	public Extent getOriginalExtent() {
		return this.originalExtent;
	}

	/**
	 * Returns the shape directly contained by this shape group, or null if this is
	 * a non-leaf shape group.
	 * @inspects | this
	 */
	public RoundedPolygon getShape() {
		return this.shape;
	}

	/**
	 * Returns the shape group that directly contains this shape group, or null if
	 * no shape group directly contains this shape group.
	 * @inspects | this
	 */
	public ShapeGroup getParentGroup() {
		return this.parentGroup;
	}
	
	/**
	 * Returns the coordinates in the global coordinate system of the point whose
	 * coordinates in this shape group's inner coordinate system are the given
	 * coordinates.
	 * @param innerCoordinates
	 * The point that you want the GlobalCoordinates from
	 * @throws IllegalArgumentException if the given coordinates are null.
	 *    | innerCoordinates == null
	 * @inspects | this
	 * @post | result != null
	 * @inspects | innerCoordinates
	 */
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		if (innerCoordinates == null) {
			throw new IllegalArgumentException("innerCoordinates are null");
		}
		
		double newX = ((double) innerCoordinates.getX())*horizontalScale + horizontalTranslation;
		double newY = ((double) innerCoordinates.getY())*verticalScale + verticalTranslation;

		IntPoint outerCoordinates = new DoublePoint(newX, newY).round();

		if (this.getParentGroup() != null) {
			return this.getParentGroup().toGlobalCoordinates(outerCoordinates);
		} else {
			return outerCoordinates;
		}
	}

	/**
	 * Returns the coordinates in this shape group's inner coordinate system of the
	 * point whose coordinates in the global coordinate system are the given
	 * coordinates.
	 * @param globalCoordinates
	 * The point that you want to get the InnerCoordinates from
	 * @throws IllegalArgumentException if the given coordinates are null.
	 *    | globalCoordinates == null
	 * @inspects | this
	 * @post | result != null
	 * @inspects | globalCoordinates
	 */
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		if (globalCoordinates == null) {
			throw new IllegalArgumentException("globalCoordinates are null");
		}
		
		
		if (this.getParentGroup() != null) {
			globalCoordinates = this.getParentGroup().toInnerCoordinates(globalCoordinates);
		}

		double newX = ((double) globalCoordinates.getX() - (double) horizontalTranslation)/horizontalScale;
		double newY = ((double) globalCoordinates.getY() - (double) verticalTranslation)/verticalScale;
		IntPoint innerCoordinates = new DoublePoint(newX, newY).round();
		return innerCoordinates;

	}

	/**
	 * Returns the coordinates in this shape group's inner coordinate system of the
	 * vector whose coordinates in the global coordinate system are the given
	 * coordinates.
	 * @param relativeGlobalCoordinates
	 * The point that you want to get the InnerCoordinates from
	 * @throws IllegalArgumentException if the given coordinates are null.
	 *    | relativeGlobalCoordinates == null
	 * @inspects | this
	 * @post | result != null
	 * @inspects | relativeGlobalCoordinates
	 */
	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
		if (relativeGlobalCoordinates == null) {
			throw new IllegalArgumentException("relativeGlobalCoordinates are null");
		}
		
		IntVector newVector;
		if (this.getParentGroup() != null) {
			newVector = this.getParentGroup().toInnerCoordinates(relativeGlobalCoordinates);
		}

		else {
			newVector = relativeGlobalCoordinates;
		}
		int newX = (int) ((int) newVector.getX() * (1 / this.horizontalScale));
		int newY = (int) ((int) newVector.getY() * (1 / this.verticalScale));

		IntVector innerVector = new IntVector(newX, newY);
		return innerVector;
	}
	
	/**
	 * Moves this shape group to the front of its parent's list of subgroups.
	 * @throws IllegalArgumentException if the shapeGroup is not part of a subgroup.
	 *    | getParentGroup() == null
	 * @post | getParentGroup().getSubgroup(0).equals(this)
	 * @inspects | this
	 */
	public void bringToFront() {
		if (this.getParentGroup() == null) {
			throw new IllegalArgumentException("This shapegroup does not have a parent!");
		}
		ShapeGroup[] tempSubgroups = new ShapeGroup[this.getParentGroup().getSubgroupCount()];
		ShapeGroup[] subgroups = this.getParentGroup().subgroups;
		tempSubgroups[0] = this;
		int j = 1;
		for (int i = 0; i < getParentGroup().getSubgroupCount(); i++) {
			if (!subgroups[i].equals(this)) {
				tempSubgroups[j] = subgroups[i];
				j++;
			}
		}
		this.getParentGroup().subgroups = tempSubgroups;
	}

	/**
	 * Moves this shape group to the back of its parent's list of subgroups.
	 * @throws IllegalArgumentException if the shapeGroup is not part of a subgroup.
	 *    | getParentGroup() == null
	 * @post | getParentGroup().getSubgroup(getParentGroup().getSubgroups().size()-1).equals(this)
	 * @inspects | this
	 */
	public void sendToBack() {
		if (this.getParentGroup() == null) {
		throw new IllegalArgumentException("This shapegroup does not have a parent!");
	}
		
		ShapeGroup[] tempSubgroups = new ShapeGroup[this.getParentGroup().getSubgroupCount()];
		ShapeGroup[] subgroups = this.getParentGroup().subgroups;
		tempSubgroups[this.getParentGroup().getSubgroupCount()-1] = this;
		int j = 0;
		for (int i = 0; i < getParentGroup().getSubgroupCount(); i++) {
			if (!subgroups[i].equals(this)) {
				tempSubgroups[j] = subgroups[i];
				j++;
			}
		}
		this.getParentGroup().subgroups = tempSubgroups;
	}

	/**
	 * Returns a textual representation of a sequence of drawing commands for
	 * drawing the shapes contained directly or indirectly by this shape group,
	 * expressed in this shape group's outer coordinate system.
	 */
	public java.lang.String getDrawingCommands() {
		StringBuilder commands = new StringBuilder();

		if (this.subgroups == null) {

			commands.append("pushTranslate "+horizontalTranslation+" "+verticalTranslation+"\n");
			commands.append("pushScale "+horizontalScale+" "+verticalScale+"\n");
			commands.append(this.getShape().getDrawingCommands());
			commands.append("popTransform\n");
			commands.append("popTransform\n");
		}

		if (subgroups!=null) {
			java.util.List<ShapeGroup> subgroups = this.getSubgroups();
			Collections.reverse(subgroups);
			for (ShapeGroup subgroup : subgroups) {
				commands.append("pushTranslate "+horizontalTranslation+" "+verticalTranslation+"\n");
				commands.append("pushScale "+horizontalScale+" "+verticalScale+"\n");
				commands.append(subgroup.getDrawingCommands());
				commands.append("popTransform\n");
				commands.append("popTransform\n");
			}
		}
		return commands.toString();
	} 
}

package drawit.shapegroups1;
import drawit.shapegroups1.Extent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import drawit.DoublePoint;
import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;

import logicalcollections.LogicalList;
import logicalcollections.LogicalSet;

public abstract class ShapeGroup {

	protected Extent currentExtent;

	protected Extent originalExtent;
	
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

	protected NonleafShapeGroup parent;



	/**
	 * Registers the given extent as this shape group's extent, expressed in this
	 * shape group's outer coordinate system. As a consequence, by definition this shape group's
	 * inner coordinate system changes so that the new extent's coordinates in the new inner
	 * coordinate system are equal to the old extent's coordinates in the old inner
	 * coordinate system. Note: this method does not mutate the coordinates of the vertices
	 * stored by the shape or the extents stored by the subgroups contained by this shape group.
	 * However, since these are interpreted in this shape group's inner coordinate system, this
	 * method effectively defines a transformation of this shape or these subgroups.
	 * 
	 * @throws IllegalArgumentException if {@code newExtent} is null
	 *    | newExtent == null
	 * @mutates_properties | getExtent()
	 * @post | getExtent().equals(newExtent)
	 */
	public void setExtent(Extent newExtent) {
		currentExtent = newExtent;
	}

	/**
	 * Returns the extent of this shape group, expressed in its outer coordinate system.
	 * The extent of a shape group is the smallest axis-aligned rectangle that contained
	 * the shape group's shape (if it is a leaf shape group) or its subgroups' extents
	 * (if it is a non-leaf shape group) when the shape group was created. After the shape
	 * group is created, subsequent mutations of the shape or subgroups contained by the shape
	 * group do not affect its extent. As a result, after mutating the shape or subgroups contained
	 * by this shape group, this shape group's extent might no longer contain its shape or its subgroups
	 * or might no longer be the smallest axis-aligned rectangle that does so.
	 *
	 * @basic
	 * @post | result != null
	 */
	public Extent getExtent() {
		return currentExtent;
	}

	/**
	 * Returns the extent of this shape group, expressed in its inner coordinate system.
	 * This coincides with the extent expressed in outer coordinates at the time of
	 * creation of the shape group. The shape transformation defined by this shape group is the one
	 * that transforms the original extent to the current extent.
	 * 
	 * This method returns an equal result throughout the lifetime of this object.
	 * 
	 * @immutable
	 * @post | result != null
	 */
	public Extent getOriginalExtent() {
		return originalExtent;
	}


	/**
	 * Returns the shape group that directly contains this shape group, or {@code null}
	 * if no shape group directly contains this shape group.
	 * 
	 * @basic
	 * @peerObject
	 */
	public ShapeGroup getParentGroup() { return parent; }

	/**
	 * Returns the set of the ancestors of this shape group.
	 * 
	 * @post | result != null
	 * @post | result.equals(LogicalSet.<ShapeGroup>matching(ancestors ->
	 *       |     getParentGroup() == null || ancestors.contains(getParentGroup()) &&
	 *       |     ancestors.allMatch(ancestor ->
	 *       |         ancestor.getParentGroup() == null || ancestors.contains(ancestor.getParentGroup()))))
	 */
	public Set<ShapeGroup> getAncestors() {
		return getAncestorsPrivate();
	}
	
	private Set<ShapeGroup> getAncestorsPrivate() {
		return LogicalSet.<ShapeGroup>matching(ancestors ->
			parent == null || ancestors.contains(parent) &&
			ancestors.allMatch(ancestor -> ancestor.parent == null || ancestors.contains(ancestor.parent))
		);
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
	 * Returns the coordinates in this shape group's inner coordinate system of the point
	 * whose coordinates in the global coordinate system are the given coordinates.
	 * 
	 * The global coordinate system is the outer coordinate system of this shape group's root ancestor,
	 * i.e. the ancestor that has no parent.
	 * 
	 * This shape group's inner coordinate system is defined by the fact that the coordinates of its extent
	 * in its inner coordinate system are constant and given by {@code this.getOriginalExtent()}.
	 * 
	 * Its outer coordinate system is defined by the fact that the coordinates of its extent in its outer
	 * coordinate system are as given by {@code this.getExtent()}.
	 * 
	 * When a shape group is created, its inner coordinate system coincides with its outer coordinate system
	 * (and with the global coordinate system). Subsequent calls of {@code this.setExtent()} may cause the
	 * inner and outer coordinate systems to no longer coincide.
	 * 
	 * The inner coordinate system of a non-leaf shape group always coincides with the outer coordinate
	 * systems of its subgroups. Furthermore, the coordinates of the vertices of a shape contained by a leaf
	 * shape group are interpreted in the inner coordinate system of the shape group.
	 * 
	 * @throws IllegalArgumentException if {@code globalCoordinates} is null
	 *    | globalCoordinates == null
	 * @inspects | this
	 * @post | result != null
	 * @post | result.equals(outerToInnerCoordinates(globalToOuterCoordinates(globalCoordinates)))
	 */
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		if (globalCoordinates == null)
			throw new IllegalArgumentException("globalCoordinates is null");
		
		return outerToInnerCoordinates(globalToOuterCoordinates(globalCoordinates));
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
	 * Returns a textual representation of a sequence of drawing commands for
	 * drawing the shapes contained directly or indirectly by this shape group,
	 * expressed in this shape group's outer coordinate system.
	 * 
	 * TODO deze functie verplaatsen naar de subclasses
	 */

	public abstract java.lang.String getDrawingCommands();
//	StringBuilder commands = new StringBuilder();
//
//	if (this.subgroups == null) {
//
//		commands.append("pushTranslate "+horizontalTranslation+" "+verticalTranslation+"\n");
//		commands.append("pushScale "+horizontalScale+" "+verticalScale+"\n");
//		commands.append(this.getShape().getDrawingCommands());
//		commands.append("popTransform\n");
//		commands.append("popTransform\n");
//	}
//
//	if (subgroups!=null) {
//		java.util.List<ShapeGroup> subgroups = this.getSubgroups();
//		Collections.reverse(subgroups);
//		for (ShapeGroup subgroup : subgroups) {
//			commands.append("pushTranslate "+horizontalTranslation+" "+verticalTranslation+"\n");
//			commands.append("pushScale "+horizontalScale+" "+verticalScale+"\n");
//			commands.append(subgroup.getDrawingCommands());
//			commands.append("popTransform\n");
//			commands.append("popTransform\n");
//		}
//	}
//	return commands.toString();
}




package drawit.shapegroups1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;

import logicalcollections.LogicalList;
import logicalcollections.LogicalSet;

public class NonleafShapeGroup extends ShapeGroup {
	
	/**
	 * Initializes this object to represent a non-leaf shape group that directly contains the given
	 * subgroups, in the given order.
	 * 
	 * @mutates | this
	 * @mutates_properties | (...subgroups).getParentGroup()
	 * @inspects | subgroups
	 * 
	 * @throws IllegalArgumentException if {@code subgroups} is null
	 *    | subgroups == null
	 * @throws IllegalArgumentException if {@code subgroups} has less than two elements
	 *    | subgroups.length < 2
	 * @throws IllegalArgumentException if any element of {@code subgroups} is null
	 *    | Arrays.stream(subgroups).anyMatch(g -> g == null)
	 * @throws IllegalArgumentException if the given subgroups are not distinct
	 *    | !LogicalList.distinct(List.of(subgroups))
	 * @throws IllegalArgumentException if any of the given subgroups already has a parent
	 *    | Arrays.stream(subgroups).anyMatch(g -> g.getParentGroup() != null)
	 * @throws IllegalArgumentException if any of the given subgroups is an ancestor of this shape group
	 *    | !Collections.disjoint(getAncestors(), Set.of(subgroups))
	 * 
	 * @post | Objects.equals(getSubgroups(), List.of(subgroups))
	 * @post | Arrays.stream(subgroups).allMatch(g -> g.getParentGroup() == this)
	 * @post | getParentGroup() == null
	 * @post | getOriginalExtent().getLeft() == Arrays.stream(subgroups).mapToInt(g -> g.getExtent().getLeft()).min().getAsInt()
	 * @post | getOriginalExtent().getTop() == Arrays.stream(subgroups).mapToInt(g -> g.getExtent().getTop()).min().getAsInt()
	 * @post | getOriginalExtent().getRight() == Arrays.stream(subgroups).mapToInt(g -> g.getExtent().getRight()).max().getAsInt()
	 * @post | getOriginalExtent().getBottom() == Arrays.stream(subgroups).mapToInt(g -> g.getExtent().getBottom()).max().getAsInt()
	 * @post | getExtent().equals(getOriginalExtent())
	 */
	public NonleafShapeGroup(ShapeGroup[] subgroups) {
		if (subgroups == null)
			throw new IllegalArgumentException("subgroups is null");
		if (subgroups.length < 2)
			throw new IllegalArgumentException("subgroups has less than two elements");
		if (Arrays.stream(subgroups).anyMatch(g -> g == null))
			throw new IllegalArgumentException("subgroups has null elements");
		if (!LogicalList.distinct(List.of(subgroups)))
			throw new IllegalArgumentException("subgroups has duplicate elements");
		if (Arrays.stream(subgroups).anyMatch(g -> g.getParentGroup() != null))
			throw new IllegalArgumentException("some of the given groups already have a parent");
		if (!Collections.disjoint(getAncestors(), Set.of(subgroups)))
			throw new IllegalArgumentException("some of the given groups are ancestors of this shape group");
		
		this.subgroups = new ArrayList<>(Arrays.asList(subgroups));
		for (ShapeGroup group : subgroups) {
			assert group.parent == null;
			group.parent = this;
		}
		
		int left = Integer.MAX_VALUE;
		int top = Integer.MAX_VALUE;
		int right = Integer.MIN_VALUE;
		int bottom = Integer.MIN_VALUE;
		for (ShapeGroup group : subgroups) {
			left = Math.min(left, group.getExtent().getLeft());
			right = Math.max(right, group.getExtent().getRight());
			top = Math.min(top, group.getExtent().getTop());
			bottom = Math.max(bottom, group.getExtent().getBottom());
		}
		originalExtent = Extent.ofLeftTopRightBottom(left, top, right, bottom);
		currentExtent = originalExtent;
	}
	
	/**
	 * Returns the list of all shapes contained directly or indirectly by this shape group, in depth-first order.
	 */
	public List<RoundedPolygon> getAllShapes() {
		return subgroups.stream().flatMap(subgroup -> subgroup.getAllShapes().stream()).collect(Collectors.toList());
	}
	
	/**
	 * Returns the list of subgroups of this shape group, or {@code null} if this is a leaf shape group.
	 * 
	 * @basic
	 * @peerObjects
	 */
	public List<ShapeGroup> getSubgroups() { return subgroups == null ? null : List.copyOf(subgroups); }
	
	/**
	 * Returns the number of subgroups of this non-leaf shape group.
	 * 
	 * @throws UnsupportedOperationException if this is a leaf shape group
	 *    | getSubgroups() == null
	 * @post | result == getSubgroups().size()
	 */
	public int getSubgroupCount() {
		if (subgroups == null)
			throw new UnsupportedOperationException();
		
		return subgroups.size();
	}

	/**
	 * Returns the subgroup at the given (zero-based) index in this non-leaf shape group's list of subgroups.
	 * 
	 * @throws UnsupportedOperationException if this is a leaf shape group
	 *    | getSubgroups() == null
	 * @throws IllegalArgumentException if the given index is out of bounds
	 *    | index < 0 || getSubgroups().size() <= index
	 * @post | result == getSubgroups().get(index)
	 */
	public ShapeGroup getSubgroup(int index) {
		if (subgroups == null)
			throw new UnsupportedOperationException();
		if (index < 0 || getSubgroups().size() <= index)
			throw new IllegalArgumentException("index out of bounds");
		return subgroups.get(index);
	}
	
	/**
	 * Return the first subgroup in this non-leaf shape group's list of subgroups whose
	 * extent contains the given point, expressed in this shape group's inner coordinate system.
	 * 
	 * @throws UnsupportedOperationException if this shape group is a leaf shape group
	 *    | getSubgroups() == null
	 * @throws IllegalArgumentException if {@code innerCoordinates} is null
	 *    | innerCoordinates == null
	 * @post
	 *    | Objects.equals(result,
	 *    |     getSubgroups().stream().filter(g -> g.getExtent().contains(innerCoordinates))
	 *    |         .findFirst().orElse(null))
	 */
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		if (subgroups == null)
			throw new UnsupportedOperationException("this is a leaf shape group");
		if (innerCoordinates == null)
			throw new IllegalArgumentException("innerCoordinates is null");
		
		for (ShapeGroup group : subgroups)
			if (group.getExtent().contains(innerCoordinates))
				return group;
		return null;
	}
	
	
	
	
	
	/**
	 * Moves this shape group to the front of its parent's list of subgroups.
	 * 
	 * @throws UnsupportedOperationException if this shape group has no parent
	 *    | getParentGroup() == null
	 * @mutates_properties | getParentGroup().getSubgroups()
	 * @post | getParentGroup().getSubgroups().equals(
	 *       |     LogicalList.plusAt(LogicalList.minus(old(getParentGroup().getSubgroups()), this), 0, this))
	 */
	public void bringToFront() {
		if (parent == null)
			throw new UnsupportedOperationException("no parent");
		
		parent.subgroups.remove(this);
		parent.subgroups.add(0, this);
	}
	
	/**
	 * Moves this shape group to the back of its parent's list of subgroups.
	 * 
	 * @throws UnsupportedOperationException if this shape group has no parent
	 *    | getParentGroup() == null
	 * @mutates_properties | getParentGroup().getSubgroups()
	 * @post | getParentGroup().getSubgroups().equals(
	 *       |     LogicalList.plus(LogicalList.minus(old(getParentGroup().getSubgroups()), this), this))
	 */
	public void sendToBack() {
		if (parent == null)
			throw new UnsupportedOperationException("no parent");
		
		parent.subgroups.remove(this);
		parent.subgroups.add(this);
	}
	
	/**
	 * Returns a textual representation of a sequence of drawing commands for drawing
	 * the shapes contained directly or indirectly by this shape group, expressed in this
	 * shape group's outer coordinate system.
	 * 
	 * For the syntax of the drawing commands, see {@code RoundedPolygon.getDrawingCommands()}.
	 * 
	 * @inspects | this, ...getAllShapes()
	 * @post | result != null
	 */
	public String getDrawingCommands() {
		StringBuilder builder = new StringBuilder();
		
		double xscale = currentExtent.getWidth() * 1.0 / originalExtent.getWidth();
		double yscale = currentExtent.getHeight() * 1.0 / originalExtent.getHeight();
		builder.append("pushTranslate " + currentExtent.getLeft() + " " + currentExtent.getTop() + "\n");
		builder.append("pushScale " + xscale + " " + yscale + "\n");
		builder.append("pushTranslate " + -originalExtent.getLeft() + " " + -originalExtent.getTop() + "\n");
		
		for (int i = subgroups.size() - 1; 0 <= i; i--)
			builder.append(subgroups.get(i).getDrawingCommands());

		builder.append("popTransform popTransform popTransform\n");
		return builder.toString();
	}
	
}

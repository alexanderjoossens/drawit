package drawit.shapegroups2;
import drawit.shapegroups2.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import logicalcollections.LogicalList;
import logicalcollections.LogicalSet;

import drawit.IntPoint;
import drawit.RoundedPolygon;

/**
 * @invar | getSubgroups() == null || LogicalList.distinct(getSubgroups())
 * @invar | getSubgroups() == null || getSubgroups().stream().allMatch(g -> g != null && g.getParentGroup() == this)

 */
public class NonleafShapeGroup extends ShapeGroup{
	
	ShapeGroup firstChild;
	
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
		
		ShapeGroup child = firstChild = subgroups[0];
		for (int i = 1; ; i++) {
			child.parent = this;
			if (subgroups.length <= i)
				break;
			ShapeGroup nextChild = subgroups[i];
			child.nextSibling = nextChild;
			nextChild.previousSibling = child;
			child = nextChild;
		}
		child.nextSibling = firstChild;
		firstChild.previousSibling = child;
		
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
	 * Returns the number of subgroups of this non-leaf shape group.
	 * 
	 * @throws UnsupportedOperationException if this is a leaf shape group
	 *    | getSubgroups() == null
	 * @post | result == getSubgroups().size()
	 */
	public int getSubgroupCount() {
		if (firstChild == null)
			throw new UnsupportedOperationException();
		
		ShapeGroup child = firstChild;
		int count = 1;
		for (;;) {
			child = child.nextSibling;
			if (child == firstChild)
				break;
			count++;
		}
		return count;
	}
	
	private List<ShapeGroup> getSubgroupsPrivate() {
		if (firstChild == null)
			return null;
		ArrayList<ShapeGroup> children = new ArrayList<>();
		ShapeGroup child = firstChild;
		for (;;) {
			children.add(child);
			child = child.nextSibling;
			if (child == firstChild)
				break;
		}
		return children;
	}
	
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
	 * Returns the list of subgroups of this shape group, or {@code null} if this is a leaf shape group.
	 * 
	 * @basic
	 * @peerObjects
	 */
	public List<ShapeGroup> getSubgroups() { return getSubgroupsPrivate(); }

	
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
		if (firstChild == null)
			throw new UnsupportedOperationException("this is a leaf shape group");
		if (innerCoordinates == null)
			throw new IllegalArgumentException("innerCoordinates is null");
		
		ShapeGroup child = firstChild;
		for (;;) {
			if (child.getExtent().contains(innerCoordinates))
				return child;
			child = child.nextSibling;
			if (child == firstChild)
				return null;
		}
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
		if (firstChild == null)
			throw new UnsupportedOperationException();
		if (index < 0 || getSubgroupCount() <= index)
			throw new IllegalArgumentException("index out of bounds");
		
		ShapeGroup child = firstChild;
		for (int i = 0; i < index; i++)
			child = child.nextSibling;
		return child;
	}
	
	/**
	 * Returns the list of all shapes contained directly or indirectly by this shape group, in depth-first order.
	 */
	public List<RoundedPolygon> getAllShapes() {
		return subgroups.stream().flatMap(subgroup -> subgroup.getAllShapes().stream()).collect(Collectors.toList());
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
		
		remove();
		insertBeforeFirstChild();
		parent.firstChild = this;
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
		
		remove();
		insertBeforeFirstChild();
	}
	
	private void remove() {
		nextSibling.previousSibling = previousSibling;
		previousSibling.nextSibling = nextSibling;
		if (this == parent.firstChild)
			parent.firstChild = nextSibling;
	}
	
	private void insertBeforeFirstChild() {
		nextSibling = parent.firstChild;
		previousSibling = nextSibling.previousSibling;
		nextSibling.previousSibling = this;
		previousSibling.nextSibling = this;
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
		

		for (ShapeGroup child = firstChild.previousSibling; ;) {
			builder.append(child.getDrawingCommands());
			if (child == firstChild)
				break;
			child = child.previousSibling;
			}
		
		builder.append("popTransform popTransform popTransform\n");
		return builder.toString();
	}
}

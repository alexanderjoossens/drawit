package drawit.shapegroups2;

import java.util.ArrayList;
import java.util.List;

import drawit.IntPoint;

public class NonleafShapeGroup extends ShapeGroup{
	
	private ShapeGroup firstChild;
	private ShapeGroup lastChild;
	private ShapeGroup previousSibling;
	private ShapeGroup nextSibling;

	/**
	 * Initializes this object to represent a non-leaf shape group that directly
	 * contains the given subgroups, in the given order.
	 * 
	 * @param subgroups
	 * The subgroups of the new ShapeGroup
	 * @throws IllegalArgumentException if one of the subgroups is null.
	 *    | Arrays.stream(subgroups).allMatch(p -> p != null)
	 * @mutates | this
	 * @inspects | subgroups
	 */
	public ShapeGroup(ShapeGroup[] subgroups) {
		ShapeGroup tempPreviousSibling = null;

		this.firstChild = subgroups[0];
		this.lastChild = subgroups[subgroups.length-1];

		int maxX = 0;
		int maxY = 0;
		int minX = 0;
		int minY = 0;
		for (ShapeGroup shapeGroup : subgroups) {
			if (shapeGroup == null) {
				throw new IllegalArgumentException("One of the subgroups is null");
			}
			
			if (tempPreviousSibling != null) {
				shapeGroup.previousSibling = tempPreviousSibling;
				tempPreviousSibling.nextSibling = shapeGroup;
			}
			tempPreviousSibling = shapeGroup;
			
			shapeGroup.parentGroup = this;
			
			Extent extent = shapeGroup.getExtent();
			if (maxX == 0 && maxY == 0 && minX == 0 && minY == 0) {
				maxY = extent.getBottom();
				maxX = extent.getRight();
				minY = extent.getTop();
				minX = extent.getLeft();
			} else {
				maxY = Math.max(extent.getBottom(), maxY);
				maxX = Math.max(extent.getRight(), maxX);
				minY = Math.min(extent.getTop(), minY);
				minX = Math.min(extent.getLeft(), minX);
			}

		}

		Extent extent = Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY);
		this.originalExtent = extent;
		this.ownExtent = extent;
		this.subgroups = subgroups;
	}
	
	
	/**
	 * Returns the number of subgroups of this non-leaf shape group.
	 * @inspects | this
	 * @throws IllegalArgumentException if the given shape is a leaf group.
	 *    | this.getShape() != null
	 */

	public int getSubgroupCount() {
		if (this.getShape() != null) {
			throw new IllegalArgumentException("This shapegroup is a leaf group");
		}
		else {
			return this.subgroups.length;
		}
	}

	/**
	 * Returns the subgroup at the given (zero-based) index in this non-leaf shape
	 * group's list of subgroups.
	 * 
	 * @param index
	 * The index of the subgroup that you want to get
	 * @inspects | this
	 * @throws IllegalArgumentException
	 *    | !(0 <= index && index < getSubgroupCount())
	 */
	public ShapeGroup getSubgroup(int index) {
		if (!(0 <= index && index < getSubgroupCount()))
			throw new IllegalArgumentException("index out of range");
		
		return (ShapeGroup) this.getSubgroups().toArray()[index];
	}
	



	/**
	 * Return the first subgroup in this non-leaf shape group's list of subgroups
	 * whose extent contains the given point, expressed in this shape group's inner
	 * coordinate system.
	 * 
	 * @param innerCoordinates
	 * The point that is contained by the extent of the first subgroup of this non-leaf shape
	 * @inspects | this
	 * @creates | result
	 * @throws IllegalArgumentException if the given coordinates are null.
	 *    | innerCoordinates == null
	 * @throws IllegalArgumentException if the given shape is a leaf group.
	 *    | this.getShape() != null
	 */
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		if (innerCoordinates == null) {
			throw new IllegalArgumentException("Coordinates are null");
		}
		if (this.getShape() != null) {
			throw new IllegalArgumentException("This shapegroup is a leaf group");
		}
		List<ShapeGroup> subgroups = this.getSubgroups() ;
		
		for (int i=0; i<this.getSubgroupCount();i++) {
			if (subgroups.get(i).getExtent().contains(innerCoordinates)) {
				return subgroups.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns the list of subgroups of this shape group, or null if this is a leaf
	 * shape group.
	 * @inspects | this
	 * @creates | result
	 */
	public java.util.List<ShapeGroup> getSubgroups() {
		if (firstChild != null) {
			ArrayList<ShapeGroup> subgroups = new ArrayList<ShapeGroup>() ;
			for (ShapeGroup subgroup = firstChild; subgroup !=null; subgroup=subgroup.nextSibling)
				subgroups.add(subgroup);
			return subgroups;
		}
		else {
			return null;
		}
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
		if (this.previousSibling!=null) {
			if (this.nextSibling != null) {
				this.nextSibling.previousSibling = this.previousSibling;
			}
			this.previousSibling.nextSibling = this.nextSibling;
			this.getParentGroup().firstChild.previousSibling = this;
			if (this.nextSibling == null) {
				this.getParentGroup().lastChild = this.previousSibling;
			}
			this.nextSibling = this.getParentGroup().firstChild;
			this.getParentGroup().firstChild = this;
			this.previousSibling = null;
		}
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
		
		if (this.nextSibling != null) {
			if (this.previousSibling != null) {
				this.previousSibling.nextSibling = this.nextSibling;
			}
			this.nextSibling.previousSibling = this.previousSibling;
			this.getParentGroup().lastChild.nextSibling = this;
			if (this.previousSibling == null) {
				this.getParentGroup().firstChild = this.nextSibling;
			}
			this.previousSibling = this.getParentGroup().lastChild;
			this.getParentGroup().lastChild = this;
			this.nextSibling = null;
		}
	}


}

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

//public class ShapeGroup {
//
//	private Extent ownExtent;
//	private ArrayList<ShapeGroup> subgroups = new ArrayList<ShapeGroup>();
//	private final Extent originalExtent;
//	
//	private double horizontalScale = 1;
//	private double verticalScale = 1;
//	private int horizontalTranslation = 0;
//	private int verticalTranslation = 0;
//
//	private RoundedPolygon shape;
//	private ShapeGroup parentGroup;
//
//	/**
//	 * Initializes this object to represent a leaf shape group that directly
//	 * contains the given shape.
//	 * 
//	 * @param shape
//	 */
//	public ShapeGroup(RoundedPolygon shape) {
//		int maxX = 0;
//		int maxY = 0;
//		int minX = 0;
//		int minY = 0;
//
//		IntPoint[] points = shape.getVertices();
//		for (int i = 0; i < points.length; i++) {
//			int xCoord = points[i].getX();
//			int yCoord = points[i].getY();
//			if (i == 0) {
//				maxX = xCoord;
//				minX = xCoord;
//				maxY = yCoord;
//				minY = yCoord;
//			} else {
//				maxX = Math.max(maxX, xCoord);
//				maxY = Math.max(maxY, yCoord);
//				minX = Math.min(minX, xCoord);
//				minY = Math.min(minY, yCoord);
//			}
//		}
//
//		Extent extent = Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY);
//		this.originalExtent = extent;
//		this.ownExtent = extent;
//		this.shape = shape;
//
//	}
//
//	/**
//	 * Initializes this object to represent a non-leaf shape group that directly
//	 * contains the given subgroups, in the given order.
//	 * 
//	 * @param subgroups
//	 */
//	public ShapeGroup(ShapeGroup[] subgroups) {
//		int maxX = 0;
//		int maxY = 0;
//		int minX = 0;
//		int minY = 0;
//		for (ShapeGroup shapeGroup : subgroups) {
//			if (shapeGroup == null) {
//				throw new IllegalArgumentException("One of the subgroups is null");
//			}
//			this.subgroups.add(shapeGroup);
//			shapeGroup.parentGroup = this;
//			Extent extent = shapeGroup.getExtent();
//			if (maxX == 0 && maxY == 0 && minX == 0 && minY == 0) {
//				maxY = extent.getBottom();
//				maxX = extent.getRight();
//				minY = extent.getTop();
//				minX = extent.getLeft();
//			} else {
//				maxY = Math.max(extent.getBottom(), maxY);
//				maxX = Math.max(extent.getRight(), maxX);
//				minY = Math.min(extent.getTop(), minY);
//				minX = Math.min(extent.getLeft(), minX);
//			}
//
//		}
//
//		Extent extent = Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY);
//		this.originalExtent = extent;
//		this.ownExtent = extent;
//	}
//
//	/**
//	 * Registers the given extent as this shape group's extent, expressed in this
//	 * shape group's outer coordinate system.
//	 * 
//	 * @param newExtent
//	 */
//	public void setExtent(Extent newExtent) {
//		this.ownExtent = newExtent;
//
//		this.horizontalScale = ((double) newExtent.getWidth() / (double) this.getOriginalExtent().getWidth());
//		this.verticalScale = ((double) newExtent.getHeight() / (double) this.getOriginalExtent().getHeight());
//		this.horizontalTranslation = (int)(newExtent.getLeft() - getOriginalExtent().getLeft()*this.horizontalScale);
//		this.verticalTranslation = (int)(newExtent.getTop() - getOriginalExtent().getTop()*this.verticalScale);
//	}
//
//	/**
//	 * Returns the extent of this shape group, expressed in its outer coordinate
//	 * system.
//	 */
//	public Extent getExtent() {
//		return ownExtent;
//	}
//
//	/**
//	 * Returns the extent of this shape group, expressed in its inner coordinate
//	 * system.
//	 */
//	public Extent getOriginalExtent() {
//		return this.originalExtent;
//	}
//
//	/**
//	 * Returns the shape directly contained by this shape group, or null if this is
//	 * a non-leaf shape group.
//	 */
//	public RoundedPolygon getShape() {
//		return this.shape;
//	}
//
//	/**
//	 * Returns the shape group that directly contains this shape group, or null if
//	 * no shape group directly contains this shape group.
//	 */
//	public ShapeGroup getParentGroup() {
//		return this.parentGroup;
//	}
//
//	/**
//	 * Returns the number of subgroups of this non-leaf shape group.
//	 */
//
//	public int getSubgroupCount() {
//		if (this.subgroups == null) {
//			throw new IllegalArgumentException("This shapegroup is a leaf group");
//		}
//		else {
//			return this.subgroups.size();
//		}
//	}
//
//	/**
//	 * Returns the subgroup at the given (zero-based) index in this non-leaf shape
//	 * group's list of subgroups.
//	 * 
//	 * @param index
//	 */
//	public ShapeGroup getSubgroup(int index) {
//		return this.subgroups.get(index);
//	}
//
//	/**
//	 * Return the first subgroup in this non-leaf shape group's list of subgroups
//	 * whose extent contains the given point, expressed in this shape group's inner
//	 * coordinate system.
//	 */
//	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
//		
//		if (subgroups.size() == 0) {
//			throw new IllegalArgumentException("This shapegroup is a leaf group");
//		}
//		
//		List<ShapeGroup> subgroups = this.getSubgroups() ;
//		for (int i=0; i<this.getSubgroupCount();i++) {
//			if (subgroups.get(i).getExtent().contains(innerCoordinates)) {
//				return subgroups.get(i);
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * Returns the list of subgroups of this shape group, or null if this is a leaf
//	 * shape group.
//	 */
//	public java.util.List<ShapeGroup> getSubgroups() {
//		if (subgroups.size() != 0) {
//			ArrayList<ShapeGroup> subgroups = new ArrayList<ShapeGroup>() ;
//			for (int i=0; i<this.getSubgroupCount(); i++)
//				subgroups.add(this.subgroups.get(i));
//			return subgroups;
//		}
//		else {
//			return null;
//		}
//	}
//	
//	/**
//	 * Returns the coordinates in the global coordinate system of the point whose
//	 * coordinates in this shape group's inner coordinate system are the given
//	 * coordinates.
//	 */
//	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
//		double newX = ((double) innerCoordinates.getX())*horizontalScale + horizontalTranslation;
//		double newY = ((double) innerCoordinates.getY())*verticalScale + verticalTranslation;
//
//		IntPoint outerCoordinates = new DoublePoint(newX, newY).round();
//
//		if (this.getParentGroup() != null) {
//			return this.getParentGroup().toGlobalCoordinates(outerCoordinates);
//		} else {
//			return outerCoordinates;
//		}
//	}
//
//	/**
//	 * Returns the coordinates in this shape group's inner coordinate system of the
//	 * point whose coordinates in the global coordinate system are the given
//	 * coordinates.
//	 */
//	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
//		if (this.getParentGroup() != null) {
//			globalCoordinates = this.getParentGroup().toInnerCoordinates(globalCoordinates);
//		}
//
//		double newX = ((double) globalCoordinates.getX() - (double) horizontalTranslation)/horizontalScale;
//		double newY = ((double) globalCoordinates.getY() - (double) verticalTranslation)/verticalScale;
//		IntPoint innerCoordinates = new DoublePoint(newX, newY).round();
//		return innerCoordinates;
//
//	}
//
//	/**
//	 * Returns the coordinates in this shape group's inner coordinate system of the
//	 * vector whose coordinates in the global coordinate system are the given
//	 * coordinates.
//	 */
//	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
//		IntVector newVector;
//		if (this.getParentGroup() != null) {
//			newVector = this.getParentGroup().toInnerCoordinates(relativeGlobalCoordinates);
//		}
//
//		else {
//			newVector = relativeGlobalCoordinates;
//		}
//		int newX = (int) ((int) newVector.getX() * (1 / this.horizontalScale));
//		int newY = (int) ((int) newVector.getY() * (1 / this.verticalScale));
//
//		IntVector innerVector = new IntVector(newX, newY);
//		return innerVector;
//	}
//	
//	
//	
//	
//
//	/**
//	 * Moves this shape group to the front of its parent's list of subgroups.
//	 */
//	public void bringToFront() {
//		for (int i = 0; i < parentGroup.subgroups.size(); i++) {
//			if (parentGroup.getSubgroup(i) == this) {
//				parentGroup.subgroups.add(parentGroup.getSubgroup(i));
//				parentGroup.subgroups.remove(i);
//				break;
//			}
//		}
//	}
//
//	/**
//	 * Moves this shape group to the back of its parent's list of subgroups.
//	 */
//	public void sendToBack() {
//		for (int i=0; i< parentGroup.subgroups.size(); i++) {
//			if (parentGroup.getSubgroup(i) == this) {
//				parentGroup.subgroups.add(parentGroup.getSubgroup(i));
//				parentGroup.subgroups.remove(i);
//				break;
//			}
//		}
//	}
//
//	/**
//	 * Returns a textual representation of a sequence of drawing commands for
//	 * drawing the shapes contained directly or indirectly by this shape group,
//	 * expressed in this shape group's outer coordinate system.
//	 */
//	public java.lang.String getDrawingCommands() {
//		StringBuilder commands = new StringBuilder();
//
//		if (this.subgroups == null) {
//
//			commands.append("pushTranslate "+horizontalTranslation+" "+verticalTranslation+"\n");
//			commands.append("pushScale "+horizontalScale+" "+verticalScale+"\n");
//			commands.append(this.getShape().getDrawingCommands());
//			commands.append("popTransform\n");
//			commands.append("popTransform\n");
//		}
//
//		if (subgroups!=null) {
//			java.util.List<ShapeGroup> subgroups = this.getSubgroups();
//			Collections.reverse(subgroups);
//			for (ShapeGroup subgroup : subgroups) {
//				commands.append("pushTranslate "+horizontalTranslation+" "+verticalTranslation+"\n");
//				commands.append("pushScale "+horizontalScale+" "+verticalScale+"\n");
//				commands.append(subgroup.getDrawingCommands());
//				commands.append("popTransform\n");
//				commands.append("popTransform\n");
//			}
////			for (int i = getSubgroupCount()-1; i >= 0; i--) {
////				commands.append("pushTranslate "+horizontalTranslation+" "+verticalTranslation+"\n");
////				commands.append("pushScale "+horizontalScale+" "+verticalScale+"\n");
////				commands.append(subgroups.get(i).getDrawingCommands());
////				commands.append("popTransform\n");
////				commands.append("popTransform\n");
////			}
//		}
//		return commands.toString();
//	} 
//}



















































public class ShapeGroup {

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
	 * @mutates | this
	 */
	public ShapeGroup(RoundedPolygon shape) {
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
	 * Initializes this object to represent a non-leaf shape group that directly
	 * contains the given subgroups, in the given order.
	 * 
	 * @param subgroups
	 * The subgroups of the new ShapeGroup
	 * @mutates | this
	 */
	public ShapeGroup(ShapeGroup[] subgroups) {


		int maxX = 0;
		int maxY = 0;
		int minX = 0;
		int minY = 0;
		for (ShapeGroup shapeGroup : subgroups) {
			if (shapeGroup == null) {
				throw new IllegalArgumentException("One of the subgroups is null");
			}
	
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
	 * Registers the given extent as this shape group's extent, expressed in this
	 * shape group's outer coordinate system.
	 * 
	 * @param newExtent
	 * The new extent of the shapegroup
	 * @mutates | this
	 */
	public void setExtent(Extent newExtent) {
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
		
		return this.subgroups[index];
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
	 * @throws IllegalArgumentException if the given shape is a leaf group.
	 *    | this.getShape() != null
	 */
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
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
		java.util.List<ShapeGroup> returnValue = Arrays.asList(this.subgroups);
		return returnValue;
	}
	
	
	
	/**
	 * Returns the coordinates in the global coordinate system of the point whose
	 * coordinates in this shape group's inner coordinate system are the given
	 * coordinates.
	 * @param innerCoordinates
	 * The point that you want the GlobalCoordinates from
	 * @inspects | this
	 */
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
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
	 * @inspects | this
	 */
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
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
	 * @inspects | this
	 */
	public IntVector toInnerCoordinates(IntVector relativeGlobalCoordinates) {
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
	 * @inspects | this
	 */
	public void bringToFront() {
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
	 * @inspects | this
	 */
	public void sendToBack() {
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

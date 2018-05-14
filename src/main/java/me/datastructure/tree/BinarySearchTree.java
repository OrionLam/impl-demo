package me.datastructure.tree;

public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
	/**
	 * 二叉树节点
	 * @author Orion
	 *
	 * @param <AnyType>
	 */
	private static class BinaryNode<AnyType> {
		AnyType element;//当前节点数据
		BinaryNode<AnyType> left;//左节点
		BinaryNode<AnyType> right;//右节点
		BinaryNode(AnyType element, BinaryNode<AnyType> left, BinaryNode<AnyType> right) {
			this.element = element;
			this.left = left;
			this.right = right;
		}
		BinaryNode(AnyType element) {
			this(element, null, null);
		}
	}
	
	private BinaryNode<AnyType> root;
	
	public BinarySearchTree() { root = null; }
	
	public void makeEmpty() { root = null; }
	public boolean isEmpty() { return root == null; }
	
	/**
	 * 树中是否包含该节点
	 * @param x
	 * @return true/false
	 */
	public boolean contains(AnyType x) {
		return contains(x, root);
	}
	private boolean contains(AnyType x, BinaryNode<AnyType> t) {
		if ( t == null )
			return false;
		int c = x.compareTo(t.element);
		if ( c < 0)
			return contains(x, t.left);
		else if ( c > 0 )
			return contains(x, t.right);
		else
			return true;
	}
	
	/**
	 * 找最小节点
	 * @return
	 */
	public AnyType findMin() {
		if ( isEmpty() )
			throw new RuntimeException();
		return findMin(root).element;
	}
	private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
		if ( t == null )
			return null;
		else if ( t.left == null )
			return t;
		else
			return findMin(t.left);
	}
	
	/**
	 * 找最大节点
	 * @return
	 */
	public AnyType findMax() {
		if ( isEmpty() )
			throw new RuntimeException();
		return findMax(root).element;
	}
	private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
		if ( t == null )
			return null;
		else if ( t.right == null )
			return t;
		else
			return findMin(t.right);
	}
}

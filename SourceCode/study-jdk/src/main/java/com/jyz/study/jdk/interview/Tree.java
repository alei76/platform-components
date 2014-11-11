package com.jyz.study.jdk.interview;

public class Tree {
	
	protected int value;
	protected Tree left;
	protected Tree right;
	
	public Tree(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Tree getLeft() {
		return left;
	}
	public void setLeft(Tree left) {
		this.left = left;
	}
	public Tree getRight() {
		return right;
	}
	public void setRight(Tree right) {
		this.right = right;
	}
	
	@Override
	public String toString() {
		return "Tree [value=" + value + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tree other = (Tree) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		return true;
	}

}

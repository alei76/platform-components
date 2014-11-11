package com.jyz.study.jdk.interview;

public class TreeMirror {
	
	public static void main(String[] args) {
		Tree t1 = new Tree(1);
		Tree t2 = new Tree(2);
		Tree t3 = new Tree(3);
		Tree t4 = new Tree(4);
		Tree t5 = new Tree(5);
		t1.setLeft(t2);
		t1.setRight(t3);
		t2.setLeft(t4);
		t2.setRight(t5);
		
		System.out.println(t1);
		mirror2(t1);
		System.out.println(t1);
	}
	
	public static void mirror(Tree tree){
		if(tree == null){
			return;
		}
		Tree left = tree.left;
		Tree right = tree.right;
		if(left != null && right != null){
			tree.left = right;
			tree.right = left;
			mirror(tree.left);
			mirror(tree.right);
		}
	}
	
	public static void mirror2(Tree tree){
		if(tree == null){
			return;
		}
		Tree tmp = tree.left;
		tree.left = tree.right;
		tree.right = tmp;
		if(tree.left != null){
			mirror(tree.left);
		}
		if(tree.right != null){
			mirror(tree.right);
		}
	}
	

}

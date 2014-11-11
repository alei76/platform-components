package com.jyz.study.jdk.interview;

public class TreePrint {
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
	
	printTree(t1);
    }
    
    public static void printTree(Tree tree){
	System.out.println(tree.value);
	if(tree.left != null){
	    printTree(tree.left);
	}
	if(tree.right != null){
	    printTree(tree.right);
	}
    }
}

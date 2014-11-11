package com.jyz.study.jdk.interview;

public class TreeSubTree {
	public static void main(String[] args) {
		Tree t1 = new Tree(1);
		Tree t2 = new Tree(2);
		Tree t3 = new Tree(3);
		Tree t4 = new Tree(4);
		Tree t5 = new Tree(5);
		Tree t6 = new Tree(6);
		t1.setLeft(t2);
		t1.setRight(t3);
		t2.setLeft(t4);
		t2.setRight(t5);
		
		System.out.println(isSubTree(t1, t2));
		System.out.println(isSubTree(t1, t6));
	}

	private static boolean isSubTree(Tree t1, Tree t2) {
		boolean result = false;
		if(t1 != null && t2 != null){
			if(t1.value == t2.value){
				result = doesTree1HaveTree2(t1, t2);
			}
			if(!result){
				result = isSubTree(t1.left, t2);
			}
			if(!result){
				result = isSubTree(t1.right, t2);
			}
		}
		return result;
	}

	//注意这里逻辑
	private static boolean doesTree1HaveTree2(Tree t1, Tree t2) {
		if(t2 == null){
			return true;
		}
		if(t1 == null){
			return false;
		}
		if(t1.value != t1.value){
			return false;
		}
		
		return doesTree1HaveTree2(t1.left, t2.left) && doesTree1HaveTree2(t1.right, t2.right);
	}

}

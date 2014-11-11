package com.jyz.study.jdk.interview;

public class NodeReverse {

	public static void main(String[] args) {
		Node node = Node.generateNode();
		Node.printNode(node);
		node = reverseNode(node);
		Node.printNode(node);
		node = reverseNode(node);
		Node.printNode(node);
	}

	private static Node reverseNode(Node node) {
		Node head = null, pre = null, next = null;
		while(node != null){
			next = node.next;
			if(next == null){
				head = node;
			}
			node.next = pre;
			pre = node;
			node = next;
		}
		return head;
	}
	
}

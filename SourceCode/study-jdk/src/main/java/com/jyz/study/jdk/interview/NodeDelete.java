package com.jyz.study.jdk.interview;

public class NodeDelete {

	public static void main(String[] args) {
		Node a = new Node("a");
		Node b = new Node("b");
		Node c = new Node("c");
		Node d = new Node("d");
		Node e = new Node("e");
		Node f = new Node("f");
		a.setNext(b);
		b.setNext(c);
		c.setNext(d);
		d.setNext(e);
		e.setNext(f);
		
		deleteNode(a, c);
//		deleteNode(a, f);
//		deleteNode(a, a);
		Node.printNode(a);
	}
	
	public static void deleteNode(Node head, Node deleted){
		if(head == null || deleted == null){
			return;
		}
		if(deleted.next != null){
			Node next = deleted.next;
			deleted.value = next.value;
			deleted.next = next.next;
			next = null;
		}else if(head == deleted){
			head = null;
		}else{
			Node node = head;
			while(node.next != deleted){
				node = node.next;
			}
			node.next = deleted.next;
			deleted = null;
		}
	}
}

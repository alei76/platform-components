package com.jyz.study.jdk.interview;


public class NodeMerge {

	public static void main(String[] args) {
		Node a = new Node("a");
		Node c = new Node("c");
		Node e = new Node("e");
		Node g = new Node("g");
		Node i = new Node("i");
		Node k = new Node("k");
		a.setNext(c);
		c.setNext(e);
		e.setNext(g);
		g.setNext(i);
		i.setNext(k);
		
		Node b = new Node("b");
		Node d = new Node("d");
		Node f = new Node("f");
		Node h = new Node("h");
		Node j = new Node("j");
		Node l = new Node("l");
		b.setNext(d);
		d.setNext(f);
		f.setNext(h);
		h.setNext(j);
		j.setNext(l);
		
		Node.printNode(a);
		Node.printNode(b);
		
		Node.printNode(merge(a, b));
	}

	private static Node merge(Node a, Node b) {
		if(a == null){
			return b;
		}
		if(b == null){
			return a;
		}
		Node head = null;
		if(a.value.compareTo(b.value) <= 0){
			head = a;
			head.next = merge(a.next, b);
		}else if(a.value.compareTo(b.value) > 0){
			head = b;
			head.next = merge(a, b.next);
		}
		return head;
	}

}

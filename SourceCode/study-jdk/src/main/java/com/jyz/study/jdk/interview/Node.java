package com.jyz.study.jdk.interview;

public class Node {

	protected String value;
	protected Node next;
	
	public Node(String value, Node next) {
		this.value = value;
		this.next = next;
	}
	public Node(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Node other = (Node) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Node [value=" + value + "]";
	}
	public static Node generateNode(){
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
		return a;
	}
	
	public static void printNode(Node node){
		if(node == null){
			System.out.println(node);
			return;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(node.value);
		while(node.next != null){
			sb.append("->").append(node.next.value);
			node = node.next;
		}
		System.out.println(sb);
	}
	
	public static void main(String[] args) {
		printNode(generateNode());
	}
	
}

package com.jyz.study.jdk.interview;


/**
 * 输出链表倒数第N个节点
 * @author JoyoungZhang@gmail.com
 *
 */
public class FindKthToTail {
	
	public static void main(String[] args) {
		int data1[] = {4,3,2,12,1,3,5,7,9,1,4,6};
		System.out.println(findKthToTail(data1, 4));
	
		System.out.println(findKthToTail(Node.generateNode(), 7));
	}

	private static int findKthToTail(int data[], int position){
		int tail = 0, head = 0;
		for(head=0;head<position-1;head++){//position-1
		}
		System.out.println(head);
		while(head<data.length-1){
			head++;
			tail++;
		}
		System.out.println(tail);
		System.out.println(head);
		return data[tail];
	}
	
	private static Node findKthToTail(Node node, int position){
		Node tail = null, head = node;
		for(int i=0;i<position-1;i++){//position-1
			if(head.next == null){
				return null;
			}
			head = head.next;
		}
		tail = node;
		while(head.next != null){
			head = head.next;
			tail = tail.next;
		}
		return tail;
	}
}

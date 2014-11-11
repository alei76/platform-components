package com.jyz.study.jdk.interview;

import java.util.Arrays;

/**
 * 奇数在前 偶数在后
 * @author JoyoungZhang@gmail.com
 *
 */
public class ReorderOddEven {
	
	public static void main(String[] args) {
		int data1[] = {4,3,2,6,1,3,5,7,9,1,4,6,-12,-3};
		System.out.println(Arrays.toString(reorderOddEven(data1)));
		int data2[] = {4,3,2,6,1,3,5,7,9,1,4,6,-12,-3};
		System.out.println(Arrays.toString(reorderOddEven2(data2)));
	}

	public static int[] reorderOddEven(int[] data){
		int begin = 0, end = data.length - 1;
		while(begin < end){
			if(!idOdd(data[begin]) && !idOdd(data[end])){
				end--;
			}else if(!idOdd(data[begin]) && idOdd(data[end])){
				Sort.swap(data, begin, end);
				begin++;
				end--;
			}else if(idOdd(data[begin]) && !idOdd(data[end])){
				begin++;
				end--;
			}else if(idOdd(data[begin]) && idOdd(data[end])){
				begin++;
			}
		}
		return data;
	}
	
	public static int[] reorderOddEven2(int[] data){
		int begin = 0, end = data.length - 1;
		while(begin < end){
			while(begin < end && idOdd(data[begin])){
				begin ++;
			}
			while(begin < end && !idOdd(data[end])){
				end --;
			}
			if(begin < end){
				Sort.swap(data, begin, end);
			}
		}
		return data;
	}
	
	//奇数
	private static boolean idOdd(int data){
//			return data % 2 != 0;
		return (data & 0x1) != 0;
//		return data > 0;
	}
	
	//偶数
//	private static boolean idEven(int data){
//			return data % 2 == 0;
//		return (data & 0x1) == 0;
//	}
	
	
}

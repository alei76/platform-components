package com.jyz.study.jdk.interview;

import java.util.Arrays;

/**
 * 合并两个有序数组
 * @author JoyoungZhang@gmail.com
 *
 */
public class ArrayMerge {

	private static int[] merge1(int[] left, int[] right){
		if(left == null || left.length == 0){
			return right;
		}
		if(right == null || right.length == 0){
			return left;
		}
		int[] newArr = new int[left.length + right.length];
		int pl = left.length - 1;
		int pr = right.length - 1;
		int pn = newArr.length - 1;
		while(pl >= 0 && pr >= 0){
			if(left[pl] < right[pr]){
				newArr[pn--] = right[pr];
				pr--;
			}else{
				newArr[pn--] = left[pl];
				pl--;
			}
		}
		if(pl >= 0){
			System.arraycopy(left, 0, newArr, 0, pl+1);//第三个参数是个数！！！
		}
		if(pr >= 0){
			System.arraycopy(right, 0, newArr, 0, pr+1);
		}
		return newArr;
	}
	
//	不要误以为这样
//	private static void merge2(int[] left, int[] right){
//		int[] newArr = new int[left.length + right.length];
//		int pn = 0;
//		for(int i=0;i<left.length;i++){
//			for(int j=0;j<right.length;j++){
//				if(left[i]<right[j]){
//					newArr[pn++] = left[i];
//				}else{
//					newArr[pn++] = right[j];
//				}
//			}
//		}
//		System.out.println(Arrays.toString(newArr));
//	}
	
	public static void main(String[] args) {
		int[] left = {3,4,6,8};
		int[] right = {1,3,5,7};
		System.out.println(Arrays.toString(merge1(left, right)));
	}

}

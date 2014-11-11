package com.jyz.study.jdk.interview;

import java.util.Arrays;

public class Sort {

	public static void main(String[] args) {
		int data[] = {4,3,2,6,1};
//		System.out.println(Arrays.toString(bubbleSort(data)));
//		System.out.println(Arrays.toString(selectSort(data)));
//		System.out.println(Arrays.toString(insertSort(data)));
//		System.out.println(Arrays.toString(shellSort(data)));
		System.out.println(Arrays.toString(quickSort(data, 0, data.length-1)));
	}
	
	private static int[] bubbleSort(int[] data){
//		int j = data.length - 1;//放外面就失败了！！！[1, 4, 6, 5, 2, 7]
		for(int i=0; i<data.length-1; i++){//data.length-1
			boolean exchange = false;
			int j = data.length - 1;
			while(j > i){
				if(data[j] < data[j-1]){
					swap(data, j, j-1);
					exchange = true;
				}
				j --;
			}
			if(!exchange){
				break;
			}
		}
		return data;
	}
	
	private static int[] selectSort(int[] data){
		int position, j = 0;
		for(int i=0; i<data.length-1; i++){//data.length-1
			position = i;
			j = i + 1;
			while(j < data.length){
				if(data[position] > data[j]){
					position = j;
				}
				j++;
			}
			if(position != i){
				swap(data, position, i);
			}
		}
		return data;
	}
	
	public static int[] insertSort(int[] data) {
		int temp = 0;
		for (int i = 1; i < data.length; i++) {
			if (data[i - 1] > data[i]) {
				temp = data[i];
				int j = i - 1;
				while(j >= 0 && data[j] > temp){
					data[j + 1] = data[j]; // 后移
					j--;
				}
				data[j+1] = temp;//仔细思考j+1
			}
		}
		return data;
	}
	
	public static int[] shellSort(int[] data) {
		int increment = data.length; // 区间为increment
		int temp = 0;
		while (true) {
			increment = increment / 2;
			// 分成了几组，一组一组处理
			for (int x = 0; x < increment; x++) {
				// 下面就是直接插入排序，只是区间由1变成了increment
				for (int i = x + increment; i < data.length; i += increment) {
					if (data[i - increment] > data[i]) {
						temp = data[i];
						int j = i - increment;
						while(j >= 0 && data[j] > temp){
							data[j + increment] = data[j];
							j -= increment;
						}
						data[j + increment] = temp;
					}
				}
			}
			if (increment == 1) {
				break;
			}
		}
		return data;
	}
	
	/**
	 * 快速排序
	 * @param data
	 * @param low data最小下标
	 * @param high data最大下标
	 * @return
	 */
	public static int[] quickSort(int[] data, int low, int high) {
		// 对data[low..high]快速排序
		int pivotpos; // 划分后的基准记录的位置
		if (low < high) {// 仅当区间长度大于1时才须排序
			pivotpos = partition(data, low, high); // 对data[low..high]做划分
			quickSort(data, low, pivotpos - 1); // 对左区间递归排序
			quickSort(data, pivotpos + 1, high);// 对右区间递归排序
		}
		return data;
	}

	/**
	 * 划分算法，划分后
	 * 左边子区间中所有记录的关键字均小于等于基准记录pivot
	 * 右边的子区间中所有记录的关键字均大于等于pivot
	 * 基准记录pivot则位于正确的位置上
	 * @param data
	 * @param i
	 * @param j
	 * @return
	 */
	public static int partition(int[] data, int i, int j) {
		// 并返回基准记录的位置
		int pivot = data[i]; // 用区间的第1个记录作为基准 ，
		while (i < j) { // 从区间两端交替向中间扫描，直至i=j为止
			while (i < j && data[j] >= pivot) {
				// pivot相当于在位置i上
				j--; // 从右向左扫描，查找第1个关键字小于pivot的记录data[j]
			}
			if (i < j) // 表示找到的data[j]的关键字<pivot
				data[i++] = data[j]; // 相当于交换data[i]和data[j]，交换后i指针加1

			while (i < j && data[i] <= pivot) {
				// pivot相当于在位置j上
				i++; // 从左向右扫描，查找第1个关键字大于pivot的记录data[i]
			}
			if (i < j) // 表示找到了R[i]，使data[i]>pivot
				data[j--] = data[i]; // 相当于交换data[i]和data[j]，交换后j指针减1
		} // endwhile
		data[i] = pivot; // 基准记录已被最后定位
		return i;
	}
	
	public static void swap(int[] data, int i, int j){
		int tmp = data[i];
		data[i] = data[j];
		data[j] = tmp;
	}
	
	

}

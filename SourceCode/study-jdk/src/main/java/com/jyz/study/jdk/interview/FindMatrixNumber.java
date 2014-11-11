package com.jyz.study.jdk.interview;

public class FindMatrixNumber {
	private static FindMatrixNumber instance;
	private static boolean found = false;

	public static FindMatrixNumber getInstance() {
		if (instance == null) {
			instance = new FindMatrixNumber();
		}
		return instance;
	}

	public static boolean find(int matrix[][], int number) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return false;
		} else {
			System.out.println("****Start finding****");
			findMatrixNumber(matrix, matrix.length, 0, matrix[0].length, number);
			System.out.println("*****End finding*****");
		}
		return found;
	}

	/**
	 * 
	 * @param matrix 	二维数组
	 * @param rows		总行数
	 * @param row		当前行
	 * @param columns	总列数
	 * @param number	要查找的数
	 */
	private static void findMatrixNumber(int matrix[][], int rows, int row, int columns, int number) {
		if (row > rows - 1 || columns < 1)
			return;
		int cornerNumber = matrix[row][columns - 1];
		System.out.println(cornerNumber);
		if (cornerNumber == number) {
			found = true;
			return;
		} else if (cornerNumber < number) {
			findMatrixNumber(matrix, rows, ++row, columns, number);
		} else if (cornerNumber > number) {
			findMatrixNumber(matrix, rows, row, --columns, number);
		}
	}

	public static void main(String[] args) {
		int matrix[][] = { { 1, 2, 8, 9 }, { 2, 4, 9, 12 }, { 4, 7, 10, 13 }, { 6, 8, 11, 15 } };
		System.out.println(FindMatrixNumber.find(matrix, -1));
	}

}

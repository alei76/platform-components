package com.jyz.study.jdk.jmm;

/**
 * ishl 位运算
 * 	0:   iconst_0
	1:   istore_1
	2:   goto
	5:   iconst_1
	6:   iload_1
	7:   ishl
	8:   istore_2
	9:   iinc
	12:  iload_1
	13:  bipush
	15:  if_icmpl
	18:  return
 *	@author zhaoyong.zhang
 *	create time 2014-1-26
 */
public class Ishl {

	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			int j = 0x1 << i;
		}
	}

}

package com.jyz.study.jdk.jmm;

/**
 * ishl 位运算
 *	@author zhaoyong.zhang
 *	create time 2014-1-26
 */
public class Ishl {

	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			int j = 0x1 << i;
		}
	}
	
	void tt(){
		int j= 0x2 << 1;
	}

}


/**
public static void main(java.lang.String[]);
Code:
 Stack=2, Locals=3, Args_size=1
 0:   iconst_0
 1:   istore_1
 2:   goto    12
 5:   iconst_1
 6:   iload_1
 7:   ishl
 8:   istore_2
 9:   iinc    1, 1
 12:  iload_1
 13:  bipush  10
 15:  if_icmplt       5
 18:  return
LineNumberTable:
 line 23: 0
 line 24: 5
 line 23: 9
 line 26: 18

LocalVariableTable:
 Start  Length  Slot  Name   Signature
 0      19      0    args       [Ljava/lang/String;
 2      16      1    i       I

StackMapTable: number_of_entries = 2
 frame_type = 252 
   offset_delta = 5
   locals = [ int ]
 frame_type = 6 


void tt();
Code:
 Stack=1, Locals=2, Args_size=1
 0:   iconst_4                //常量4，不经过ishl运算？！！！！！！！！！！！！！！！
 1:   istore_1
 2:   return
LineNumberTable:
 line 29: 0
 line 30: 2

LocalVariableTable:
 Start  Length  Slot  Name   Signature
 0      3      0    this       Lcom/jyz/study/jdk/jmm/Ishl;
 2      1      1    j       I


}
 */
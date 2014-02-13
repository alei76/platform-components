package com.jyz.study.jdk.jmm;

/**
 * 	深入java虚拟机p351
 * 	1.同步块时字节码里monitorenter monitorenter 同时抛出异常前先monitorenter
 * 	2.同步方法里没有任何特别的操作码，无论方法正常/异常返回，jvm都会释放锁
 *	@author zhaoyong.zhang
 *	create time 2014-2-13
 */
public class Synchronized {
	public void test1(){
		synchronized (this) {
			int i=100;
		}
	}
	public synchronized void test2(){
		int i=200;
	}
}


/**
 * public void test1();
  Code:
   Stack=2, Locals=4, Args_size=1
   0:   aload_0
   1:   dup
   2:   astore_1
   3:   monitorenter
   4:   bipush  100
   6:   istore_2
   7:   aload_1
   8:   monitorexit
   9:   goto    17	
   12:  astore_3  	//1.把异常对象存放于局部变量3位置
   13:  aload_1		//2.释然锁
   14:  monitorenter
   15:  aload_3		//3.取出异常对象打操作数栈
   16:  athrow		//4.弹出异常对象 抛出
   17:  return
  Exception table:
   from   to  target type
     4     9    12   any
    12    15    12   any
  LineNumberTable:
   line 5: 0
   line 6: 4
   line 7: 7
   line 8: 17

  StackMapTable: number_of_entries = 2
   frame_type = 255  full_frame 
     offset_delta = 12
     locals = [ class com/jyz/study/jdk/jmm
 ]
     stack = [ class java/lang/Throwable ]
   frame_type = 250  chop 
     offset_delta = 4


public synchronized void test2();
  Code:
   Stack=1, Locals=2, Args_size=1
   0:   sipush  200
   3:   istore_1
   4:   return
  LineNumberTable:
   line 10: 0
   line 11: 4


}
**/

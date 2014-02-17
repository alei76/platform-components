package com.jyz.study.jdk.jmm;

public class StringSame {

	public static void main(String[] args) {
		String s1 = "saa";
		String s2 = "sa" + "a";
	}

}

/**
 * 
public static void main(java.lang.String[]);
  Code:
   Stack=1, Locals=3, Args_size=1
   0:   ldc     #2; //String saa
   2:   astore_1
   3:   ldc     #2; //String saa       //编译器常量
   5:   astore_2
   6:   return
}
*/

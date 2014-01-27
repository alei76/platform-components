package com.jyz.study.jdk.jmm;

/**
 *  http://guhanjie.iteye.com/blog/1683637
 *  http://bbs.csdn.net/topics/90058535
 *	@author zhaoyong.zhang
 *	create time 2014-1-27
 */
public class PassByValueOrReference {
	
	

	public static void main(String[] args) {
//		int i = 100;
//		test1(i);
		
		StringBuffer s1 = new StringBuffer("sdf");
		test2(s1);
		System.out.println(s1);
		
		String s2 = new String("sdf");
		test3(s2);
		System.out.println(s2);
		
//		PassByValueOrReferenceInner inner = new PassByValueOrReferenceInner();
//		test4(inner);
		
	}

	static int test1(int num){
		num = num + 10;
		return num;
	}
	static void test2(StringBuffer s){
		s.append("xxxx");
//		s = new StringBuffer("xasdf");
	}
	static void test3(String s){
		s = "xxx";
	}
	
//	static void test4(PassByValueOrReferenceInner inner){
//		inner.num = 10;
//	}
	
	
}

class PassByValueOrReferenceInner{
	public int num;
}
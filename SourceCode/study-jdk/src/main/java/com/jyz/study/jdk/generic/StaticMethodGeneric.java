package com.jyz.study.jdk.generic;

/**
 * Java泛型里,static方法是无法访问泛型类的类型参数(T)的.
 * 如果想让static方法具有泛型能力,就必须使其成为泛型方法
 * @author JoyoungZhang@gmail.com
 *
 */
public class StaticMethodGeneric<T> {
	
//	compile error, can not access T
//	public static T test1(T obj){
//		return obj;
//	}
	
	//you must declare test1 as generic method, 
	//add generic paremeters before return value
	public static <T> T test1(T obj){
		return obj;
	}
	
	//can access T
	private T test2(T obj){
		return obj;
	}
	
	public static void main(String[] args) {
		System.out.println(StaticMethodGeneric.test1("sa"));
		System.out.println(StaticMethodGeneric.test1(1));
		
		
		System.out.println(new StaticMethodGeneric<String>().test2("sa"));
		System.out.println(new StaticMethodGeneric<Integer>().test2(1));
	}

	
}

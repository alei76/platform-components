package com.jyz.study.jdk.generic;


/**
 * Java泛型里,static方法是无法访问泛型类的类型参数(T)的.需要自己定义 
 * http://zy19982004.iteye.com/blog/1976993
 * @author JoyoungZhang@gmail.com
 *
 */
public class StaticMethodGeneric<T> {
	
//	compile error, can not access T
//	public static T test1(T obj){
//		return obj;
//	}
	
	//泛型类里定义了类型参数，static方法不能使用这些类型参数，需要自己定义
	public static <T> T test1(T obj){
		return obj;
	}
	
	//泛型类里定义了类型参数T，泛型方法可以使用这些类型参数
	private T test2(T obj){
		return obj;
	}
	
	//泛型类里没有定义泛型方法想要的类型参数TT，泛型方法需要自己定义
	private <TT> TT test3(TT obj){
		return obj;
	}
	
	public static void main(String[] args) {
		//使用泛型方法的时候，通常不必指明类型参数的值，
		//因为编译器会为我们找出具体的类型，这被称为“类型参数推断”。
		System.out.println(StaticMethodGeneric.test1("sa"));
		System.out.println(StaticMethodGeneric.test1(1));
		//使用泛型类时，必须在创建对象的时候指定类型参数的值；
		System.out.println(new StaticMethodGeneric<String>().test2("sa"));
		System.out.println(new StaticMethodGeneric<Integer>().test2(1));
		
		Object[] objs = new Long[1];
		objs[0] = "a";
	}
	
}

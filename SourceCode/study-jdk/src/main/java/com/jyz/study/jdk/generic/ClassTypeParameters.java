package com.jyz.study.jdk.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在泛型代码内部，无法获得任何有关泛型参数类型的信息
 * @author JoyoungZhang@gmail.com
 *
 */
public class ClassTypeParameters {
	
	static class Frob<T>{}
	static class FrobF<T extends Number>{}
	static class FrobPM<P,M>{}
	
	private static List list1 = new ArrayList();
	private static List<Integer> list2 = new ArrayList<Integer>();
	private static Map<Integer, Integer> map1 = new HashMap<Integer, Integer>();
	
	
	static Frob f1 = new Frob();
	static FrobF<Integer> f2 = new FrobF<Integer>();
	static FrobPM<Integer, Double> f3 = new FrobPM<Integer, Double>();
	

	//Calss.getTypeParameters()将返回一个TypeVariable对象数组
	//表示有泛型声明所声明的类型参数
	public static void main(String[] args) {
		System.out.println(Arrays.toString(list1.getClass().getTypeParameters()));
		System.out.println(Arrays.toString(list2.getClass().getTypeParameters()));
		System.out.println(Arrays.toString(map1.getClass().getTypeParameters()));
		
		System.out.println(Arrays.toString(f1.getClass().getTypeParameters()));
		System.out.println(Arrays.toString(f2.getClass().getTypeParameters()));
		System.out.println(Arrays.toString(f3.getClass().getTypeParameters()));
	}
	
}

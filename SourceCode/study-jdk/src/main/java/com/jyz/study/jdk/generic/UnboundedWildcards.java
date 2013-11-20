package com.jyz.study.jdk.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * 三者区别
 * @author JoyoungZhang@gmail.com
 *
 */
public class UnboundedWildcards {

	private static List list1;
	private static List<?> list2;
	private static List<? extends Object> list3;
	
	static void assing1(List list){
		list1 = list;
		list2 = list;
		list3 = list;
	}
	
	static void assing2(List<?> list){
		list1 = list;
		list2 = list;
		list3 = list;
	}
	
	static void assing3(List<? extends Object> list){
		list1 = list;
		list2 = list;
		list3 = list;
	}
	
	public static void main(String[] args) {
		assing1(new ArrayList());
		assing2(new ArrayList());
		assing3(new ArrayList());
		
		assing1(new ArrayList<String>());
		assing2(new ArrayList<String>());
		assing3(new ArrayList<String>());
		
		List<?> wildList = new ArrayList();
		wildList = new ArrayList<String>();
		assing1(wildList);
		assing2(wildList);
		assing3(wildList);
		
	}
	
}

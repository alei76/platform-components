package com.jyz.study.jdk.generic;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JoyoungZhang@gmail.com
 *
 */
public class UpCast {

	//返回一个参数化的map对象
	private static Map<Integer, String> test1(){
		return new HashMap<Integer, String>();
	}
	
	//返回一个非参数化的map对象
	//参数化的对象-----向上转型为----->非参数化的对象,不会警告
	private static Map test2(){
		return test1();
	}
	
	//非参数化的对象-----向下转型为----->参数化的对象,会警告test2需要参数化
	private static Map<Integer, String> test3(){
		return test2();
	}
	
	
}

package com.jyz.study.jdk.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * 数组是协变的covarant, Super[]是Sub[]的父类型
 * 泛型是不可变的invariant, List<Super>跟List<Sub>没有任何关系
 * @author JoyoungZhang@gmail.com
 *
 */
public class ArrayAndList<T> {

    void test1(){
	Number[] objectArray = new Long[1];
	objectArray[0] = 10.10;//运行时java.lang.ArrayStoreException: java.lang.Double
    }
    
    void test2(){
//	List<Number> list = new ArrayList<Long>();//comiple error
    }
    
    //数组和泛型不能很好的混用
    //如创建 泛型数组，参数化类型的数组，类型参数的数组都是非法的
    void illegal(Object object){
	//可以按编译器喜欢的方式定义一个泛型数组的引用
//	List<String>[] justAReference;
//	//but 永远是一个引用，不能被实例化
//	justAReference = new ArrayList<String>()[];
//	new ArrayList<T>()[];
//	new T[10];
//	//以下也不能工作
//	obj instanceof T
//	new T();
    }
    
    public static void main(String[] args) {
	System.out.println(new ArrayList<Number>().getClass());
	System.out.println(new ArrayList<Integer>().getClass());
    }
    
}

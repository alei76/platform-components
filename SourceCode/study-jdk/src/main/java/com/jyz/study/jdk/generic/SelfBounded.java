package com.jyz.study.jdk.generic;

/**
 *  自限定的类型
 *	@author JoyoungZhang@gmail.com
 *
 */
public class SelfBounded<T extends SelfBounded<T>> {                                                                                                                                                                                                                                                                         

}

//基类用导出类作为其实际类型参数
class GenericType<T>{}

class CuriouslyRecurringGeneric extends GenericType<CuriouslyRecurringGeneric>{}
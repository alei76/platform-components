package com.jyz.study.jdk.generic;

/**
 * 擦除存在于泛型类，也存在于泛型方法
 * 两者擦除原则一样
 * 1.声明形式参数的部分“消失”
 * 2.无限定的形式类型参数将被替换为Object,有限定的形式类型参数将被替换为第一个实际类型参数
 * @author JoyoungZhang@gmail.com
 *
 */
public class AfterErasure {
	
}

class GenericClass<K, V extends Number>{
	private K object1;
	private V object2;
	private K get1(){
		return object1;
	}
	private V get2(){
		return object2;
	}
	
	private  <KK> KK singleMethod1(KK object){
		return object;
	}
	private  <VV extends Number> VV singleMethod2(VV object){
		return object;
	}
}

class ErasureClass{
	private Object object1;
	private Number object2;
	private Object get1(){
		return object1;
	}
	private Number get2(){
		return object2;
	}
	
	private Object singleMethod1(Object object){
		return object;
	}
	private Number singleMethod2(Number object){
		return object;
	}
}

package com.jyz.study.jdk.generic;

/**
 * 擦除继承
 * @author JoyoungZhang@gmail.com
 *
 */
public class ErasureAndInheritance {

	public static void main(String[] args) {
		Derived2 d2 = new Derived2();
		d2.get();
		d2.set("1");
	}
}

class GenericBasic<T> {

	private T element;
	
	public void set(T arg){
		this.element = arg;
	}
	
	public T get(){
		return element;
	}
	
}


class Derived1<T> extends GenericBasic<T>{}
class Derived2 extends GenericBasic{}
//The type Derived3 cannot extend or implement GenericBasic<?>. A supertype may not specify any wildcard
class Derived3 extends GenericBasic<?>{}


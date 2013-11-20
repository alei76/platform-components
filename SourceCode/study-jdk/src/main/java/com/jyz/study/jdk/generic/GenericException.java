package com.jyz.study.jdk.generic;

/**
 * 泛型在异常中的限制
 * @author JoyoungZhang@gmail.com
 *
 */
public class GenericException<T extends Throwable> {
    void test(T t) throws T{//T被擦除到了Throwable, 可以声明形式类型参数这个异常
	try{
	    throw t;	//T被擦除到了Throwable，这里也可以编译通过
	}catch(T ex){ 	//1.catch块不能捕获泛型类型的异常
	    		//因为在编译器和运行期间都必须知道异常的确切类型，即使知道T被擦除到了Throwable也没用
	}
    }
    
    void erasureTest(Throwable t) throws Throwable {
	try{
	    throw t;
	}catch(Throwable ex){
	}
    }
}

//compile error
//2.泛型类不能直接或间接继承Throwable
//并不是说泛型形式类型参数不能继承Throwable
class MyException<T extends Throwable> extends Throwable{
}

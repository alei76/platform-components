package com.jyz.study.jdk.generic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* 什么时候用T 什么时候用?
* @author JoyoungZhang@gmail.com
* http://zy19982004.iteye.com/blog/1978028
*
*/
public class TOrQuestionMark<TT> {

	protected Holder<? extends Throwable> list1;
	protected Holder<? super Throwable> list2;
	private Holder<? extends Throwable> test1(Holder<? extends Throwable> list){
		return list;
	}
	private Holder<? super Throwable> test2(Holder<? super Throwable> list){
		return list;
	}
	
	protected List<? extends TT> list3;
	protected List<? super TT> list4;
	protected List<? extends TT> test3(List<? extends TT> list){
		return list;
	}
	protected List<? super TT> test4(List<? super TT> list){
		return list;
	}
	
	public void init() {
		list1 = new Holder<RuntimeException>();
		list1 = new Holder<IOException>();
		list2 = new Holder<Throwable>();//没办法Throwable已经没有超类了，用自己
		Holder<? extends Throwable> listreturn1 = test1(new Holder<IOException>());
		//Holder<RuntimeException> listreturnbak = test1(new Holder<IOException>()); can not return Holder<RuntimeException>
		Holder<? super Throwable> listreturn2 = test2(new Holder<Throwable>());
		
		//以下依赖TT，先实例化tq
		TOrQuestionMark<Exception> tq = new TOrQuestionMark<Exception>();
		tq.list3 = new ArrayList<RuntimeException>();
		tq.list3 = new ArrayList<IOException>();
		tq.list4 = new ArrayList<Throwable>();
		List<? extends Exception> listreturn3 = tq.test3(new ArrayList<RuntimeException>());
		List<? super Exception> listreturn4 = tq.test4(new ArrayList<Throwable>());
	}

}

class Holder<T extends Throwable>{
	private T obj;
	public void set(T obj){
		this.obj = obj;
	}
	public T get(){
		return obj;
	}
}
//sorry,compile error!!!!!
//class Holder2<T super IOException>{
//	private T obj;
//	public void set(T obj){
//		this.obj = obj;
//	}
//	public T get(){
//		return obj;
//	}
//}

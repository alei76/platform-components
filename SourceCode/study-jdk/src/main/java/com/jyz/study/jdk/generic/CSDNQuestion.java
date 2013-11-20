package com.jyz.study.jdk.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JoyoungZhang@gmail.com
 * http://bbs.csdn.net/topics/300180772
 */
public class CSDNQuestion {

	private List<? extends A> list;
	
	void init(){
		list = new ArrayList<A>();
		list = new ArrayList<B>();
		list = new ArrayList<C>();
		
		list.add(null);
	}
	
	public List<? extends A> get(){
		return list;
	}
	
	public static void main(String[] args) {
		CSDNQuestion question = new CSDNQuestion();
		question.init();
		System.out.println(question.get().size());
	}
	
}

class A{
	
}

class B extends A{
	
}

class C extends A{
	
}

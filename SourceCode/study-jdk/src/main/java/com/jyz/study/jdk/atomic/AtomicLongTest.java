package com.jyz.study.jdk.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongTest {

	public static void main(String[] args) {
		//AtomicLong
		AtomicLong al = new AtomicLong(2);
		System.out.println(al.doubleValue());
		System.out.println(al.floatValue());
		System.out.println(al.intValue());
		System.out.println(al.get());
		//先get再操作，类似i++
//		System.out.println(al.getAndAdd(2));		
//		System.out.println(al.getAndIncrement()); 	
//		System.out.println(al.getAndDecrement());	
		
		//先操作再get类似++i
//		System.out.println(al.addAndGet(2));		
//		System.out.println(al.incrementAndGet());
//		System.out.println(al.decrementAndGet());
		
		//期望2
		//如果是设置为3
		//如果不是，不设置
		//返回al
		//weakCompareAndSet同http://www.blogjava.net/xylz/archive/2010/07/01/324988.html
//		System.out.println(al.compareAndSet(2, 3));
//		System.out.println(al.get());//3
//		System.out.println(al.compareAndSet(3, 3));
//		System.out.println(al.get());//2
		
		
		//AtomicInteger比AtomicLong多一个getAndSet
		AtomicInteger ai = new AtomicInteger(2);
		System.out.println(ai.getAndSet(3));
		System.out.println(ai.get());
		
	}

}

package com.jyz.study.jdk.ref;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakReferenceTest {
	public static void main(String[] args) {
		A a = new A();
		// 使用a
		a.test();
		// 使用完了a,将它设置为soft引用类型,并且释放强引用
		ReferenceQueue<A> queue = new ReferenceQueue<A>();
		WeakReference  sr = new WeakReference (a, queue);
		a = null;
		System.gc();  
		System.out.println(sr.isEnqueued());
		System.out.println(queue.poll());
		// 下次使用
		if (sr != null && sr.get() != null) {
			System.out.println("a is not null");
			a = (A) sr.get();
			a.test();
		} else {
			System.out.println("a is null");
			// GC由于低内存,已释放a,因此需要重新装载
			a = new A();
			a.test();
			a = null;
			sr = new WeakReference (a);
		}
	}
}


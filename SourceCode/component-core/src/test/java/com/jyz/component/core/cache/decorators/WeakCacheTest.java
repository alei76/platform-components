package com.jyz.component.core.cache.decorators;

import junit.framework.TestCase;

import com.jyz.component.core.cache.impl.PerpetualCache;

/**
 * @author JoyoungZhang@gmail.com
 * 
 */
public class WeakCacheTest extends TestCase {

	public void testGetObject() {
		//init size = 1
		WeakCache cache = new WeakCache(new PerpetualCache("l1"), 1);
		Inner s1 = new Inner(1);
		Inner s2 = new Inner(2);
		cache.putObject("key1", s1);
		cache.putObject("key2", s2);
		
		System.out.println(cache.getObject("key1"));//此时hardLinksToAvoidGarbageCollection保存s1
		System.out.println(cache.getObject("key2"));//此时hardLinksToAvoidGarbageCollection保存s2
		s1 = null;
		System.gc();//System.gc()后s1满足对象外部无引用 + hardLinksToAvoidGarbageCollection不引用
		System.out.println(cache.getObject("key1"));
		System.out.println(cache.getObject("key2"));
	}

}

class Inner{
	private int p;
	public Inner(int p) {
		this.p = p;
	}
	
	@Override
	public String toString() {
		return "p=" + p;
	}
}

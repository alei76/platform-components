package com.jyz.component.core.cache.decorators;

import java.io.Serializable;

import junit.framework.TestCase;

import com.jyz.component.core.cache.impl.PerpetualCache;

public class SerializedCacheTest extends TestCase {

	public void testGetObject() {
		SerializedCache cache = new SerializedCache(new PerpetualCache("s1"));
		cache.putObject("sa", new Inner());
		((Inner)cache.getObject("sa")).print1();
	}
	
	private static class Inner implements Serializable{
		private static final long serialVersionUID = 1L;
		void print1(){
			System.out.println(1);
		}
	}

}

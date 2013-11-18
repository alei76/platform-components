package com.jyz.study.jdk.generic;

import java.util.Iterator;

/**
 * has fail
 * @author JoyoungZhang@gmail.com
 *
 */
public class IterableTest<T> implements Iterable<T>{
	
	private static final int size = 10;

	@Override
	public Iterator<T> iterator() {
		return new IteratorTest<T>(size);
	}
	
	public static void main(String[] args) {
		for(String str : new IterableTest<String>()){
			System.out.println(str);
		}
	}
	

}

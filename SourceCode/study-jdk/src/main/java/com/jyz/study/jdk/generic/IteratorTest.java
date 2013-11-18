package com.jyz.study.jdk.generic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * has fail
 * @author JoyoungZhang@gmail.com
 *
 */
public class IteratorTest<T> implements Iterator<T>{
	
	private int size;
	private List<T> list;
	
	public IteratorTest(int size){
		this.size = size;
		list = new ArrayList<T>(size);
	}

	@Override
	public boolean hasNext() {
		if(size > 0){
			return true;
		}
		return false;
	}

	@Override
	public T next() {
		return list.get(size--);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}

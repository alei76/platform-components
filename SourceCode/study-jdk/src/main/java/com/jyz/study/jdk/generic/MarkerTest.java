package com.jyz.study.jdk.generic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author JoyoungZhang@gmail.com
 *
 */
public class MarkerTest<T> {

	public static void main(String[] args) {
		ArrayMarker<String> arrMarker = new ArrayMarker<String>(String.class);
		System.out.println(arrMarker.create(10) instanceof String[]);
		System.out.println(Arrays.toString(arrMarker.create(10)));
		
		System.out.println(Arrays.toString((String[])Array.newInstance(String.class, 10)));
		System.out.println(Array.newInstance(String.class, 10) instanceof String[]);
		
		ListMarker<String> listMarker = new ListMarker<String>();
		System.out.println(listMarker.create());
		
		FilledListMarker<String> filledMarker = new FilledListMarker<String>();
		System.out.println(Arrays.toString(filledMarker.create("a", 10).toArray()));
	}
	
}

class ArrayMarker<T>{
	private Class<T> kind;
	
	public ArrayMarker(Class<T> kind) {
		this.kind = kind;
	}
	
	T[] create(int size){
		return (T[]) Array.newInstance(kind, size);
	}
}

class ListMarker<T>{
	List<T> create(){
		return new ArrayList<T>();
	}
}

class FilledListMarker<T>{
	List<T> create(T t, int n){
		List<T> result = new ArrayList<T>();
		for(int i=0; i<n; i++){
			result.add(t);
		}
		return result;
	}
}

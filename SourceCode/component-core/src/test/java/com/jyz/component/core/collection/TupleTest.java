package com.jyz.component.core.collection;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 *  
 *	@author JoyoungZhang@gmail.com
 *
 */
public class TupleTest extends TestCase {
	
	public void test(){
		Tuple<String, String> tuple1 = new Tuple<String, String>("1s", "2说");
		Tuple<String, String> tuple2 = new Tuple<String, String>("1s", "2说");
		System.out.println(tuple1.equals(tuple2));
		System.out.println(tuple1);
		System.out.println(tuple2);
		
		System.out.println("=========================================================");
		Map<Tuple<String, String>, String> triples = new HashMap<Tuple<String, String>, String>();
		triples.put(tuple1, tuple1.toString());
		System.out.println(triples.get(tuple2));
	}
	
}

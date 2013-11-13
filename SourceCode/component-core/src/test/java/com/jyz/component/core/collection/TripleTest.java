package com.jyz.component.core.collection;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 *  
 *	@author JoyoungZhang@gmail.com
 *
 */
public class TripleTest extends TestCase {
	
	public void test(){
		Triple<String, String, String> triple1 = new Triple<String, String, String>("1s", "2说", "3说");
		Triple<String, String, String> triple2 = new Triple<String, String, String>("1s", "2说", "3说");
		System.out.println(triple1.equals(triple2));
		System.out.println(triple1);
		System.out.println(triple2);
		
		System.out.println("=========================================================");
		Map<Triple<String, String, String>, String> triples = new HashMap<Triple<String, String, String>, String>();
		triples.put(triple1, triple1.toString());
		System.out.println(triples.get(triple2));
	}
	

}

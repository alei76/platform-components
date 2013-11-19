package com.jyz.study.jdk.generic;

import java.util.HashMap;
import java.util.Map;


/**
 * GnericMethodReturnObject
 * 显式的类型说明
 * @author JoyoungZhang@gmail.com
 * http://zy19982004.iteye.com/blog/1976993
 *
 */
public class ExplicitTypeSpecification {
	
	static class NewCollections1{
		public static <K, V> Map<K, V> map(){
			return new HashMap<K, V>();
		}
	}
	
	class NewCollections2{
		public <K, V> Map<K, V> map(){
			return new HashMap<K, V>();
		}
	}
	
	public <K, V> Map<K, V> map(){
		return new HashMap<K, V>();
	}
	
	static void test1(Map<Integer, String> map){
		
	}
	
	public void main() {
		//compile error, NewCollections.map() return Map<Object, Object>, but test1 requried Map<Integer, String>
//		test1(NewCollections1.map());
		//compile ok. 等价于Map<Integer, String> map = NewCollections1.<Integer, String>map();
		test1(NewCollections1.<Integer, String>map());
		
		//compile error, NewCollections.map() return Map<Object, Object>, but test1 requried Map<Integer, String>
//		test1(new NewCollections2().map());
		//compile ok. 等价于Map<Integer, String> map2 = new NewCollections2().<Integer, String>map();
		test1(new NewCollections2().<Integer, String>map());
		
		//compile error, this.map() return Map<Object, Object>, but test1 requried Map<Integer, String>
//		test1(this.map());
		//compile ok. 等价于Map<Integer, String> map = this.<Integer, String>map();
		test1(this.<Integer, String>map());
	}
	
}

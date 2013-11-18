package com.jyz.component.core.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 *	@author JoyoungZhang@gmail.com
 *
 */
public class SetUtils {
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return 一个新的set,包含a b的全集
	 */
	public static <T> Set<T> union(Set<T> a, Set<T> b) {
		Set<T> result = new HashSet<T>(a);
		result.addAll(b);
		return result;
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @return 一个新的set,包含a b的交集
	 */
	public static <T> Set<T> intersection(Set<T> a, Set<T> b) {
		Set<T> result = new HashSet<T>(a);
		result.retainAll(b);
		return result;
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return 一个新的set,包含了从a里移掉b后的元素
	 */
	public static <T> Set<T> difference(Set<T> a, Set<T> b) {
		Set<T> result = new HashSet<T>(a);
		result.removeAll(b);
		return result;
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return 一个新的set,包含了a b交集之外的其它元素
	 */
	public static <T> Set<T> complement(Set<T> a, Set<T> b) {
		return difference(union(a, b), intersection(a, b));
	}
	
}

package com.jyz.component.core.collection;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 *  泛型数组
 *  from Thinking in Java page 385
 *	@author JoyoungZhang@gmail.com
 *
 */
public class JyzArray<T> {
	
	private T[] array;
	
	/**
	 * 
	 * @param clazz 从擦除中恢复类型，使得我们可以创建需要的实际类型的数组
	 * 				该数组的运行时类型就是确切类型T[]，而不是擦除后的Object
	 * @param length 数组长度
	 */
	@SuppressWarnings("unchecked")
	public JyzArray(Class<T> clazz, int length){
		array =  (T[]) Array.newInstance(clazz, length);
	}
	
	/**
	 * put item to index
	 * @param index
	 * @param item
	 * @throws IndexOutOfBoundsException
	 */
	public void put(int index, T item){
		RangeCheck(index, array.length);
		array[index] = item;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public T get(int index){
		RangeCheck(index, array.length);
		return array[index];
	}
	
	/**
	 * 
	 * @return 泛型数组
	 */
	public T[] rep(){
		return array;
	}
	
	private void RangeCheck(int index, int length) {
		if (index >= length) {
		    throw new IndexOutOfBoundsException("Index: "+index+", Size: "+length);
		}
    }
	
	public static void main(String[] args) {
		JyzArray<Integer> jyzArray = new JyzArray<Integer>(Integer.class, 8);
		for(int i=0; i<8; i++){
			jyzArray.put(i-1, i);
		}
		Integer[] array = jyzArray.rep();
		System.out.println(Arrays.toString(array));
	}
	
}

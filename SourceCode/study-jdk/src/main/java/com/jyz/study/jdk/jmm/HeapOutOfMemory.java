package com.jyz.study.jdk.jmm;

import java.util.ArrayList;
import java.util.List;

/**
 *  堆溢出
 *  http://blog.csdn.net/zapldy/article/details/7401063
 *	@author zhaoyong.zhang
 *  @VM args:-verbose:gc -Xms20M -Xmx20M -XX:+PrintGCDetails 
 *	create time 2014-1-21
 */ 
public class HeapOutOfMemory  {

	public static void main(String[] args) {
		final List<HeapOutOfMemory> list = new ArrayList<HeapOutOfMemory>();
		while(true){
			list.add(new HeapOutOfMemory());
		}
	}

}

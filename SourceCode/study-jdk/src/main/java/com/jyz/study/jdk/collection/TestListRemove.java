package com.jyz.study.jdk.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试List移除元素时下标的变化
 * @author JoyoungZhang@gmail.com
 *
 */
public class TestListRemove {

	public static void main(String[] args) {
		List<Integer> ts = new ArrayList<Integer>();
		ts.add(1);
		ts.add(2);
		ts.add(3);
		ts.add(4);
		ts.remove(0);
		ts.remove(1);
		System.out.println(Arrays.toString(ts.toArray()));
	}
}

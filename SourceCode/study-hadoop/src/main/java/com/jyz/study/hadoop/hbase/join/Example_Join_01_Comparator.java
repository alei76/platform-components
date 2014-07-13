package com.jyz.study.hadoop.hbase.join;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 分组比较器
 * @author JoyoungZhang@gmail.com
 *
 */
public class Example_Join_01_Comparator extends WritableComparator {
    public Example_Join_01_Comparator() {
	super(TextPair.class, true);
    }

    @SuppressWarnings("unchecked")
    public int compare(WritableComparable a, WritableComparable b) {
	TextPair t1 = (TextPair) a;
	TextPair t2 = (TextPair) b;
	return t1.getFirst().compareTo(t2.getFirst());
    }
}
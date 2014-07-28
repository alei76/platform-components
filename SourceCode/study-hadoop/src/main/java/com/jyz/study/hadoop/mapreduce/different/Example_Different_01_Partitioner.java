package com.jyz.study.hadoop.mapreduce.different;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import com.jyz.study.hadoop.common.TextPair;

/**
 * 分区
 * 
 * @author JoyoungZhang@gmail.com
 */
public class Example_Different_01_Partitioner extends Partitioner<TextPair, Text> {
    @Override
    public int getPartition(TextPair key, Text value, int numParititon) {
	return Math.abs(key.getFirst().hashCode() * 127) % numParititon;
    }

    public static void main(String[] args) {
	System.out.println(Math.abs("1003".hashCode() * 127) % 3);
	System.out.println(Math.abs("1004".hashCode() * 127) % 3);
	System.out.println(Math.abs("1005".hashCode() * 127) % 3);
	System.out.println(Math.abs("1006".hashCode() * 127) % 3);
    }
}
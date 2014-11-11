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
	int result = Math.abs(key.getFirst().hashCode() * 127) % numParititon;
	System.out.println(key + "--------" + result);
	return result;
    }

    public static void main(String[] args) {
	System.out.println(Math.abs("1".hashCode() * 127) % 3);
	System.out.println(Math.abs("3".hashCode() * 127) % 3);
	System.out.println(Math.abs("2".hashCode() * 127) % 3);
    }
}
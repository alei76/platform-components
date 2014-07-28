package com.jyz.study.hadoop.mapreduce.joinmore;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.jyz.study.hadoop.common.TextPair;

/**
 * Reduce函数
 * 
 * @author JoyoungZhang@gmail.com
 */
public class Example_JoinMore_01_Reduce extends Reducer<TextPair, Text, Text, Text> {
    protected void reduce(TextPair key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
	Text pid = key.getFirst();
	String desc = values.iterator().next().toString();
	while (values.iterator().hasNext()) {
	    context.write(pid, new Text(values.iterator().next().toString() + "\t" + desc));
	}
    }
}
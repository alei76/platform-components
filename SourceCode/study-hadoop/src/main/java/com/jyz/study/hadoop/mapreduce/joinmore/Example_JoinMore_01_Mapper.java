package com.jyz.study.hadoop.mapreduce.joinmore;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import com.jyz.study.hadoop.common.TextPair;

/**
 * Mapper函数
 * @author JoyoungZhang@gmail.com
 *
 */
public class Example_JoinMore_01_Mapper extends
	Mapper<LongWritable, Text, TextPair, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context)
	    throws IOException, InterruptedException {
	// 获取输入文件的全路径和名称
	String pathName = ((FileSplit) context.getInputSplit()).getPath()
		.toString();
	if (pathName.contains("data.txt")) {
	    String values[] = value.toString().split("\t");
	    if (values.length < 3) {
		// data数据格式不规范，字段小于3，抛弃数据
		return;
	    } else {
		// 数据格式规范，区分标识为1
		TextPair tp = new TextPair(new Text(values[1]), new Text("1"));
		context.write(tp, new Text(values[0] + "\t" + values[2]));
	    }
	}
	if (pathName.contains("info.txt")) {
	    String values[] = value.toString().split("\t");
	    if (values.length < 2) {
		// data数据格式不规范，字段小于2，抛弃数据
		return;
	    } else {
		// 数据格式规范，区分标识为0
		TextPair tp = new TextPair(new Text(values[0]), new Text("0"));
		context.write(tp, new Text(values[1]));
	    }
	}
    }
}

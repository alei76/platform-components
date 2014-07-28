package com.jyz.study.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.jyz.study.hadoop.common.ConfigurationUtils;
import com.jyz.study.hadoop.common.TextPair;
import com.jyz.study.hadoop.common.Utils;
import com.jyz.study.hadoop.mapreduce.different.Example_Different_01_Comparator;
import com.jyz.study.hadoop.mapreduce.different.Example_Different_01_KeyComparator;
import com.jyz.study.hadoop.mapreduce.different.Example_Different_01_Partitioner;

/**
 * 同一个key下的values组内排序
 * 
 * @author JoyoungZhang@gmail.com
 */
public class ValuesSort {

    public static class TokenizerMapper extends Mapper<Object, Text, TextPair, Text> {

	/**
	 * key is offset value is line
	 */
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
	    String values[] = value.toString().split("\t");
	    if (values[0].equals("1001")) {
		return;
	    }
	    context.write(new TextPair(values[0], values[1]), new Text(values[1]));
	}
    }

    public static class JoinReduce extends Reducer<TextPair, Text, Text, Text> {

	public void reduce(TextPair key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
	    Text keyout = new Text(key.getFirst());
	    StringBuffer sb = new StringBuffer();
	    for (Text val : values) {
		sb.append(val + ",");
	    }
	    if (sb.length() > 0) {
		sb.deleteCharAt(sb.length() - 1);
	    }
	    Text valueout = new Text(sb.toString());
	    context.write(keyout, valueout);
	}
    }

    public static void main(String[] args) throws Exception {
	Configuration conf = ConfigurationUtils.getHadoopConfiguration();
	String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
	if (otherArgs.length != 2) {
	    System.err.println("Usage: wordcount <in> <out>");
	    System.exit(2);
	}
	Job job = new Job(conf, "ValuesSort");
	job.setJarByClass(ValuesSort.class);
	job.setMapperClass(TokenizerMapper.class);
	job.setReducerClass(JoinReduce.class);
	job.setMapOutputKeyClass(TextPair.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(Text.class);
	job.setNumReduceTasks(1);

	// 设置partition
	job.setPartitionerClass(Example_Different_01_Partitioner.class);
	// 在分区之后按照指定的条件分组
	job.setGroupingComparatorClass(Example_Different_01_Comparator.class);
	// key比较函数
	job.setSortComparatorClass(Example_Different_01_KeyComparator.class);

	FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
	Utils.deleteIfExists(conf, otherArgs[1]);
	FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
	System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}

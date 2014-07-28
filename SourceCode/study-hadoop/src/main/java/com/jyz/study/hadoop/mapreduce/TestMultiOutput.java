package com.jyz.study.hadoop.mapreduce;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.jyz.study.hadoop.common.ConfigurationUtils;
import com.jyz.study.hadoop.common.Utils;

/**
 * http://www.iteye.com/topic/1133853 如何使用Hadoop的MultipleOutputs进行多文件输出
 * 
 * @author JoyoungZhang@gmail.com
 */
public class TestMultiOutput {

    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
	    StringTokenizer itr = new StringTokenizer(value.toString());
	    while (itr.hasMoreTokens()) {
		word.set(itr.nextToken());
		context.write(word, one);
	    }
	}
    }

    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	private IntWritable result = new IntWritable();
	private MultipleOutputs mos;

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
	    mos = new MultipleOutputs(context);// 初始化mos
	}

	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
	    mos.close();// 释放资源
	}

	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
	    int sum = 0;
	    for (IntWritable val : values) {
		sum += val.get();
	    }
	    result.set(sum);
	    System.out.println(key);
	    if (key.equals(new Text("china"))) {
		System.out.println("china");
		mos.write("cn", key, result);
	    } else if (key.equals(new Text("us"))) {
		System.out.println("us");
		mos.write("us", key, result);
	    } else {
		System.out.println(new Text("other"));
		mos.write("other", key, result);
	    }
	    System.out.println("=====");
	    // context.write(key, result);
	}
    }

    public static void main(String[] args) throws Exception {
	Configuration conf = ConfigurationUtils.getHadoopConfiguration();
	String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
	if (otherArgs.length != 2) {
	    System.err.println("Usage: wordcount <in> <out>");
	    System.exit(2);
	}
	Job job = new Job(conf, "myWordCount");
	job.setJarByClass(TestMultiOutput.class);
	job.setMapperClass(TokenizerMapper.class);
	job.setCombinerClass(IntSumReducer.class);
	job.setReducerClass(IntSumReducer.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(IntWritable.class);
	job.setNumReduceTasks(3);
	FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
	Utils.deleteIfExists(conf, otherArgs[1]);
	FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

	MultipleOutputs.addNamedOutput(job, "cn", TextOutputFormat.class, Text.class, Text.class);
	MultipleOutputs.addNamedOutput(job, "us", TextOutputFormat.class, Text.class, Text.class);
	MultipleOutputs.addNamedOutput(job, "other", TextOutputFormat.class, Text.class, Text.class);

	System.exit(job.waitForCompletion(true) ? 0 : 1);
	// System.out.println(System.getProperty("java.library.path"));
	// String arch = System.getProperty("sun.arch.data.model");
	// System.out.println(arch);
	// System.out.println(System.getProperty("os.arch"));
	// System.loadLibrary("hadoop");

    }
}

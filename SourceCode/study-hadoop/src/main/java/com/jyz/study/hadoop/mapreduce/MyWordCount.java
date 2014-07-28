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
import org.apache.hadoop.util.GenericOptionsParser;

import com.jyz.study.hadoop.common.ConfigurationUtils;
import com.jyz.study.hadoop.common.Utils;

public class MyWordCount {

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

	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
	    int sum = 0;
	    for (IntWritable val : values) {
		sum += val.get();
	    }
	    result.set(sum);
	    context.write(key, result);
	}
    }

    public static void main(String[] args) throws Exception {
	Configuration conf = ConfigurationUtils.getHadoopConfiguration();
	String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
	if (otherArgs.length != 3) {
	    System.err.println("Usage: wordcount <in> <in> <out>");
	    System.exit(2);
	}
	Job job = new Job(conf, "myWordCount");
	job.setJarByClass(MyWordCount.class);
	job.setMapperClass(TokenizerMapper.class);
	job.setCombinerClass(IntSumReducer.class);
	job.setReducerClass(IntSumReducer.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(IntWritable.class);
	job.setNumReduceTasks(1);
	// job.setInputFormatClass(TextInputFormat.class);
	// job.setOutputFormatClass(TextOutputFormat.class);
	FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
	FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
	Utils.deleteIfExists(conf, otherArgs[2]);
	FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
	System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}

package com.jyz.study.hadoop.mapreduce;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.jyz.study.hadoop.common.ConfigurationUtils;
import com.jyz.study.hadoop.common.Utils;

public class SequenceFileFormat {
    public static class SequenceFileMapper extends
	    Mapper<Text, IntWritable, Text, IntWritable> {

	/**
	 * key is Text, value is IntWritable
	 */
	public void map(Text key, IntWritable value, Context context)
		throws IOException, InterruptedException {
	    context.write(key, value);
	}
    }

    public static class SequenceFileReduce extends
	    Reducer<Text, IntWritable, Text, Text> {

	public void reduce(Text key, Iterable<IntWritable> values, Context context)
		throws IOException, InterruptedException {
	    StringBuffer sb = new StringBuffer();
	    Iterator<IntWritable> it = values.iterator();
	    while(it.hasNext()){
		sb.append(it.next().toString());
	    }
	    context.write(key, new Text(sb.toString()));
	}
    }

    public static void main(String[] args) throws Exception {
	Configuration conf = ConfigurationUtils.getHadoopConfiguration();
	String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
	Job job = new Job(conf, "SequenceFileFormat");
	job.setJarByClass(ValuesSortWithKeyValueTextInputFormat.class);
	job.setMapperClass(SequenceFileMapper.class);
	job.setReducerClass(SequenceFileReduce.class);
	job.setMapOutputKeyClass(Text.class);
	job.setMapOutputValueClass(IntWritable.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(Text.class);
	job.setNumReduceTasks(1);

	job.setInputFormatClass(SequenceFileInputFormat.class);
//	job.setOutputFormatClass(SequenceFileOutputFormat.class);//默认使用FileOutputFormat输出

	Utils.deleteIfExists(conf, "hdfs://200master:9000/user/root/output2");
	FileInputFormat.addInputPath(job, new Path("hdfs://200master:9000/user/root/test/SequenceFile.txt"));
	FileOutputFormat.setOutputPath(job, new Path("hdfs://200master:9000/user/root/output2"));
	System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}

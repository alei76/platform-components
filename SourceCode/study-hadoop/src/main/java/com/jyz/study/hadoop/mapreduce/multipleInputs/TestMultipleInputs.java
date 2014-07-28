package com.jyz.study.hadoop.mapreduce.multipleInputs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.citic.zxyjs.zwlscx.mapreduce.lib.input.MultipleInputs;
import com.jyz.study.hadoop.common.ConfigurationUtils;
import com.jyz.study.hadoop.common.Utils;

/**
 * 不同input 不同InputFormat
 * 
 * @author JoyoungZhang@gmail.com
 */
public class TestMultipleInputs {

    public static class TextInputFormatMapper extends Mapper<LongWritable, Text, Text, Text> {
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	    context.write(new Text(key.toString()), value);
	}
    }

    public static class KeyValueTextInputFormatMapper extends Mapper<Text, Text, Text, Text> {
	public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
	    context.write(key, value);
	}
    }

    public static class TableInputFormatMapper extends TableMapper<Text, Text> {
	public void map(ImmutableBytesWritable row, Result columns, Context context) throws IOException {
	    try {
		context.write(new Text(Bytes.toStringBinary(row.get())), new Text(Bytes.toStringBinary(columns.list().get(0)
			.getValue())));
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    public static class MergeReducer extends Reducer<Text, Text, Text, Text> {

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
	    StringBuffer sb = new StringBuffer();
	    for (Text val : values) {
		sb.append(val + ",");
	    }
	    if (sb.length() > 0) {
		sb.deleteCharAt(sb.length() - 1);
	    }
	    context.write(key, new Text(sb.toString()));
	}
    }

    public static void main(String[] args) throws Exception {
	Configuration conf = ConfigurationUtils.getHbaseConfiguration();
	conf.set(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR, " ");
	Job job = new Job(conf, "TestMultipleInputs");
	job.setJarByClass(TestMultipleInputs.class);

	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(Text.class);

	job.setReducerClass(MergeReducer.class);
	job.setNumReduceTasks(1);

	MultipleInputs.addInputPath(job, new Path("hdfs://200master:9000/user/root/multipleinputs/TextInputFormat.txt"),
		TextInputFormat.class, TextInputFormatMapper.class);
	MultipleInputs.addInputPath(job, new Path("hf"), TableInputFormat.class, TableInputFormatMapper.class);

	Utils.deleteIfExists(conf, "hdfs://200master:9000/user/root/multipleinputs/output.txt");
	FileOutputFormat.setOutputPath(job, new Path("hdfs://200master:9000/user/root/multipleinputs/output.txt"));
	System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}

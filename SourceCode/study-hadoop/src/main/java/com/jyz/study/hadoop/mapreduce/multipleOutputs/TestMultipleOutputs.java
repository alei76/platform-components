package com.jyz.study.hadoop.mapreduce.multipleOutputs;

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

import com.jyz.study.hadoop.common.ConfigurationUtils;
import com.jyz.study.hadoop.common.Utils;

public class TestMultipleOutputs {

    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

	private MultipleOutputs<Text, IntWritable> mos;
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
	    super.setup(context);
	    this.mos = new MultipleOutputs(context);
	}

	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
	    mos.close();
	}

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
	    StringTokenizer itr = new StringTokenizer(value.toString());
	    while (itr.hasMoreTokens()) {
		String token = itr.nextToken();
		word.set(token);
		//		if (token.equals("1002")) {
		mos.write(word, one, "hdfs://200master:9000/user/root/error");
		//		} else {
		//mos.write("normal", key, result);
		//		    context.write(word, one);
		//		}
	    }
	}
    }

    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	private MultipleOutputs<Text, IntWritable> mos;
	private IntWritable result = new IntWritable();

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
	    super.setup(context);
	    this.mos = new MultipleOutputs(context);
	}

	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
	    mos.close();
	}

	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
	    int sum = 0;
	    for (IntWritable val : values) {
		sum += val.get();
	    }
	    result.set(sum);
	    //	    if(key.toString().equals("1001")){
	    //	    		mos.write("error", key, result);
	    //	    }else{
	    //		mos.write("normal", key, result);
	    //	    context.write(key, result);
	    //	    }
	}
    }

    public static void main(String[] args) throws Exception {
	Configuration conf = ConfigurationUtils.getHadoopConfiguration();
	Job job = new Job(conf, "TestMultipleOutputs");
	job.setJarByClass(TestMultipleOutputs.class);
	job.setMapperClass(TokenizerMapper.class);
	job.setReducerClass(IntSumReducer.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(IntWritable.class);
	job.setNumReduceTasks(1);

	//	MultipleOutputs.addNamedOutput(job, "normal", TextOutputFormat.class, Text.class, IntWritable.class);
	//	MultipleOutputs.addNamedOutput(job, "error", TextOutputFormat.class, Text.class, IntWritable.class);

	FileInputFormat.addInputPath(job, new Path("hdfs://200master:9000/user/root/input/all.txt"));
	Utils.deleteIfExists(conf, "hdfs://200master:9000/user/root/multioutput");
	FileOutputFormat.setOutputPath(job, new Path("hdfs://200master:9000/user/root/multioutput"));
	System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}

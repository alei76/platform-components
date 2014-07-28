package com.jyz.study.hadoop.mapreduce.join;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.jyz.study.hadoop.common.ConfigurationUtils;
import com.jyz.study.hadoop.common.Utils;

/**
 * Mapper函数
 * 
 * @author JoyoungZhang@gmail.com
 */
public class Example_Join_01_Mapper_DistributedCache extends Mapper<Text, Text, Text, Text> {

    private Hashtable<String, String> joinData = new Hashtable<String, String>();

    protected void setup(org.apache.hadoop.mapreduce.Mapper<Text, Text, Text, Text>.Context context) throws IOException,
	    InterruptedException {
	try {
	    Path[] cacheFiles = DistributedCache.getLocalCacheFiles(context.getConfiguration());
	    if (cacheFiles != null && cacheFiles.length > 0) {
		String line;
		String[] tokens;
		BufferedReader joinReader = new BufferedReader(new FileReader(cacheFiles[0].toString()));
		try {
		    while ((line = joinReader.readLine()) != null) {
			tokens = line.split("\t");
			joinData.put(tokens[0], tokens[1]);
		    }
		} finally {
		    joinReader.close();
		}
	    }
	} catch (IOException e) {
	    System.err.println("Exception reading DistributedCache: " + e);
	}
    }

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
	String joinValue = joinData.get(key.toString());
	if (joinValue != null) {
	    context.write(key, new Text(value.toString() + "," + joinValue));
	}
    }

    public static void main(String agrs[]) throws IOException, InterruptedException, ClassNotFoundException {
	Configuration conf = ConfigurationUtils.getHadoopConfiguration();
	GenericOptionsParser parser = new GenericOptionsParser(conf, agrs);
	String[] otherArgs = parser.getRemainingArgs();
	if (agrs.length < 3) {
	    System.err.println("Usage: Example_Join_01 <in_path_one> <in_path_two> <output>");
	    System.exit(2);
	}
	Utils.deleteIfExists(conf, otherArgs[2]);
	DistributedCache.addCacheFile(new Path(otherArgs[0]).toUri(), conf);
	DistributedCache.addCacheFile(new Path(otherArgs[1]).toUri(), conf);
	Job job = new Job(conf, "Example_Join_01_Mapper_DistributedCache");

	conf.set(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR, "\t");
	job.setInputFormatClass(KeyValueTextInputFormat.class);

	job.setJarByClass(Example_Join_01_Mapper_DistributedCache.class);
	// 设置Map相关内容
	job.setMapperClass(Example_Join_01_Mapper_DistributedCache.class);
	job.setNumReduceTasks(0);
	job.setOutputKeyClass(LongWritable.class);
	job.setOutputValueClass(Text.class);
	FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
	FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
	FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
	System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}

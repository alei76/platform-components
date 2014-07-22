package com.jyz.study.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.jyz.study.hadoop.common.ConfigurationUtils;
import com.jyz.study.hadoop.common.TextPair;
import com.jyz.study.hadoop.common.Utils;
import com.jyz.study.hadoop.mapreduce.ValuesSort.JoinReduce;
import com.jyz.study.hadoop.mapreduce.different.Example_Different_01_Comparator;
import com.jyz.study.hadoop.mapreduce.different.Example_Different_01_KeyComparator;
import com.jyz.study.hadoop.mapreduce.different.Example_Different_01_Partitioner;

/**
 * 同一个key下的values组内排序
 * @author JoyoungZhang@gmail.com
 *
 */
public class ValuesSortWithKeyValueTextInputFormat {

    public static class TokenizerMapper extends
	    Mapper<Text, Text, TextPair, Text> {

	/**
	 * key is linekey
	 * value is linevalue
	 */
	public void map(Text key, Text value, Context context)
		throws IOException, InterruptedException {
	    context.write(new TextPair(key, value), new Text(value));
	}
    }

//    public static class JoinReduce extends
//	    Reducer<TextPair, Text, Text, Text> {
//
//	public void reduce(TextPair key, Iterable<Text> values,
//		Context context) throws IOException, InterruptedException {
//	    Text keyout = new Text(key.getFirst());
//	    Text valueout = new Text();
//	    String bak = key.getSecond().toString();
//	    values.iterator().next();
//	    if (values.iterator().hasNext()) {
//		values.iterator().next();
//		valueout.set(bak + "\t" + key.getSecond());
//	    }else{
//		valueout.set(key.getSecond());
//	    }
//	    context.write(keyout, valueout);
//	}
//    }

    public static void main(String[] args) throws Exception {
	Configuration conf = ConfigurationUtils.getHadoopConfiguration();
	String[] otherArgs = new GenericOptionsParser(conf, args)
		.getRemainingArgs();
	if (otherArgs.length != 2) {
	    System.err.println("Usage: wordcount <in> <out>");
	    System.exit(2);
	}
	conf.set(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR, " ");
	Job job = new Job(conf, "ValuesSort");
	job.setJarByClass(ValuesSortWithKeyValueTextInputFormat.class);
	job.setMapperClass(TokenizerMapper.class);
	job.setReducerClass(JoinReduce.class);
	job.setMapOutputKeyClass(TextPair.class);
	job.setMapOutputValueClass(Text.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(Text.class);
	job.setNumReduceTasks(1);
	
	job.setInputFormatClass(KeyValueTextInputFormat.class);  
	
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

package com.jyz.study.hadoop.mapreduce.join;

/**
 * 合并两个文件
 */
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.jyz.study.hadoop.common.ConfigurationUtils;
import com.jyz.study.hadoop.common.TextPair;
import com.jyz.study.hadoop.common.Utils;
import com.jyz.study.hadoop.mapreduce.different.Example_Different_01_KeyComparator;

public class Example_Join_01_Test {
    public static void main(String agrs[]) throws IOException,
	    InterruptedException, ClassNotFoundException {
	Configuration conf = ConfigurationUtils.getHadoopConfiguration();
	GenericOptionsParser parser = new GenericOptionsParser(conf, agrs);
	String[] otherArgs = parser.getRemainingArgs();
	if (agrs.length < 3) {
	    System.err
		    .println("Usage: Example_Join_01 <in_path_one> <in_path_two> <output>");
	    System.exit(2);
	}
	Utils.deleteIfExists(conf, otherArgs[2]);
	Job job = new Job(conf, "Example_Join_01_Test");

	job.setJarByClass(Example_Join_01_Test.class);
	// 设置Map相关内容
	job.setMapperClass(Example_Join_01_Mapper.class);
	// 设置Map的输出
	job.setMapOutputKeyClass(TextPair.class);
	job.setMapOutputValueClass(Text.class);
	// 设置partition
	job.setPartitionerClass(Example_Join_01_Partitioner.class);
	// key比较函数
	job.setSortComparatorClass(Example_Different_01_KeyComparator.class); 
	// 在分区之后按照指定的条件分组
	job.setGroupingComparatorClass(Example_Join_01_Comparator.class);

	job.setReducerClass(Example_Join_01_Reduce.class);
	// 设置reduce的输出
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(Text.class);
	job.setNumReduceTasks(3);
	FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
	FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
	FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
	System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

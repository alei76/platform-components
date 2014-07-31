package com.citic.zxyjs.zwlscx.mapreduce.join.impl;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.io.DefaultStringifier;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.citic.zxyjs.zwlscx.bean.Source;
import com.citic.zxyjs.zwlscx.bean.Table;
import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.mapreduce.JobGenerator;
import com.citic.zxyjs.zwlscx.mapreduce.JobGeneratorBase;
import com.citic.zxyjs.zwlscx.mapreduce.join.impl.DataJoinMapper.DataJoinTableInputFormatMapper;
import com.citic.zxyjs.zwlscx.mapreduce.join.impl.DataJoinMapper.DataJoinTextInputFormatMapper;
import com.citic.zxyjs.zwlscx.mapreduce.lib.input.MultipleInputs;
import com.jyz.study.hadoop.common.ConfigurationUtils;
import com.jyz.study.hadoop.common.Utils;

/**
 * 文件关联Job生成器
 * 
 * @author JoyoungZhang@gmail.com
 */
public class JoinJobGenerator extends JobGeneratorBase {

    public JoinJobGenerator(Task task) {
	super(task);
    }

    public Job generateJob() throws IOException {

	if (task.getOutput() instanceof Table) {
	    throw new IOException("JoinJob mapreduce's output can only File.");
	}

	Configuration conf = ConfigurationUtils.getHbaseConfiguration();
	DefaultStringifier.store(conf, task, JobGenerator.JOIN_JOB_TASK);

	Job job = new Job(conf, "JoinJob[" + task.getIdentify() + "]");
	job.setJarByClass(JoinJobGenerator.class);

	switch (task.getSourceType()) {
	case FF:
	    FileInputFormat.addInputPath(job, new Path(task.getLeftSource().getPath()));
	    FileInputFormat.addInputPath(job, new Path(task.getRightSource().getPath()));
	    break;
	case FT:
	    MultipleInputs.addInputPath(job, new Path(task.getLeftSource().getPath()), TextInputFormat.class,
		    DataJoinTextInputFormatMapper.class);
	    MultipleInputs.addInputPath(job, new Path(task.getRightSource().getName()), TableInputFormat.class,
		    DataJoinTableInputFormatMapper.class);
	    break;
	case TF:
	    MultipleInputs.addInputPath(job, new Path(task.getLeftSource().getName()), TableInputFormat.class,
		    DataJoinTableInputFormatMapper.class);
	    MultipleInputs.addInputPath(job, new Path(task.getRightSource().getPath()), TextInputFormat.class,
		    DataJoinTextInputFormatMapper.class);
	    break;
	case TT:
	    MultipleInputs.addInputPath(job, new Path(task.getLeftSource().getName()), TableInputFormat.class,
		    DataJoinTableInputFormatMapper.class);
	    MultipleInputs.addInputPath(job, new Path(task.getRightSource().getName()), TableInputFormat.class,
		    DataJoinTableInputFormatMapper.class);
	    break;
	}
	job.setReducerClass(DataJoinReducer.class);
	job.setMapOutputValueClass(TaggedWritable.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(Text.class);
	job.setNumReduceTasks(1);
	
	MultipleOutputs.addNamedOutput(job, "normal", TextOutputFormat.class, Text.class, Text.class); 
	MultipleOutputs.addNamedOutput(job, "error", TextOutputFormat.class, Text.class, Text.class); 

	Source output = task.getOutput();
	Utils.deleteIfExists(conf, output.getPath());
	FileOutputFormat.setOutputPath(job, new Path(output.getPath()));

	return job;
    }

}
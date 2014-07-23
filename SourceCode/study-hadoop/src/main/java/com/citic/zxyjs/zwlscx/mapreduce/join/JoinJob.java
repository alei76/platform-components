package com.citic.zxyjs.zwlscx.mapreduce.join;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.io.DefaultStringifier;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.citic.zxyjs.zwlscx.bean.File;
import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.mapreduce.join.DataJoinMapper.DataJoinTableInputFormatMapper;
import com.citic.zxyjs.zwlscx.mapreduce.join.DataJoinMapper.DataJoinTextInputFormatMapper;
import com.citic.zxyjs.zwlscx.mapreduce.lib.input.MultipleInputs;

public class JoinJob {

    public JoinJob() {
    }

    public Job generateJob(Task task, boolean init) throws IOException {
	Configuration conf = new Configuration();
	DefaultStringifier.store(conf, task ,"task");
	DefaultStringifier.store(conf, init ,"init");
	
	Job job = new Job(conf, "JoinJob");
	job.setJarByClass(JoinJob.class);
	
	switch(task.getSourceType()){
    	case FF :
    	    job.setMapperClass(DataJoinTextInputFormatMapper.class);
    	    break;
    	case FT :
    	    MultipleInputs.addInputPath(job, new Path(((File) task.getLeftSource()).getPath()), TextInputFormat.class, DataJoinTextInputFormatMapper.class);
    	    MultipleInputs.addInputPath(job, new Path(task.getRightSource().getName()), TableInputFormat.class, DataJoinTableInputFormatMapper.class);
    	    break;
    	case TF :
    	    MultipleInputs.addInputPath(job, new Path(task.getLeftSource().getName()), TableInputFormat.class, DataJoinTableInputFormatMapper.class);
    	    MultipleInputs.addInputPath(job, new Path(((File) task.getRightSource()).getPath()), TextInputFormat.class, DataJoinTextInputFormatMapper.class);
    	    break;
    	case TT :
    	    job.setMapperClass(DataJoinTableInputFormatMapper.class);
    	    break;
	}
	job.setReducerClass(DataJoinReducer.class);
	job.setMapOutputValueClass(TaggedWritable.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(Text.class);
	job.setNumReduceTasks(1);
	
	//Utils.deleteIfExists(conf, "hdfs://200master:9000/user/root/output20");
	FileOutputFormat.setOutputPath(job, new Path(task.getOutput()));
	return job;
    }
    

}
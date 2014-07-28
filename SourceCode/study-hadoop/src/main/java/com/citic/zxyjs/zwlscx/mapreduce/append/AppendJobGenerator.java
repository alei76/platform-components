package com.citic.zxyjs.zwlscx.mapreduce.append;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.DefaultStringifier;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.citic.zxyjs.zwlscx.bean.File;
import com.citic.zxyjs.zwlscx.bean.SourceType;
import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.mapreduce.JobGenerator;
import com.citic.zxyjs.zwlscx.mapreduce.lib.input.HFileOutputFormatWithIgnore;
import com.jyz.study.hadoop.common.ConfigurationUtils;
import com.jyz.study.hadoop.common.Utils;

/**
 * 文件追加Job生成器
 * 
 * @author JoyoungZhang@gmail.com
 */
public class AppendJobGenerator implements JobGenerator {

    private Task task;
    private boolean init;

    public AppendJobGenerator(Task task, boolean init) {
	this.task = task;
	this.init = init;
    }

    @Override
    public Job generateJob() throws IOException {
	if (task.getSourceType() != SourceType.FT) {
	    throw new IOException("AppendJob mapreduce's input can only file, output can only Table.");
	}
	Configuration conf = ConfigurationUtils.getHbaseConfiguration();
	DefaultStringifier.store(conf, task, JobGenerator.APPEND_JOB_TASK);
	conf.set("init", String.valueOf(init));

	Job job = new Job(conf, "AppendJob[" + task.getIdentify() + "]");
	job.setJarByClass(AppendJobGenerator.class);
	
	job.setJarByClass(AppendJobGenerator.class);
	job.setMapperClass(DataAppendMapper.class);
	job.setMapOutputKeyClass(ImmutableBytesWritable.class);
	job.setMapOutputValueClass(Put.class);
	
	String tmp = "hdfs://200master:9000/user/root/zxyh/tmp";
	Utils.deleteIfExists(conf, tmp);
	
	FileInputFormat.addInputPath(job, new Path(((File)task.getLeftSource()).getPath()));
	FileOutputFormat.setOutputPath(job, new Path(tmp));
	
	HTable htable = new HTable(conf, task.getRightSource().getName());
	HFileOutputFormatWithIgnore.configureIncrementalLoad(job, htable, HFileOutputFormatWithIgnore.class);
	return job;
    }

}

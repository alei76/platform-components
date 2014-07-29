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

import com.citic.zxyjs.zwlscx.bean.SourceType;
import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.mapreduce.JobGenerator;
import com.citic.zxyjs.zwlscx.mapreduce.JobGeneratorBase;
import com.citic.zxyjs.zwlscx.mapreduce.lib.input.HFileOutputFormatWithIgnore;
import com.jyz.study.hadoop.common.ConfigurationUtils;
import com.jyz.study.hadoop.common.Utils;

/**
 * 文件追加Job生成器
 * 
 * @author JoyoungZhang@gmail.com
 */
public class AppendJobGenerator extends JobGeneratorBase {

    public AppendJobGenerator(Task task) {
	super(task);
    }

    @Override
    public Job generateJob() throws IOException {
	if (task.getSourceType() != SourceType.FT) {
	    throw new IOException("AppendJob mapreduce's input can only file, output can only Table.");
	}
	Configuration conf = ConfigurationUtils.getHbaseConfiguration();
	DefaultStringifier.store(conf, task, JobGenerator.APPEND_JOB_TASK);

	Job job = new Job(conf, "AppendJob[" + task.getIdentify() + "]");
	job.setJarByClass(AppendJobGenerator.class);

	job.setJarByClass(AppendJobGenerator.class);
	job.setMapperClass(DataAppendMapper.class);
	job.setMapOutputKeyClass(ImmutableBytesWritable.class);
	job.setMapOutputValueClass(Put.class);

	String hfilePath = task.getRightSource().getPath();
	Utils.deleteIfExists(conf, hfilePath);

	FileInputFormat.addInputPath(job, new Path(task.getLeftSource().getPath()));
	FileOutputFormat.setOutputPath(job, new Path(hfilePath));

	HTable htable = new HTable(conf, task.getRightSource().getName());
	HFileOutputFormatWithIgnore.configureIncrementalLoad(job, htable, HFileOutputFormatWithIgnore.class);
	return job;
    }

}

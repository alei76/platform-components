package com.citic.zxyjs.zwlscx.mapreduce;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Job;

import com.citic.zxyjs.zwlscx.bean.Task;

/**
 * Job生成器抽象类 提供一些公用的方法
 * 
 * @author JoyoungZhang@gmail.com
 */
public class JobGeneratorBase implements JobGenerator {

    protected Task task;

    public JobGeneratorBase(Task task) {
	this.task = task;
    }

    @Override
    public Job generateJob() throws IOException {
	return null;
    }

}

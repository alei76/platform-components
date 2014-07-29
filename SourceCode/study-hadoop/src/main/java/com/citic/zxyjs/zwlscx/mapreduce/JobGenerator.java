package com.citic.zxyjs.zwlscx.mapreduce;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Job;

/**
 * Job生成器接口
 * 
 * @author JoyoungZhang@gmail.com
 */
public interface JobGenerator {

    public static final String JOIN_JOB_TASK = "join.job.task";
    public static final String APPEND_JOB_TASK = "append.job.task";

    public Job generateJob() throws IOException;

}

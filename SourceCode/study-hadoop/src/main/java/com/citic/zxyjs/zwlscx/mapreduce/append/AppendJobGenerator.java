package com.citic.zxyjs.zwlscx.mapreduce.append;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Job;

import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.mapreduce.JobGenerator;

/**
 * 文件追加Job
 * @author JoyoungZhang@gmail.com
 *
 */
public class AppendJobGenerator implements JobGenerator {

    public AppendJobGenerator(Task task, boolean init) {
	// TODO Auto-generated constructor stub
    }

    @Override
    public Job generateJob() throws IOException {
	// TODO Auto-generated method stub
	return null;
    }


}

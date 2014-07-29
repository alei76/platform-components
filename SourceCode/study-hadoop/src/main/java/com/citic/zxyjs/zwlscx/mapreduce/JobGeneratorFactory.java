package com.citic.zxyjs.zwlscx.mapreduce;

import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.mapreduce.append.AppendJobGenerator;
import com.citic.zxyjs.zwlscx.mapreduce.join.impl.JoinJobGenerator;

/**
 * Job生成器工厂类
 * 
 * @author JoyoungZhang@gmail.com
 */
public class JobGeneratorFactory {

    public static JobGenerator getJobGenerator(Task task) {
	JobGenerator jobGenerator = null;
	switch (task.getTaskType()) {
	case Join:
	    jobGenerator = new JoinJobGenerator(task);
	    break;
	case Append:
	    jobGenerator = new AppendJobGenerator(task);
	    break;
	}
	return jobGenerator;
    }
}

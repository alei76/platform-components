package com.citic.zxyjs.zwlscx.mapreduce;

import com.citic.zxyjs.zwlscx.bean.AppendTask;
import com.citic.zxyjs.zwlscx.bean.JoinTask;
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
	if (task instanceof JoinTask) {
	    jobGenerator = new JoinJobGenerator(task);
	} else if (task instanceof AppendTask) {
	    jobGenerator = new AppendJobGenerator(task);
	}
	return jobGenerator;
    }
}

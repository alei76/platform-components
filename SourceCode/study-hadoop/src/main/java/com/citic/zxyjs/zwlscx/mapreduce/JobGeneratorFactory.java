package com.citic.zxyjs.zwlscx.mapreduce;

import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.mapreduce.append.AppendJobGenerator;
import com.citic.zxyjs.zwlscx.mapreduce.join.impl.JoinJobGenerator;
import com.citic.zxyjs.zwlscx.mapreduce.union.UnionJobGenerator;

public class JobGeneratorFactory {

    public static JobGenerator getJobGenerator(Task task, boolean init) {
	JobGenerator jobGenerator = null;
	switch (task.getTaskType()) {
	case Join:
	    jobGenerator =  new JoinJobGenerator(task, init);
	    break;
	case Union:
	    jobGenerator =  new UnionJobGenerator(task, init);
	    break;
	case Append:
	    jobGenerator =  new AppendJobGenerator(task, init);
	    break;
	}
	return jobGenerator;
    }
}

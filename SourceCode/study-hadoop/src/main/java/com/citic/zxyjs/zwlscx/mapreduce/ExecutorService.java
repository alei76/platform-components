package com.citic.zxyjs.zwlscx.mapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;

import com.citic.zxyjs.zwlscx.bean.Conf;
import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.mapreduce.append.AppendJob;
import com.citic.zxyjs.zwlscx.mapreduce.join.JoinJob;
import com.citic.zxyjs.zwlscx.mapreduce.union.UnionJob;
import com.citic.zxyjs.zwlscx.xml.ParseXmlUtils;

/**
 * .sh入口
 * 
 * @author JoyoungZhang@gmail.com
 */
public class ExecutorService {

    private static final Log LOG = LogFactory.getLog(ExecutorService.class);

    public static void main(String[] args) throws IOException {
	Conf conf = ParseXmlUtils.parseXml();
	List<Job> jobs = getJobs(conf);
	List<ControlledJob> controlledJobs = new ArrayList<ControlledJob>();
	for (int i = 0; i < jobs.size(); i++) {
	    Job job = jobs.get(i);
	    ControlledJob controlledJob = new ControlledJob(job.getConfiguration());
	    controlledJob.setJob(job);
	    if (i > 0) {
		controlledJob.addDependingJob(controlledJobs.get(i - 1));
	    }
	    controlledJobs.add(controlledJob);
	}

	JobControl jc = new JobControl("JobControl");
	jc.addJobCollection(controlledJobs);
//	LOG.info("将要运行的Job如下：");
//	LOG.info("Job信息：" + Arrays.toString(jobs.toArray()));
//	LOG.info("ControlledJob信息：" + Arrays.toString(controlledJobs.toArray()));

	Thread jcThread = new Thread(jc);
	jcThread.start();
	while (true) {
	    if (jc.allFinished()) {
		int code = 0;
		LOG.info("成功Job：" + Arrays.toString(jc.getSuccessfulJobList().toArray()));
		if (jc.getFailedJobList().size() > 0) {
		    LOG.info("失败Job：" + Arrays.toString(jc.getFailedJobList().toArray()));
		    code = 1;
		}
		jc.stop();
		System.exit(code);
	    }
	}

    }

    private static List<Job> getJobs(Conf conf) throws IOException {
	boolean init = conf.isInit();
	List<Job> jobs = new ArrayList<Job>();
	for (Task task : conf.getTasks()) {
	    switch (task.getTaskType()) {
	    case Join:
		jobs.add(new JoinJob().generateJob(task, init));
		break;
	    case Union:
		jobs.add(new UnionJob().generateJob(task, init));
		break;
	    case Append:
		jobs.add(new AppendJob().generateJob(task, init));
		break;
	    }
	}
	return jobs;
    }
}

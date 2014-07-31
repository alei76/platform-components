package com.citic.zxyjs.zwlscx.mapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;

import com.citic.zxyjs.zwlscx.bean.Conf;
import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.bean.TaskType;
import com.citic.zxyjs.zwlscx.hdfs.HDFSUtil;
import com.citic.zxyjs.zwlscx.xml.ParseXmlUtils;

/**
 * .sh脚本入口
 * 1.pasre xml
 * 2.get job list base on xml
 * 3.run job list
 * 4.callback
 * 5.clear
 * @author JoyoungZhang@gmail.com
 */
public class ExecutorService {

    private static final Log LOG = LogFactory.getLog(ExecutorService.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
	Conf conf = ParseXmlUtils.parseXml();
	List<Job> jobs = null;
	List<ControlledJob> controlledJobs = new ArrayList<ControlledJob>();
	JobControl jc = new JobControl("JobControl");
	int code = 0;
	try {
	    jobs = getJobs(conf);
	    for (int i = 0; i < jobs.size(); i++) {
		Job job = jobs.get(i);
		ControlledJob controlledJob = new ControlledJob(job.getConfiguration());
		controlledJob.setJob(job);
		if (i > 0) {
		    controlledJob.addDependingJob(controlledJobs.get(i - 1));
		}
		controlledJobs.add(controlledJob);
	    }

	    jc.addJobCollection(controlledJobs);

	    Thread jcThread = new Thread(jc);
	    jcThread.start();
	    while (true) {
		if (jc.allFinished()) {
		    LOG.info("成功Job：\n" + Arrays.toString(jc.getSuccessfulJobList().toArray()));
		    if (jc.getFailedJobList().size() > 0) {
			LOG.info("失败Job：\n" + Arrays.toString(jc.getFailedJobList().toArray()));
			code = 1;
		    }
		    jc.stop();
		    if (code == 0) {
			callback(jobs, conf.getTasks());
		    }
		    break;
		}
	    }
	} catch (IOException ex) {
	    //TODO throw new etl exception etl可以识别的异常
	    LOG.error(ex);
	} catch (Exception ex) {
	    //TODO throw new etl exception etl可以识别的异常
	    LOG.error(ex);
	} finally {
	    if (conf.isClear()) {
		try {
		    clear(jobs, conf.getTasks());
		} catch (IllegalArgumentException ex) {
		    //TODO throw new etl exception etl可以识别的异常
		    LOG.error(ex);
		} catch (IOException ex) {
		    //TODO throw new etl exception etl可以识别的异常
		    LOG.error(ex);
		}
		System.exit(code);
	    }
	}

    }

    /**
     * 根据配置文件获得job
     * 
     * @param conf
     * @return
     * @throws IOException
     */
    private static List<Job> getJobs(Conf conf) throws IOException {
	List<Job> jobs = new ArrayList<Job>();
	for (Task task : conf.getTasks()) {
	    jobs.add(JobGeneratorFactory.getJobGenerator(task).generateJob());
	}
	return jobs;
    }

    /**
     * TODO callback执行mv hfile
     * 
     * @param jobs
     * @throws Exception
     */
    private static void callback(List<Job> jobs, List<Task> tasks) throws Exception {
	Task task = null;
	Job job = null;
	for (int i = 0; i < tasks.size(); i++) {
	    task = tasks.get(i);
	    job = jobs.get(i);
	    if (task.getTaskType() == TaskType.Append) {
		LoadIncrementalHFiles loader = new LoadIncrementalHFiles(job.getConfiguration());
		HTable htable = new HTable(job.getConfiguration(), task.getRightSource().getName());
		loader.doBulkLoad(new Path(task.getRightSource().getPath()), htable);
		LOG.info("Move hfile[" + task.getRightSource().getPath() + "] to hbase[" + task.getRightSource().getName()
			+ "] success.");
	    }
	}
    }

    /**
     * TODO 清理
     * 
     * @param jobs
     * @param tasks
     * @throws IOException
     * @throws IllegalArgumentException
     */
    private static void clear(List<Job> jobs, List<Task> tasks) throws IllegalArgumentException, IOException {
	Task task = null;
	Job job = null;
	for (int i = 0; i < tasks.size(); i++) {
	    task = tasks.get(i);
	    job = jobs.get(i);
	    if (job.isComplete()) {
		switch (task.getTaskType()) {
		case Join:
		    HDFSUtil.deleteByDir(job.getConfiguration(), new Path(task.getOutput().getPath()), true);
		    break;
		case Append:
		    HDFSUtil.deleteByDir(job.getConfiguration(), new Path(task.getRightSource().getPath()), true);
		    break;
		}
	    }

	}
    }
}

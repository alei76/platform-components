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

import com.citic.zxyjs.zwlscx.bean.AppendTask;
import com.citic.zxyjs.zwlscx.bean.Conf;
import com.citic.zxyjs.zwlscx.bean.File;
import com.citic.zxyjs.zwlscx.bean.JoinTask;
import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.hdfs.HDFSUtil;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.ExtensionUtils;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.JobExtensionParmeters;
import com.citic.zxyjs.zwlscx.mapreduce.lib.jobcontrol.ControlledJob;
import com.citic.zxyjs.zwlscx.mapreduce.lib.jobcontrol.JobControl;
import com.citic.zxyjs.zwlscx.xml.ParseXmlUtils;

/**
 * 
 * .sh脚本入口 
 * 1.解析并校验xml
 * 2.根据xml得到Job列表
 * 3.顺序运行Job列表
 * 4.调用回调函数
 * 5.清理临时文件
 * 
 * @author JoyoungZhang@gmail.com
 */
public class ExecutorService {

    private static final Log LOG = LogFactory.getLog(ExecutorService.class);

    /**
     *	
     * @param args
     */
    public static void main(String[] args) {
	Conf conf = ParseXmlUtils.parseAndVerifyXml();
	List<Task> tasks = conf.getTasks();
	List<Job> jobs = null;
	List<ControlledJob> controlledJobs = new ArrayList<ControlledJob>();
	JobControl jc = new JobControl("JobControl");
	int code = 0;
	try {
	    jobs = getJobs(tasks);
	    for (int i = 0; i < jobs.size(); i++) {
		Job job = jobs.get(i);
		Task task = tasks.get(i);
		ControlledJob controlledJob = new ControlledJob(job.getConfiguration());
		controlledJob.setJob(job);
		controlledJob.setExtendable(ExtensionUtils.newInstance(task.getJobExtension(), job.getConfiguration()));
		controlledJob.setExtensionParmeters(new JobExtensionParmeters(task));
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
     * 根据配置文件获得Job列表
     * 
     * @param conf 通过解析xml得到conf对象
     * @return Job列表
     * @throws IOException
     */
    private static List<Job> getJobs(List<Task> tasks) throws IOException {
	List<Job> jobs = new ArrayList<Job>();
	for (Task task : tasks) {
	    jobs.add(JobGeneratorFactory.getJobGenerator(task).generateJob());
	}
	return jobs;
    }

    /**
     * callback执行mv hfile
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
	    if (task instanceof AppendTask) {
		AppendTask appendTask = (AppendTask) task;
		LoadIncrementalHFiles loader = new LoadIncrementalHFiles(job.getConfiguration());
		HTable htable = new HTable(job.getConfiguration(), appendTask.getTo().getName());
		loader.doBulkLoad(new Path(appendTask.getHfileOutput()), htable);
		LOG.info("Move hfile[" + appendTask.getHfileOutput() + "] to hbase[" + appendTask.getTo().getName()
			+ "] success.");
	    }
	}
    }

    /**
     * 清理临时文件
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
		if (task instanceof JoinTask) {
		    HDFSUtil
			    .deleteByDir(job.getConfiguration(), new Path(((File) ((JoinTask) task).getOutput()).getPath()), true);
		} else if (task instanceof AppendTask) {
		    HDFSUtil.deleteByDir(job.getConfiguration(), new Path(((AppendTask) task).getHfileOutput()), true);
		}
	    }

	}
    }
}

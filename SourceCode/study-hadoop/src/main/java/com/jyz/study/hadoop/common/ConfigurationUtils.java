package com.jyz.study.hadoop.common;

import org.apache.hadoop.conf.Configuration;

public class ConfigurationUtils {

//	public static final String LOCATION = "172.16.1.101";
	public static final String LOCATION = "192.168.1.200";
	
	public static Configuration getHbaseConfiguration(){
	    Configuration conf = getHadoopConfiguration();
	    conf.set("hbase.zookeeper.quorum", LOCATION);
	    return conf;
	}
	
	public static Configuration getHadoopConfiguration(){
	    Configuration conf = new Configuration();
	    conf.set("fs.defaultFS", "hdfs://" + LOCATION + ":9000");
	    conf.set("mapred.job.tracker", "" + LOCATION + ":9001");  
//	    conf.set("mapreduce.framework.name", "yarn");
	    conf.set("mapred.remote.os", "Linux");
	    conf.set("mapreduce.application.classpath", "/usr/hadoop/etc,/usr/hadoop/share/hadoop/common/*,/usr/hadoop/share/hadoop/common/lib/*,/usr/hadoop/share/hadoop/hdfs/*,/usr/hadoop/share/hadoop/hdfs/lib/*,/usr/hadoop/share/hadoop/mapreduce/*,/usr/hadoop/share/hadoop/mapreduce/lib/*,/usr/hadoop/share/hadoop/yarn/*,/usr/hadoop/share/hadoop/yarn/lib/*,/usr/hbase/lib/*,/usr/mrlibs/*");
	    
	    conf.set("yarn.resourcemanager.scheduler.address", "192.168.1.200:8030");
	    conf.set("yarn.resourcemanager.resource-tracker.address", "192.168.1.200:8031");
	    conf.set("yarn.resourcemanager.address", "192.168.1.200:8032");
	    conf.set("yarn.resourcemanager.admin.address", "192.168.1.200:8033");
	    conf.set("yarn.resourcemanager.webapp.address", "192.168.1.200:50030");
	    conf.set("mapreduce.jobhistory.webapp.address", "192.168.1.200:19888");
	    conf.set("mapreduce.jobhistory.address", "192.168.1.200:10020");
//	    conf.set(LocalJobRunner.LOCAL_MAX_MAPS, "2");
//	    conf.set("yarn.application.classpath", "/usr/hadoop/etc,/usr/hadoop/share/hadoop/common/*,/usr/hadoop/share/hadoop/common/lib/*,/usr/hadoop/share/hadoop/hdfs/*,/usr/hadoop/share/hadoop/hdfs/lib/*,/usr/hadoop/share/hadoop/mapreduce/*,/usr/hadoop/share/hadoop/mapreduce/lib/*,/usr/hadoop/share/hadoop/yarn/*,/usr/hadoop/share/hadoop/yarn/lib/*");
	    return conf;
	}

}

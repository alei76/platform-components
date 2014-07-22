package com.jyz.study.hadoop.common;

import org.apache.hadoop.conf.Configuration;

public class ConfigurationUtils {

//	public static final String LOCATION = "172.16.1.101";
	public static final String LOCATION = "200master";
	
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
//	    conf.set("mapreduce.application.classpath", "/usr/hadoop/etc,/usr/hadoop/share/hadoop/common/*,/usr/hadoop/share/hadoop/common/lib/*,/usr/hadoop/share/hadoop/hdfs/*,/usr/hadoop/share/hadoop/hdfs/lib/*,/usr/hadoop/share/hadoop/mapreduce/*,/usr/hadoop/share/hadoop/mapreduce/lib/*,/usr/hadoop/share/hadoop/yarn/*,/usr/hadoop/share/hadoop/yarn/lib/*,/usr/hbase/lib/*,/usr/mrlibs/*");
//	    conf.set("mapreduce.application.classpath", "/usr/hadoop/etc,/usr/hadoop/share/hadoop/common/*,/usr/hadoop/share/hadoop/common/lib/*,/usr/hadoop/share/hadoop/hdfs/*,/usr/hadoop/share/hadoop/hdfs/lib/*,/usr/hadoop/share/hadoop/mapreduce/*,/usr/hadoop/share/hadoop/mapreduce/lib/*,/usr/hadoop/share/hadoop/yarn/*,/usr/hadoop/share/hadoop/yarn/lib/*,/usr/hbase/lib/*");
//	    conf.set("tmpjars", "file:/D:/GoogleCode/platform-components/trunk/SourceCode/study-hadoop/lib/json-simple-1.1.jar");
	    System.setProperty("path.separator", ":");
	    conf.set("yarn.resourcemanager.scheduler.address", LOCATION + ":8030");
	    conf.set("yarn.resourcemanager.resource-tracker.address", LOCATION + ":8031");
	    conf.set("yarn.resourcemanager.address", LOCATION + ":8032");
	    conf.set("yarn.resourcemanager.admin.address", LOCATION + ":8033");
	    conf.set("yarn.resourcemanager.webapp.address", LOCATION + ":50030");
	    conf.set("mapreduce.jobhistory.webapp.address", LOCATION + ":19888");
	    conf.set("mapreduce.jobhistory.address", LOCATION + ":10020");
//	    conf.set(LocalJobRunner.LOCAL_MAX_MAPS, "2");
//	    conf.set("yarn.application.classpath", "/usr/hadoop/etc,/usr/hadoop/share/hadoop/common/*,/usr/hadoop/share/hadoop/common/lib/*,/usr/hadoop/share/hadoop/hdfs/*,/usr/hadoop/share/hadoop/hdfs/lib/*,/usr/hadoop/share/hadoop/mapreduce/*,/usr/hadoop/share/hadoop/mapreduce/lib/*,/usr/hadoop/share/hadoop/yarn/*,/usr/hadoop/share/hadoop/yarn/lib/*");
	    return conf;
	}

}

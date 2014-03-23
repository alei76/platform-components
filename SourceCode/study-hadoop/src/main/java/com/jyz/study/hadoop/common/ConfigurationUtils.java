package com.jyz.study.hadoop.common;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public class ConfigurationUtils {

//	public static final String LOCATION = "172.16.1.101";
	public static final String LOCATION = "192.168.1.200";
	
	public static Configuration getHbaseConfiguration(){
	    Configuration conf = HBaseConfiguration.create();
	    conf.set("fs.defaultFS", "hdfs://" + LOCATION + ":9000");
	    conf.set("mapred.job.tracker", "" + LOCATION + ":9001");   
	    conf.set("hbase.zookeeper.quorum", LOCATION);
	    return conf;
	}
	
	public static Configuration getHadoopConfiguration(){
	    Configuration conf = new Configuration();
	    conf.set("fs.defaultFS", "hdfs://" + LOCATION + ":9000");
	    conf.set("mapred.job.tracker", "" + LOCATION + ":9001");  
	    conf.set("mapreduce.framework.name", "local");
	    conf.set("yarn.resourcemanager.address", "192.168.1.200:8032");
//	    conf.set("mapred.remote.os", "Linux");
	    return conf;
	}

}

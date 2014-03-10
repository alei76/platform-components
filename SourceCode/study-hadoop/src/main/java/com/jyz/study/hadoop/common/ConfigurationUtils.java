package com.jyz.study.hadoop.common;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public class ConfigurationUtils {

	public static final String LOCATION = "192.168.1.200";
	
	public static Configuration getConfiguration(){
	    Configuration conf = HBaseConfiguration.create();
	    conf.set("fs.defaultFS", "hdfs://200master:9000");
	    conf.set("mapred.job.tracker", "200master:9001");   
	    conf.set("hbase.zookeeper.quorum", ConfigurationUtils.LOCATION);
	    return conf;
	}

}

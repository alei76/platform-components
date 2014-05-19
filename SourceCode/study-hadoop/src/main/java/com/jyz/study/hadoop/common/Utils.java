package com.jyz.study.hadoop.common;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Utils {

    private static final Log LOG = LogFactory.getLog(Utils.class);
    
    public static void deleteIfExists(Configuration conf, String path) throws IOException{
	FileSystem fs = FileSystem.get(conf);   
        Path pout = new Path(path);   
        if(fs.exists(pout)){   
            fs.delete(pout, true);   
            LOG.info("Delete Hdfs path[" + path + "] success.");   
        }    

    }
}

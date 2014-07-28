package com.citic.zxyjs.zwlscx.hdfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.jyz.study.hadoop.common.ConfigurationUtils;

/**
 * HDFS工具类
 * 
 * @author JoyoungZhang@gmail.com
 */
public class HDFSUtil {

    private static final Log LOG = LogFactory.getLog(HDFSUtil.class);

    /**
     * 获取FileSystem
     * 
     * @param conf
     * @return
     * @throws IOException
     */
    public static FileSystem getFileSystem(Configuration conf) throws IOException {
	FileSystem fs = FileSystem.get(conf);
	return fs;
    }

    /**
     * 获得path下的part-r-文件
     * 
     * @param conf
     * @param path
     * @return
     * @throws IOException
     */
    public static List<Path> findReduceOutputByPath(Configuration conf, Path path) throws IOException {
	List<Path> paths = new ArrayList<Path>();
	FileSystem fs = getFileSystem(conf);
	if (fs.isDirectory(path)) {
	    FileStatus[] statuss = fs.listStatus(path);
	    for (FileStatus status : statuss) {
		if (status.isFile() && status.getPath().getName().startsWith("part-r-")) {
		    paths.add(status.getPath());
		}
	    }
	} else {
	    paths.add(path);
	}
	LOG.info("Find reduce output " + Arrays.toString(paths.toArray()) + " by path[" + path + "] success.");
	return paths;
    }

    public static void main(String[] args) throws IllegalArgumentException, IOException {
	System.out.println(Arrays.toString(findReduceOutputByPath(ConfigurationUtils.getHadoopConfiguration(),
		new Path("hdfs://200master:9000/user/root/zxyh/1_2output")).toArray()));
    }
}
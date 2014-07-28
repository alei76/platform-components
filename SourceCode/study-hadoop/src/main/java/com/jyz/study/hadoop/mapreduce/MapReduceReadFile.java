package com.jyz.study.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.ReflectionUtils;

import com.jyz.study.hadoop.common.ConfigurationUtils;
import com.jyz.study.hadoop.common.Utils;
import com.jyz.study.hadoop.hdfs.SequenceFileTest;

public class MapReduceReadFile {

    private static SequenceFile.Reader reader = null;
    private static Configuration conf = ConfigurationUtils.getHadoopConfiguration();

    public static class ReadFileMapper extends Mapper<Text, IntWritable, Text, IntWritable> {

	@Override
	public void map(Text key, IntWritable value, Context context) {
	    key = (Text) ReflectionUtils.newInstance(reader.getKeyClass(), conf);
	    value = (IntWritable) ReflectionUtils.newInstance(reader.getValueClass(), conf);
	    try {
		while (reader.next(key, value)) {
		    System.out.printf("%s\t%s\n", key, value);
		    context.write(key, value);
		}
	    } catch (IOException e1) {
		e1.printStackTrace();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}

    }

    /**
     * @param args
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

	Job job = new Job(conf, "read seq file");
	job.setJarByClass(MapReduceReadFile.class);
	job.setMapperClass(ReadFileMapper.class);
	job.setMapOutputValueClass(IntWritable.class);
	Path path = new Path(SequenceFileTest.Output_path);
	FileSystem fs = FileSystem.get(conf);
	reader = new SequenceFile.Reader(fs, path, conf);
	FileInputFormat.addInputPath(job, path);
	FileOutputFormat.setOutputPath(job, new Path("/user/root/output96"));
	Utils.deleteIfExists(conf, "/user/root/output96");
	System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}
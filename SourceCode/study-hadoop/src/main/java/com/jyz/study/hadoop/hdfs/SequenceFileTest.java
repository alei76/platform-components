package com.jyz.study.hadoop.hdfs;

import java.io.IOException;
import java.net.URI;
import java.util.Random;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import com.jyz.study.hadoop.common.ConfigurationUtils;

public class SequenceFileTest {
    /** * @param args */
    public static FileSystem fs;
    public static final String Output_path = "/user/root/test/A.txt";
    public static Random random = new Random();
    private static final String[] DATA = { "One,two,buckle my shoe",
	    "Three,four,shut the door", "Five,six,pick up sticks",
	    "Seven,eight,lay them straight", "Nine,ten,a big fat hen" };
    public static Configuration conf = ConfigurationUtils.getHadoopConfiguration();

    public static void write(String pathStr) throws IOException {
	Path path = new Path(pathStr);
	FileSystem fs = FileSystem.get(URI.create(pathStr), conf);
	SequenceFile.Writer writer = SequenceFile.createWriter(fs, conf, path,
		Text.class, IntWritable.class);
	Text key = new Text();
	IntWritable value = new IntWritable();
	for (int i = 0; i < DATA.length; i++) {
	    key.set(DATA[i]);
	    value.set(random.nextInt(10));
	    System.out.println(key);
	    System.out.println(value);
	    System.out.println(writer.getLength());
	    writer.append(key, value);
	}
	writer.close();
    }

    public static void read(String pathStr) throws IOException {
	FileSystem fs = FileSystem.get(URI.create(pathStr), conf);
	SequenceFile.Reader reader = new SequenceFile.Reader(fs, new Path(
		pathStr), conf);
	Text key = new Text();
	IntWritable value = new IntWritable();
	while (reader.next(key, value)) {
	    System.out.println(key);
	    System.out.println(value);
	}
    }

    public static void main(String[] args) throws IOException {
	write(Output_path);
	read(Output_path);
    }
}
package com.jyz.study.hadoop.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;

public class Debug {

    @SuppressWarnings("serial")
    public static void main(String[] args) {
	SparkConf conf = new SparkConf().setAppName("local").setMaster("local");
	JavaSparkContext sc = new JavaSparkContext(conf);
	JavaRDD<String> lines = sc.textFile("a.txt");
	JavaRDD<Integer> lineLengths = lines.map(new Function<String, Integer>() {
	    @Override
	    public Integer call(String arg0) throws Exception {
		return arg0 == null ? 0 : arg0.length();
	    }
	});
	int totalLength = lineLengths.reduce(new Function2<Integer, Integer, Integer>() {
	    @Override
	    public Integer call(Integer arg0, Integer arg1) throws Exception {
		return arg0 + arg1;
	    }
	});
	System.out.println(totalLength);
    }
}

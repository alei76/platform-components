package com.jyz.study.hadoop.hdfs;

import java.io.RandomAccessFile;

public class RandomAccessFileTest {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
	solveData("D:\\GoogleCode\\platform-components\\trunk\\SourceCode\\hbase-book-master\\ch07\\data\\word.txt",
		"D:\\GoogleCode\\platform-components\\trunk\\SourceCode\\hbase-book-master\\ch07\\data\\wordBak.txt");
    }

    public static void solveData(String src, String desc) throws Exception {
	RandomAccessFile r = new RandomAccessFile(src, "rw");// 读取一个文件
	RandomAccessFile w = new RandomAccessFile(desc, "rw");// 判断写入另一个文件

	String temp = "";
	int count = 0;
	while ((temp = r.readLine()) != null) {
	    StringBuffer sb = new StringBuffer();
	    sb.append("        ").append(temp).append("         \n");
	    if (sb.toString().trim().length() > 0) {
		String h = new String((temp + sb.toString()).getBytes("iso-8859-1"), "UTF-8");// 加入乱码控制
		System.out.println(h);// 输出是否为正常编码
		w.write(h.getBytes());// 获取字节输出
		count++;
	    }
	}
	r.close();
	w.close();
	System.out.println("本次，检测共有" + count + "条数据");

    }
}

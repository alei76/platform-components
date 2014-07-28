package com.jyz.study.hadoop.hbase;

import java.io.IOException;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import com.jyz.study.hadoop.common.ConfigurationUtils;

public class CoprocessorTest {

    static final Configuration conf = ConfigurationUtils.getHbaseConfiguration();
    private static final Log LOG = LogFactory.getLog(CoprocessorTest.class);

    public static void creatTable(String tableName, String family) throws IOException {
	HBaseAdmin admin = new HBaseAdmin(conf);
	HTableDescriptor desc = new HTableDescriptor(tableName);
	desc.addFamily(new HColumnDescriptor(family));
	if (admin.tableExists(tableName)) {
	    admin.disableTable(tableName);
	    admin.deleteTable(tableName);
	    LOG.info("Delete table Success!");
	}
	admin.createTable(desc);
	LOG.info("Create table Success!");
    }

    public static void addData(int rowKey, String tableName, String family, String column, String value) throws IOException {
	Put put = new Put(Bytes.toBytes(rowKey));
	HTable table = new HTable(conf, Bytes.toBytes(tableName));
	put.add(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
	table.put(put);
	LOG.info("Add data Success!");
    }

    public static String getRandomString(int length) { // length表示生成字符串的长度
	String base = "abcdefghijklmnopqrstuvwxyz0123456789";
	Random random = new Random();
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < length; i++) {
	    int number = random.nextInt(base.length());
	    sb.append(base.charAt(number));
	}
	return sb.toString();
    }

    public static long getResultScann(String tableName, int start_rowkey, int stop_rowkey) throws IOException {
	long rowCount = 0;
	Scan scan = new Scan();
	scan.setStartRow(Bytes.toBytes(start_rowkey));
	scan.setStopRow(Bytes.toBytes(stop_rowkey));
	ResultScanner rs = null;
	HTable table = new HTable(conf, Bytes.toBytes(tableName));
	try {
	    rs = table.getScanner(scan);
	    for (Result r : rs) {
		for (KeyValue kv : r.list()) {
		    rowCount++;
		    LOG.info(Bytes.toInt(kv.getRow()) + ":" + Bytes.toString(kv.getValue()));
		}
	    }
	} finally {
	    rs.close();
	}
	return rowCount;
    }

    // public static long getTotalNumber(String tableName, int start_rowkey, int
    // stop_rowkey) {
    // Scan scan = new Scan();
    // scan.setStartRow(Bytes.toBytes(start_rowkey));
    // scan.setStopRow(Bytes.toBytes(stop_rowkey));
    // scan.addColumn(Bytes.toBytes("f"), Bytes.toBytes("random"));
    // // AggregationClient aggregationClient = new AggregationClient(conf);
    // // long rowCount = aggregationClient.rowCount(tableName, null, scan);
    // return rowCount;
    // }

    public static void main(String[] args) throws IOException {
	// creatTable("CoprocessorTest", "f");
	// for(int i=1;i<=1000;i++){
	// addData(i, "CoprocessorTest", "f", "random", getRandomString(10));
	// }
	// System.out.println(getResultScann("CoprocessorTest", 1, 1000));
	// System.out.println(getTotalNumber("CoprocessorTest", 1, 1000));
    }
}

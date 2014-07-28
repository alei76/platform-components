package com.jyz.study.hadoop.hbase;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import com.jyz.study.hadoop.common.ConfigurationUtils;

/**
 * HBase工具类
 * 
 * @author JoyoungZhang@gmail.com
 */
public class HBaseUtils {

    private static final Log LOG = LogFactory.getLog(HBaseUtils.class);

    static Configuration conf = null;
    static {
	conf = ConfigurationUtils.getHbaseConfiguration();
    }

    /*
     * 创建表
     * @tableName 表名
     * @family 列族列表
     */
    public static void creatTable(String tableName, String[] family) throws IOException {
	HBaseAdmin admin = new HBaseAdmin(conf);
	HTableDescriptor desc = new HTableDescriptor(tableName);
	for (int i = 0; i < family.length; i++) {
	    desc.addFamily(new HColumnDescriptor(family[i]));
	}
	if (admin.tableExists(tableName)) {
	    LOG.info("table Exists!");
	    System.exit(0);
	} else {
	    admin.createTable(desc);
	    LOG.info("create table Success!");
	}
    }

    public static void addData(String rowKey, String tableName, String[] column1, String[] value1) throws IOException {
	Put put = new Put(Bytes.toBytes(rowKey));
	HTable table = new HTable(conf, Bytes.toBytes(tableName));
	for (int j = 0; j < column1.length; j++) {
	    put.add(Bytes.toBytes("cf"), Bytes.toBytes(column1[j]), Bytes.toBytes(value1[j]));
	}
	table.put(put);
	LOG.info("add data Success!");
    }

    /*
     * 根据rwokey查询
     * @rowKey rowKey
     * @tableName 表名
     */
    public static Result getResult(String tableName, String rowKey) throws IOException {
	Get get = new Get(Bytes.toBytes(rowKey));
	HTable table = new HTable(conf, Bytes.toBytes(tableName));// 获取表
	Result result = table.get(get);
	for (KeyValue kv : result.list()) {
	    System.out.println("family:" + Bytes.toString(kv.getFamily()));
	    System.out.println("qualifier:" + Bytes.toString(kv.getQualifier()));
	    System.out.println("value:" + Bytes.toString(kv.getValue()));
	    System.out.println("Timestamp:" + kv.getTimestamp());
	    System.out.println("-------------------------------------------");
	}
	return result;
    }

    /*
     * 遍历查询hbase表
     * @tableName 表名
     */
    public static void getResultScann(String tableName) throws IOException {
	Scan scan = new Scan();
	ResultScanner rs = null;
	HTable table = new HTable(conf, Bytes.toBytes(tableName));
	try {
	    rs = table.getScanner(scan);
	    for (Result r : rs) {
		for (KeyValue kv : r.list()) {
		    System.out.println("row:" + Bytes.toString(kv.getRow()));
		    System.out.println("family:" + Bytes.toString(kv.getFamily()));
		    System.out.println("qualifier:" + Bytes.toString(kv.getQualifier()));
		    System.out.println("value:" + Bytes.toString(kv.getValue()));
		    System.out.println("timestamp:" + kv.getTimestamp());
		    System.out.println("-------------------------------------------");
		}
	    }
	} finally {
	    rs.close();
	}
    }

    /*
     * 删除表
     * @tableName 表名
     */
    public static void deleteIfExistsTable(String tableName) throws IOException {
	HBaseAdmin admin = new HBaseAdmin(conf);
	if (admin.tableExists(tableName)) {
	    admin.disableTable(tableName);
	    admin.deleteTable(tableName);
	    LOG.info("table Exists!");
	}
    }

}

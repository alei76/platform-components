package com.jyz.study.hadoop.common;

import com.jyz.study.hadoop.hbase.HBaseUtils;

/**
 * 初始化数据
 * 
 * @author JoyoungZhang@gmail.com
 */
public class DatePreproccess {

    public static final String[] TABLE_NAME = { "user", "tradeDetails", "userTradeDetails" };

    public static void main(String[] args) throws Exception {
	// 用户表
	String tableName = TABLE_NAME[0];
	HBaseUtils.deleteIfExistsTable(tableName);
	String[] family = { "cf" };
	HBaseUtils.creatTable(tableName, family);

	HBaseUtils.addData("user", tableName, new String[] { "age" }, new String[] { "20" });
	HBaseUtils.getResultScann(tableName);

	// 交易表
	tableName = TABLE_NAME[1];
	HBaseUtils.deleteIfExistsTable(tableName);
	HBaseUtils.creatTable(tableName, family);

	for (int i = 1; i <= 5; i++) {
	    HBaseUtils.addData("user" + i, tableName, new String[] { "describe" }, new String[] { "abc" + i });
	}
	HBaseUtils.getResultScann(tableName);

	tableName = TABLE_NAME[2];
	HBaseUtils.deleteIfExistsTable(tableName);
	HBaseUtils.creatTable(tableName, family);
    }
}

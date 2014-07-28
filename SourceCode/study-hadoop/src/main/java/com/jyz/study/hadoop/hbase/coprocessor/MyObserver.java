package com.jyz.study.hadoop.hbase.coprocessor;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CoprocessorEnvironment;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author JoyoungZhang@gmail.com
 */
public class MyObserver extends BaseRegionObserver {

    private static final Log LOG = LogFactory.getLog(MyObserver.class);
    private static Configuration conf = HBaseConfiguration.create();
    private static final String TN = "CreateFromMyObserver";

    @Override
    public void start(CoprocessorEnvironment e) throws IOException {
	HBaseAdmin admin = new HBaseAdmin(conf);
	if (admin.tableExists(TN)) {
	    admin.disableTable(TN);
	    admin.deleteTable(TN);
	    LOG.info("table drop Success!");
	} else {
	    admin.createTable(new HTableDescriptor(TN));
	    LOG.info("create table Success!");
	}
    }

    @Override
    public void stop(CoprocessorEnvironment e) throws IOException {
	LOG.info("stop success!.");
    }

    @Override
    public void prePut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability)
	    throws IOException {
	byte[] tableName = e.getEnvironment().getRegion().getRegionInfo().getTableName();
	if (!Bytes.equals(tableName, Bytes.toBytes("MyObserver"))) {
	    return;
	}
	LOG.info("WALEdit信息：" + edit);
	LOG.info("region信息：" + e.getEnvironment().getRegion().getRegionInfo());
	KeyValue kv = (KeyValue) put.get(Bytes.toBytes("cf"), Bytes.toBytes("c1")).get(0);
	LOG.info("KeyValue信息：" + kv);
    }

    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability)
	    throws IOException {
	byte[] tableName = e.getEnvironment().getRegion().getRegionInfo().getTableName();
	if (!Bytes.equals(tableName, Bytes.toBytes("MyObserver"))) {
	    return;
	}
	HTable table = new HTable(conf, Bytes.toBytes(TN));
	table.put(put);
	LOG.info("add data to " + TN + " success!");
    }

}

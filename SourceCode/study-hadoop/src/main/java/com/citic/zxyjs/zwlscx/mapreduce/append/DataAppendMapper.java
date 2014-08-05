package com.citic.zxyjs.zwlscx.mapreduce.append;

import java.io.IOException;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.DefaultStringifier;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.mapreduce.JobGenerator;

/**
 * Append操作下不同类型的mapper
 * 
 * @author JoyoungZhang@gmail.com
 */
public class DataAppendMapper {

    /**
     * input is hdfs file output is ImmutableBytesWritable and Put
     * 
     * @author JoyoungZhang@gmail.com
     */
    public static class DataAppendTextInputFormatMapper extends Mapper<LongWritable, Text, ImmutableBytesWritable, Put> {

	private Task task;

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
	    super.setup(context);
	    this.task = DefaultStringifier.load(context.getConfiguration(), JobGenerator.APPEND_JOB_TASK, Task.class);
	}

	@Override
	public void map(LongWritable offset, Text line, Context context) throws IOException {
	    //	    String lineString = line.toString();
	    //	    //TODO 
	    //	    //调用导入配置MetadateManager.getPuInfoByLine(lineString, List<Filed>)获取rowkey及若干cf:qualifier value
	    //	    byte[] rowkey = Bytes.toBytes(lineString.hashCode());
	    //	    Put put = new Put(rowkey);
	    //	    String[] tokens = lineString.split(Separator.SEP_COMMA);
	    //	    List<Field> fields = task.getRightSource().getFields();
	    //	    for (int i = 0; i < tokens.length; i++) {
	    //		put.add(Bytes.toBytes("cf"), Bytes.toBytes(fields.get(i).getName()), Bytes.toBytes(tokens[i]));
	    //	    }
	    //	    //TODO end
	    //	    try {
	    //		context.write(new ImmutableBytesWritable(rowkey), put);
	    //	    } catch (InterruptedException e) {
	    //		e.printStackTrace();
	    //	    }
	}
    }

    /**
     * input is hbase table output is ImmutableBytesWritable and Put
     * 
     * @author JoyoungZhang@gmail.com
     */
    public static class DataAppendTableInputFormatMapper extends
	    Mapper<ImmutableBytesWritable, Result, ImmutableBytesWritable, Put> {

	private Task task;

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
	    super.setup(context);
	    this.task = DefaultStringifier.load(context.getConfiguration(), JobGenerator.APPEND_JOB_TASK, Task.class);
	}

	@Override
	public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException {
	    //TODO 
	    //调用导入配置MetadateManager.getPuInfoByResult(value, List<Filed>)获取rowkey及若干cf:qualifier value
	    Put put = new Put(row.get());
	    for (KeyValue kv : value.list()) {
		put.add(kv.getFamily(), kv.getQualifier(), kv.getValue());
	    }
	    //TODO end
	    try {
		context.write(row, put);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

}

package com.citic.zxyjs.zwlscx.mapreduce.join.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.classification.InterfaceStability.Unstable;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DefaultStringifier;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import com.citic.zxyjs.zwlscx.bean.Field;
import com.citic.zxyjs.zwlscx.bean.File;
import com.citic.zxyjs.zwlscx.bean.Table;
import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.mapreduce.JobGenerator;
import com.citic.zxyjs.zwlscx.mapreduce.join.api.DataJoinMapperBase;
import com.citic.zxyjs.zwlscx.mapreduce.join.api.TaggedMapOutput;
import com.citic.zxyjs.zwlscx.mapreduce.lib.input.TaggedInputSplit;
import com.citic.zxyjs.zwlscx.xml.Separator;

/**
 * Join操作下不同类型的mapper
 * 
 * @author JoyoungZhang@gmail.com
 */
public class DataJoinMapper {

    /**
     * input is hdfs file output is text and text
     * 
     * @author JoyoungZhang@gmail.com
     */
    public static class DataJoinTextInputFormatMapper extends DataJoinMapperBase<LongWritable, Text, Text, TaggedMapOutput> {

	private Task task;
	private File currentFile;
	private List<Field> currentFields;
	private boolean init;

	protected void setup(Context context) throws IOException, InterruptedException {
	    super.setup(context);
	    this.task = DefaultStringifier.load(context.getConfiguration(), JobGenerator.JOIN_JOB_TASK, Task.class);//ParseXmlUtilsBak.parseXml().getTasks().get(0);
	    this.init = context.getConfiguration().getBoolean("init", false);

	    switch (task.getSourceType()) {
	    case FF:
		File leftSource = (File) task.getLeftSource();
		File rightSource = (File) task.getRightSource();
		if (datasource.equals(leftSource.getPath())) {
		    currentFile = leftSource;
		    currentFields = task.getLeftFields();
		} else {
		    currentFile = rightSource;
		    currentFields = task.getRightFields();
		}
		break;
	    case FT:
		currentFile = (File) task.getLeftSource();
		currentFields = task.getLeftFields();
		break;
	    case TF:
		currentFile = (File) task.getRightSource();
		currentFields = task.getRightFields();
		break;
	    case TT:
		//do nothing
		break;
	    }
	}

	@Override
	protected String generateDatasource(Context context) throws IOException {
	    if (context.getInputSplit() instanceof TaggedInputSplit) {
		return ((FileSplit) ((TaggedInputSplit) context.getInputSplit()).getInputSplit()).getPath().toString();
	    }
	    return ((FileSplit) context.getInputSplit()).getPath().toString();
	}

	@Override
	protected TaggedMapOutput generateTaggedMapOutput(Text value, Context context) throws IOException {
	    String[] tokens = value.toString().split(Separator.SEP_COMMA);
	    List<Field> fields = currentFile.getFields();
	    if (tokens.length != fields.size()) {
		return null;
	    }
	    MapWritable map = new MapWritable();
	    for (int i = 0; i < tokens.length; i++) {
		map.put(fields.get(i), new Text(tokens[i]));
	    }
	    TaggedWritable retv = new TaggedWritable(map);
	    retv.setTag(this.inputTag);
	    return retv;
	}

	@Override
	protected Text generateGroupKey(TaggedMapOutput aRecord, Context context) throws IOException {
	    MapWritable map = (MapWritable) aRecord.getData();

	    StringBuffer groupKey = new StringBuffer();
	    for (Field field : currentFields) {
		groupKey.append(map.get(field)).append(Separator.SEP_COMMA);
	    }
	    if (currentFields.size() > 0) {
		groupKey.deleteCharAt(groupKey.length() - 1);
	    }
	    return new Text(groupKey.toString());
	}

    }

    /**
     * 暂时没有任何业务场景使用此类，此类也经测试 input is hdfs file output is rowky and put
     * 
     * @author JoyoungZhang@gmail.com
     */
    @Unstable
    @Deprecated
    public static class DataJoinTextInputFormatForHBaseMapper extends
	    DataJoinMapperBase<LongWritable, Text, ImmutableBytesWritable, Put> {

	private Task task;
	private List<Integer> fieldPositions;
	private boolean init;

	protected void setup(Context context) throws IOException, InterruptedException {
	    super.setup(context);
	    this.task = DefaultStringifier.load(context.getConfiguration(), "task", Task.class);//ParseXmlUtilsBak.parseXml().getTasks().get(0);
	    this.init = context.getConfiguration().getBoolean("init", false);

	    File currentFile = null;
	    List<Field> currentFields = null;
	    switch (task.getSourceType()) {
	    case FF:
		File leftSource = (File) task.getLeftSource();
		File rightSource = (File) task.getRightSource();
		if (datasource.equals(leftSource.getPath())) {
		    currentFile = leftSource;
		    currentFields = task.getLeftFields();
		} else {
		    currentFile = rightSource;
		    currentFields = task.getRightFields();
		}
		break;
	    case FT:
		currentFile = (File) task.getLeftSource();
		currentFields = task.getLeftFields();
		break;
	    case TF:
		currentFile = (File) task.getRightSource();
		currentFields = task.getRightFields();
		break;
	    case TT:
		//do nothing
		break;
	    }
	    fieldPositions = new ArrayList<Integer>();
	    for (Field fieldOut : currentFields) {
		for (int i = 0; i < currentFile.getFields().size(); i++) {
		    Field fieldIn = currentFile.getFields().get(i);
		    if (fieldIn.getId().equals(fieldOut.getId())) {
			fieldPositions.add(i);
			break;
		    }
		}
	    }
	}

	@Override
	protected String generateDatasource(Context context) throws IOException {
	    if (context.getInputSplit() instanceof TaggedInputSplit) {
		return ((FileSplit) ((TaggedInputSplit) context.getInputSplit()).getInputSplit()).getPath().toString();
	    }
	    return ((FileSplit) context.getInputSplit()).getPath().toString();
	}

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	    String line = value.toString();
	    String[] tokens = line.split(Separator.SEP_COMMA);

	    StringBuffer groupKey = new StringBuffer();
	    for (Integer position : fieldPositions) {
		groupKey.append(tokens[position]).append(Separator.SEP_COMMA);
	    }
	    if (fieldPositions.size() > 0) {
		groupKey.deleteCharAt(groupKey.length() - 1);
	    }
	    List<Field> fields = task.getOutput().getFields();
	    Put put = new Put(Bytes.toBytes(groupKey.toString()));
	    for (int i = 0; i < tokens.length; i++) {
		String token = tokens[i];
		put.add(Bytes.toBytes("cf"), Bytes.toBytes(fields.get(i).getName()), Bytes.toBytes(token));
	    }
	    context.write(new ImmutableBytesWritable(Bytes.toBytes(groupKey.toString())), put);
	}

	@Override
	protected Put generateTaggedMapOutput(Text value, Context context) throws IOException {
	    return null;
	}

	@Override
	protected ImmutableBytesWritable generateGroupKey(Put aRecord, Context context) throws IOException {
	    return null;
	}
    }

    /**
     * input is hbase table output is text and text
     * 
     * @author JoyoungZhang@gmail.com
     */
    public static class DataJoinTableInputFormatMapper extends
	    DataJoinMapperBase<ImmutableBytesWritable, Result, Text, TaggedMapOutput> {

	private Task task;
	private Table currentTable;
	private List<Field> currentFields;
	private boolean init;

	protected void setup(Context context) throws IOException, InterruptedException {
	    super.setup(context);
	    this.task = DefaultStringifier.load(context.getConfiguration(), JobGenerator.JOIN_JOB_TASK, Task.class);//ParseXmlUtilsBak.parseXml().getTasks().get(1);
	    this.init = context.getConfiguration().getBoolean("init", false);

	    switch (task.getSourceType()) {
	    case FF:
		//do nothing
		break;
	    case FT:
		currentTable = (Table) task.getRightSource();
		currentFields = task.getRightFields();
		break;
	    case TF:
		currentTable = (Table) task.getLeftSource();
		currentFields = task.getLeftFields();
		break;
	    case TT:
		Table leftSource = (Table) task.getLeftSource();
		Table rightSource = (Table) task.getRightSource();
		if (datasource.equals(leftSource.getName())) {
		    currentTable = leftSource;
		    currentFields = task.getLeftFields();
		} else {
		    currentTable = rightSource;
		    currentFields = task.getRightFields();
		}
		break;
	    }
	}

	@Override
	protected String generateDatasource(Context context) throws IOException {
	    return context.getConfiguration().get(TableInputFormat.INPUT_TABLE);
	}

	@Override
	protected TaggedMapOutput generateTaggedMapOutput(Result value, Context context) {
	    List<Field> fields = currentTable.getFields();
	    List<KeyValue> kvs = value.list();
	    if (kvs.size() != fields.size()) {
		return null;
	    }
	    MapWritable map = new MapWritable();
	    for (int i = 0; i < kvs.size(); i++) {
		map.put(fields.get(i), new Text(Bytes.toString(kvs.get(i).getValue())));
	    }
	    TaggedWritable retv = new TaggedWritable(map);
	    retv.setTag(this.inputTag);
	    return retv;
	}

	@Override
	protected Text generateGroupKey(TaggedMapOutput aRecord, Context context) throws IOException {
	    MapWritable map = (MapWritable) aRecord.getData();

	    StringBuffer groupKey = new StringBuffer();
	    for (Field field : currentFields) {
		groupKey.append(map.get(field)).append(Separator.SEP_COMMA);
	    }
	    if (currentFields.size() > 0) {
		groupKey.deleteCharAt(groupKey.length() - 1);
	    }
	    return new Text(groupKey.toString());
	}

    }

    /**
     * 暂时没有任何业务场景使用此类，此类也经测试 input is hbase table output is rowkey and put
     * 
     * @author JoyoungZhang@gmail.com
     */
    @Unstable
    @Deprecated
    public static class DataJoinTableInputFormatForHBaseMapper extends
	    DataJoinMapperBase<ImmutableBytesWritable, Result, ImmutableBytesWritable, Put> {

	private Task task;
	private List<Integer> fieldPositions;
	private boolean init;

	protected void setup(Context context) throws IOException, InterruptedException {
	    super.setup(context);
	    this.task = DefaultStringifier.load(context.getConfiguration(), "task", Task.class);//ParseXmlUtilsBak.parseXml().getTasks().get(1);
	    this.init = context.getConfiguration().getBoolean("init", false);

	    Table currentTable = null;
	    List<Field> currentFields = null;
	    switch (task.getSourceType()) {
	    case FF:
		//do nothing
		break;
	    case FT:
		currentTable = (Table) task.getRightSource();
		currentFields = task.getRightFields();
		break;
	    case TF:
		currentTable = (Table) task.getLeftSource();
		currentFields = task.getLeftFields();
		break;
	    case TT:
		Table leftSource = (Table) task.getLeftSource();
		Table rightSource = (Table) task.getRightSource();
		if (datasource.equals(leftSource.getName())) {
		    currentTable = leftSource;
		    currentFields = task.getLeftFields();
		} else {
		    currentTable = rightSource;
		    currentFields = task.getRightFields();
		}
		break;
	    }
	    fieldPositions = new ArrayList<Integer>();
	    for (Field fieldOut : currentFields) {
		for (int i = 0; i < currentTable.getFields().size(); i++) {
		    Field fieldIn = currentTable.getFields().get(i);
		    if (fieldIn.getId().equals(fieldOut.getId())) {
			fieldPositions.add(i);
			break;
		    }
		}
	    }
	}

	@Override
	protected String generateDatasource(Context context) throws IOException {
	    return context.getConfiguration().get(TableInputFormat.INPUT_TABLE);
	}

	@Override
	protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
	    Put put = new Put(key.get());
	    for (KeyValue kv : value.list()) {
		put.add(Bytes.toBytes("cf"), kv.getQualifier(), kv.getValue());
	    }
	    context.write(key, put);
	}

	@Override
	protected Put generateTaggedMapOutput(Result value, Context context) {
	    return null;
	}

	@Override
	protected ImmutableBytesWritable generateGroupKey(Put aRecord, Context context) throws IOException {
	    return null;
	}

    }
}

package com.citic.zxyjs.zwlscx.mapreduce.join.impl;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.KeyValue;
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

	protected void setup(Context context) throws IOException, InterruptedException {
	    super.setup(context);
	    this.task = DefaultStringifier.load(context.getConfiguration(), JobGenerator.JOIN_JOB_TASK, Task.class);//ParseXmlUtilsBak.parseXml().getTasks().get(0);

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
     * input is hbase table output is text and text
     * 
     * @author JoyoungZhang@gmail.com
     */
    public static class DataJoinTableInputFormatMapper extends
	    DataJoinMapperBase<ImmutableBytesWritable, Result, Text, TaggedMapOutput> {

	private Task task;
	private Table currentTable;
	private List<Field> currentFields;

	protected void setup(Context context) throws IOException, InterruptedException {
	    super.setup(context);
	    this.task = DefaultStringifier.load(context.getConfiguration(), JobGenerator.JOIN_JOB_TASK, Task.class);//ParseXmlUtilsBak.parseXml().getTasks().get(1);

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
	protected TaggedMapOutput generateTaggedMapOutput(Result value, Context context) throws IOException {
	    //TODO
	    //调用导出配置MetadateManager.getLineByResult(value, tableId) 将HBase的一个Result转换为<Field, String>对
	    List<Field> fields = currentTable.getFields();
	    List<KeyValue> kvs = value.list();
	    if (kvs.size() != fields.size()) {
		return null;
	    }
	    //TODO end
	    MapWritable map = new MapWritable();
	    for (int i = 0; i < kvs.size(); i++) {
		KeyValue kv = kvs.get(i);
		String qualifier = Bytes.toString(kv.getQualifier());
		for (Field field : fields) {
		    if (field.getName().equals(qualifier)) {
			map.put(field, new Text(Bytes.toString(kvs.get(i).getValue())));
			break;
		    }
		}
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

}

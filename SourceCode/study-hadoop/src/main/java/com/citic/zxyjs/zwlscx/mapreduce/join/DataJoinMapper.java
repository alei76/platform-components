package com.citic.zxyjs.zwlscx.mapreduce.join;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import com.citic.zxyjs.zwlscx.bean.Field;
import com.citic.zxyjs.zwlscx.bean.File;
import com.citic.zxyjs.zwlscx.bean.Table;
import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.mapreduce.lib.input.TaggedInputSplit;
import com.citic.zxyjs.zwlscx.xml.ParseXmlUtils;
import com.citic.zxyjs.zwlscx.xml.Separator;

/**
 * Join时不同类型的mapper
 * 
 * @author JoyoungZhang@gmail.com
 */
public class DataJoinMapper {

    /**
     * 数据源为Hdfs文件的mapper
     * 
     * @author JoyoungZhang@gmail.com
     */
    public static class DataJoinTextInputFormatMapper extends DataJoinMapperBase<LongWritable, Text, Text, TaggedMapOutput> {

	private Task task;
	private List<Integer> fieldPositions;
	private boolean init;

	protected void setup(Context context) throws IOException, InterruptedException {
	    super.setup(context);
	    this.task = ParseXmlUtils.parseXml().getTasks().get(0);
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
		}else{
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
	    if(context.getInputSplit() instanceof TaggedInputSplit){
		return ((FileSplit)((TaggedInputSplit) context.getInputSplit()).getInputSplit()).getPath().toString();
	    }
	    return ((FileSplit) context.getInputSplit()).getPath().toString();
	}
	
	@Override
	protected TaggedMapOutput generateTaggedMapOutput(Text value, Context context) throws IOException {
	    TaggedWritable retv = new TaggedWritable(value);
	    retv.setTag(this.inputTag);
	    return retv;
	}
	
	@Override
	protected Text generateGroupKey(TaggedMapOutput aRecord, Context context) throws IOException {
	    String line = aRecord.getData().toString();
	    String[] tokens = line.split(Separator.SEP_COMMA);

	    StringBuffer groupKey = new StringBuffer();
	    for (Integer position : fieldPositions) {
		groupKey.append(tokens[position]).append(Separator.SEP_COMMA);
	    }
	    if (fieldPositions.size() > 0) {
		groupKey.deleteCharAt(groupKey.length() - 1);
	    }
	    return new Text(groupKey.toString());
	}
	
    }

    /**
     * 数据源为HBase的mapper
     * 
     * @author JoyoungZhang@gmail.com
     */
    public static class DataJoinTableInputFormatMapper extends DataJoinMapperBase<ImmutableBytesWritable, Result, Text, TaggedMapOutput> {

	private Task task;
	private List<Integer> fieldPositions;
	private boolean init;

	protected void setup(Context context) throws IOException, InterruptedException {
	    super.setup(context);
	    this.task = ParseXmlUtils.parseXml().getTasks().get(0);
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
		}else{
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
	protected TaggedMapOutput generateTaggedMapOutput(Result value, Context context) {
	    TaggedWritable retv = new TaggedWritable();
	    StringBuffer text = new StringBuffer();
	    boolean hasAppend = false;
	    for (KeyValue kv : value.list()) {
		text.append(Bytes.toStringBinary(kv.getValue())).append(Separator.SEP_COMMA);
		hasAppend = true;
	    }
	    if(hasAppend){
		text.deleteCharAt(text.length() - 1);
	    }
	    retv.setData(new Text(text.toString()));
	    retv.setTag(this.inputTag);
	    return retv;
	}
	
	@Override
	protected Text generateGroupKey(TaggedMapOutput aRecord, Context context) throws IOException {
	    String line = aRecord.getData().toString();
	    String[] tokens = line.split(Separator.SEP_COMMA);

	    StringBuffer groupKey = new StringBuffer();
	    for (Integer position : fieldPositions) {
		groupKey.append(tokens[position]).append(Separator.SEP_COMMA);
	    }
	    if (fieldPositions.size() > 0) {
		groupKey.deleteCharAt(groupKey.length() - 1);
	    }
	    return new Text(groupKey.toString());
	}

	
    }
}

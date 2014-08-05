package com.citic.zxyjs.zwlscx.mapreduce.join.impl;

import java.io.IOException;
import java.util.ArrayList;
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

import com.citic.zxyjs.zwlscx.bean.File;
import com.citic.zxyjs.zwlscx.bean.JoinTask;
import com.citic.zxyjs.zwlscx.bean.Rule;
import com.citic.zxyjs.zwlscx.bean.Table;
import com.citic.zxyjs.zwlscx.bean.future.Field;
import com.citic.zxyjs.zwlscx.bean.future.MetaDataHelper;
import com.citic.zxyjs.zwlscx.mapreduce.JobGenerator;
import com.citic.zxyjs.zwlscx.mapreduce.join.api.DataJoinMapperBase;
import com.citic.zxyjs.zwlscx.mapreduce.join.api.TaggedMapOutput;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.Extendable;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.ExtensionUtils;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.MapperReducerExtensionParmeters;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.SystemExtensionParmeters;
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

	private JoinTask joinTask;
	private Extendable userExtension;
	private Extendable systemExtension;
	private File currentFile;
	private List<Field> joinFields;

	protected void setup(Context context) throws IOException, InterruptedException {
	    super.setup(context);
	    this.joinTask = DefaultStringifier.load(context.getConfiguration(), JobGenerator.JOIN_JOB_TASK, JoinTask.class);//ParseXmlUtilsBak.parseXml().getTasks().get(0);
	    this.userExtension = ExtensionUtils.newInstance(joinTask.getMapperExtension(), context.getConfiguration());
	    this.systemExtension = ExtensionUtils.getSystenExtension(context.getConfiguration());
	    
	    switch (joinTask.getSourceType()) {
	    case FF:
		File leftSource = (File) joinTask.getLeftSource();
		File rightSource = (File) joinTask.getRightSource();
		if (datasource.equals(leftSource.getPath())) {
		    currentFile = leftSource;
		    this.joinFields = getJoinFieldsByRule(Direction.L, joinTask.getJoinRule());
		} else {
		    currentFile = rightSource;
		    this.joinFields = getJoinFieldsByRule(Direction.R, joinTask.getJoinRule());
		}
		break;
	    case FT:
		currentFile = (File) joinTask.getLeftSource();
		this.joinFields = getJoinFieldsByRule(Direction.L, joinTask.getJoinRule());
		break;
	    case TF:
		currentFile = (File) joinTask.getRightSource();
		this.joinFields = getJoinFieldsByRule(Direction.R, joinTask.getJoinRule());
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
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	    TaggedMapOutput aRecord = generateTaggedMapOutput(value, context);
	    if (aRecord == null) {
		return;
	    }
	    Text groupKey = generateGroupKey(aRecord, context);
	    if (groupKey == null) {
		return;
	    }
	    systemExtension.doExtend(new SystemExtensionParmeters());
	    if (userExtension == null) {
		context.write(groupKey, aRecord);
	    } else {
		MapperReducerExtensionParmeters pars = new MapperReducerExtensionParmeters(joinTask, groupKey, aRecord);
		userExtension.doExtend(pars);
		context.write((Text) pars.getKey(), (TaggedMapOutput) pars.getValue());
	    }
	}

	@Override
	protected TaggedMapOutput generateTaggedMapOutput(Text value, Context context) throws IOException {
	    String[] tokens = value.toString().split(Separator.SEP_COMMA);
	    List<Field> fields = MetaDataHelper.getFieldByFileName(currentFile.getName());
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
	    for (Field field : joinFields) {
		groupKey.append(map.get(field)).append(Separator.SEP_COMMA);
	    }
	    if (joinFields.size() > 0) {
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

	private JoinTask joinTask;
	private Extendable userExtension;
	private Extendable systemExtension;
	private Table currentTable;
	private List<Field> joinFields;

	protected void setup(Context context) throws IOException, InterruptedException {
	    super.setup(context);
	    this.joinTask = DefaultStringifier.load(context.getConfiguration(), JobGenerator.JOIN_JOB_TASK, JoinTask.class);//ParseXmlUtilsBak.parseXml().getTasks().get(1);
	    this.userExtension = ExtensionUtils.newInstance(joinTask.getMapperExtension(), context.getConfiguration());
	    this.systemExtension = ExtensionUtils.getSystenExtension(context.getConfiguration());

	    switch (joinTask.getSourceType()) {
	    case FF:
		//do nothing
		break;
	    case FT:
		currentTable = (Table) joinTask.getRightSource();
		this.joinFields = getJoinFieldsByRule(Direction.R, joinTask.getJoinRule());
		break;
	    case TF:
		currentTable = (Table) joinTask.getLeftSource();
		this.joinFields = getJoinFieldsByRule(Direction.L, joinTask.getJoinRule());
		break;
	    case TT:
		Table leftSource = (Table) joinTask.getLeftSource();
		Table rightSource = (Table) joinTask.getRightSource();
		if (datasource.equals(leftSource.getName())) {
		    currentTable = leftSource;
		    this.joinFields = getJoinFieldsByRule(Direction.L, joinTask.getJoinRule());
		} else {
		    currentTable = rightSource;
		    this.joinFields = getJoinFieldsByRule(Direction.R, joinTask.getJoinRule());
		}
		break;
	    }
	}

	@Override
	protected String generateDatasource(Context context) throws IOException {
	    return context.getConfiguration().get(TableInputFormat.INPUT_TABLE);
	}

	@Override
	protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
	    TaggedMapOutput aRecord = generateTaggedMapOutput(value, context);
	    if (aRecord == null) {
		return;
	    }
	    Text groupKey = generateGroupKey(aRecord, context);
	    if (groupKey == null) {
		return;
	    }
	    systemExtension.doExtend(new SystemExtensionParmeters());
	    if (userExtension == null) {
		context.write(groupKey, aRecord);
	    } else {
		MapperReducerExtensionParmeters pars = new MapperReducerExtensionParmeters(joinTask, groupKey, aRecord);
		userExtension.doExtend(pars);
		context.write((Text) pars.getKey(), (TaggedMapOutput) pars.getValue());
	    }
	}

	@Override
	protected TaggedMapOutput generateTaggedMapOutput(Result value, Context context) throws IOException {
	    //TODO
	    //调用导出配置MetadateManager.getLineByResult(value, tableId) 将HBase的一个Result转换为<Field, String>对
	    List<Field> fields = MetaDataHelper.getFieldByTableName(currentTable.getName());
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
	    for (Field field : joinFields) {
		groupKey.append(map.get(field)).append(Separator.SEP_COMMA);
	    }
	    if (joinFields.size() > 0) {
		groupKey.deleteCharAt(groupKey.length() - 1);
	    }
	    return new Text(groupKey.toString());
	}

    }

    public static List<Field> getJoinFieldsByRule(Direction r, List<Rule> joinRules) {
	List<Field> fields = new ArrayList<Field>();
	if(joinRules == null || joinRules.size() == 0){
	    return fields;
	}
	for(Rule rule : joinRules){
	    fields.add(r == Direction.L ? rule.getLeftField() : rule.getRightField());
	}
	return fields;
    }
    
    private enum Direction{
	L,R
    }

}

package com.citic.zxyjs.zwlscx.mapreduce.join;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import com.citic.zxyjs.zwlscx.bean.Field;
import com.citic.zxyjs.zwlscx.bean.File;
import com.citic.zxyjs.zwlscx.bean.Task;
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
	private File currentFile;
	private List<String> currentFields;
	private boolean init;

	protected void setup(Context context) throws IOException, InterruptedException {
	    super.setup(context);
	    this.task = ParseXmlUtils.parseXml().getTasks().get(0);
	    this.init = context.getConfiguration().getBoolean("init", false);
	    File leftSource = (File) task.getLeftSource();
	    File rightSource = (File) task.getRightSource();
	    this.currentFile = leftSource;
	    this.currentFields = task.getLeftFields();
	    if (inputFile.equals(rightSource.getPath())) {
		this.currentFile = rightSource;
		this.currentFields = task.getRightFields();
	    }
	}

	protected Text generateGroupKey(TaggedMapOutput aRecord, Context context) throws IOException {
	    String line = aRecord.getData().toString();
	    String[] tokens = line.split(Separator.SEP_FIELD);
	    List<Integer> groupKeyPosition = new ArrayList<Integer>();

	    for (String fieldStr : currentFields) {
		for (int i = 0; i < currentFile.getFields().size(); i++) {
		    Field field = currentFile.getFields().get(i);
		    if (field.getId().equals(fieldStr)) {
			groupKeyPosition.add(i);
		    }
		}
	    }

	    StringBuffer groupKey = new StringBuffer();
	    for (Integer position : groupKeyPosition) {
		groupKey.append(tokens[position]).append(Separator.SEP_FIELD);
	    }
	    if (groupKeyPosition.size() > 0) {
		groupKey.deleteCharAt(groupKey.length() - 1);
	    }
	    return new Text(groupKey.toString());
	}

	protected TaggedMapOutput generateTaggedMapOutput(Text value, Context context) throws IOException {
	    TaggedWritable retv = new TaggedWritable(value);
	    retv.setTag(this.inputTag);
	    return retv;
	}
    }

    /**
     * 数据源为HBase的mapper
     * 
     * @author JoyoungZhang@gmail.com
     */
    public static class DataJoinTableInputFormatMapper extends
	    DataJoinMapperBase<ImmutableBytesWritable, Result, Text, TaggedMapOutput> {

	protected Text generateGroupKey(TaggedMapOutput aRecord, Context context) {
	    String line = aRecord.getData().toString();
	    String[] tokens = line.split(",");
	    String groupKey = tokens[0];
	    return new Text(groupKey);
	}

	protected TaggedMapOutput generateTaggedMapOutput(Result value, Context context) {
	    TaggedWritable retv = new TaggedWritable(new Text(Bytes.toStringBinary(value.list().get(0).getValue())));
	    retv.setTag(this.inputTag);
	    return retv;
	}
    }
}

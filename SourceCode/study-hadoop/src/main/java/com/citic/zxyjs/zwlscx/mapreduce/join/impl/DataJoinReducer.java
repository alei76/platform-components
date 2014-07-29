package com.citic.zxyjs.zwlscx.mapreduce.join.impl;

import java.io.IOException;

import org.apache.hadoop.io.DefaultStringifier;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;

import com.citic.zxyjs.zwlscx.bean.Field;
import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.mapreduce.JobGenerator;
import com.citic.zxyjs.zwlscx.mapreduce.join.api.DataJoinReducerBase;
import com.citic.zxyjs.zwlscx.mapreduce.join.api.TaggedMapOutput;
import com.citic.zxyjs.zwlscx.xml.Separator;

/**
 * Join reduce
 * 
 * @author JoyoungZhang@gmail.com
 */
public class DataJoinReducer extends DataJoinReducerBase {

    private Task task;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
	super.setup(context);
	this.task = DefaultStringifier.load(context.getConfiguration(), JobGenerator.JOIN_JOB_TASK, Task.class);//ParseXmlUtilsBak.parseXml().getTasks().get(0);
    }

    @Override
    protected Text combine(Text[] tags, TaggedMapOutput[] values) {
	MapWritable map = new MapWritable();
	for (TaggedMapOutput taggedMapOutput : values) {
	    map.putAll((MapWritable) taggedMapOutput.getData());
	}
	StringBuffer combined = new StringBuffer();
	for (Field field : task.getOutput().getFields()) {
	    combined.append(map.get(field)).append(Separator.SEP_COMMA);
	}
	if (task.getOutput().getFields().size() > 0) {
	    combined.deleteCharAt(combined.length() - 1);
	}
	return new Text(combined.toString());
    }

    @Override
    protected void write(Context context, Text key, Text value) throws IOException, InterruptedException {
	context.write(null, value);
    }
}

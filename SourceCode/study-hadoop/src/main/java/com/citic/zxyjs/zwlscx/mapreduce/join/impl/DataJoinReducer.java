package com.citic.zxyjs.zwlscx.mapreduce.join.impl;

import java.io.IOException;

import org.apache.hadoop.io.DefaultStringifier;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import com.citic.zxyjs.zwlscx.bean.Field;
import com.citic.zxyjs.zwlscx.bean.File;
import com.citic.zxyjs.zwlscx.bean.Table;
import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.mapreduce.JobGenerator;
import com.citic.zxyjs.zwlscx.mapreduce.join.api.DataJoinReducerBase;
import com.citic.zxyjs.zwlscx.mapreduce.join.api.TaggedMapOutput;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.Extendable;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.ExtensionUtils;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.MapperReducerExtensionParmeters;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.SystemExtensionParmeters;
import com.citic.zxyjs.zwlscx.xml.Separator;

/**
 * Join reduce
 * 
 * @author JoyoungZhang@gmail.com
 */
public class DataJoinReducer extends DataJoinReducerBase {

    private Task task;
    private Extendable userExtendable;
    private Extendable systemExtension;
    private MultipleOutputs<Text, Text> mos;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
	super.setup(context);
	this.task = DefaultStringifier.load(context.getConfiguration(), JobGenerator.JOIN_JOB_TASK, Task.class);//ParseXmlUtilsBak.parseXml().getTasks().get(0);
	this.userExtendable = ExtensionUtils.newInstance(task.getReducerExtension(), context.getConfiguration());
	this.systemExtension = ExtensionUtils.getSystenExtension(context.getConfiguration());
	this.mos = new MultipleOutputs(context);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
	mos.close();
    }

    @Override
    protected void doCombinedAndWrite(Context context, Text key, Text[] tags, TaggedMapOutput[] partialList) throws IOException,
	    InterruptedException {
	Text tag = null;
	if (tags.length == 0 && partialList.length == 0 && (tags.length != partialList.length)) {
	    return;
	}
	if (tags.length == 1) {
	    tag = tags[0];
	    //只有从表记录，忽略
	    if ((task.getRightSource() instanceof File && tag.toString().equals(task.getRightSource().getPath()))
		    || (task.getRightSource() instanceof Table && tag.toString().equals(task.getRightSource().getName()))) {
		return;
	    }
	    MapWritable map = (MapWritable) partialList[0].getData();
	    StringBuffer letfSourceInfo = new StringBuffer();
	    for (Field field : task.getLeftSource().getFields()) {
		letfSourceInfo.append(map.get(field)).append(Separator.SEP_COMMA);
	    }
	    if (task.getLeftSource().getFields().size() > 0) {
		letfSourceInfo.deleteCharAt(letfSourceInfo.length() - 1);
	    }
	    //只有主表记录，先记录到指定error文件，再正常输出
	    mos.write(key, new Text(letfSourceInfo.toString()), ((File) task.getOutput()).getErrorPath());
	}
	//主表有记录，从表有记录，正常输出
	Text combined = combine(tags, partialList);

	systemExtension.doExtend(new SystemExtensionParmeters());
	if (userExtendable == null) {
	    write(context, key, combined);
	} else {
	    MapperReducerExtensionParmeters pars = new MapperReducerExtensionParmeters(task, key, combined);
	    userExtendable.doExtend(pars);
	    write(context, (Text) pars.getKey(), (Text) pars.getValue());
	}
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

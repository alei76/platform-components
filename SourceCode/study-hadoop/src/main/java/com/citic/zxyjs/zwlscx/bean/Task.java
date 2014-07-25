package com.citic.zxyjs.zwlscx.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

import com.citic.zxyjs.zwlscx.mapreduce.join.DataJoinMapper.DataJoinTableInputFormatMapper;
import com.citic.zxyjs.zwlscx.mapreduce.join.DataJoinMapper.DataJoinTextInputFormatMapper;
import com.citic.zxyjs.zwlscx.mapreduce.lib.input.MultipleInputs;

/**
 * 任务
 * @author JoyoungZhang@gmail.com
 */
public class Task implements Writable  {

    private static final long serialVersionUID = 1L;

    private Source leftSource;
    private Source rightSource;
    private List<Field> leftFields;
    private List<Field> rightFields;
    private String output;

    private TaskType taskType;

    public Source getLeftSource() {
	return leftSource;
    }

    public void setLeftSource(Source leftSource) {
	this.leftSource = leftSource;
    }

    public Source getRightSource() {
	return rightSource;
    }

    public void setRightSource(Source rightSource) {
	this.rightSource = rightSource;
    }

    public List<Field> getLeftFields() {
	return leftFields;
    }

    public void setLeftFields(List<Field> leftFields) {
	this.leftFields = leftFields;
    }

    public List<Field> getRightFields() {
	return rightFields;
    }

    public void setRightFields(List<Field> rightFields) {
	this.rightFields = rightFields;
    }

    public String getOutput() {
	return output;
    }

    public void setOutput(String output) {
	this.output = output;
    }

    public TaskType getTaskType() {
	return taskType;
    }

    public void setTaskType(TaskType taskType) {
	this.taskType = taskType;
    }
    
    public SourceType getSourceType(){
	if(leftSource instanceof File){
	    if(rightSource instanceof File){
		return SourceType.FF;
	    }else{
		return SourceType.FT;
	    }
	}else{
	    if(rightSource instanceof File){
		return SourceType.TF;
	    }else{
		return SourceType.TT;
	    }
	}
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	SourceType sourceType = WritableUtils.readEnum(in, SourceType.class);
	Source leftsource = new Source();
	Source rightsource = new Source();
	
	switch(sourceType){
    	case FF :
    	    leftsource = new File();
    	    rightsource = new File();
    	    break;
    	case FT :
    	    leftsource = new File();
    	    rightsource = new Table();
    	    break;
    	case TF :
    	    leftsource = new Table();
	    rightsource = new File();
    	    break;
    	case TT :
    	    leftsource = new Table();
	    rightsource = new Table();
    	    break;
	}
	
	leftsource.readFields(in);
	rightsource.readFields(in);
	leftSource = leftsource;
	rightSource = rightsource;
	
	int size = in.readInt();
	leftFields = new ArrayList<Field>(size);
	for(int i=0;i<size;i++){
	    Field field = new Field();
	    field.readFields(in);
	    leftFields.add(field);
	}
	size = in.readInt();
	rightFields = new ArrayList<Field>(size);
	for(int i=0;i<size;i++){
	    Field field = new Field();
	    field.readFields(in);
	    rightFields.add(field);
	}
	this.output = Text.readString(in);
	this.taskType = WritableUtils.readEnum(in, TaskType.class);
    }

    @Override
    public void write(DataOutput out) throws IOException {
	WritableUtils.writeEnum(out, this.getSourceType());
	leftSource.write(out);
	rightSource.write(out);
	out.writeInt(leftFields.size());
	for(Field field : leftFields){
	    field.write(out);
	}
	out.writeInt(rightFields.size());
	for(Field field : rightFields){
	    field.write(out);
	}
	Text.writeString(out, output);
	WritableUtils.writeEnum(out, taskType);
    }
    
    public String getIdentify(){
	return leftSource.getName() + " " + rightSource.getName();
    }
}

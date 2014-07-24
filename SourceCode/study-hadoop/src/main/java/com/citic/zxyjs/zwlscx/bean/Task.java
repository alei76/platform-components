package com.citic.zxyjs.zwlscx.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableUtils;

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
	leftSource.readFields(in);
	rightSource.readFields(in);
	int size = in.readInt();
	leftFields = new ArrayList<Field>(size);
	for(int i=0;i<size;i++){
	    Field field = null;
	    field.readFields(in);
	    leftFields.set(i, field);
	}
	size = in.readInt();
	rightFields = new ArrayList<Field>(size);
	for(int i=0;i<size;i++){
	    Field field = null;
	    field.readFields(in);
	    rightFields.set(i, field);
	}
	this.output = Text.readString(in);
	this.taskType = WritableUtils.readEnum(in, TaskType.class);
    }

    @Override
    public void write(DataOutput out) throws IOException {
	leftSource.write(out);
	rightSource.write(out);
	out.writeInt(leftFields.size());
	for(Field filed : leftFields){
	    filed.write(out);
	}
	out.writeInt(rightFields.size());
	for(Field filed : rightFields){
	    filed.write(out);
	}
	Text.writeString(out, output);
	WritableUtils.writeEnum(out, taskType);
    }

}

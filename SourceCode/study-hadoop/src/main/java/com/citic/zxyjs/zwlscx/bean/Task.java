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
    private List<String> leftFields;
    private List<String> rightFields;
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

    public List<String> getLeftFields() {
	return leftFields;
    }

    public void setLeftFields(List<String> leftFields) {
	this.leftFields = leftFields;
    }

    public List<String> getRightFields() {
	return rightFields;
    }

    public void setRightFields(List<String> rightFields) {
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
	Source leftsource = new Source();
	leftsource.readFields(in);
	leftSource = leftsource;
	Source rightsource = new Source();
	rightsource.readFields(in);
	rightSource = rightsource;
	int size = in.readInt();
	leftFields = new ArrayList<String>(size);
	for(int i=0;i<size;i++){
	    leftFields.add(Text.readString(in));
	}
	size = in.readInt();
	rightFields = new ArrayList<String>(size);
	for(int i=0;i<size;i++){
	    rightFields.add(Text.readString(in));
	}
	this.output = Text.readString(in);
	this.taskType = WritableUtils.readEnum(in, TaskType.class);
    }

    @Override
    public void write(DataOutput out) throws IOException {
	leftSource.write(out);
	rightSource.write(out);
	out.writeInt(leftFields.size());
	for(String filed : leftFields){
	    Text.writeString(out, filed);
	}
	out.writeInt(rightFields.size());
	for(String filed : rightFields){
	    Text.writeString(out, filed);
	}
	Text.writeString(out, output);
	WritableUtils.writeEnum(out, taskType);
    }

}

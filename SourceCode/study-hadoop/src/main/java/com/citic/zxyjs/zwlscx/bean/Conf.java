package com.citic.zxyjs.zwlscx.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Writable;

/**
 * 一个xml文件一个Conf实例
 * 
 * @author JoyoungZhang@gmail.com
 */
public class Conf implements Writable {

    private static final long serialVersionUID = 1L;

    private List<Task> tasks;
    private boolean clear;

    public List<Task> getTasks() {
	return tasks;
    }

    public void setTasks(List<Task> tasks) {
	this.tasks = tasks;
    }

    public boolean isClear() {
	return clear;
    }

    public void setClear(boolean clear) {
	this.clear = clear;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	int size = in.readInt();
	tasks = new ArrayList<Task>(size);
	for (int i = 0; i < size; i++) {
	    Task task = null;
	    task.readFields(in);
	    tasks.add(task);
	}
	this.clear = in.readBoolean();
    }

    @Override
    public void write(DataOutput out) throws IOException {
	out.writeInt(tasks.size());
	for (Task task : tasks) {
	    task.write(out);
	}
	out.writeBoolean(clear);
    }

}

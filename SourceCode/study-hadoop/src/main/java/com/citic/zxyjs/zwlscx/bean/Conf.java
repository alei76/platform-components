package com.citic.zxyjs.zwlscx.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
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
    private boolean init;

    public List<Task> getTasks() {
	return tasks;
    }

    public void setTasks(List<Task> tasks) {
	this.tasks = tasks;
    }

    public boolean isInit() {
	return init;
    }

    public void setInit(boolean init) {
	this.init = init;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void write(DataOutput out) throws IOException {
	// TODO Auto-generated method stub
	
    }

}

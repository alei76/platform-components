package com.citic.zxyjs.zwlscx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 一个xml文件一个Conf实例
 * 
 * @author JoyoungZhang@gmail.com
 */
public class Conf implements Serializable {

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

}

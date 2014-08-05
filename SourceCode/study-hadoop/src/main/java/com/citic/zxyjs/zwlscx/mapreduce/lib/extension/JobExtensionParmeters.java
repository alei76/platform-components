package com.citic.zxyjs.zwlscx.mapreduce.lib.extension;

import com.citic.zxyjs.zwlscx.bean.Task;

public class JobExtensionParmeters extends UserExtensionParmeters {

    private Task task;

    public JobExtensionParmeters(Task task) {
	this.task = task;
    }

    public Task getTask() {
	return task;
    }

    public void setTask(Task task) {
	this.task = task;
    }

}

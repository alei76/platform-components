package com.citic.zxyjs.zwlscx.mapreduce.lib.extension;

import org.apache.hadoop.io.Writable;

import com.citic.zxyjs.zwlscx.bean.Task;

public class MapperReducerExtensionParmeters extends JobExtensionParmeters {

    private Writable key;
    private Writable value;

    public MapperReducerExtensionParmeters(Task task, Writable key, Writable value) {
	super(task);
	this.key = key;
	this.value = value;
    }

    public Writable getKey() {
	return key;
    }

    public void setKey(Writable key) {
	this.key = key;
    }

    public Writable getValue() {
	return value;
    }

    public void setValue(Writable value) {
	this.value = value;
    }

}

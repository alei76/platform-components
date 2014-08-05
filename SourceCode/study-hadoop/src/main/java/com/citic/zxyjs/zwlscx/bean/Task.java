package com.citic.zxyjs.zwlscx.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

/**
 * 任务
 * 
 * @author JoyoungZhang@gmail.com
 */
public abstract class Task implements Writable {

    private static final long serialVersionUID = 1L;

    private String mapperExtension;
    private String reducerExtension;
    private String jobExtension;

    public String getMapperExtension() {
	return mapperExtension;
    }

    public void setMapperExtension(String mapperExtension) {
	this.mapperExtension = mapperExtension;
    }

    public String getReducerExtension() {
	return reducerExtension;
    }

    public void setReducerExtension(String reducerExtension) {
	this.reducerExtension = reducerExtension;
    }

    public String getJobExtension() {
	return jobExtension;
    }

    public void setJobExtension(String jobExtension) {
	this.jobExtension = jobExtension;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	boolean mapperExtensionIsNotNull = in.readBoolean();
	if (mapperExtensionIsNotNull) {
	    this.mapperExtension = Text.readString(in);
	}
	boolean reducerExtensionIsNotNull = in.readBoolean();
	if (reducerExtensionIsNotNull) {
	    this.reducerExtension = Text.readString(in);
	}
	boolean jobExtensionIsNotNull = in.readBoolean();
	if (jobExtensionIsNotNull) {
	    this.jobExtension = Text.readString(in);
	}
    }

    @Override
    public void write(DataOutput out) throws IOException {
	boolean mapperExtensionIsNotNull = (mapperExtension != null);
	out.writeBoolean(mapperExtensionIsNotNull);
	if (mapperExtensionIsNotNull) {
	    Text.writeString(out, mapperExtension);
	}
	boolean reducerExtensionIsNotNull = (reducerExtension != null);
	out.writeBoolean(reducerExtensionIsNotNull);
	if (reducerExtensionIsNotNull) {
	    Text.writeString(out, reducerExtension);
	}
	boolean jobExtensionIsNotNull = (jobExtension != null);
	out.writeBoolean(jobExtensionIsNotNull);
	if (jobExtensionIsNotNull) {
	    Text.writeString(out, jobExtension);
	}
    }

    public abstract String getIdentify();

    public abstract SourceType getSourceType();

}

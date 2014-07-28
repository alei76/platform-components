package com.citic.zxyjs.zwlscx.mapreduce.join.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;

import com.citic.zxyjs.zwlscx.mapreduce.join.api.TaggedMapOutput;

public class TaggedWritable extends TaggedMapOutput {
	private Writable data;

	public TaggedWritable() {
	    this.tag = new Text();
	}

	public TaggedWritable(Writable data) {
	    this.tag = new Text("");
	    this.data = data;
	}

	public Writable getData() {
	    return data;
	}

	public void setData(Writable data) {
	    this.data = data;
	}

	public void write(DataOutput out) throws IOException {
	    this.tag.write(out);
	    out.writeUTF(this.data.getClass().getName());
	    this.data.write(out);
	}

	public void readFields(DataInput in) throws IOException {
	    this.tag.readFields(in);
	    String dataClz = in.readUTF();
	    if (this.data == null || !this.data.getClass().getName().equals(dataClz)) {
		try {
		    this.data = (Writable) ReflectionUtils.newInstance(Class.forName(dataClz), null);
		} catch (ClassNotFoundException e) {
		    e.printStackTrace();
		}
	    }
	    this.data.readFields(in);
	}
}

package com.citic.zxyjs.zwlscx.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * 列实体对象
 * 
 * @author JoyoungZhang@gmail.com
 */
public class Field implements Writable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
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

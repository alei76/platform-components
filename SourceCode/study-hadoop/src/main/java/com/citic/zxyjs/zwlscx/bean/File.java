package com.citic.zxyjs.zwlscx.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * 文件实体对象
 * 
 * @author JoyoungZhang@gmail.com
 */
public class File extends Source implements Writable{

    private static final long serialVersionUID = 1L;

    private String path;

    public String getPath() {
	return path;
    }

    public void setPath(String path) {
	this.path = path;
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

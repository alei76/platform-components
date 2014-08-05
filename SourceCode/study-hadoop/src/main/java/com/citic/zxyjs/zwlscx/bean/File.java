package com.citic.zxyjs.zwlscx.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

/**
 * 文件实体对象
 * 
 * @author JoyoungZhang@gmail.com
 */
public class File extends Source implements Writable {

    private static final long serialVersionUID = 1L;

    private String errorPath;

    public File() {
	super();
    }

    public File(String errorPath) {
	super();
	this.errorPath = errorPath;
    }

    public String getErrorPath() {
	return errorPath;
    }

    public void setErrorPath(String errorPath) {
	this.errorPath = errorPath;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	super.readFields(in);
	boolean errorPathIsNotNull = in.readBoolean();
	if (errorPathIsNotNull) {
	    this.errorPath = Text.readString(in);
	}
    }

    @Override
    public void write(DataOutput out) throws IOException {
	super.write(out);
	boolean errorPathIsNotNull = (errorPath != null);
	out.writeBoolean(errorPathIsNotNull);
	if (errorPathIsNotNull) {
	    Text.writeString(out, errorPath);
	}
    }

}

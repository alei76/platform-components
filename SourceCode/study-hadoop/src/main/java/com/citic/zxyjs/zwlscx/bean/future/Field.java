package com.citic.zxyjs.zwlscx.bean.future;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

/**
 * 列实体对象
 * 
 * @author JoyoungZhang@gmail.com
 */
public class Field implements Writable {

    private static final long serialVersionUID = 1L;

    private String name;
    
    public Field() {
	super();
    }

    public Field(String name) {
	super();
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Field other = (Field) obj;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Field [name=" + name + "]";
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	this.name = Text.readString(in);
    }

    @Override
    public void write(DataOutput out) throws IOException {
	Text.writeString(out, name);
    }

}

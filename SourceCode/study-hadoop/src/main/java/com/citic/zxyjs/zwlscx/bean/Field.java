package com.citic.zxyjs.zwlscx.bean;

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

    private String id;
    private String name;

    private String sourceId;

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

    public String getSourceId() {
	return sourceId;
    }

    public void setSourceId(String sourceId) {
	this.sourceId = sourceId;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((sourceId == null) ? 0 : sourceId.hashCode());
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
	if (sourceId == null) {
	    if (other.sourceId != null)
		return false;
	} else if (!sourceId.equals(other.sourceId))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Field [id=" + id + ", name=" + name + ", sourceId=" + sourceId + "]";
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	this.id = Text.readString(in);
	this.name = Text.readString(in);
	this.sourceId = Text.readString(in);
    }

    @Override
    public void write(DataOutput out) throws IOException {
	Text.writeString(out, id);
	Text.writeString(out, name);
	Text.writeString(out, sourceId);
    }

}

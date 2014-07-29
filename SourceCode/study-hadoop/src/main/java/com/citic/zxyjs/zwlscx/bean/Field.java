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
    private String sourceFieldId;

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

    public String getSourceFieldId() {
	return sourceFieldId;
    }

    public void setSourceFieldId(String sourceFieldId) {
	this.sourceFieldId = sourceFieldId;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((sourceFieldId == null) ? 0 : sourceFieldId.hashCode());
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
	if (sourceFieldId == null) {
	    if (other.sourceFieldId != null)
		return false;
	} else if (!sourceFieldId.equals(other.sourceFieldId))
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
	return "Field [id=" + id + ", name=" + name + ", sourceFieldId=" + sourceFieldId + ", sourceId=" + sourceId + "]";
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	this.id = Text.readString(in);
	this.name = Text.readString(in);
	this.sourceId = Text.readString(in);
	this.sourceFieldId = Text.readString(in);
    }

    @Override
    public void write(DataOutput out) throws IOException {
	Text.writeString(out, id);
	Text.writeString(out, name);
	Text.writeString(out, sourceId);
	Text.writeString(out, sourceFieldId);
    }

}

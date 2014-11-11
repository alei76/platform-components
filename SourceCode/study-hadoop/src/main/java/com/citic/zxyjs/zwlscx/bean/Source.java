package com.citic.zxyjs.zwlscx.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import com.citic.zxyjs.zwlscx.bean.future.Field;

/**
 * 资源基类
 * 
 * @author JoyoungZhang@gmail.com
 */
public class Source implements Writable {

    private static final long serialVersionUID = 1L;

    private String name;
    private List<Field> excludeField;
    private List<Source> parentSource;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public List<Field> getExcludeField() {
	return excludeField;
    }

    public void setExcludeField(List<Field> excludeField) {
	this.excludeField = excludeField;
    }

    public List<Source> getParentSource() {
	return parentSource;
    }

    public void setParentSource(List<Source> parentSource) {
	this.parentSource = parentSource;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	this.name = Text.readString(in);
	boolean excludeFieldIsNotNull = in.readBoolean();
	if (excludeFieldIsNotNull) {
	    int size = in.readInt();
	    excludeField = new ArrayList<Field>(size);
	    for (int i = 0; i < size; i++) {
		Field field = new Field();
		field.readFields(in);
		excludeField.add(field);
	    }
	}

	boolean parentSourceIsNotNull = in.readBoolean();
	if (parentSourceIsNotNull) {
	    int size = in.readInt();
	    parentSource = new ArrayList<Source>(size);
	    for (int i = 0; i < size; i++) {
		Source source = null;
		String className = Text.readString(in);
		if (File.class.getName().equals(className)) {
		    source = new File();
		    source.readFields(in);
		} else if (Table.class.getName().equals(className)) {
		    source = new Table();
		    source.readFields(in);
		}
		parentSource.add(source);
	    }
	}
    }

    @Override
    public void write(DataOutput out) throws IOException {
	Text.writeString(out, name);
	boolean excludeFieldIsNotNull = (excludeField != null);
	out.writeBoolean(excludeFieldIsNotNull);
	if (excludeFieldIsNotNull) {
	    out.writeInt(excludeField.size());
	    for (Field field : excludeField) {
		field.write(out);
	    }
	}
	boolean parentSourceIsNotNull = (parentSource != null);
	out.writeBoolean(parentSourceIsNotNull);
	if (parentSourceIsNotNull) {
	    out.writeInt(parentSource.size());
	    for (Source source : parentSource) {
		Text.writeString(out, source.getClass().getName());
		source.write(out);
	    }
	}
    }

}

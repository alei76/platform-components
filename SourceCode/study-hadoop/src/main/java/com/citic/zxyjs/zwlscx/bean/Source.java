package com.citic.zxyjs.zwlscx.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

/**
 * 资源基类
 * 
 * @author JoyoungZhang@gmail.com
 */
public class Source implements Writable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    private List<Field> fields;

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

    public List<Field> getFields() {
	return fields;
    }

    public void setFields(List<Field> fields) {
	this.fields = fields;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	this.id = Text.readString(in);
	this.name = Text.readString(in);
	int size = in.readInt();
	fields = new ArrayList<Field>(size);
	for (int i = 0; i < size; i++) {
	    Field field = new Field();
	    field.readFields(in);
	    fields.add(field);
	}
    }

    @Override
    public void write(DataOutput out) throws IOException {
	Text.writeString(out, id);
	Text.writeString(out, name);
	out.writeInt(fields.size());
	for (Field field : fields) {
	    field.write(out);
	}
    }

}

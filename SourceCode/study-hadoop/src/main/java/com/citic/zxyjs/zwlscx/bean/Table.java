package com.citic.zxyjs.zwlscx.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

/**
 * 表实体对象
 * 
 * @author JoyoungZhang@gmail.com
 */
public class Table extends Source implements Writable {

    private static final long serialVersionUID = 1L;

    private List<Field> field;
    private boolean hasZipperTable;
    private String zipperTableName;

    public List<Field> getField() {
	return field;
    }

    public void setField(List<Field> field) {
	this.field = field;
    }

    public boolean isHasZipperTable() {
	return hasZipperTable;
    }

    public void setHasZipperTable(boolean hasZipperTable) {
	this.hasZipperTable = hasZipperTable;
    }

    public String getZipperTableName() {
	return zipperTableName;
    }

    public void setZipperTableName(String zipperTableName) {
	this.zipperTableName = zipperTableName;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	super.readFields(in);
	boolean fieldIsNotNull = in.readBoolean();
	if (fieldIsNotNull) {
	    int size = in.readInt();
	    field = new ArrayList<Field>(size);
	    for (int i = 0; i < size; i++) {
		Field f = new Field();
		f.readFields(in);
		field.add(f);
	    }
	}
	this.hasZipperTable = in.readBoolean();
	boolean zipperTableNameIsNotNull = in.readBoolean();
	if (zipperTableNameIsNotNull) {
	    this.zipperTableName = Text.readString(in);
	}
    }

    @Override
    public void write(DataOutput out) throws IOException {
	super.write(out);
	boolean fieldIsNotNull = (field != null);
	out.writeBoolean(fieldIsNotNull);
	if (fieldIsNotNull) {
	    out.writeInt(field.size());
	    for (Field f : field) {
		f.write(out);
	    }
	}
	out.writeBoolean(hasZipperTable);
	boolean zipperTableNameIsNotNull = (zipperTableName != null);
	out.writeBoolean(zipperTableNameIsNotNull);
	if (zipperTableNameIsNotNull) {
	    Text.writeString(out, zipperTableName);
	}
    }

    private static class Field extends com.citic.zxyjs.zwlscx.bean.future.Field {

	private String sourceName;

	public String getSourceName() {
	    return sourceName;
	}

	public void setSourceName(String sourceName) {
	    this.sourceName = sourceName;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
	    super.readFields(in);
	    this.sourceName = Text.readString(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
	    super.write(out);
	    Text.writeString(out, sourceName);
	}
    }

}

package com.citic.zxyjs.zwlscx.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class Table extends Source implements Writable{

    private static final long serialVersionUID = 1L;

    private boolean hasZipperTable;
    private String zipperTableName;

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
	this.hasZipperTable = in.readBoolean();
	this.zipperTableName = Text.readString(in);
    }

    @Override
    public void write(DataOutput out) throws IOException {
	super.write(out);
	out.writeBoolean(hasZipperTable);
	Text.writeString(out, zipperTableName);
    }
    
}

package com.citic.zxyjs.zwlscx.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import com.citic.zxyjs.zwlscx.bean.future.Field;

public class Rule implements Writable {

    private static final long serialVersionUID = 1L;

    private Field leftField;
    private Field rightField;
    private String output;

    public Field getLeftField() {
	return leftField;
    }

    public void setLeftField(Field leftField) {
	this.leftField = leftField;
    }

    public Field getRightField() {
	return rightField;
    }

    public void setRightField(Field rightField) {
	this.rightField = rightField;
    }

    public String getOutput() {
	return output;
    }

    public void setOutput(String output) {
	this.output = output;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	Field left = new Field();
	left.readFields(in);
	this.leftField = left;
	Field right = new Field();
	right.readFields(in);
	this.rightField = right;
	boolean outputIsNotNull = in.readBoolean();
	if (outputIsNotNull) {
	    this.output = Text.readString(in);
	}
    }

    @Override
    public void write(DataOutput out) throws IOException {
	leftField.write(out);
	rightField.write(out);
	boolean outputIsNotNull = (output != null);
	out.writeBoolean(outputIsNotNull);
	if (outputIsNotNull) {
	    Text.writeString(out, output);
	}
    }

}

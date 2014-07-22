package com.jyz.study.hadoop.common;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class TextSoftSize implements WritableComparable<TextSoftSize> {

    private Text[] text;

    public TextSoftSize(Text[] text) {
	this.text = text;
    }

    public Text[] getText() {
        return text;
    }

    public void setText(Text[] text) {
        this.text = text;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	for(Text t : text){
	    t.readFields(in);
	}
    }

    @Override
    public void write(DataOutput out) throws IOException {
	for(Text t : text){
	    t.write(out);
	}
    }

    @Override
    public int compareTo(TextSoftSize o) {
	return 0;
    }
    
    
    
}

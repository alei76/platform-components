package com.citic.zxyjs.zwlscx.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableUtils;

/**
 * 任务
 * 
 * @author JoyoungZhang@gmail.com
 */
public class AppendTask extends Task {

    private Source from;
    private Source to;
    private String hfileOutput;

    public Source getFrom() {
	return from;
    }

    public void setFrom(Source from) {
	this.from = from;
    }

    public Source getTo() {
	return to;
    }

    public void setTo(Source to) {
	this.to = to;
    }

    public String getHfileOutput() {
	return hfileOutput;
    }

    public void setHfileOutput(String hfileOutput) {
	this.hfileOutput = hfileOutput;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	SourceType sourceType = WritableUtils.readEnum(in, SourceType.class);
	Source fromSource = new Source();
	Source toSource = new Source();

	switch (sourceType) {
	case FF:
	    fromSource = new File();
	    toSource = new File();
	    break;
	case FT:
	    fromSource = new File();
	    toSource = new Table();
	    break;
	case TF:
	    fromSource = new Table();
	    toSource = new File();
	    break;
	case TT:
	    fromSource = new Table();
	    toSource = new Table();
	    break;
	}

	fromSource.readFields(in);
	toSource.readFields(in);
	this.from = fromSource;
	this.to = toSource;
	this.hfileOutput = Text.readString(in);
    }

    @Override
    public void write(DataOutput out) throws IOException {
	WritableUtils.writeEnum(out, this.getSourceType());
	from.write(out);
	to.write(out);
	Text.writeString(out, hfileOutput);
    }

    @Override
    public String getIdentify() {
	return from.getName() + " " + to.getName();
    }

    @Override
    public SourceType getSourceType() {
	if (from instanceof File) {
	    if (to instanceof File) {
		return SourceType.FF;
	    } else {
		return SourceType.FT;
	    }
	} else {
	    if (to instanceof File) {
		return SourceType.TF;
	    } else {
		return SourceType.TT;
	    }
	}
    }

}

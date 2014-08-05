package com.citic.zxyjs.zwlscx.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableUtils;

/**
 * 任务
 * 
 * @author JoyoungZhang@gmail.com
 */
public class JoinTask extends Task {

    private Source leftSource;
    private Source rightSource;
    private List<Rule> joinRule;

    private Source output;
    private String errorOutput;

    public Source getLeftSource() {
	return leftSource;
    }

    public void setLeftSource(Source leftSource) {
	this.leftSource = leftSource;
    }

    public Source getRightSource() {
	return rightSource;
    }

    public void setRightSource(Source rightSource) {
	this.rightSource = rightSource;
    }

    public List<Rule> getJoinRule() {
	return joinRule;
    }

    public void setJoinRule(List<Rule> joinRule) {
	this.joinRule = joinRule;
    }

    public Source getOutput() {
	return output;
    }

    public void setOutput(Source output) {
	this.output = output;
    }

    public String getErrorOutput() {
	return errorOutput;
    }

    public void setErrorOutput(String errorOutput) {
	this.errorOutput = errorOutput;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
	super.readFields(in);
	SourceType sourceType = WritableUtils.readEnum(in, SourceType.class);
	Source leftsource = new Source();
	Source rightsource = new Source();

	switch (sourceType) {
	case FF:
	    leftsource = new File();
	    rightsource = new File();
	    break;
	case FT:
	    leftsource = new File();
	    rightsource = new Table();
	    break;
	case TF:
	    leftsource = new Table();
	    rightsource = new File();
	    break;
	case TT:
	    leftsource = new Table();
	    rightsource = new Table();
	    break;
	}

	leftsource.readFields(in);
	rightsource.readFields(in);
	leftSource = leftsource;
	rightSource = rightsource;

	int size = in.readInt();
	joinRule = new ArrayList<Rule>(size);
	for (int i = 0; i < size; i++) {
	    Rule rule = new Rule();
	    rule.readFields(in);
	    joinRule.add(rule);
	}
	String className = Text.readString(in);
	if (File.class.getName().equals(className)) {
	    File file = new File();
	    file.readFields(in);
	    this.output = file;
	} else if (Table.class.getName().equals(className)) {
	    Table table = new Table();
	    table.readFields(in);
	    this.output = table;
	}
	boolean errorOutputIsNotNull = in.readBoolean();
	if (errorOutputIsNotNull) {
	    this.errorOutput = Text.readString(in);
	}
    }

    @Override
    public void write(DataOutput out) throws IOException {
	super.write(out);
	WritableUtils.writeEnum(out, this.getSourceType());
	leftSource.write(out);
	rightSource.write(out);
	out.writeInt(joinRule.size());
	for (Rule rule : joinRule) {
	    rule.write(out);
	}
	Text.writeString(out, output.getClass().getName());
	output.write(out);
	boolean errorOutputIsNotNull = (errorOutput != null);
	out.writeBoolean(errorOutputIsNotNull);
	if (errorOutputIsNotNull) {
	    Text.writeString(out, errorOutput);
	}
    }

    @Override
    public String getIdentify() {
	return leftSource.getName() + " " + rightSource.getName();
    }

    @Override
    public SourceType getSourceType() {
	if (leftSource instanceof File) {
	    if (rightSource instanceof File) {
		return SourceType.FF;
	    } else {
		return SourceType.FT;
	    }
	} else {
	    if (rightSource instanceof File) {
		return SourceType.TF;
	    } else {
		return SourceType.TT;
	    }
	}
    }
}

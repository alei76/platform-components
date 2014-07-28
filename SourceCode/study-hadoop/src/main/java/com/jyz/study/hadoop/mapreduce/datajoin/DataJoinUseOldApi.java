package com.jyz.study.hadoop.mapreduce.datajoin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.contrib.utils.join.DataJoinMapperBase;
import org.apache.hadoop.contrib.utils.join.DataJoinReducerBase;
import org.apache.hadoop.contrib.utils.join.TaggedMapOutput;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.jyz.study.hadoop.common.ConfigurationUtils;
import com.jyz.study.hadoop.common.Utils;

public class DataJoinUseOldApi extends Configured implements Tool {
    public static class MapClass extends DataJoinMapperBase {
	protected Text generateInputTag(String inputFile) {
	    String datasource = inputFile.split("-")[0];
	    return new Text(datasource);
	}

	protected Text generateGroupKey(TaggedMapOutput aRecord) {
	    String line = ((Text) aRecord.getData()).toString();
	    String[] tokens = line.split(",");
	    String groupKey = tokens[0];
	    return new Text(groupKey);
	}

	protected TaggedMapOutput generateTaggedMapOutput(Object value) {
	    TaggedWritable retv = new TaggedWritable((Text) value);
	    retv.setTag(this.inputTag);
	    return retv;
	}
    }

    public static class Reduce extends DataJoinReducerBase {
	protected TaggedMapOutput combine(Object[] tags, Object[] values) {
	    String joinedStr = "";
	    TaggedWritable tw = (TaggedWritable) values[0];
	    String line = ((Text) tw.getData()).toString();
	    String[] tokens = line.split(",", 2);
	    joinedStr += tokens[1];

	    if (tags.length > 1) {
		for (int i = 1; i < tags.length; i++) {
		    joinedStr += ",";
		    tw = (TaggedWritable) values[i];
		    line = ((Text) tw.getData()).toString();
		    tokens = line.split(",", 2);
		    joinedStr += tokens[1];
		}
	    }
	    TaggedWritable retv = new TaggedWritable(new Text(joinedStr));
	    retv.setTag((Text) tags[0]);
	    return retv;
	}
    }

    public static class TaggedWritable extends TaggedMapOutput {
	private Writable data;

	public TaggedWritable() {
	    this.tag = new Text();
	}

	public TaggedWritable(Writable data) {
	    this.tag = new Text("");
	    this.data = data;
	}

	public Writable getData() {
	    return data;
	}

	public void setData(Writable data) {
	    this.data = data;
	}

	public void write(DataOutput out) throws IOException {
	    this.tag.write(out);
	    out.writeUTF(this.data.getClass().getName());
	    this.data.write(out);
	}

	public void readFields(DataInput in) throws IOException {
	    this.tag.readFields(in);
	    String dataClz = in.readUTF();
	    if (this.data == null || !this.data.getClass().getName().equals(dataClz)) {
		try {
		    this.data = (Writable) ReflectionUtils.newInstance(Class.forName(dataClz), null);
		} catch (ClassNotFoundException e) {
		    e.printStackTrace();
		}
	    }
	    this.data.readFields(in);
	}
    }

    public int run(String[] args) throws Exception {
	for (String string : args) {
	    System.out.println(string);
	}

	Configuration conf = ConfigurationUtils.getHadoopConfiguration();
	Utils.deleteIfExists(conf, "hdfs://200master:9000/user/root/output20");
	JobConf job = new JobConf(conf, DataJoinUseOldApi.class);
	Path in = new Path(args[0]);
	Path out = new Path(args[1]);
	FileInputFormat.setInputPaths(job, in);
	FileOutputFormat.setOutputPath(job, out);
	job.setJobName("DataJoin");
	job.setMapperClass(MapClass.class);
	job.setReducerClass(Reduce.class);
	job.setInputFormat(TextInputFormat.class);
	job.setOutputFormat(TextOutputFormat.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(TaggedWritable.class);
	// job.set("mapred.textoutputformat.separator", ",");
	// job.setLong("datajoin.maxNumOfValuesPerGroup", 2);
	JobClient.runJob(job);
	return 0;
    }

    public static void main(String[] args) throws Exception {
	String[] arg = { "hdfs://200master:9000/user/root/input2", "hdfs://200master:9000/user/root/output20" };
	int res = ToolRunner.run(new Configuration(), new DataJoinUseOldApi(), arg);
	System.exit(res);
    }
}
package HBaseIA.TwitBase.filters;

import HBaseIA.TwitBase.hbase.UsersDAO;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.filter.FilterBase;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PasswordStrengthFilter extends FilterBase {
	private int len;
	private boolean filterRow = false;

	public PasswordStrengthFilter() {
		super();
	}

	public PasswordStrengthFilter(int len) {
		this.len = len;
	}

	public ReturnCode filterKeyValue(KeyValue v) {
		if (Bytes.toString(v.getQualifier()).equals(Bytes.toString(UsersDAO.PASS_COL))) {//如果该列是密码列 ReturnCode.SKIP
			if (v.getValueLength() >= len) {//如果密码长度大于等于4 被过滤掉 filterRow=true（不出现在发送结果集合里）
				this.filterRow = true;
			}
			return ReturnCode.SKIP;
		}
		return ReturnCode.INCLUDE;
	}

	public boolean filterRow() {
		return this.filterRow;
	}

	public void reset() {
		this.filterRow = false;
	}

	public void write(DataOutput out) throws IOException {
		out.writeInt(len);
	}

	public void readFields(DataInput in) throws IOException {
		this.len = in.readInt();
	}
}

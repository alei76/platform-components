package HBaseIA.TwitBase.filters;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;

import utils.ZookeeperLocation;
import HBaseIA.TwitBase.hbase.UsersDAO;

public class PasswordStrengthFilterExample {

	public static void main(String[] args) throws IOException {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", ZookeeperLocation.LOCATION);

		HTablePool pool = new HTablePool(conf, 10);
		HTableInterface t = pool.getTable(UsersDAO.TABLE_NAME);
		Scan scan = new Scan();
		scan.addColumn(UsersDAO.INFO_FAM, UsersDAO.PASS_COL);
		scan.addColumn(UsersDAO.INFO_FAM, UsersDAO.NAME_COL);
		scan.addColumn(UsersDAO.INFO_FAM, UsersDAO.EMAIL_COL);
		Filter f = new PasswordStrengthFilter(4);
		scan.setFilter(f);
		ResultScanner rs = t.getScanner(scan);
		for (Result r : rs) {
			System.out.println(r);
		}
		pool.closeTablePool(UsersDAO.TABLE_NAME);
	}
}

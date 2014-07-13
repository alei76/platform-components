package com.jyz.study.hadoop.phoenix ;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jyz.study.hadoop.hbase.CoprocessorTest;


public class PhoenixIndexTest {
    
    static int i=12;
    static final String INDEX_TABLE_NAME = "USER";
    static final String NO_INDEX_TABLE_NAME = "NOINDEX_USER";
    static final String IDX_NAME = "USER_IDX";
    static  final String base = "abcdefghijklmnopqrstwzy";
    static String firstname = null;

    public static void main(String[] args) throws SQLException {
	drop(INDEX_TABLE_NAME);
//	create(INDEX_TABLE_NAME);
	
//	index(IDX_NAME, INDEX_TABLE_NAME);
//	query(INDEX_TABLE_NAME);
    }
    
    private static void create(String tableName) throws SQLException{
	String createddl = "create table " + tableName + " (id varchar primary key, firstname varchar, lastname varchar)";
//	String createddl = "create table " + tableName + " (id varchar primary key, firstname varchar, lastname varchar) IMMUTABLE_ROWS=true";
	Statement stmt = null;
	Connection con = DriverManager.getConnection("jdbc:phoenix:200master");
	stmt = con.createStatement();
	stmt.executeUpdate(createddl);
	for(int i=1;i<=12;i++){
	    firstname = CoprocessorTest.getRandomString(3);
	    System.out.println(stmt.executeUpdate("upsert into " + tableName + " values ('"+i+"', '"+firstname+"', '"+CoprocessorTest.getRandomString(3)+"')"));
	}
//	System.out.println(stmt.executeUpdate("DELETE FROM "+tableName+" WHERE id='1'"));
	stmt.close();
	con.commit();
	con.close();
    }
    
    private static void index(String idx_name, String tableName) throws SQLException{
	Statement stmt = null;
	Connection con = DriverManager.getConnection("jdbc:phoenix:200master");
	stmt = con.createStatement();
	stmt.executeUpdate("create index " + idx_name + " on " + tableName + " (firstname)");
	stmt.close();
	con.commit();
	con.close();
    }

    private static void query(String tableName) throws SQLException{
	ResultSet rset = null;
	Connection con = DriverManager.getConnection("jdbc:phoenix:200master");
	long begin = System.currentTimeMillis();
	PreparedStatement statement = con.prepareStatement("select * from " + tableName + " where firstname = '"+firstname+"'");
	rset = statement.executeQuery();
	while (rset.next()) {
		System.out.println(rset.getString("firstname"));
		System.out.println(rset.getString("lastname"));
	}
	long end = System.currentTimeMillis();
	System.out.println("query from " + tableName + " cost "+ (end - begin) + " s");
	statement.close();
	con.close();
    }
    
    private static void drop(String tableName) throws SQLException{
	Connection con = DriverManager.getConnection("jdbc:phoenix:200master");
	DatabaseMetaData meta = con.getMetaData(); 
	ResultSet rsTables = meta.getTables(null, null, tableName,  new String[] { "TABLE" });  
	while (rsTables.next()) {   
            System.out.println(rsTables.getString("TABLE_CAT") + "\t"  
                    + rsTables.getString("TABLE_SCHEM") + "\t"  
                    + rsTables.getString("TABLE_NAME") + "\t"  
                    + rsTables.getString("TABLE_TYPE"));   
        }   


//	String createddl = "drop table  IF EXISTS " + tableName;
//	Statement stmt = null;
//	Connection con = DriverManager.getConnection("jdbc:phoenix:200master");
//	stmt = con.createStatement();
//	stmt.executeUpdate(createddl);
//	stmt.close();
//	con.commit();
//	con.close();
    }
}

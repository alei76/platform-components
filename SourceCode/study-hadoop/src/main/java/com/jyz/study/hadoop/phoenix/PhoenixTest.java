package com.jyz.study.hadoop.phoenix ;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class PhoenixTest {
    
    static final String TABLE_NAME = "test8";

    public static void main(String[] args) throws SQLException {
	Statement stmt = null;
	ResultSet rset = null;
	
	Connection con = DriverManager.getConnection("jdbc:phoenix:200master");
	stmt = con.createStatement();
	
//	stmt.executeUpdate("create table " + TABLE_NAME + " (mykey integer not null primary key, mycolumn varchar) SALT_BUCKETS=16");
	stmt.executeUpdate("create table " + TABLE_NAME + " (mykey integer not null primary key, cf.mycolumn varchar, cy.youcolumn varchar)");
	stmt.executeUpdate("upsert into " + TABLE_NAME + " values (1, 'Hello', 'Hellos')");
	stmt.executeUpdate("upsert into " + TABLE_NAME + " values (2, 'World!', 'World!d')");
	stmt.executeUpdate("upsert into " + TABLE_NAME + " values (3, 'World!', 'World!f')");
	con.commit();
	
	PreparedStatement statement = con.prepareStatement("select * from " + TABLE_NAME);
	rset = statement.executeQuery();
	while (rset.next()) {
		System.out.println(rset.getString("mycolumn"));
		System.out.println(rset.getString("youcolumn"));
	}
	statement.close();
	con.close();
    }

}

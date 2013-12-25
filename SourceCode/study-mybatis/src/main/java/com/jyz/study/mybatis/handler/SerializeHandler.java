package com.jyz.study.mybatis.handler;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 *  扩展mybatis TypeHandler
 *	@author zhaoyong.zhang
 *	create time 2013-12-24
 */
public class SerializeHandler implements TypeHandler<Object> {

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		if (parameter == null) {
			ps.setString(i, null);
			return;
		}
		try {
		    byte[] ss = SerializeUtils.serializeObject(parameter);
		    ps.setBytes(i, ss);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		Object object = null;
	    try {
	    	object =  SerializeUtils.deserializeObject(rs.getBytes(columnName));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	    	e.printStackTrace();
	    }
	    return object;
	}

	@Override
	public Object getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Object object = null;
	    try {
	    	object =  SerializeUtils.deserializeObject(cs.getBytes(columnIndex));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	    	e.printStackTrace();
	    }
	    return object;
	}


	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		Object object = null;
	    try {
	    	object =  SerializeUtils.deserializeObject(rs.getBytes(columnIndex));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	    	e.printStackTrace();
	    }
	    return object;
	}

}

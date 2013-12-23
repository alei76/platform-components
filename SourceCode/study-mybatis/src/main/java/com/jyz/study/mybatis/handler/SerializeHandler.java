package com.jyz.study.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.jyz.study.mybatis.po.Content;

public class SerializeHandler implements TypeHandler<Object> {
	/**
	 * json数据和类名的分隔符号
	 * */
	private static final char SPLIT = '/';

	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		if (parameter == null) {
			ps.setString(i, null);
			return;
		}
		ps.setString(i, JsonUtils.getJsonStringForTransport(parameter));
	}

	public Object getResult(ResultSet rs, String columnName)
			throws SQLException {
		String json = rs.getString(columnName);
		return jsonToObject(json);
	}

	public Object getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		String json = cs.getString(columnIndex);
		return jsonToObject(json);
	}

	/**
	 * json 转换成对象
	 * */
	private Object jsonToObject(String json) {
//		if (json == null) {
//			return null;
//		}
//		int index = json.lastIndexOf(SPLIT);
//		if (index < 0) {
//			return null;
//		}
//		String key = json.substring(index + 1, json.length());
//		json = json.substring(0, index);
//		Class<?> cls = null;
//		try {
//			cls = Class.forName(key);
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("序列化成json时找不到指定的类", e);
//		}
		return JsonUtils.getObject(json, Content.class);
	}

	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		String json = rs.getString(columnIndex);
		return jsonToObject(json);
	}

}

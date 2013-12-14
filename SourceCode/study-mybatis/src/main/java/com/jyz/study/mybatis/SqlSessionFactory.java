package com.jyz.study.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author JoyoungZhang@gmail.com
 * 
 */
public class SqlSessionFactory {

	static final String CONFIG = "com/jyz/study/mybatis/mybatis_config.xml";
	
	private static org.apache.ibatis.session.SqlSessionFactory sqlSessionFactory = null;

	static {
		try {
			InputStream is = Resources.getResourceAsStream(CONFIG);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static org.apache.ibatis.session.SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
	
}

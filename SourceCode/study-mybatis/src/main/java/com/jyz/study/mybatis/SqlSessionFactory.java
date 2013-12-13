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

	private static org.apache.ibatis.session.SqlSessionFactory sqlSessionFactory = null;

	static {
		try {
			InputStream is = Resources.getResourceAsStream("com/jyz/study/mybatis/mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static org.apache.ibatis.session.SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
	
	
	public static org.apache.ibatis.session.SqlSessionFactory getSqlSessionFactoryWithOutXml(){
//		DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();
//		TransactionFactory transactionFactory = new JdbcTransactionFactory();
//		Environment environment = new Environment("development", transactionFactory, dataSource);
//		Configuration configuration = new Configuration(environment);
//		configuration.addMapper(BlogMapper.class);
//		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
	}
}

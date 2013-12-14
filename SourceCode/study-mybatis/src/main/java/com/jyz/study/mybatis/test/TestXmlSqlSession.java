package com.jyz.study.mybatis.test;

import junit.framework.TestCase;

import org.apache.ibatis.session.SqlSession;

import com.jyz.study.mybatis.SqlSessionFactory;
import com.jyz.study.mybatis.po.Blog;

/**
 * 把sql写在配置文件里面
 * 用SqlSession操作
 * @author JoyoungZhang@gmail.com
 * 
 */
public class TestXmlSqlSession extends TestCase{
	
    public void testInsert() { 
        SqlSession session = SqlSessionFactory.getSqlSessionFactory().openSession();  
        Blog blog = new Blog();  
        blog.setTitle("xxx");  
        blog.setContent("ssc");  
        blog.setOwner("e山水f");  
        session.insert("com.jyz.study.mybatis.mapper.BlogMapper.insert", blog);  
        session.commit();  
        session.close();  
    }  
    
    public void testSelect() {  
        SqlSession session = SqlSessionFactory.getSqlSessionFactory().openSession();  
        Blog blog = (Blog)session.selectOne("com.jyz.study.mybatis.mapper.BlogMapper.select", 10);  
        System.out.println(blog);  
        session.close();  
    }  
    
}

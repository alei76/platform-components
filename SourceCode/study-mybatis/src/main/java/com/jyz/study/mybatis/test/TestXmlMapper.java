package com.jyz.study.mybatis.test;

import junit.framework.TestCase;

import org.apache.ibatis.session.SqlSession;

import com.jyz.study.mybatis.SqlSessionFactory;
import com.jyz.study.mybatis.mapper.BlogMapper;
import com.jyz.study.mybatis.po.Blog;

/**
 * 把sql写在配置文件里面
 * 用Mapper操作
 * 需要mapper.xml, Mapper接口操作
 * 一般采取这个方式
 * @author JoyoungZhang@gmail.com
 * 
 */
public class TestXmlMapper extends TestCase{
	
    public void testInsert() { 
        SqlSession session = SqlSessionFactory.getSqlSessionFactory().openSession();  
        Blog blog = new Blog();  
        blog.setTitle("xx顶顶顶ddx");  
        blog.setContent("ss打的c");  
        blog.setOwner("e山水f是谁");  
        BlogMapper blogMapper = session.getMapper(BlogMapper.class);
        blogMapper.insert(blog);
        session.commit();  
        session.close();  
    }  
    
    public void testSelect() {  
        SqlSession session = SqlSessionFactory.getSqlSessionFactory().openSession();  
        BlogMapper blogMapper = session.getMapper(BlogMapper.class);
        Blog blog = blogMapper.select(1);  
        System.out.println(blog);  
        session.close();  
    }  
    
}

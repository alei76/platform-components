package com.jyz.study.mybatis.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.ibatis.session.SqlSession;

import com.jyz.study.mybatis.SqlSessionFactory;
import com.jyz.study.mybatis.mapper.BlogMapper;
import com.jyz.study.mybatis.po.Blog;
import com.jyz.study.mybatis.po.Content;

public class JSONHandlerTest extends TestCase {

	public void testInsert() { 
        SqlSession session = SqlSessionFactory.getSqlSessionFactory().openSession();  
        Blog blog = new Blog();  
        blog.setTitle("xx顶顶sdafsd顶ddx");  
        Content c = genetaterContent();
        blog.setContent(c);  
        blog.setOwner("e山水f是谁");  
        BlogMapper blogMapper = session.getMapper(BlogMapper.class);   
        blogMapper.insert2(blog);
        session.commit();  
        session.close();  
    }  
    
    public void testSelect() {  
        SqlSession session = SqlSessionFactory.getSqlSessionFactory().openSession();  
        BlogMapper blogMapper = session.getMapper(BlogMapper.class);
        Blog blog = blogMapper.select2(2);  
        System.out.println(blog);  
        session.close();  
    }  
    
    private Content genetaterContent(){
    	Content c =  new Content("cs2", "us1", 1);
    	Map<String, String> maps = new HashMap<String, String>();
    	maps.put("12", "1223");
    	maps.put("23", "3434");
    	maps.put("34", "4545");
    	c.setBlogs(maps);
    	return c;
    }
    
}

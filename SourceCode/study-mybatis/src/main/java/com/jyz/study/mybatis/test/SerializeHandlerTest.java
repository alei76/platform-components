package com.jyz.study.mybatis.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.ibatis.session.SqlSession;

import com.jyz.study.mybatis.SqlSessionFactory;
import com.jyz.study.mybatis.mapper.BlogMapper;
import com.jyz.study.mybatis.po.Blog;
import com.jyz.study.mybatis.po.Content;
import com.jyz.study.mybatis.po.Money;

public class SerializeHandlerTest extends TestCase {

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
        Blog blog = blogMapper.select2(11);  
        System.out.println(blog);  
        session.close();  
    }  
    
    private Content genetaterContent(){
    	Content c =  new Content("cs2", "us1", 1);
    	Map<String, List<Money>> maps = new HashMap<String, List<Money>>();
    	List<Money> ms = new ArrayList<Money>();
    	ms.add(new Money(10.00));
    	ms.add(new Money(20.00));
    	ms.add(new Money(30.00));
    	for(int i=0;i<4500;i++){
    	    maps.put("1山东省电饭锅电d玩玩玩玩玩玩玩玩玩玩玩玩玩玩玩玩玩玩玩玩玩玩玩玩哇饭锅" + i, ms);
    	}
    	c.setMoneys(maps);
    	return c;
    }
    
}

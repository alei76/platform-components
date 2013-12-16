package com.jyz.study.mybatis.test;

import junit.framework.TestCase;

import org.apache.ibatis.session.SqlSession;

import com.jyz.study.mybatis.SqlSessionFactory;
import com.jyz.study.mybatis.mapper.BlogMapper;
import com.jyz.study.mybatis.po.Blog;

/**
 * 看起来只需要Mapper接口
 * 但依然需要mapper.xml, Mapper接口
 * 把sql写此接口的方法注解上
 * org.apache.ibatis.binding.BindingException: Type interface com.jyz.study.mybatis.mapper.CopyOfBlogMapper is not known to the MapperRegistry.
	at org.apache.ibatis.binding.MapperRegistry.getMapper(MapperRegistry.java:42)
	at org.apache.ibatis.session.Configuration.getMapper(Configuration.java:639)
	at org.apache.ibatis.session.defaults.DefaultSqlSession.getMapper(DefaultSqlSession.java:218)
	at com.jyz.study.mybatis.test.TestMapper.testInsert(TestMapper.java:25)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
	at java.lang.reflect.Method.invoke(Method.java:597)
	at junit.framework.TestCase.runTest(TestCase.java:154)
	at junit.framework.TestCase.runBare(TestCase.java:127)
	at junit.framework.TestResult$1.protect(TestResult.java:106)
	at junit.framework.TestResult.runProtected(TestResult.java:124)
	at junit.framework.TestResult.run(TestResult.java:109)
	at junit.framework.TestCase.run(TestCase.java:118)
	at junit.framework.TestSuite.runTest(TestSuite.java:208)
	at junit.framework.TestSuite.run(TestSuite.java:203)
	at org.eclipse.jdt.internal.junit.runner.junit3.JUnit3TestReference.run(JUnit3TestReference.java:130)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:467)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:683)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:390)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:197)


 * @author JoyoungZhang@gmail.com
 * 
 */
public class TestMapper extends TestCase{
	
    public void testInsert() { 
        SqlSession session = SqlSessionFactory.getSqlSessionFactory().openSession();  
        Blog blog = new Blog();  
        blog.setTitle("xx顶顶sdafsd顶ddx");  
        blog.setContent("ss打的c");  
        blog.setOwner("e山水f是谁");  
        BlogMapper blogMapper = session.getMapper(BlogMapper.class);   
        blogMapper.insert2(blog);
        session.commit();  
        session.close();  
    }  
    
    public void testSelect() {  
        SqlSession session = SqlSessionFactory.getSqlSessionFactory().openSession();  
        BlogMapper blogMapper = session.getMapper(BlogMapper.class);
        Blog blog = blogMapper.select2(1);  
        System.out.println(blog);  
        session.close();  
    }  
    
}

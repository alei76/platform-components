package com.jyz.study.mybatis.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.jyz.study.mybatis.po.Blog;


/**
 * @author JoyoungZhang@gmail.com
 * 
 */
public interface BlogMapper {
	
	void insert(Blog blog);  
	
	Blog select(int id);
	
	@Insert("insert into blog(title,content,owner) values(#{title},#{content},#{owner})") 
	public void insert2(Blog blog);  
	
	@Select("select * from blog where id = #{id}")   
	public Blog select2(int id);   

	
}

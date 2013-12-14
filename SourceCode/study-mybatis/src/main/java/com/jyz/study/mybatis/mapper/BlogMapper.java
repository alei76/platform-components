package com.jyz.study.mybatis.mapper;

import com.jyz.study.mybatis.po.Blog;


/**
 * @author JoyoungZhang@gmail.com
 * 
 */
public interface BlogMapper {
	
	void insert(Blog blog);  

	Blog select(int id);
	
}

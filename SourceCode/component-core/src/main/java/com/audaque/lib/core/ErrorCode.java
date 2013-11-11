/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.audaque.lib.core;

/**
 * 类名称： ErrorCode
 * 描述：
 * 
 * 创建：   changzhu.lu, 2013-3-13 10:44:09
 *
 * 修订历史：（降序）
 * BugID/BacklogID  -  开发人员  日期
 * #  
 */
public interface ErrorCode {

    //执行成功
    public static final String EXECUTE_SUCCESSED = "000000";
     //用户名无效
    public static final String USER_NAME_INVALID = "000001";
    //密码无效
    public static final String PASSWORD_INVALID = "000002";
    
    public String getErrorCode();

    public String getMessage();
    
    @Override
    public String toString();
}

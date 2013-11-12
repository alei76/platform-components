/*
 * 
//*******************************************************     
//**版权所有 2011-2013 深圳市华傲数据技术有限公司
//**任何人或组织未经授权，不能修改代码     
//*******************************************************       
 */
package com.audaque.lib.core.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * 类名: AdqDataSourceUtils.java
 * 创建者: lu chang zhu<changzhu.lu@audaque.com>
 * 时间: 2013-8-1, 9:41:31
 */
public class AdqDataSourceUtils {

    /**
     * 测试数据源是否有效
     * @param dataSource
     * @throws SQLException 
     */
    public static void testDataSource(DataSource dataSource) throws SQLException {
        if (dataSource == null) {
            throw new NullPointerException("DataSorce is NULL.");
        }
        
        Connection conection = null;
        try {
            conection = dataSource.getConnection();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (conection != null) {
                conection.close();
            }
        }
    }

    /**
     * 测试连接是否有效
     * @param connection
     * @throws SQLException 
     */
    public static void testConnection(Connection connection) throws SQLException {
        if (connection == null) {
            throw new NullPointerException("Connection is NULL.");
        }
        
        try {
            connection.getMetaData();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            connection.close();
        }
    }
}

/**
 * 
 */
package com.wangyin.cds.common.db.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

/**
 * 数据库工具类
 * <p>检测摸个表是否存在等方法</p>
 * @author wymaoxiaoliang
 */
public class DbUtils {
    private static final String _SQL_SHOW_TBL   = "SHOW TABLES LIKE ?;";
    private static final String _SQL_SELECT_TBL = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = ? and TABLE_NAME = ?;";

    /**
     * 判断表是否存在
     * 
     * @param ds
     * @param tblName
     * @return
     * @throws SQLException
     */
    public static boolean isExistsTbl(DataSource ds, String tblName) throws SQLException {
        List<Object> parameters = new ArrayList<Object>();
        parameters.add(tblName);
        List<Map<String, Object>> list = JdbcUtils.executeQuery(ds, _SQL_SHOW_TBL, parameters);
        return (list != null && !list.isEmpty());
    }

    /**
     * 判断表在指定库是否存在
     * 
     * @param ds
     * @param dbName
     * @param tblName
     * @return
     * @throws SQLException
     */
    public static boolean isExistsTbl(DataSource ds, String dbName, String tblName) throws SQLException {
        List<Object> parameters = new ArrayList<Object>();
        parameters.add(dbName);
        parameters.add(tblName);
        List<Map<String, Object>> list = JdbcUtils.executeQuery(ds, _SQL_SELECT_TBL, parameters);
        return (list != null && !list.isEmpty());
    }
}

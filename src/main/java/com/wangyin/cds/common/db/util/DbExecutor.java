/**
 * 
 */
package com.wangyin.cds.common.db.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.StringUtils;

/**
 * DB SQL执行工具类
 * 
 * @author wymaoxiaoliang
 */
public class DbExecutor {
    private static Logger       logger      = LoggerFactory.getLogger(DbExecutor.class);

    /**
     * 默认查询结果最大行数
     */
    private static final int    MAX_ROW     = 20000;
    /**
     * 预览结果行数
     */
    private static final int    PREVIEW_ROW = 10000;

    //过滤 ‘  
    //ORACLE 注解 --  /**/  
    //关键字过滤 update ,delete   
    public static final String  reg         = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
    public static final Pattern sqlPattern  = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

    /**
     * 参数校验 (防止SQL注入)
     * 
     * @param sqlStr
     */
    public static boolean isValid(String sqlStr) {
        if (sqlStr != null && sqlStr != "" && sqlPattern.matcher(sqlStr).find()) {
            logger.error("未能能通过SQL过滤器校验：p=" + sqlStr);
            return false;
        }
        return true;
    }

    /**
     * 执行查询的到结果
     * 
     * @param conn
     * @param sql
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> executeQuery(Connection conn, String sql) throws SQLException {
        String limitSql = limitSQL(conn, sql);
        Statement st = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            st = conn.createStatement();
            rs = st.executeQuery(limitSql);
            List<String> columnLabelList = new ArrayList<String>();
            if (rs != null) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int cc = rsmd.getColumnCount();
                for (int i = 1; i <= cc; i++) {
                    columnLabelList.add(rsmd.getColumnName(i));
                }
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<String, Object>();
                    for (String columnLabel : columnLabelList) {
                        row.put(columnLabel, rs.getObject(columnLabel));
                    }
                    result.add(row);
                }
            }
        } finally {
            if (st != null) {
                st.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                closeConnection(conn);
            }
        }
        return result;
    }

    /**
     * 预览查询的到结果(限制输出行数)
     * 
     * @param conn
     * @param sql
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> executePreview(Connection conn, String sql) throws SQLException {
        String limitSql = limitSQL(conn, sql, PREVIEW_ROW);
        Statement st = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            st = conn.createStatement();
            rs = st.executeQuery(limitSql);
            List<String> columnLabelList = new ArrayList<String>();
            if (rs != null) {
                ResultSetMetaData rsmd = rs.getMetaData();
                Map<String, Object> columnLabelRow = new HashMap<String, Object>();
                int cc = rsmd.getColumnCount();
                for (int i = 1; i <= cc; i++) {
                    columnLabelList.add(rsmd.getColumnName(i));
                    columnLabelRow.put(String.valueOf(i), rsmd.getColumnName(i));
                }
                // 添加列名ROW
                result.add(columnLabelRow);
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<String, Object>();
                    for (String columnLabel : columnLabelList) {
                        row.put(columnLabel, rs.getObject(columnLabel));
                    }
                    result.add(row);
                }
            }
        } finally {
            if (st != null) {
                st.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                closeConnection(conn);
            }
        }
        return result;
    }

    /**
     * 查询的表列名称
     * 
     * @param conn
     * @param sql
     * @return
     * @throws SQLException
     */
    public static List<String> showColumnLabel(Connection conn, String sql) throws SQLException {
        String limitSql = limitSQL(conn, sql, PREVIEW_ROW);
        logger.info(limitSql);
        Statement st = null;
        ResultSet rs = null;
        List<String> columnLabelList = new ArrayList<String>();
        try {
            st = conn.createStatement();
            rs = st.executeQuery(limitSql);
            if (rs != null) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int cc = rsmd.getColumnCount();
                for (int i = 1; i <= cc; i++) {
                    columnLabelList.add(rsmd.getColumnName(i));
                }
            }
        } finally {
            if (st != null) {
                st.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                closeConnection(conn);
            }
        }
        return columnLabelList;
    }

    /**
     * 获取max_row行结果的SQL查询
     * 
     * @param conn
     * @param sql
     * @return
     * @throws SQLException
     */
    private static String limitSQL(Connection conn, String sql) throws SQLException {
        return limitSQL(conn, sql, MAX_ROW);
    }

    /**
     * 获取限定行数的SQL的语句
     * 
     * @param conn
     * @param sql
     * @return
     * @throws SQLException
     */
    private static String limitSQL(Connection conn, String sql, int limit) throws SQLException {
        if (sql == null || "".equals(sql)) {
            logger.error("SQL为空");
            return sql;
        }
        String _sql = sql.trim();
        if (_sql.endsWith(";")) {
            _sql = _sql.substring(0, _sql.length() - 1);
        } else {
            _sql = sql;
        }
        if (StringUtils.indexOfIgnoreCase(conn.getMetaData().getDriverName(), "mysql") >= 0) {
            // MySQL
            return "select _qv_.* from (" + _sql + ") _qv_ limit " + limit;
        } else if (StringUtils.indexOfIgnoreCase(conn.getMetaData().getDriverName(), "oracle") >= 0) {
            // Oracle
            return "select * from (" + _sql + ") where rownum <=" + limit;
        }
        logger.error("limitSQL错误：_sql=" + _sql + ",driver=" + conn.getMetaData().getDriverName());
        return null;
    }

    /**
     * 关闭数据库连接
     * 
     * @param conn 连接
     */
    public static void closeConnection(Connection conn) {
        try {
            if (null != conn && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("关闭数据库连接错误：" + e.getMessage(), e);
        }
    }

    /**
     * DB表信息预览
     * 
     * @param conn
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> viewDsTable(Connection conn, String tablePrefix) throws SQLException {
        if (StringUtils.indexOfIgnoreCase(conn.getMetaData().getDriverName(), "mysql") >= 0) {
            String sql = "SELECT TABLE_NAME,t.table_comment AS COMMENTS  FROM  INFORMATION_SCHEMA.TABLES t WHERE table_name LIKE '"
                         + tablePrefix + "%';";
            return executeQuery(conn, sql);
        } else if (StringUtils.indexOfIgnoreCase(conn.getMetaData().getDriverName(), "oracle") >= 0) {
            String sql = "SELECT TABLE_NAME, COMMENTS  FROM USER_TAB_COMMENTS  WHERE TABLE_NAME like '" + tablePrefix
                         + "%';";
            return executeQuery(conn, sql);
        }
        return null;
    }

    /**
     * DB表结构预览
     * 
     * @param conn
     * @param tableName
     * @return
     */
    public static List<Map<String, Object>> viewDsTableColumn(Connection conn, String tableName) throws SQLException {
        if (StringUtils.indexOfIgnoreCase(conn.getMetaData().getDriverName(), "mysql") >= 0) {
            String sql = "SELECT t.COLUMN_NAME AS COLUMN_NAME,t.DATA_TYPE AS DATA_TYPE,t.CHARACTER_MAXIMUM_LENGTH AS DATA_LENGTH,t.NUMERIC_SCALE AS DATA_SCALE ,"
                         + "t.IS_NULLABLE AS NULLABLE,t.COLUMN_COMMENT AS COMMENTS,t.COLUMN_DEFAULT AS DATA_DEFAULT FROM INFORMATION_SCHEMA.COLUMNS t WHERE table_name = '"
                         + tableName + "';";
            return executeQuery(conn, sql);
        } else if (StringUtils.indexOfIgnoreCase(conn.getMetaData().getDriverName(), "oracle") >= 0) {
            String sql = "select A.COLUMN_NAME as COLUMN_NAME,  A.DATA_TYPE as DATA_TYPE,  A.DATA_LENGTH as DATA_LENGTH, A.Data_Scale as DATA_SCALE, A.nullable as NULLABLE,  B.comments as COMMENTS,A.DATA_DEFAULT as DATA_DEFAULT "
                         + "from user_tab_columns A, user_col_comments B where A.Table_Name = B.Table_Name  and A.Column_Name = B.Column_Name  and A.Table_Name = '"
                         + tableName + "'";
            return executeQuery(conn, sql);
        }
        return null;
    }

}

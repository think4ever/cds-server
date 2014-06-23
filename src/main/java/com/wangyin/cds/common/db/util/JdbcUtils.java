/**
 * 
 */
package com.wangyin.cds.common.db.util;

import java.io.Closeable;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JDBC工具类
 * <p>提供简单通用的SQL执行方法</p>
 * 
 * @author wymaoxiaoliang<think4ever@sohu.com>
 */
public final class JdbcUtils {

    private static Logger logger = LoggerFactory.getLogger(JdbcUtils.class);

    /**
     * 通过数据源执行SQL更新操作
     * 
     * @param dataSource
     * @param sql
     * @param parameters
     * @return
     * @throws SQLException
     */
    public static int executeUpdate(DataSource dataSource, String sql, List<Object> parameters) throws SQLException {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            return executeUpdate(conn, sql, parameters);
        } finally {
            close(conn);
        }
    }

    /**
     * 通过conn执行更新操作
     * 
     * @param conn
     * @param sql
     * @param parameters
     * @return
     * @throws SQLException
     */
    public static int executeUpdate(Connection conn, String sql, List<Object> parameters) throws SQLException {
        PreparedStatement stmt = null;

        int updateCount;
        try {
            stmt = conn.prepareStatement(sql);

            setParameters(stmt, parameters);

            updateCount = stmt.executeUpdate();
        } finally {
            JdbcUtils.close(stmt);
        }

        return updateCount;
    }

    /**
     * 通过数据源执行SQL语句
     * 
     * @param dataSource
     * @param sql
     * @param parameters
     * @throws SQLException
     */
    public static void execute(DataSource dataSource, String sql, List<Object> parameters) throws SQLException {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            execute(conn, sql, parameters);
        } finally {
            close(conn);
        }
    }

    /**
     * 通过conn执行SQL语句
     * 
     * @param conn
     * @param sql
     * @param parameters
     * @throws SQLException
     */
    public static void execute(Connection conn, String sql, List<Object> parameters) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);

            setParameters(stmt, parameters);

            stmt.executeUpdate();
        } finally {
            JdbcUtils.close(stmt);
        }
    }

    /**
     * 获取一条记录的方法
     * <p>依赖于下面的executeQuery方法，注意泛型的使用</p>
     * 
     * @param dataSource
     * @param sql
     * @param parameters
     * @return　Map<String,Object>
     * @throws SQLException
     */
    public static Map<String, Object> executeQueryOneRow(DataSource dataSource, String sql, List<Object> parameters) throws SQLException {
        List<Map<String, Object>> rows = executeQuery(dataSource, sql, parameters);
        return rows.size() > 0 ? rows.get(0) : null;
    }

    /**
     * 获取一条记录的方法
     * 
     * <p>依赖于下面的executeQuery方法，注意泛型的使用</p>
     * 
     * @param conn
     * @param sql
     * @param parameters
     * @return　Map<String,Object>
     * @throws SQLException
     */
    public static Map<String, Object> executeQueryOneRow(Connection conn, String sql, List<Object> parameters) throws SQLException {
        List<Map<String, Object>> rows = executeQuery(conn, sql, parameters);
        return rows.size() > 0 ? rows.get(0) : null;
    }

    /**
     * 通过数据源执行SQL查询
     * <p>返回多条记录</p>
     * 
     * @param dataSource
     * @param sql
     * @param parameters
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> executeQuery(DataSource dataSource, String sql, List<Object> parameters) throws SQLException {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            return executeQuery(conn, sql, parameters);
        } finally {
            close(conn);
        }
    }

    /**
     * 通过conn执行更新操作
     * <p>返回多条记录</p>
     * 
     * @param conn
     * @param sql
     * @param parameters
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> executeQuery(Connection conn, String sql, List<Object> parameters) throws SQLException {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);

            setParameters(stmt, parameters);

            rs = stmt.executeQuery();

            ResultSetMetaData rsMeta = rs.getMetaData();

            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<String, Object>();

                for (int i = 0, size = rsMeta.getColumnCount(); i < size; ++i) {
                    String columName = rsMeta.getColumnLabel(i + 1);
                    Object value = rs.getObject(i + 1);
                    row.put(columName, value);
                }

                rows.add(row);
            }
        } finally {
            JdbcUtils.close(rs);
            JdbcUtils.close(stmt);
        }

        return rows;
    }
    
    /**
     * 通过conn查询表的列名
     * 
     * @param conn
     * @param sql
     * @param parameters
     * @return
     * @throws SQLException
     */
    public static List<String> queryColumnLabel(Connection conn, String tblName) throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        List<String> columns = new ArrayList<String>();
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM `" + tblName + "` LIMIT 0,1;");
            if (rs != null) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int cc = rsmd.getColumnCount();
                for (int i = 1; i <= cc; i++) {
                    columns.add(rsmd.getColumnName(i));
                }
            }
        } finally {
            JdbcUtils.close(rs);
            JdbcUtils.close(st);
        }

        return columns;
    }

    /**
     * 设置SQL参数
     * 
     * @param stmt
     * @param parameters
     * @throws SQLException
     */
    private static void setParameters(PreparedStatement stmt, List<Object> parameters) throws SQLException {
        if (parameters == null || parameters.isEmpty()) {
            return;
        }
        for (int i = 0, size = parameters.size(); i < size; ++i) {
            stmt.setObject(i + 1, parameters.get(i));
        }
    }

    /**
     * 关闭连接
     * 
     * @param x
     */
    public final static void close(Connection x) {
        if (x != null) {
            try {
                x.close();
            } catch (Exception e) {
                logger.error("close connection error", e);
            }
        }
    }

    /**
     * 关闭Statement
     * 
     * @param x
     */
    public final static void close(Statement x) {
        if (x != null) {
            try {
                x.close();
            } catch (Exception e) {
                logger.error("close statement error", e);
            }
        }
    }

    /**
     * 关闭结果集
     * 
     * @param x
     */
    public final static void close(ResultSet x) {
        if (x != null) {
            try {
                x.close();
            } catch (Exception e) {
                logger.error("close resultset error", e);
            }
        }
    }

    /**
     * 关闭其他资源
     * 
     * @param x
     */
    public final static void close(Closeable x) {
        if (x != null) {
            try {
                x.close();
            } catch (Exception e) {
                logger.error("close error", e);
            }
        }
    }

    /**
     * 在控制台打印结果集
     * 
     * @param rs
     * @throws SQLException
     */
    public final static void printResultSet(ResultSet rs) throws SQLException {
        printResultSet(rs, System.out);
    }

    /**
     * 在输出流中打印结果集
     * 
     * @param rs
     * @param out
     * @throws SQLException
     */
    private final static void printResultSet(ResultSet rs, PrintStream out) throws SQLException {
        ResultSetMetaData metadata = rs.getMetaData();
        int columnCount = metadata.getColumnCount();
        for (int columnIndex = 1; columnIndex <= columnCount; ++columnIndex) {
            if (columnIndex != 1) {
                out.print('\t');
            }
            out.print(metadata.getColumnName(columnIndex));
        }

        out.println();

        while (rs.next()) {

            for (int columnIndex = 1; columnIndex <= columnCount; ++columnIndex) {
                if (columnIndex != 1) {
                    out.print('\t');
                }

                int type = metadata.getColumnType(columnIndex);

                if (type == Types.VARCHAR || type == Types.CHAR || type == Types.NVARCHAR || type == Types.NCHAR) {
                    out.print(rs.getString(columnIndex));
                } else if (type == Types.DATE) {
                    Date date = rs.getDate(columnIndex);
                    if (rs.wasNull()) {
                        out.print("null");
                    } else {
                        out.print(date.toString());
                    }
                } else if (type == Types.BIT) {
                    boolean value = rs.getBoolean(columnIndex);
                    if (rs.wasNull()) {
                        out.print("null");
                    } else {
                        out.print(Boolean.toString(value));
                    }
                } else if (type == Types.BOOLEAN) {
                    boolean value = rs.getBoolean(columnIndex);
                    if (rs.wasNull()) {
                        out.print("null");
                    } else {
                        out.print(Boolean.toString(value));
                    }
                } else if (type == Types.TINYINT) {
                    byte value = rs.getByte(columnIndex);
                    if (rs.wasNull()) {
                        out.print("null");
                    } else {
                        out.print(Byte.toString(value));
                    }
                } else if (type == Types.SMALLINT) {
                    short value = rs.getShort(columnIndex);
                    if (rs.wasNull()) {
                        out.print("null");
                    } else {
                        out.print(Short.toString(value));
                    }
                } else if (type == Types.INTEGER) {
                    int value = rs.getInt(columnIndex);
                    if (rs.wasNull()) {
                        out.print("null");
                    } else {
                        out.print(Integer.toString(value));
                    }
                } else if (type == Types.BIGINT) {
                    long value = rs.getLong(columnIndex);
                    if (rs.wasNull()) {
                        out.print("null");
                    } else {
                        out.print(Long.toString(value));
                    }
                } else if (type == Types.TIMESTAMP) {
                    out.print(String.valueOf(rs.getTimestamp(columnIndex)));
                } else if (type == Types.DECIMAL) {
                    out.print(String.valueOf(rs.getBigDecimal(columnIndex)));
                } else if (type == Types.CLOB) {
                    out.print(String.valueOf(rs.getString(columnIndex)));
                } else if (type == Types.JAVA_OBJECT) {
                    Object objec = rs.getObject(columnIndex);

                    if (rs.wasNull()) {
                        out.print("null");
                    } else {
                        out.print(String.valueOf(objec));
                    }
                } else if (type == Types.LONGVARCHAR) {
                    Object objec = rs.getString(columnIndex);

                    if (rs.wasNull()) {
                        out.print("null");
                    } else {
                        out.print(String.valueOf(objec));
                    }
                } else {
                    Object objec = rs.getObject(columnIndex);

                    if (rs.wasNull()) {
                        out.print("null");
                    } else {
                        out.print(String.valueOf(objec));
                    }
                }
            }
            out.println();
        }
    }

    /**
     * DB中的类型 转换到 JAVA中的类型
     * 
     * @param sqlType
     * @return
     */
    public static String getTypeName(int sqlType) {
        switch (sqlType) {
            case Types.ARRAY:
                return "ARRAY";

            case Types.BIGINT:
                return "BIGINT";

            case Types.BINARY:
                return "BINARY";

            case Types.BIT:
                return "BIT";

            case Types.BLOB:
                return "BLOB";

            case Types.BOOLEAN:
                return "BOOLEAN";

            case Types.CHAR:
                return "CHAR";

            case Types.CLOB:
                return "CLOB";

            case Types.DATALINK:
                return "DATALINK";

            case Types.DATE:
                return "DATE";

            case Types.DECIMAL:
                return "DECIMAL";

            case Types.DISTINCT:
                return "DISTINCT";

            case Types.DOUBLE:
                return "DOUBLE";

            case Types.FLOAT:
                return "FLOAT";

            case Types.INTEGER:
                return "INTEGER";

            case Types.JAVA_OBJECT:
                return "JAVA_OBJECT";

            case Types.LONGNVARCHAR:
                return "LONGNVARCHAR";

            case Types.LONGVARBINARY:
                return "LONGVARBINARY";

            case Types.NCHAR:
                return "NCHAR";

            case Types.NCLOB:
                return "NCLOB";

            case Types.NULL:
                return "NULL";

            case Types.NUMERIC:
                return "NUMERIC";

            case Types.NVARCHAR:
                return "NVARCHAR";

            case Types.REAL:
                return "REAL";

            case Types.REF:
                return "REF";

            case Types.ROWID:
                return "ROWID";

            case Types.SMALLINT:
                return "SMALLINT";

            case Types.SQLXML:
                return "SQLXML";

            case Types.STRUCT:
                return "STRUCT";

            case Types.TIME:
                return "TIME";

            case Types.TIMESTAMP:
                return "TIMESTAMP";

            case Types.TINYINT:
                return "TINYINT";

            case Types.VARBINARY:
                return "VARBINARY";

            case Types.VARCHAR:
                return "VARCHAR";

            default:
                return "OTHER";

        }
    }
}

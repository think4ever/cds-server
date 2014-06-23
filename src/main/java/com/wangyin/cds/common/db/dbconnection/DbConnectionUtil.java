/**
 * Project: quickview.dal
 * 
 * File Created at 2013-1-23
 * $Id$
 * 
 * Copyright 1999-2100 Alibaba.com Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */
package com.wangyin.cds.common.db.dbconnection;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangyin.cds.service.dal.dataobject.DataSourceDO;

/**
 * 连接池配置熟悉构造器
 * 
 * @author wymaoxiaoliang
 */
public class DbConnectionUtil {
    private static Logger      logger     = LoggerFactory.getLogger(DbConnectionUtil.class);
    /**
     * 数据库类型
     */
    public static final String MYSQL      = "mysql";
    public static final String ORACLE     = "oracle";
    public static final String SQL_SERVER = "sqlserver";
    public static final String HSQL       = "hsql";
    public static final String DB2        = "db2";
    public static final String POSTGRESQL = "postgresql";
    public static final String SYBASE     = "sybase";
    public static final String DERBY      = "derby";
    public static final String HBASE      = "hbase";
    public static final String HIVE       = "hive";
    public static final String MOCK       = "mock";

    /**
     * 构建连接池属性对象
     * 
     * @param qvDsConfig
     * @return
     * @throws Exception 
     */
    public static Properties createProperties(DataSourceDO dsDo) {
        // 参数校验
        if (null == dsDo || StringUtils.isBlank(dsDo.getUrl()) || StringUtils.isBlank(dsDo.getPassWord())
            || StringUtils.isBlank(dsDo.getUserName()) || StringUtils.isBlank(dsDo.getPassWord())) {
            logger.error("参数配置有误,创建Properties失败,dsDo=" + dsDo);
            return null;
        }

        // 连通性测试
        try {
            DriverManager.getConnection(dsDo.getUrl(), dsDo.getUserName(), dsDo.getPassWord());
        } catch (SQLException e) {
            logger.error("测试连接失败,创建Properties失败,url=" + dsDo.getUrl(), e);
            return null;
        }

        Properties properties = new Properties();
        properties.setProperty("driverClassName", dsDo.getDriver());
        properties.setProperty("url", dsDo.getUrl());
        properties.setProperty("password", dsDo.getPassWord());
        properties.setProperty("username", dsDo.getUserName());

        properties.setProperty("validationQuery", "select 1 from dual");
        properties.setProperty("initialSize", "6");
        properties.setProperty("minIdle", "6");
        properties.setProperty("maxIdle", "10");
        properties.setProperty("maxActive", "10");
        properties.setProperty("maxWait", "3000");
        properties.setProperty("testOnBorrow", "false");
        properties.setProperty("testWhileIdle", "true");
        properties.setProperty("timeBetweenEvictionRunsMillis", "10000");
        properties.setProperty("numTestsPerEvictionRun", "3");
        properties.setProperty("minEvictableIdleTimeMillis", "300000");

        properties.setProperty("removeAbandoned", "false");
        properties.setProperty("removeAbandonedTimeout", "120");
        properties.setProperty("logAbandoned", "true");

        return properties;
    }

    /**
     * 构建连接池属性对象
     * 
     * @param qvDsConfig
     * @return
     * @throws Exception 
     */
    public static Properties createProperties(DataSourceConfig qvDsConfig) throws Exception {
        if (null == qvDsConfig) {
            return null;
        }

        String url = getConectUrl(qvDsConfig);
        String driverName = getDbDriverName(qvDsConfig.getDbType());
        if (StringUtils.isBlank(url) || StringUtils.isBlank(driverName)
            || StringUtils.isBlank(qvDsConfig.getUserName()) || StringUtils.isBlank(qvDsConfig.getPassword())) {
            return null;
        }

        Properties properties = new Properties();
        properties.setProperty("driverClassName", driverName);
        properties.setProperty("url", url);
        properties.setProperty("password", qvDsConfig.getPassword());
        properties.setProperty("username", qvDsConfig.getUserName());

        properties.setProperty("validationQuery", "select 1 from dual");
        properties.setProperty("initialSize", "6");
        properties.setProperty("minIdle", "6");
        properties.setProperty("maxIdle", "10");
        properties.setProperty("maxActive", "10");
        properties.setProperty("maxWait", "3000");
        properties.setProperty("testOnBorrow", "false");
        properties.setProperty("testWhileIdle", "true");
        properties.setProperty("timeBetweenEvictionRunsMillis", "10000");
        properties.setProperty("numTestsPerEvictionRun", "3");
        properties.setProperty("minEvictableIdleTimeMillis", "300000");

        properties.setProperty("removeAbandoned", "false");
        properties.setProperty("removeAbandonedTimeout", "120");
        properties.setProperty("logAbandoned", "true");

        return properties;
    }

    /**
     * 获取DB连接URL
     * 
     * @param qvDsConfig
     * @return
     */
    private static String getConectUrl(DataSourceConfig qvDsConfig) {
        if (null == qvDsConfig) {
            return null;
        }
        return getConectUrl(qvDsConfig.getIp(), qvDsConfig.getPort(), qvDsConfig.getDbName(), qvDsConfig.getDbType());
    }

    /**
     * 获取DB连接URL字符串
     * 
     * @param ip
     * @param port
     * @param dbName
     * @param dbType
     * @return
     */
    private static String getConectUrl(String ip, String port, String dbName, String dbType) {
        if (StringUtils.isBlank(ip) || StringUtils.isBlank(port) || StringUtils.isBlank(dbName)
            || StringUtils.isBlank(dbType)) {
            logger.error("参数有误,拼接数据库URL错误：ip=" + ip + ", port=" + port + ", dbName=" + dbName + ", dbType=" + dbType);
            return null;
        }
        // MySQL
        if (StringUtils.equals(MYSQL, dbType)) {
            StringBuilder urlBuilder = new StringBuilder("jdbc:mysql://");
            urlBuilder.append(ip);
            urlBuilder.append(':');
            urlBuilder.append(port);
            urlBuilder.append('/');
            urlBuilder.append(dbName);
            urlBuilder.append("?rewriteBatchedStatements=true");
            return urlBuilder.toString();
        }
        // Oracle
        if (StringUtils.equals(ORACLE, dbType)) {
            StringBuilder urlBuilder = new StringBuilder("jdbc:oracle:thin:@");
            urlBuilder.append(ip);
            urlBuilder.append(':');
            urlBuilder.append(port);
            urlBuilder.append(':');
            urlBuilder.append(dbName);
            return urlBuilder.toString();
        }
        logger.error("拼接数据库URL错误：ip=" + ip + ", port=" + port + ", dbName=" + dbName + ", dbType=" + dbType);
        return null;
    }

    /**
     * 验证DB数据源配置
     * 
     * @param ip
     * @param port
     * @param dbName
     * @param dbType
     * @param user
     * @param password
     * @return
     */
    public static boolean testDBSource(String ip, String port, String dbName, String dbType, String user,
                                       String password) {
        try {
            String url = getConectUrl(ip, port, dbName, dbType);
            DriverManager.getConnection(url, user, password);
            return true;
        } catch (SQLException e) {
            logger.error("验证DBConfig失败：" + e.getMessage(), e);
        }
        return false;
    }

    /**
     * 根据dbType获取DB连接驱动名
     * 
     * @param dbType
     * @return
     */
    private static String getDbDriverName(String dbType) throws SQLException {
        if (MYSQL.equals(dbType)) {
            return "com.mysql.jdbc.Driver";
        } else if (ORACLE.equals(dbType)) {
            return "oracle.jdbc.driver.OracleDriver";
        } else if (SQL_SERVER.equals(dbType)) {
            return "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        } else if (DERBY.equals(dbType)) {
            return "org.apache.derby.jdbc.EmbeddedDriver";
        } else if (SYBASE.equals(dbType)) {
            return "com.sybase.jdbc2.jdbc.SybDriver";
        } else if (MOCK.equals(dbType)) {
            return "com.alibaba.druid.mock.MockDriver";
        } else if (POSTGRESQL.equals(dbType)) {
            return "org.postgresql.Driver";
        } else if (HSQL.equals(dbType)) {
            return "org.hsqldb.jdbcDriver";
        } else if (DB2.equals(dbType)) {
            return "COM.ibm.db2.jdbc.app.DB2Driver";
        } else {
            throw new SQLException("unkow dbType : " + dbType);
        }
    }

    /**
     * 根据rawUrl获取JDBC驱动名称
     * 
     * @param rawUrl
     * @return
     * @throws SQLException
     */
    public static String getDriverClassName(String rawUrl) throws SQLException {
        if (rawUrl.startsWith("jdbc:derby:")) {
            return "org.apache.derby.jdbc.EmbeddedDriver";
        } else if (rawUrl.startsWith("jdbc:mysql:")) {
            return "com.mysql.jdbc.Driver";
        } else if (rawUrl.startsWith("jdbc:oracle:")) {
            return "oracle.jdbc.driver.OracleDriver";
        } else if (rawUrl.startsWith("jdbc:microsoft:")) {
            return "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        } else if (rawUrl.startsWith("jdbc:sqlserver:")) {
            return "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        } else if (rawUrl.startsWith("jdbc:sybase:Tds:")) {
            return "com.sybase.jdbc2.jdbc.SybDriver";
        } else if (rawUrl.startsWith("jdbc:jtds:")) {
            return "net.sourceforge.jtds.jdbc.Driver";
        } else if (rawUrl.startsWith("jdbc:fake:") || rawUrl.startsWith("jdbc:mock:")) {
            return "com.alibaba.druid.mock.MockDriver";
        } else if (rawUrl.startsWith("jdbc:postgresql:")) {
            return "org.postgresql.Driver";
        } else if (rawUrl.startsWith("jdbc:hsqldb:")) {
            return "org.hsqldb.jdbcDriver";
        } else if (rawUrl.startsWith("jdbc:db2:")) {
            return "COM.ibm.db2.jdbc.app.DB2Driver";
        } else if (rawUrl.startsWith("jdbc:sqlite:")) {
            return "org.sqlite.JDBC";
        } else if (rawUrl.startsWith("jdbc:ingres:")) {
            return "com.ingres.jdbc.IngresDriver";
        } else if (rawUrl.startsWith("jdbc:h2:")) {
            return "org.h2.Driver";
        } else if (rawUrl.startsWith("jdbc:mckoi:")) {
            return "com.mckoi.JDBCDriver";
        } else if (rawUrl.startsWith("jdbc:cloudscape:")) {
            return "COM.cloudscape.core.JDBCDriver";
        } else if (rawUrl.startsWith("jdbc:informix-sqli:")) {
            return "com.informix.jdbc.IfxDriver";
        } else if (rawUrl.startsWith("jdbc:timesten:")) {
            return "com.timesten.jdbc.TimesTenDriver";
        } else if (rawUrl.startsWith("jdbc:as400:")) {
            return "com.ibm.as400.access.AS400JDBCDriver";
        } else if (rawUrl.startsWith("jdbc:sapdb:")) {
            return "com.sap.dbtech.jdbc.DriverSapDB";
        } else if (rawUrl.startsWith("jdbc:JSQLConnect:")) {
            return "com.jnetdirect.jsql.JSQLDriver";
        } else if (rawUrl.startsWith("jdbc:JTurbo:")) {
            return "com.newatlanta.jturbo.driver.Driver";
        } else if (rawUrl.startsWith("jdbc:firebirdsql:")) {
            return "org.firebirdsql.jdbc.FBDriver";
        } else if (rawUrl.startsWith("jdbc:interbase:")) {
            return "interbase.interclient.Driver";
        } else if (rawUrl.startsWith("jdbc:pointbase:")) {
            return "com.pointbase.jdbc.jdbcUniversalDriver";
        } else if (rawUrl.startsWith("jdbc:edbc:")) {
            return "ca.edbc.jdbc.EdbcDriver";
        } else if (rawUrl.startsWith("jdbc:mimer:multi1:")) {
            return "com.mimer.jdbc.Driver";
        } else {
            throw new SQLException("unkow jdbc driver : " + rawUrl);
        }
    }

    /**
     * 根据rawUrl获取数据库类型
     * 
     * @param rawUrl
     * @return
     */
    public static String getDbType(String rawUrl) {
        if (rawUrl == null) {
            return null;
        }

        if (rawUrl.startsWith("jdbc:derby:")) {
            return DERBY;
        } else if (rawUrl.startsWith("jdbc:mysql:")) {
            return MYSQL;
        } else if (rawUrl.startsWith("jdbc:oracle:")) {
            return ORACLE;
        } else if (rawUrl.startsWith("jdbc:microsoft:")) {
            return SQL_SERVER;
        } else if (rawUrl.startsWith("jdbc:sybase:Tds:")) {
            return SYBASE;
        } else if (rawUrl.startsWith("jdbc:jtds:")) {
            return "jtds";
        } else if (rawUrl.startsWith("jdbc:fake:") || rawUrl.startsWith("jdbc:mock:")) {
            return MOCK;
        } else if (rawUrl.startsWith("jdbc:postgresql:")) {
            return POSTGRESQL;
        } else if (rawUrl.startsWith("jdbc:hsqldb:")) {
            return HSQL;
        } else if (rawUrl.startsWith("jdbc:db2:")) {
            return DB2;
        } else if (rawUrl.startsWith("jdbc:sqlite:")) {
            return "sqlite";
        } else if (rawUrl.startsWith("jdbc:ingres:")) {
            return "ingres";
        } else if (rawUrl.startsWith("jdbc:h2:")) {
            return "h2";
        } else if (rawUrl.startsWith("jdbc:mckoi:")) {
            return "mckoi";
        } else if (rawUrl.startsWith("jdbc:cloudscape:")) {
            return "cloudscape";
        } else if (rawUrl.startsWith("jdbc:informix-sqli:")) {
            return "informix";
        } else if (rawUrl.startsWith("jdbc:timesten:")) {
            return "timesten";
        } else if (rawUrl.startsWith("jdbc:as400:")) {
            return "as400";
        } else if (rawUrl.startsWith("jdbc:sapdb:")) {
            return "sapdb";
        } else if (rawUrl.startsWith("jdbc:JSQLConnect:")) {
            return "JSQLConnect";
        } else if (rawUrl.startsWith("jdbc:JTurbo:")) {
            return "JTurbo";
        } else if (rawUrl.startsWith("jdbc:firebirdsql:")) {
            return "firebirdsql";
        } else if (rawUrl.startsWith("jdbc:interbase:")) {
            return "interbase";
        } else if (rawUrl.startsWith("jdbc:pointbase:")) {
            return "pointbase";
        } else if (rawUrl.startsWith("jdbc:edbc:")) {
            return "edbc";
        } else if (rawUrl.startsWith("jdbc:mimer:multi1:")) {
            return "mimer";
        } else {
            return null;
        }
    }

    /**
     * Create数据库JDBC驱动类
     * 
     * @param driverClassName
     * @return
     * @throws SQLException
     */
    public static Driver createDriver(String driverClassName) throws SQLException {
        try {
            return (Driver) Class.forName(driverClassName).newInstance();
        } catch (IllegalAccessException e) {
            throw new SQLException(e.getMessage(), e);
        } catch (InstantiationException e) {
            throw new SQLException(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new SQLException(e.getMessage(), e);
        }
    }
}

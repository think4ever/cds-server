/**
 * 
 */
package com.wangyin.cds.common.db.dbconnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangyin.cds.service.dal.datainterface.DataSourceDAO;
import com.wangyin.cds.service.dal.dataobject.DataSourceDO;
import com.wangyin.cds.service.util.MybatisUtil;

/**
 * 数据库连接池工具类
 * <p>
 * 连接使用完只要简单地使用connection.close()就可把此连接返回到连接池中.
 * </p>
 * 
 * @author wymaoxiaoliang
 */
public class DataSourcePoolUtils {
    private static Logger                             logger    = LoggerFactory.getLogger(DataSourcePoolUtils.class);

    /* 数据源K/V */
    private final static Map<String, BasicDataSource> dsPoolMap = new Hashtable<String, BasicDataSource>();

    private static DataSourceDAO                      dsDao     = MybatisUtil.getMapper(DataSourceDAO.class);

    /**
     * 连接池dsPoolMap初始化;指定连接池为空时被调用. 
     * XXX 有改进空间 tmpCase
     */
    private static void init() {
        List<DataSourceDO> dsList = dsDao.getAllDataSourceList();
        if (null == dsList || dsList.isEmpty()) {
            logger.error("初始化连接池出错,dsList为空.");
            return;
        }

        // 清空旧的连接对象
        if (!dsPoolMap.isEmpty()) {
            for (Entry<String, BasicDataSource> entry : dsPoolMap.entrySet()) {
                BasicDataSource ds = entry.getValue();
                closeDataSource(ds);
            }
            dsPoolMap.clear();
        }

        // PUT连接池MAP
        for (DataSourceDO ds : dsList) {
            putDataSourceByDsDo(ds);
        }
    }

    /**
     * 创建数据源对应链接并PUT进dsPoolMap中
     * 
     * @param dsCfg
     */
    private static BasicDataSource putDataSourceByDsDo(DataSourceDO dsDo) {
        if (dsDo == null) {
            logger.error("创建数据源并PUT进dsPoolMap过程中出错,DataSourceConfig为空.");
            return null;
        }
        BasicDataSource ds = createDataSource(DbConnectionUtil.createProperties(dsDo));
        if (null != ds) {
            dsPoolMap.put(getDsPoolMapKey(dsDo), ds);
        }
        return ds;
    }

    /**
     * 创建数据源对应链接并PUT进dsPoolMap中
     * 
     * @param dsCfg
     */
    private static BasicDataSource putDataSourceByDsCfg(DataSourceConfig dsCfg) {
        if (dsCfg == null) {
            logger.error("创建数据源并PUT进dsPoolMap过程中出错,DataSourceConfig为空.");
            return null;
        }
        BasicDataSource ds = null;
        try {
            ds = createDataSource(DbConnectionUtil.createProperties(dsCfg));
        } catch (Exception e) {
            logger.error("创建数据源并PUT进dsPoolMap过程中异常," + dsCfg, e);
        }
        if (null != ds) {
            dsPoolMap.put(getDsPoolMapKey(dsCfg), ds);
        }
        return ds;
    }

    /**
     * 创建连接池BasicDataSource对象
     * 
     * @param propt
     * @return
     */
    private static BasicDataSource createDataSource(Properties propt) {
        if (null == propt) {
            logger.error("创建连接池失败,Properties为空!");
            return null;
        }
        try {
            return (BasicDataSource) BasicDataSourceFactory.createDataSource(propt);
        } catch (Exception e) {
            logger.error("创建连接池失败,Properties:" + propt, e);
            return null;
        }
    }

    /**
     * 关闭连接池
     * 
     * @param dataSource
     */
    public static void closeDataSource(BasicDataSource dataSource) {
        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (Exception e) {
                logger.error("关闭连接池出错", e);
            }
        }
    }
    
    /**
     * 根据数据源名称从连接池中获取数据源
     * 
     * @param dsName 数据源名称
     * @return
     * @throws SQLException
     */
    public static synchronized DataSource getDataSource(String dsName) throws SQLException {
        // dsPoolMap为空,则初始化dsPoolMap
        if (dsPoolMap.isEmpty()) {
            init();
        }
        DataSourceDO dsDo = dsDao.getDataSourceByName(dsName);
        // 获取对应的源
        String key = getDsPoolMapKey(dsDo);
        BasicDataSource dataSource = null;
        if (dsPoolMap.containsKey(key)) {
            dataSource = dsPoolMap.get(key);
        } else {
            // dsPoolMap没有对应的连接池,则尝试获取配置创建对应连接池.
            dataSource = putDataSourceByDsDo(dsDo);
        }
        if (null == dataSource) {
            logger.error("无对应连接池, 获取DB连接失败, 请检查配置, dsName: " + dsName);
            throw new SQLException("无对应连接池, 获取DB连接失败, 请检查配置, dsName: " + dsName);
        }
        logger.info("GET Connection success, DB-URL:" + dataSource.getUrl());
        return dataSource;
    }

    /**
     * 根据数据源名称从连接池中获取对应DB连接
     * 
     * @param dsName 数据源名称
     * @return
     * @throws SQLException
     */
    public static synchronized Connection getConnection(String dsName) throws SQLException {
        DataSource dataSource = getDataSource(dsName);
        return dataSource.getConnection();
    }

    /**
     * 获取DB连接
     * 
     * @param dsCfg 数据源配置
     * @return
     * @throws SQLException
     */
    public static synchronized Connection getConnection(DataSourceConfig dsCfg) throws SQLException {
        // dsPoolMap为空,则初始化dsPoolMap
        if (dsPoolMap.isEmpty()) {
            init();
        }
        // 获取对应的源
        String key = getDsPoolMapKey(dsCfg);
        BasicDataSource dataSource = null;
        if (dsPoolMap.containsKey(key)) {
            dataSource = dsPoolMap.get(key);
        } else {
            // dsPoolMap没有对应的连接池,则尝试获取配置创建对应连接池.
            dataSource = putDataSourceByDsCfg(dsCfg);
        }
        if (null == dataSource) {
            logger.error("无对应连接池, 获取DB连接失败, 请检查配置,IP: " + dsCfg.getIp() + ",PORT:" + dsCfg.getPort());
            throw new SQLException("无对应连接池, 获取DB连接失败, 请检查配置,IP: " + dsCfg.getIp() + ",PORT:" + dsCfg.getPort());
        }
        if (logger.isInfoEnabled()) {
            logger.info("GET Connection success, DB-URL:" + dataSource.getUrl());
        }
        return dataSource.getConnection();
    }

    /**
     * 获取DsPool的KEY
     * 
     * @param dsCfg
     * @return
     */
    private static String getDsPoolMapKey(DataSourceConfig dsCfg) {
        if (dsCfg == null || StringUtils.isBlank(dsCfg.getIp()) || StringUtils.isBlank(dsCfg.getPort())
            || StringUtils.isBlank(dsCfg.getDbName())) {
            return null;
        }

        return dsCfg.getIp() + "_" + dsCfg.getPort() + "_" + dsCfg.getDbName();
    }

    /**
     * 获取DsPool的KEY
     * 
     * @param dsDo
     * @return
     */
    private static String getDsPoolMapKey(DataSourceDO dsDo) {
        if (dsDo == null || StringUtils.isBlank(dsDo.getName())) {
            return null;
        }

        return dsDo.getName();
    }
}

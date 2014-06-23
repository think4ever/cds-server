/**
 * 
 */
package com.wangyin.cds.common.db.dbconnection;

import java.io.Serializable;

/**
 * 数据源配置
 * @author wymaoxiaoliang
 */
public class DataSourceConfig implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4789712069581835785L;

    /**数据类型(MySQL/Oracle/SQLServer)*/
    private String            dbType;
    /**数据库IP*/
    private String            ip;
    /**数据库端口*/
    private String            port;
    /**数据库名称*/
    private String            dbName;
    /**用户名*/
    private String            userName;
    /**密码[AESEncoder.decrypt(qvDsConfig.getPassword())]*/
    private String            password;
    /**备注*/
    private String            memo;

    /**
     * @return 数据类型
     */
    public String getDbType() {
        return this.dbType;
    }

    /**
     * @param name 数据类型
     */
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    /**
     * @param ip 数据库IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip 数据库IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @param port 端口
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port 端口
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @param dbName 数据库名称
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName 数据库名称
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * @return 用户名
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * @param name 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return 密码
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param name 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return 备注信息
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @return 备注信息
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

}
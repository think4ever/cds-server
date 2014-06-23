/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-${year} All Rights Reserved.
 */
package com.wangyin.cds.service.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 数据库基本信息视图
 * 
 * @author wymaoxiaoliang
 */
public class DbInfoExt extends Base {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6067745561428741163L;
    /**
     * 数据库ID
     */
    private Long              id;
    /**
     * 数据库地址
     */
    //@NotBlank(message = "数据库地址不能为空!")
    @Pattern(regexp = "^(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9]{1,2})(\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9]{1,2})){3}$", message = "数据库地址格式有误!")
    private String            ip;
    /**
     * 数据库端口
     */
    //@NotBlank(message = "数据库端口不能为空!")
    private String            port;
    /**
     * 数据库名称
     */
    @Size(min = 0, max = 30, message = "数据库名称长度不能大于30字!")
    private String            dbName;
    /**
     * 数据库Server ID
     */
    private int               serverId;
    /**
     * 数据库类型(Oralce或者MySql)
     */
    //@NotBlank(message = "数据库类型不能为空!")
    private String            dbType;
    /**
     * 数据库状态(只读,读写,只写)
     */
    //@NotBlank(message = "数据库状态不能为空!")
    private String            dbStatus;

    /**
     * 主/备类型
     */
    //@NotBlank(message = "主/备类型不能为空!")
    private String            masterOrSlave;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * @return the serverId
     */
    public int getServerId() {
        return serverId;
    }

    /**
     * @param serverId the serverId to set
     */
    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(String dbStatus) {
        this.dbStatus = dbStatus;
    }

    /**
     * @return the masterOrSlave
     */
    public String getMasterOrSlave() {
        return masterOrSlave;
    }

    /**
     * @param masterOrSlave the masterOrSlave to set
     */
    public void setMasterOrSlave(String masterOrSlave) {
        this.masterOrSlave = masterOrSlave;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DbInfoExt [id=");
        builder.append(id);
        builder.append(", ip=");
        builder.append(ip);
        builder.append(", port=");
        builder.append(port);
        builder.append(", dbName=");
        builder.append(dbName);
        builder.append(", serverId=");
        builder.append(serverId);
        builder.append(", dbType=");
        builder.append(dbType);
        builder.append(", dbStatus=");
        builder.append(dbStatus);
        builder.append(", masterOrSlave=");
        builder.append(masterOrSlave);
        builder.append("]");
        return builder.toString();
    }

}

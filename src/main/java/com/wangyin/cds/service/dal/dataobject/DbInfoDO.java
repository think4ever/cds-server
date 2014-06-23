/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.dal.dataobject;

import java.io.Serializable;

/**
 * 数据库信息DO对象
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/4/30 13:27 Exp $$
 */
public class DbInfoDO implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8305608303297348317L;
    /**
     * 数据库ID
     */
    private Long              id;
    /**
     * 数据库地址
     */
    private String            ip;
    /**
     * 数据库端口
     */
    private String            port;
    /**
     * 数据库名称
     */
    private String            dbName;
    /**
     * 数据库Server ID
     */
    private int               serverId;
    /**
     * 数据库类型(Oralce或者MySql)
     */
    private String            dbType;
    /**
     * 数据库状态(只读,读写,只写)
     */
    private String            dbStatus;
    /**
     * 创建人
     */
    private String            createBy;
    /**
     * 创建时间
     */
    private String            creationDate;
    /**
     * 最后修改人
     */
    private String            modifiedBy;
    /**
     * 最后修改时间
     */
    private String            modificationDate;
    /**
     * 删除状态(true已删除,false未删除)
     */
    private String            deleteStatus;

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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}

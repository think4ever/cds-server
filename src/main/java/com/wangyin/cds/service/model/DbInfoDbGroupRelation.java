/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model;


/**
 * 数据库基本信息和群组关系视图
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/4/29 16:37 Exp $$
 */
public class DbInfoDbGroupRelation extends Base {
    /**
     * 关系ID
     */
    private String id;

    /**
     * 群组ID
     */
    //@NotBlank(message = "群组ID不能为空!")
    private String dbGroupId;

    /**
     * 数据库信息ID
     */
    //@NotBlank(message = "数据库ID不能为空!")
    private String dbInfoId;

    /**
     * 主/备类型
     */
    //@NotBlank(message = "主/备类型不能为空!")
    private String masterOrSlave;

    /**
     * 数据库地址
     */
    private String ip;
    /**
     * 数据库端口
     */
    //@NotBlank(message = "数据库端口不能为空!")
    private String port;
    /**
     * 数据库名称
     */
    private String dbName;
    /**
     * 数据库类型(Oralce或者MySql)
     */
    private String dbType;
    /**
     * 数据库状态(只读,读写,只写)
     */
    private String dbStatus;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DbInfoDbGroupRelationVO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", dbGroupId='").append(dbGroupId).append('\'');
        sb.append(", dbInfoId='").append(dbInfoId).append('\'');
        sb.append(", masterOrSlave='").append(masterOrSlave).append('\'');
        sb.append(", ip=").append(ip);
        sb.append(", port=").append(port);
        sb.append(", dbName='").append(dbName).append('\'');
        sb.append(", dbType='").append(dbType).append('\'');
        sb.append(", dbStatus='").append(dbStatus).append('\'');
        sb.append(", createBy='").append(getCreateBy()).append('\'');
        sb.append(", creationDate='").append(getCreationDate()).append('\'');
        sb.append(", modifiedBy='").append(getModifiedBy()).append('\'');
        sb.append(", modificationDate='").append(getModificationDate()).append('\'');
        sb.append(", deleteStatus='").append(getDeleteStatus()).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDbGroupId() {
        return dbGroupId;
    }

    public void setDbGroupId(String dbGroupId) {
        this.dbGroupId = dbGroupId;
    }

    public String getDbInfoId() {
        return dbInfoId;
    }

    public void setDbInfoId(String dbInfoId) {
        this.dbInfoId = dbInfoId;
    }

    public String getMasterOrSlave() {
        return masterOrSlave;
    }

    public void setMasterOrSlave(String masterOrSlave) {
        this.masterOrSlave = masterOrSlave;
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
}

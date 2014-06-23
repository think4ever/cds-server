/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.dal.dataobject;

import java.io.Serializable;

/**
 * 数据库源DO对象
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/1 20:38 Exp $$
 */
public class DataSourceDO implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 378600297449344625L;
    /**
     * 数据源ID
     */
    private Long id;
    /**
     * 数据源名称,非空
     */
    private String name;
    /**
     *连接URL,非空
     */
    private String url;
    /**
     * jdbc驱动类,非空
     */
    private String driver;
    /**
     * 最小连接数,非空
     */
    private String minConnections;
    /**
     * 最大连接数,非空
     */
    private String maxConnections;
    /**
     * 用户名,非空
     */
    private String userName;
    /**
     * 密码,非空
     */
    private String passWord;
    /**
     * 等待超时时间,可空
     */
    private String checkoutTimeoutMilliSec;
    /**
     * 连接最大空闲时间,可空
     */
    private String idleTimeoutSec;
    /**
     * 检测连接是否可用的查询语句,可空
     */
    private String checkStatement;
    /**
     * 池中最大普通语句数,可空
     */
    private String maxStatements;
    /**
     * 池中最大预编译语句数,可空
     */
    private String maxPreStatements;
    /**
     * 记录SQL语句及执行时间,可空
     */
    private String printSql;
    /**
     * 记录除SQL语句及执行时间外的其他信息,可空
     */
    private String verbose;
    /**
     * 驱动支持的其他参数,多个参数用&分割,可空,可空
     */
    private String connectionInfo;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private String creationDate;
    /**
     * 最后修改人
     */
    private String modifiedBy;
    /**
     * 最后修改时间
     */
    private String modificationDate;
    /**
     * 删除状态(true已删除,false未删除)
     */
    private String deleteStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinConnections() {
        return minConnections;
    }

    public void setMinConnections(String minConnections) {
        this.minConnections = minConnections;
    }

    public String getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(String maxConnections) {
        this.maxConnections = maxConnections;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getCheckoutTimeoutMilliSec() {
        return checkoutTimeoutMilliSec;
    }

    public void setCheckoutTimeoutMilliSec(String checkoutTimeoutMilliSec) {
        this.checkoutTimeoutMilliSec = checkoutTimeoutMilliSec;
    }

    public String getIdleTimeoutSec() {
        return idleTimeoutSec;
    }

    public void setIdleTimeoutSec(String idleTimeoutSec) {
        this.idleTimeoutSec = idleTimeoutSec;
    }

    public String getCheckStatement() {
        return checkStatement;
    }

    public void setCheckStatement(String checkStatement) {
        this.checkStatement = checkStatement;
    }

    public String getMaxStatements() {
        return maxStatements;
    }

    public void setMaxStatements(String maxStatements) {
        this.maxStatements = maxStatements;
    }

    public String getMaxPreStatements() {
        return maxPreStatements;
    }

    public void setMaxPreStatements(String maxPreStatements) {
        this.maxPreStatements = maxPreStatements;
    }

    public String getPrintSql() {
        return printSql;
    }

    public void setPrintSql(String printSql) {
        this.printSql = printSql;
    }

    public String getVerbose() {
        return verbose;
    }

    public void setVerbose(String verbose) {
        this.verbose = verbose;
    }

    public String getConnectionInfo() {
        return connectionInfo;
    }

    public void setConnectionInfo(String connectionInfo) {
        this.connectionInfo = connectionInfo;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DataSourceDO [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", url=");
        builder.append(url);
        builder.append(", driver=");
        builder.append(driver);
        builder.append(", minConnections=");
        builder.append(minConnections);
        builder.append(", maxConnections=");
        builder.append(maxConnections);
        builder.append(", userName=");
        builder.append(userName);
        builder.append(", passWord=");
        builder.append("******");
        builder.append(", checkoutTimeoutMilliSec=");
        builder.append(checkoutTimeoutMilliSec);
        builder.append(", idleTimeoutSec=");
        builder.append(idleTimeoutSec);
        builder.append(", checkStatement=");
        builder.append(checkStatement);
        builder.append(", maxStatements=");
        builder.append(maxStatements);
        builder.append(", maxPreStatements=");
        builder.append(maxPreStatements);
        builder.append(", printSql=");
        builder.append(printSql);
        builder.append(", verbose=");
        builder.append(verbose);
        builder.append(", connectionInfo=");
        builder.append(connectionInfo);
        builder.append(", createBy=");
        builder.append(createBy);
        builder.append(", creationDate=");
        builder.append(creationDate);
        builder.append(", modifiedBy=");
        builder.append(modifiedBy);
        builder.append(", modificationDate=");
        builder.append(modificationDate);
        builder.append(", deleteStatus=");
        builder.append(deleteStatus);
        builder.append("]");
        return builder.toString();
    }
    
}

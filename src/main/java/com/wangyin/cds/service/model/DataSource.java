/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model;

import javax.validation.constraints.Size;

/**
 * 数据源基本信息视图
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/1 17:10 Exp $$
 */
public class DataSource extends Base {
    /**
     * 数据库ID
     */
    private Long id;
    /**
     *连接URL,非空
     */
    //@NotBlank(message = "连接URL不能为空!")
    @Size(min = 0, max = 400, message = "连接URL长度不能大于60字!")
    private String url;
    /**
     * jdbc驱动类,非空
     */
    //@NotBlank(message = "驱动不能为空!")
    @Size(min = 0, max = 400, message = "驱动长度不能大于60字!")
    private String driver;
    /**
     * 数据源名称,非空
     */
    //@NotBlank(message = "数据源名称不能为空!")
    @Size(min = 0, max = 60, message = "数据库名称长度不能大于60字!")
    private String name;
    /**
     * 最小连接数,非空
     */
    //@NotBlank(message = "最小连接数不能为空!")
    private String minConnections;
    /**
     * 最大连接数,非空
     */
    //@NotBlank(message = "最大连接数不能为空!")
    private String maxConnections;
    /**
     * 用户名,非空
     */
    //@NotBlank(message = "用户名不能为空!")
    @Size(min = 0, max = 40, message = "用户名称长度不能大于40字!")
    private String userName ;
    /**
     * 密码,非空
     */
    //@NotBlank(message = "密码不能为空!")
    @Size(min = 0, max = 30, message = "密码长度不能大于40字!")
    private String passWord ;
    /**
     * 等待超时时间,可空
     */
    private String checkoutTimeoutMilliSec ;
    /**
     * 连接最大空闲时间,可空
     */
    @Size(min = 0, max = 45, message = "连接最大空闲时间长度不能大于40字!")
    private String idleTimeoutSec ;
    /**
     * 检测连接是否可用的查询语句,可空
     */
    @Size(min = 0, max = 400, message = "检测连接是否可用的查询语句长度不能大于400字!")
    private String checkStatement ;
    /**
     * 池中最大普通语句数,可空
     */
    private String maxStatements ;
    /**
     * 池中最大预编译语句数,可空
     */
    private String maxPreStatements ;
    /**
     * 记录SQL语句及执行时间,可空
     */
    private String printSql ;
    /**
     * 记录除SQL语句及执行时间外的其他信息,可空
     */
    private String verbose ;
    /**
     * 驱动支持的其他参数,多个参数用&分割,可空,可空
     */
    @Size(min = 0, max = 400, message = "驱动支持的其他参数长度不能大于400字!")
    private String connectionInfo ;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DataSourceVO{");
        sb.append("id=").append(id);
        sb.append(", url='").append(url).append('\'');
        sb.append(", driver='").append(driver).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", minConnections='").append(minConnections).append('\'');
        sb.append(", maxConnections='").append(maxConnections).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", passWord='").append(passWord).append('\'');
        sb.append(", checkoutTimeoutMilliSec='").append(checkoutTimeoutMilliSec).append('\'');
        sb.append(", idleTimeoutSec='").append(idleTimeoutSec).append('\'');
        sb.append(", checkStatement='").append(checkStatement).append('\'');
        sb.append(", maxStatements='").append(maxStatements).append('\'');
        sb.append(", maxPreStatements='").append(maxPreStatements).append('\'');
        sb.append(", printSql='").append(printSql).append('\'');
        sb.append(", verbose='").append(verbose).append('\'');
        sb.append(", connectionInfo='").append(connectionInfo).append('\'');
        sb.append(", createBy='").append(getCreateBy()).append('\'');
        sb.append(", creationDate='").append(getCreationDate()).append('\'');
        sb.append(", modifiedBy='").append(getModifiedBy()).append('\'');
        sb.append(", modificationDate='").append(getModificationDate()).append('\'');
        sb.append(", deleteStatus='").append(getDeleteStatus()).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}

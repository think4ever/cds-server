/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 分库分表规则
 * 
 * @author wymaoxiaoliang
 */
@XmlRootElement(name = "depotsTableRulesInfo")
public class DepotsTableRulesExt extends Base {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4471439946364204003L;
    /**
     * 规则ID
     */
    private Long              id;
    /**
     * 规则类型
     */
    //@NotBlank(message = "规则类型不能为空!")
    private String            ruleType;

    /**
     * 群组ID
     */
    //@NotBlank(message = "群组ID不能为空!")
    private String            dbGroupId;
    
    /**
     * 切分键映射的Group信息
     */
    private DbGroup           dbGroup;

    /**
     * 表后缀
     */
    //@NotBlank(message = "表后缀不能为空!")
    private String            tableSuffix;

    /**
     * 规则上限
     */
    private String            upperLimit;

    /**
     * 规则下限
     */
    private String            lowerLimit;

    /**
     * 哈希值
     */
    private String            hashValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getDbGroupId() {
        return dbGroupId;
    }

    public void setDbGroupId(String dbGroupId) {
        this.dbGroupId = dbGroupId;
    }

    public String getTableSuffix() {
        return tableSuffix;
    }

    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }

    public String getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(String upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(String lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }

    /**
     * @return the dbGroup
     */
    public DbGroup getDbGroup() {
        return dbGroup;
    }

    /**
     * @param dbGroup the dbGroup to set
     */
    public void setDbGroup(DbGroup dbGroup) {
        this.dbGroup = dbGroup;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DepotsTableRulesExt [id=");
        builder.append(id);
        builder.append(", ruleType=");
        builder.append(ruleType);
        builder.append(", dbGroupId=");
        builder.append(dbGroupId);
        builder.append(", tableSuffix=");
        builder.append(tableSuffix);
        builder.append(", dbGroup=");
        builder.append(dbGroup);
        builder.append(", upperLimit=");
        builder.append(upperLimit);
        builder.append(", lowerLimit=");
        builder.append(lowerLimit);
        builder.append(", hashValue=");
        builder.append(hashValue);
        builder.append("]");
        return builder.toString();
    }

}

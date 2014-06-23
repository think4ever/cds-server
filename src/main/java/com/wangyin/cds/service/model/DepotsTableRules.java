/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model;


/**
 * 分库分表规则VO
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/5 14:33 Exp $$
 */
public class DepotsTableRules extends Base{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -59903091814784682L;
    /**
     * 规则ID
     */
    private Long id;
    /**
     * 规则类型
     */
    //@NotBlank(message = "规则类型不能为空!")
    private String ruleType;

    /**
     * 群组ID
     */
    //@NotBlank(message = "群组ID不能为空!")
    private String dbGroupId;

    /**
     * 表后缀
     */
    //@NotBlank(message = "表后缀不能为空!")
    private String tableSuffix;

    /**
     * 规则上限
     */
    private String upperLimit;

    /**
     * 规则下限
     */
    private String lowerLimit;

    /**
     * 哈希值
     */
    private String hashValue;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DepotsTableRules{");
        sb.append("id=").append(id);
        sb.append(", ruleType='").append(ruleType).append('\'');
        sb.append(", dbGroupId='").append(dbGroupId).append('\'');
        sb.append(", tableSuffix='").append(tableSuffix).append('\'');
        sb.append(", upperLimit='").append(upperLimit).append('\'');
        sb.append(", lowerLimit='").append(lowerLimit).append('\'');
        sb.append(", hashValue='").append(hashValue).append('\'');
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
}

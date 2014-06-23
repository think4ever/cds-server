/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model;

/**
 * 表字段VO
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/4 11:18 Exp $$
 */
public class Field {

    /**
     * 字段名称
     */
    private String field;
    /**
     * 字段类型
     */
    private String type;
    /**
     * 是否必填
     */
    private String isNull;
    /**
     * 默认值
     */
    private String defaultValue;


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}

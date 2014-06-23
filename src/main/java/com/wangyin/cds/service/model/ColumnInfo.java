/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 切分键映射表字段
 * 
 * @author wymaoxiaoliang
 */
@XmlRootElement(name = "columninfo")
public class ColumnInfo extends Base {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 143488029317169437L;
    /**
     * 主键ID
     */
    private Long              id;
    /**
     * 切分键ID
     */
    //@NotBlank(message = "切分键ID不能为空!")
    private String            splittingKeyId;

    /**
     * 表名
     */
    //@NotBlank(message = "表名不能为空!")
    private String            table;

    /**
     * 字段名
     */
    //@NotBlank(message = "字段名不能为空!")
    private String            column;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSplittingKeyId() {
        return splittingKeyId;
    }

    public void setSplittingKeyId(String splittingKeyId) {
        this.splittingKeyId = splittingKeyId;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ColumnInfo [id=");
        builder.append(id);
        builder.append(", splittingKeyId=");
        builder.append(splittingKeyId);
        builder.append(", table=");
        builder.append(table);
        builder.append(", column=");
        builder.append(column);
        builder.append("]");
        return builder.toString();
    }
}

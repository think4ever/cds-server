/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 切分键信息,包含切分键列表信息
 * 
 * @author wymaoxiaoliang
 */
@XmlRootElement(name = "splittingKeyExtInfo")
public class SplittingKeyExt extends Base {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3713607304933568373L;

    /**
     * 切分键ID
     */

    private Long              id;

    /**
     * 切分键名称
     */
    private String            splitName;

    /**
     * 切分键包含的表/字段信息
     */
    private List<ColumnInfo>  columnInfos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSplitName() {
        return splitName;
    }

    public void setSplitName(String splitName) {
        this.splitName = splitName;
    }

    /**
     * @return the columnInfos
     */
    public List<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    /**
     * @param columnInfos the columnInfos to set
     */
    public void setColumnInfos(List<ColumnInfo> columnInfos) {
        this.columnInfos = columnInfos;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SplittingKeyExt [id=");
        builder.append(id);
        builder.append(", splitName=");
        builder.append(splitName);
        builder.append(", columnInfos=");
        builder.append(columnInfos);
        builder.append("]");
        return builder.toString();
    }
}

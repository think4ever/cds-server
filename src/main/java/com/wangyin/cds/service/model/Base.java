/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model;

import java.io.Serializable;

/**
 * 页面视图基础类
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/4/26 16:25 Exp $$
 */
public class Base implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8160303791486750644L;
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
        builder.append("Base [createBy=");
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

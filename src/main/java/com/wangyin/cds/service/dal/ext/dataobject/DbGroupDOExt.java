/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.dal.ext.dataobject;

import java.io.Serializable;

/**
 * 数据库群组DO扩展对象
 * 
 * @author wymaoxiaoliang
 */
public class DbGroupDOExt implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5910026618478906494L;
    /**
     * 群组ID
     */
    private Long id;
    /**
     * 群组名称
     */
    private String groupName;
    /**
     * 群组类型(全局组,工作组),非空
     */
    private String groupType;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
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
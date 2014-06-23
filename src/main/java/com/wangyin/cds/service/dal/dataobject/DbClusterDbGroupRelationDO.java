/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.dal.dataobject;

import java.io.Serializable;

/**
 * 集群与数组群组关系DO对象
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/3 13:06 Exp $$
 */
public class DbClusterDbGroupRelationDO implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4977758028129467213L;
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 数据库集群ID,非空
     */
    private String dbClusterId;

    /**
     * 群组id,非空
     */
    private String dbGroupId;

    /**
     * 群组类型(全局组,工作组),非空
     */
    private String groupType;

    /**
     * 群组名称
     */
    private String groupName;
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

    public String getDbClusterId() {
        return dbClusterId;
    }

    public void setDbClusterId(String dbClusterId) {
        this.dbClusterId = dbClusterId;
    }

    public String getDbGroupId() {
        return dbGroupId;
    }

    public void setDbGroupId(String dbGroupId) {
        this.dbGroupId = dbGroupId;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

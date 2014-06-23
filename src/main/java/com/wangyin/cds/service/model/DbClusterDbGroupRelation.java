/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model;


/**
 * 集群与数据群组关系视图
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/3 12:58 Exp $$
 */
public class DbClusterDbGroupRelation extends Base{

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 数据库集群ID,非空
     */
    //@NotBlank(message = "集群名称不能为空!")
    private String dbClusterId;

    /**
     * 群组id,非空
     */
    //@NotBlank(message = "集群名称不能为空!")
    private String dbGroupId;

    /**
     * 群组类型(全局组,工作组),非空
     */
    //@NotBlank(message = "集群名称不能为空!")
    private String groupType;

    /**
     * 群组名称
     */
    private String groupName;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DbClusterDbGroupRelaction{");
        sb.append("id=").append(id);
        sb.append(", dbClusterId='").append(dbClusterId).append('\'');
        sb.append(", dbGroupId='").append(dbGroupId).append('\'');
        sb.append(", groupType='").append(groupType).append('\'');
        sb.append(", groupName='").append(groupName).append('\'');
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
}

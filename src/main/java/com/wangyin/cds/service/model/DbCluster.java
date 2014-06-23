/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model;

import java.util.List;

import javax.validation.constraints.Size;

/**
 * 数据库集群基本信息
 * 
 * @author wymaoxiaoliang
 */
public class DbCluster extends Base {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2034815971375389212L;

    /**
     * 数据库集群ID
     */
    private Long              id;

    /**
     * 数据库集群信息视图
     */
    //@NotBlank(message = "集群名称不能为空!")
    @Size(min = 0, max = 30, message = "集群名称长度不能大于30字!")
    private String            clusterName;

    /**
     * 数据库集群中包涵的群组列表
     */
    private List<DbGroupExt>  DbGroups;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DbClusterVO{");
        sb.append("id=").append(id);
        sb.append(", clusterName='").append(clusterName).append('\'');
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

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    /**
     * @return the dbGroups
     */
    public List<DbGroupExt> getDbGroups() {
        return DbGroups;
    }

    /**
     * @param dbGroups the dbGroups to set
     */
    public void setDbGroups(List<DbGroupExt> dbGroups) {
        DbGroups = dbGroups;
    }
}

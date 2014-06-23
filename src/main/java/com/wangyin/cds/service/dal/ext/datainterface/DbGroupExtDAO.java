/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.dal.ext.datainterface;

import java.util.List;

import com.wangyin.cds.service.model.DbGroupExt;

/**
 * 数据库群组信息扩展查询接口
 * 
 * @author wymaoxiaoliang
 */
public interface DbGroupExtDAO {

    /**
     * 根据群组名称,获取集群中"未删除"的所有数据库群组信息
     * 
     * @param clusterName 数据库集群(簇)名称
     * @return List<DbGroupExt>
     */
    public List<DbGroupExt> getDbGroupListByClusterName(String clusterName);
}

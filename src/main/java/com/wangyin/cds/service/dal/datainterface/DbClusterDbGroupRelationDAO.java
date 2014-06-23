/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.dal.datainterface;

import java.util.List;

import com.wangyin.cds.service.dal.dataobject.DbClusterDbGroupRelationDO;

/**
 * 集群与数组群组关系查询接口
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/3 13:08 Exp $$
 */
//@Repository
//@Transactional
public interface DbClusterDbGroupRelationDAO {

    /**
     * 获取所有数据库集群下所有群组信息
     *
     * @param dbClusterId
     * @return
     */
    public List<DbClusterDbGroupRelationDO> getDbClusterDbGroupRelationList(String dbClusterId);

    /**
     * 新增一条数据库集群群组关联记录
     *
     * @param dbClusterDbGroupRelationDO
     * @return
     */
    public int insertDbClusterDbGroupRelation(DbClusterDbGroupRelationDO dbClusterDbGroupRelationDO);

    /**
     * 删除一条数据库集群群组关联记录
     *
     * @param ids
     *
     */
    public int deleteDbClusterDbGroupRelation(List<String> ids);
}

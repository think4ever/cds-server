/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.dal.datainterface;

import java.util.List;

import com.wangyin.cds.service.dal.dataobject.DbInfoDbGroupRelationDO;

/**
 * 群组与数据库关系查询接口
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/3 19:59 Exp $$
 */
//@Repository
//@Transactional
public interface DbInfoDbGroupRelationDAO {

    /**
     * 获取群组下所有数据库信息
     *
     * @param dbGroupId
     * @return
     */
    public List<DbInfoDbGroupRelationDO> getDbInfoDbGroupRelationList(String dbGroupId);

    /**
     * 插入群组和数据库关系
     *
     * @param dbInfoDbGroupRelationDO
     * @return
     */
    public int insertDbInfoDbGroupRelation(DbInfoDbGroupRelationDO dbInfoDbGroupRelationDO);

    /**
     * 主备关系切换
     *<p>根据组ID和组名称更新主备关系<p>
     *
     * @param dbInfoDbGroupRelationDO
     * @return
     */
    public int dbMasterOrSlaveSwitch(DbInfoDbGroupRelationDO dbInfoDbGroupRelationDO);

    /**
     * 删除群组和数据库关系
     *
     * @param ids
     *
     */
    public int deleteDbInfoDbGroupRelation(List<String> ids);
}

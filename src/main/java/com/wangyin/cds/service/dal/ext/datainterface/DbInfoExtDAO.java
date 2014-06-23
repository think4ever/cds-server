/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.dal.ext.datainterface;

import java.util.List;

import com.wangyin.cds.service.model.DbInfoExt;

/**
 * 数据库扩展信息查询接口
 * 
 * @author wymaoxiaoliang
 */
public interface DbInfoExtDAO {

    /**
     * 根据群组名称,获取状态为"未删除"的所有数据库信息
     * 
     * @param groupName 群组名称
     * @return
     */
    public List<DbInfoExt> getDbInfoListByGroupName(String groupName);
    
    /**
     * 根据群组ID,获取状态为"未删除"的所有数据库信息
     * 
     * @param groupId 群组Id
     * @return
     */
    public List<DbInfoExt> getDbInfoListByGroupId(int groupId);
    
    /**
     * 根据主备类型,获取状态为"未删除"的所有数据库信息
     * 
     * @param  主备类型（Master or Slave）
     * @return
     */
    public List<DbInfoExt> getDbInfoListByType(String type);
    
    
}

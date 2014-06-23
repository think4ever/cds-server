/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.dal.datainterface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wangyin.cds.service.dal.dataobject.DbGroupDO;
import com.wangyin.cds.service.model.DbGroup;
import com.wangyin.cds.service.util.Paginator;

/**
 * 数据库群组信息查询接口
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/1 11:00 Exp $$
 */
//@Repository
//@Transactional
public interface DbGroupDAO {

    /**
     * 获取状态为"未删除"的所有数据库群组信息
     *
     * @return
     */
    public List<DbGroupDO> getDbGroupAllList();

    /**
     * 获取状态为"未删除"的所有数据库群组信息,带分页
     *
     * @return
     */
    public List<DbGroupDO> getDbGroupList(Paginator paginator);

    /**
     * 获取状态为"未删除"的所有数据库群组信息的总数
     *
     * @return
     */
    public Integer getDbGroupListCount();

    /**
     * 根据数据库id查询信息
     *
     * @param id
     * @return
     */
    public DbGroupDO getDbGroup(String id);
    
    /**
     * 根据数据库ID查询对应的群组列表
     *
     * @param serverId
     * @return
     */
    public List<DbGroupDO> getDbGroupListByDbinfoId(long serverId);

    /**
     * 组合条件查询不在选中数据库集中中的数据库群组信息,无分页
     *
     * @param dbGroupVO
     * @param clusterId
     * @return
     */
    public List<DbGroupDO> getDbGroupAndClassNoPaginator(@Param("dbGroupVO") DbGroup dbGroupVO, @Param("clusterId") String clusterId);

    /**
     * 根据组合条件查询数据库群组信息
     *
     * @param dbGroupVO
     * @param beginIndex
     * @param itemsPerPage
     * @return
     */
    public List<DbGroupDO> getDbGroupAndClass(@Param("dbGroupVO") DbGroup dbGroupVO, @Param("beginIndex") Integer beginIndex, @Param("itemsPerPage") Integer itemsPerPage);

    /**
     * 根据组合条件查询数据库群组信息总数
     *
     * @param dbGroupVO
     * @return
     */
    public Integer getDbGroupAndClassCount(@Param("dbGroupVO") DbGroup dbGroupVO);


    /**
     * 新增一条数据库群组信息
     *
     * @param dbGroupDO
     * @return
     */
    public int insertDbGroup(DbGroupDO dbGroupDO);

    /**
     * 更新一条数据库群组信息
     *
     * @param dbGroupDO
     * @return
     */
    public int updateDbGroup(DbGroupDO dbGroupDO);

    /**
     * 删除一条数据库群组信息
     *
     * @param id
     * @param modifiedBy
     */
    public int deleteDbGroup(@Param("id") String id, @Param("modifiedBy") String modifiedBy);
}

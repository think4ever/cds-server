/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.dal.datainterface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wangyin.cds.service.dal.dataobject.DataSourceDO;
import com.wangyin.cds.service.model.DataSource;
import com.wangyin.cds.service.util.Paginator;

/**
 * 数据库源信息查询接口
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/1 20:37 Exp $$
 */
//@Repository
//@Transactional
public interface DataSourceDAO {
    /**
     * 获取状态为"未删除"的所有数据源信息(分页)
     *
     * @return
     */
    public List<DataSourceDO> getDataSourceList(Paginator paginator);
    
    /**
     * 获取所有状态为"未删除"的数据源信息(不分页)
     *
     * @return
     */
    public List<DataSourceDO> getAllDataSourceList();

    /**
     * 获取状态为"未删除"的所有数据源信息的总数
     *
     * @return
     */
    public Integer getDataSourceListCount();

    /**
     * 根据数据库id查询信息
     *
     * @param id
     * @return
     */
    public DataSourceDO getDataSource(String id);
    
    /**
     * 根据数据源名称查询信息
     *
     * @param name
     * @return
     */
    public DataSourceDO getDataSourceByName(String name);

    /**
     * 根据组合条件查询数据源信息
     *
     * @param dataSourceVO
     * @param beginIndex
     * @param itemsPerPage
     * @return
     */
    public List<DataSourceDO> getDataSourceAndClass(@Param("dataSourceVO") DataSource dataSourceVO, @Param("beginIndex") Integer beginIndex, @Param("itemsPerPage") Integer itemsPerPage);

    /**
     * 根据组合条件查询数据源信息总数
     *
     * @param dataSourceVO
     * @return
     */
    public Integer getDataSourceAndClassCount(@Param("dataSourceVO") DataSource dataSourceVO);


    /**
     * 新增一条数据源信息
     *
     * @param dataSourceDO
     * @return
     */
    public int insertDataSource(DataSourceDO dataSourceDO);

    /**
     * 更新一条数据源信息
     *
     * @param dataSourceDO
     * @return
     */
    public int updateDataSource(DataSourceDO dataSourceDO);

    /**
     * 删除一条数据源信息
     *
     * @param id
     * @param modifiedBy
     */
    public int deleteDataSource(@Param("id") String id, @Param("modifiedBy") String modifiedBy);
}

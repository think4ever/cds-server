/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.dal.datainterface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wangyin.cds.service.dal.dataobject.SplittingKeyDO;
import com.wangyin.cds.service.model.SplittingKey;
import com.wangyin.cds.service.util.Paginator;

/**
 * 切分键信息查询接口
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/4 15:47 Exp $$
 */
//@Repository
//@Transactional
public interface SplittingKeyDAO {


    /**
     * 获取状态为"未删除"的所有数据库信息,无分页
     *
     * @return
     */
    public List<SplittingKeyDO> getSplittingKeyListNoPaginator();

    /**
     * 获取状态为"未删除"的所有数据库信息
     *
     * @param paginator
     * @return
     */
    public List<SplittingKeyDO> getSplittingKeyList(Paginator paginator);

    /**
     * 获取状态为"未删除"的所有数据库信息的总数
     *
     * @return
     */
    public Integer getSplittingKeyListCount();

    /**
     * 根据数据库id查询信息
     *
     * @param id
     * @return
     */
    public SplittingKeyDO getSplittingKey(String id);

    /**
     * 根据组合条件查询数据库信息
     * @param splittingKeyVO
     * @param beginIndex
     * @param itemsPerPage
     * @return
     */
    public List<SplittingKeyDO> getSplittingKeyAndClass(@Param("splittingKeyVO") SplittingKey splittingKeyVO, @Param("beginIndex") Integer beginIndex, @Param("itemsPerPage") Integer itemsPerPage);

    /**
     * 根据组合条件查询数据库信息总数
     *
     * @param splittingKeyVO
     * @return
     */
    public Integer getSplittingKeyAndClassCount(@Param("splittingKeyVO") SplittingKey splittingKeyVO);

    /**
     * 新增一条数据库信息
     *
     * @param splittingKeyDO
     * @return
     */
    public int insertSplittingKey(SplittingKeyDO splittingKeyDO);

    /**
     * 更新一条数据库信息
     *
     * @param splittingKeyDO
     * @return
     */
    public int updateSplittingKey(SplittingKeyDO splittingKeyDO);

    /**
     * 删除一条数据库信息
     *
     * @param id
     * @param modifiedBy
     */
    public int deleteSplittingKey(@Param("id")String id,@Param("modifiedBy")String modifiedBy);
}

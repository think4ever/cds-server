/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.dal.datainterface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wangyin.cds.service.dal.dataobject.DepotsTableRulesDO;
import com.wangyin.cds.service.model.DepotsTableRules;
import com.wangyin.cds.service.util.Paginator;

/**
 * 分库分表规则映射表字段信息查询接口
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/5 15:08 Exp $$
 */
//@Repository
//@Transactional
public interface DepotsTableRulesDAO {

    /**
     * 获取状态为"未删除"的所有分库分表规则信息,无分页
     *
     * @return
     */
    public List<DepotsTableRulesDO> getDepotsTableRulesListNoPaginator();

    /**
     * 获取状态为"未删除"的所有分库分表规则信息
     *
     * @param paginator
     * @return
     */
    public List<DepotsTableRulesDO> getDepotsTableRulesList(Paginator paginator);

    /**
     * 获取状态为"未删除"的所有分库分表规则信息的总数
     *
     * @return
     */
    public Integer getDepotsTableRulesListCount();

    /**
     * 根据分库分表规则id查询信息
     *
     * @param id
     * @return
     */
    public DepotsTableRulesDO getDepotsTableRules(String id);

    /**
     * 根据组合条件查询分库分表规则信息
     *
     * @param depotsTableRulesVO
     * @param beginIndex
     * @param itemsPerPage
     * @return
     */
    public List<DepotsTableRulesDO> getDepotsTableRulesAndClass(@Param("depotsTableRulesVO") DepotsTableRules depotsTableRulesVO, @Param("beginIndex") Integer beginIndex, @Param("itemsPerPage") Integer itemsPerPage);

    /**
     * 根据组合条件查询分库分表规则信息总数
     *
     * @param depotsTableRulesVO
     * @return
     */
    public Integer getDepotsTableRulesAndClassCount(@Param("depotsTableRulesVO") DepotsTableRules depotsTableRulesVO);


    /**
     * 新增一条分库分表规则信息
     *
     * @param depotsTableRulesDO
     * @return
     */
    public int insertDepotsTableRules(DepotsTableRulesDO depotsTableRulesDO);

    /**
     * 更新一条分库分表规则信息
     *
     * @param depotsTableRulesDO
     * @return
     */
    public int updateDepotsTableRules(DepotsTableRulesDO depotsTableRulesDO);

    /**
     * 删除一条分库分表规则信息
     *
     * @param id
     * @param modifiedBy
     */
    public int deleteDepotsTableRules(@Param("id") String id, @Param("modifiedBy") String modifiedBy);
}

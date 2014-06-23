/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.dal.ext.datainterface;

import java.util.List;

import com.wangyin.cds.service.model.DepotsTableRulesExt;

/**
 * 分库分表规则映射表字段扩展信息查询接口
 * 
 * @author wymaoxiaoliang
 */
public interface DepotsTableRulesExtDAO {

    /**
     * 根据分库分表规则ID,获得切分键映射的Group信息，包含规则
     * 
     * @param id
     * @return
     */
    public DepotsTableRulesExt getDepotsTableRuleById(int id);

    /**
     * 根据切分键ID,获得切分键映射的Group信息，包含规则
     * 
     * @param id
     * @return
     */
    public List<DepotsTableRulesExt> getDepotsTableRuleBySplitKeyId(int id);

    /**
     * 根据切分键名称,获得切分键映射的Group信息，包含规则
     * 
     * @param splitName
     * @return
     */
    public DepotsTableRulesExt getDepotsTableRuleBySplitName(String splitName);
}

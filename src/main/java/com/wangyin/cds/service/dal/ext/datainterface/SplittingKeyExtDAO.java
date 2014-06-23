/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.dal.ext.datainterface;

import java.util.List;

import com.wangyin.cds.service.model.SplittingKeyExt;

/**
 * 切分键扩展信息查询接口
 * 
 * @author wymaoxiaoliang
 */
public interface SplittingKeyExtDAO {

    /**
     * 获取状态为"未删除"的所有切分键信息列表
     * 
     * @return
     */
    public List<SplittingKeyExt> getAllSplittingKeyList();

}

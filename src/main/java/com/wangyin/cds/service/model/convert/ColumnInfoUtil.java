/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model.convert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.wangyin.cds.service.dal.dataobject.ColumnInfoDO;
import com.wangyin.cds.service.model.ColumnInfo;

/**
 * 切分键映射表字段信息工具类
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/4 17:28 Exp $$
 */
public class ColumnInfoUtil {

    /**
     * 转换器
     */
    private final static BeanCopier DO2VO_BC = BeanCopier.create(ColumnInfoDO.class,
            ColumnInfo.class, false);
    /**
     * 转换器
     */
    private final static BeanCopier VO2DO_BC = BeanCopier.create(ColumnInfo.class,
            ColumnInfoDO.class, false);

    /**
     * 把数据库返回的DO对象列表转换成页面展示VO对象列表
     *
     * @param columnInfoDOList
     * @return
     */
    public static List<ColumnInfo> convert2VOList(List<ColumnInfoDO> columnInfoDOList) {
        if (columnInfoDOList == null || columnInfoDOList.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<ColumnInfo> columnInfoVOList = new ArrayList<ColumnInfo>();
        for (ColumnInfoDO columnInfoDO : columnInfoDOList) {
            ColumnInfo columnInfoVO = new ColumnInfo();
            DO2VO_BC.copy(columnInfoDO, columnInfoVO, null);
            columnInfoVOList.add(columnInfoVO);
        }
        return columnInfoVOList;
    }

    /**
     * 把数据库返回的DO对象转换成页面展示VO对象
     *
     * @param columnInfoDO
     * @return
     */
    public static ColumnInfo convert2VO(ColumnInfoDO columnInfoDO) {
        if (columnInfoDO == null) {
            return null;
        }
        ColumnInfo columnInfoVO = new ColumnInfo();
        DO2VO_BC.copy(columnInfoDO, columnInfoVO, null);
        return columnInfoVO;
    }

    /**
     * 把页面上的DO对象转换成数据库存储的VO对象
     *
     * @param columnInfoVO
     * @return
     */
    public static ColumnInfoDO convert2DO(ColumnInfo columnInfoVO) {
        if (columnInfoVO == null) {
            return null;
        }
        ColumnInfoDO columnInfoDO = new ColumnInfoDO();
        VO2DO_BC.copy(columnInfoVO, columnInfoDO, null);
        return columnInfoDO;
    }

    /**
     * 校验参数是否等于1,是的话返回true,否的话返回false
     *
     * @param size
     * @return
     */
    public static boolean checkIsOne(int size) {
        if (size == 1) {
            return true;
        } else {
            return false;
        }
    }
}

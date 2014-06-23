/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.wangyin.cds.service.dal.dataobject.DataSourceDO;
import com.wangyin.cds.service.model.DataSource;

/**
 * 数据源工具类
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/1 21:10 Exp $$
 */
public class DataSourceUtil {
    /**
     * 转换器
     */
    private final static BeanCopier DO2VO_BC = BeanCopier.create(DataSourceDO.class,
            DataSource.class, false);
    /**
     * 转换器
     */
    private final static BeanCopier VO2DO_BC = BeanCopier.create(DataSource.class,
            DataSourceDO.class, false);


    /**
     * 把数据库返回的DO对象列表转换成页面展示VO对象列表
     *
     * @param dataSourceDOList
     * @return
     */
    public static List<DataSource> convert2VOList(List<DataSourceDO> dataSourceDOList) {
        List<DataSource> dataSourceVOList = new ArrayList<DataSource>();
        if (dataSourceDOList==null || dataSourceDOList.isEmpty()) {
            return dataSourceVOList;
        }
        for (DataSourceDO dataSourceDO : dataSourceDOList) {
            DataSource dataSourceVO = new DataSource();
            DO2VO_BC.copy(dataSourceDO, dataSourceVO, null);
            dataSourceVOList.add(dataSourceVO);
        }
        return dataSourceVOList;
    }

    /**
     * 把数据库返回的DO对象转换成页面展示VO对象
     *
     * @param dataSourceDO
     * @return
     */
    public static DataSource convert2VO(DataSourceDO dataSourceDO) {
        if (dataSourceDO == null) {
            return null;
        }
        DataSource dataSourceVO = new DataSource();
        DO2VO_BC.copy(dataSourceDO, dataSourceVO, null);
        return dataSourceVO;
    }

    /**
     * 把页面上的DO对象转换成数据库存储的VO对象
     *
     * @param dataSourceVO
     * @return
     */
    public static DataSourceDO convert2DO(DataSource dataSourceVO) {
        if (dataSourceVO == null) {
            return null;
        }
        DataSourceDO dataSourceDO = new DataSourceDO();
        VO2DO_BC.copy(dataSourceVO, dataSourceDO, null);
        return dataSourceDO;
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


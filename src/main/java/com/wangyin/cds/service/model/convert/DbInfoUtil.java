/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.wangyin.cds.service.dal.dataobject.DbInfoDO;
import com.wangyin.cds.service.model.DbInfo;

/**
 * 数据库信息工具类
 * 
 * @author 蒋鲁宾
 * @version v 0.1 2014/4/30 23:23 Exp $$
 */
public class DbInfoUtil {
    /**
     * 转换器
     */
    private final static BeanCopier DO2VO_BC = BeanCopier.create(DbInfoDO.class, DbInfo.class,
                                                     false);
    /**
     * 转换器
     */
    private final static BeanCopier VO2DO_BC = BeanCopier.create(DbInfo.class, DbInfoDO.class,
                                                     false);

    /**
     * 把数据库返回的DO对象列表转换成页面展示VO对象列表
     * 
     * @param dbInfoDOList
     * @return
     */
    public static List<DbInfo> convert2VOList(List<DbInfoDO> dbInfoDOList) {
        List<DbInfo> dbInfoVOList = new ArrayList<DbInfo>();
        if (dbInfoDOList==null || dbInfoDOList.isEmpty()) {
            return dbInfoVOList;
        }
        for (DbInfoDO dbInfoDO : dbInfoDOList) {
            DbInfo dbInfoVO = new DbInfo();
            DO2VO_BC.copy(dbInfoDO, dbInfoVO, null);
            dbInfoVOList.add(dbInfoVO);
        }
        return dbInfoVOList;
    }

    /**
     * 把数据库返回的DO对象转换成页面展示VO对象
     * 
     * @param dbInfoDO
     * @return
     */
    public static DbInfo convert2VO(DbInfoDO dbInfoDO) {
        if (dbInfoDO == null) {
            return null;
        }
        DbInfo dbInfoVO = new DbInfo();
        DO2VO_BC.copy(dbInfoDO, dbInfoVO, null);
        return dbInfoVO;
    }

    /**
     * 把页面上的DO对象转换成数据库存储的VO对象
     * 
     * @param dbInfoVO
     * @return
     */
    public static DbInfoDO convert2DO(DbInfo dbInfoVO) {
        if (dbInfoVO == null) {
            return null;
        }
        DbInfoDO dbInfoDO = new DbInfoDO();
        VO2DO_BC.copy(dbInfoVO, dbInfoDO, null);
        return dbInfoDO;
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

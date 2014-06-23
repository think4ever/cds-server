/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.wangyin.cds.service.dal.dataobject.DbClusterDO;
import com.wangyin.cds.service.model.DbCluster;

/**
 * 数据库集群信息工具类
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/1 16:33 Exp $$
 */
public class DbClusterUtil {
    /**
     * 转换器
     */
    private final static BeanCopier DO2VO_BC = BeanCopier.create(DbClusterDO.class,
            DbCluster.class, false);
    /**
     * 转换器
     */
    private final static BeanCopier VO2DO_BC = BeanCopier.create(DbCluster.class,
            DbClusterDO.class, false);


    /**
     * 把数据库返回的DO对象列表转换成页面展示VO对象列表
     *
     * @param dbClusterDOList
     * @return
     */
    public static List<DbCluster> convert2VOList(List<DbClusterDO> dbClusterDOList) {
        List<DbCluster> dbClusterVOList = new ArrayList<DbCluster>();
        if (dbClusterDOList==null || dbClusterDOList.isEmpty()) {
            return dbClusterVOList;
        }
        for (DbClusterDO dbClusterDO : dbClusterDOList) {
            DbCluster dbClusterVO = new DbCluster();
            DO2VO_BC.copy(dbClusterDO, dbClusterVO, null);
            dbClusterVOList.add(dbClusterVO);
        }
        return dbClusterVOList;
    }

    /**
     * 把数据库返回的DO对象转换成页面展示VO对象
     *
     * @param dbClusterDO
     * @return
     */
    public static DbCluster convert2VO(DbClusterDO dbClusterDO) {
        if (dbClusterDO == null) {
            return null;
        }
        DbCluster dbClusterVO = new DbCluster();
        DO2VO_BC.copy(dbClusterDO, dbClusterVO, null);
        return dbClusterVO;
    }

    /**
     * 把页面上的DO对象转换成数据库存储的VO对象
     *
     * @param dbClusterVO
     * @return
     */
    public static DbClusterDO convert2DO(DbCluster dbClusterVO) {
        if (dbClusterVO == null) {
            return null;
        }
        DbClusterDO dbClusterDO = new DbClusterDO();
        VO2DO_BC.copy(dbClusterVO, dbClusterDO, null);
        return dbClusterDO;
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

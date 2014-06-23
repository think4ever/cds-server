/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.wangyin.cds.service.dal.dataobject.DbClusterDbGroupRelationDO;
import com.wangyin.cds.service.model.DbClusterDbGroupRelation;

/**
 * 数据库集群工具类
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/1 16:33 Exp $$
 */
public class DbClusterDbGroupRelationUtil {
    /**
     * 转换器
     */
    private final static BeanCopier DO2VO_BC = BeanCopier.create(DbClusterDbGroupRelationDO.class,
            DbClusterDbGroupRelation.class, false);
    /**
     * 转换器
     */
    private final static BeanCopier VO2DO_BC = BeanCopier.create(DbClusterDbGroupRelation.class,
            DbClusterDbGroupRelationDO.class, false);


    /**
     * 把数据库返回的DO对象列表转换成页面展示VO对象列表
     *
     * @param dbClusterDbGroupRelationDOList
     * @return
     */
    public static List<DbClusterDbGroupRelation> convert2VOList(List<DbClusterDbGroupRelationDO> dbClusterDbGroupRelationDOList) {
        List<DbClusterDbGroupRelation> dbClusterDbGroupRelationVOList = new ArrayList<DbClusterDbGroupRelation>();
        if (dbClusterDbGroupRelationDOList==null || dbClusterDbGroupRelationDOList.isEmpty()) {
            return dbClusterDbGroupRelationVOList;
        }
        for (DbClusterDbGroupRelationDO dbClusterDbGroupRelationDO : dbClusterDbGroupRelationDOList) {
            DbClusterDbGroupRelation dbClusterDbGroupRelationVO = new DbClusterDbGroupRelation();
            DO2VO_BC.copy(dbClusterDbGroupRelationDO, dbClusterDbGroupRelationVO, null);
            dbClusterDbGroupRelationVOList.add(dbClusterDbGroupRelationVO);
        }
        return dbClusterDbGroupRelationVOList;
    }

    /**
     * 把数据库返回的DO对象转换成页面展示VO对象
     *
     * @param dbClusterDbGroupRelationDO
     * @return
     */
    public static DbClusterDbGroupRelation convert2VO(DbClusterDbGroupRelationDO dbClusterDbGroupRelationDO) {
        if (dbClusterDbGroupRelationDO == null) {
            return null;
        }
        DbClusterDbGroupRelation dbClusterDbGroupRelationVO = new DbClusterDbGroupRelation();
        DO2VO_BC.copy(dbClusterDbGroupRelationDO, dbClusterDbGroupRelationVO, null);
        return dbClusterDbGroupRelationVO;
    }

    /**
     * 把页面上的DO对象转换成数据库存储的VO对象
     *
     * @param dbClusterDbGroupRelationVO
     * @return
     */
    public static DbClusterDbGroupRelationDO convert2DO(DbClusterDbGroupRelation dbClusterDbGroupRelationVO) {
        if (dbClusterDbGroupRelationVO == null) {
            return null;
        }
        DbClusterDbGroupRelationDO dbClusterDbGroupRelationDO = new DbClusterDbGroupRelationDO();
        VO2DO_BC.copy(dbClusterDbGroupRelationVO, dbClusterDbGroupRelationDO, null);
        return dbClusterDbGroupRelationDO;
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

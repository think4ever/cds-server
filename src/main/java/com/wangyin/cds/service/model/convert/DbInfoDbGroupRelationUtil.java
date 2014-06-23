/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.wangyin.cds.service.dal.dataobject.DbInfoDbGroupRelationDO;
import com.wangyin.cds.service.model.DbInfoDbGroupRelation;

/**
 * 群组与数据库关系工具类
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/3 20:22 Exp $$
 */
public class DbInfoDbGroupRelationUtil {
    /**
     * 转换器
     */
    private final static BeanCopier DO2VO_BC = BeanCopier.create(DbInfoDbGroupRelationDO.class,
            DbInfoDbGroupRelation.class, false);
    /**
     * 转换器
     */
    private final static BeanCopier VO2DO_BC = BeanCopier.create(DbInfoDbGroupRelation.class,
            DbInfoDbGroupRelationDO.class, false);


    /**
     * 把数据库返回的DO对象列表转换成页面展示VO对象列表
     *
     * @param dbDbInfoDbGroupRelationDOList
     * @return
     */
    public static List<DbInfoDbGroupRelation> convert2VOList(List<DbInfoDbGroupRelationDO> dbDbInfoDbGroupRelationDOList) {
        List<DbInfoDbGroupRelation> dbDbInfoDbGroupRelationVOList = new ArrayList<DbInfoDbGroupRelation>();
        if (dbDbInfoDbGroupRelationDOList==null || dbDbInfoDbGroupRelationDOList.isEmpty()) {
            return dbDbInfoDbGroupRelationVOList;
        }
        for (DbInfoDbGroupRelationDO dbDbInfoDbGroupRelationDO : dbDbInfoDbGroupRelationDOList) {
            DbInfoDbGroupRelation dbDbInfoDbGroupRelationVO = new DbInfoDbGroupRelation();
            DO2VO_BC.copy(dbDbInfoDbGroupRelationDO, dbDbInfoDbGroupRelationVO, null);
            dbDbInfoDbGroupRelationVOList.add(dbDbInfoDbGroupRelationVO);
        }
        return dbDbInfoDbGroupRelationVOList;
    }

    /**
     * 把数据库返回的DO对象转换成页面展示VO对象
     *
     * @param dbDbInfoDbGroupRelationDO
     * @return
     */
    public static DbInfoDbGroupRelation convert2VO(DbInfoDbGroupRelationDO dbDbInfoDbGroupRelationDO) {
        if (dbDbInfoDbGroupRelationDO == null) {
            return null;
        }
        DbInfoDbGroupRelation dbDbInfoDbGroupRelationVO = new DbInfoDbGroupRelation();
        DO2VO_BC.copy(dbDbInfoDbGroupRelationDO, dbDbInfoDbGroupRelationVO, null);
        return dbDbInfoDbGroupRelationVO;
    }

    /**
     * 把页面上的DO对象转换成数据库存储的VO对象
     *
     * @param dbDbInfoDbGroupRelationVO
     * @return
     */
    public static DbInfoDbGroupRelationDO convert2DO(DbInfoDbGroupRelation dbDbInfoDbGroupRelationVO) {
        if (dbDbInfoDbGroupRelationVO == null) {
            return null;
        }
        DbInfoDbGroupRelationDO dbDbInfoDbGroupRelationDO = new DbInfoDbGroupRelationDO();
        VO2DO_BC.copy(dbDbInfoDbGroupRelationVO, dbDbInfoDbGroupRelationDO, null);
        return dbDbInfoDbGroupRelationDO;
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

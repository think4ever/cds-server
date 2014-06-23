/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.wangyin.cds.service.dal.dataobject.DbGroupDO;
import com.wangyin.cds.service.model.DbGroup;

/**
 * 数据库群组信息工具类
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/1 11:46 Exp $$
 */
public class DbGroupUtil {
    /**
     * 转换器
     */
    private final static BeanCopier DO2VO_BC = BeanCopier.create(DbGroupDO.class,
            DbGroup.class, false);
    /**
     * 转换器
     */
    private final static BeanCopier VO2DO_BC = BeanCopier.create(DbGroup.class,
            DbGroupDO.class, false);

    /**
     * 把数据库返回的DO对象列表转换成页面展示VO对象列表
     *
     * @param dbGroupDOList
     * @return
     */
    public static List<DbGroup> convert2VOList(List<DbGroupDO> dbGroupDOList) {
        List<DbGroup> dbGroupVOList = new ArrayList<DbGroup>();
        if (dbGroupDOList==null || dbGroupDOList.isEmpty()) {
            return dbGroupVOList;
        }
        for (DbGroupDO dbGroupDO : dbGroupDOList) {
            DbGroup dbGroupVO = new DbGroup();
            DO2VO_BC.copy(dbGroupDO, dbGroupVO, null);
            dbGroupVOList.add(dbGroupVO);
        }
        return dbGroupVOList;
    }

    /**
     * 把数据库返回的DO对象转换成页面展示VO对象
     *
     * @param dbGroupDO
     * @return
     */
    public static DbGroup convert2VO(DbGroupDO dbGroupDO) {
        if (dbGroupDO == null) {
            return null;
        }
        DbGroup dbGroupVO = new DbGroup();
        DO2VO_BC.copy(dbGroupDO, dbGroupVO, null);
        return dbGroupVO;
    }

    /**
     * 把页面上的DO对象转换成数据库存储的VO对象
     *
     * @param dbGroupVO
     * @return
     */
    public static DbGroupDO convert2DO(DbGroup dbGroupVO) {
        if (dbGroupVO == null) {
            return null;
        }
        DbGroupDO dbGroupDO = new DbGroupDO();
        VO2DO_BC.copy(dbGroupVO, dbGroupDO, null);
        return dbGroupDO;
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

/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model.convert;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.wangyin.cds.service.dal.dataobject.SplittingKeyDO;
import com.wangyin.cds.service.model.SplittingKey;

/**
 * 切分键信息工具类
 * 
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/4 15:59 Exp $$
 */
public class SplittingKeyUtil {

    /**
     * 转换器
     */
    private final static BeanCopier DO2VO_BC = BeanCopier.create(SplittingKeyDO.class,
                                                     SplittingKey.class, false);
    /**
     * 转换器
     */
    private final static BeanCopier VO2DO_BC = BeanCopier.create(SplittingKey.class,
                                                     SplittingKeyDO.class, false);

    /**
     * 把数据库返回的DO对象列表转换成页面展示VO对象列表
     * 
     * @param splittingKeyDOList
     * @return
     */
    public static List<SplittingKey> convert2VOList(List<SplittingKeyDO> splittingKeyDOList) {
        List<SplittingKey> splittingKeyVOList = new ArrayList<SplittingKey>();
        if (splittingKeyDOList == null || splittingKeyDOList.isEmpty()) {
            return splittingKeyVOList;
        }
        for (SplittingKeyDO splittingKeyDO : splittingKeyDOList) {
            SplittingKey splittingKeyVO = new SplittingKey();
            DO2VO_BC.copy(splittingKeyDO, splittingKeyVO, null);
            splittingKeyVOList.add(splittingKeyVO);
        }
        return splittingKeyVOList;
    }

    /**
     * 把数据库返回的DO对象转换成页面展示VO对象
     * 
     * @param splittingKeyDO
     * @return
     */
    public static SplittingKey convert2VO(SplittingKeyDO splittingKeyDO) {
        if (splittingKeyDO == null) {
            return null;
        }
        SplittingKey splittingKeyVO = new SplittingKey();
        DO2VO_BC.copy(splittingKeyDO, splittingKeyVO, null);
        return splittingKeyVO;
    }

    /**
     * 把页面上的DO对象转换成数据库存储的VO对象
     * 
     * @param splittingKeyVO
     * @return
     */
    public static SplittingKeyDO convert2DO(SplittingKey splittingKeyVO) {
        if (splittingKeyVO == null) {
            return null;
        }
        SplittingKeyDO splittingKeyDO = new SplittingKeyDO();
        VO2DO_BC.copy(splittingKeyVO, splittingKeyDO, null);
        return splittingKeyDO;
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

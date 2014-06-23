/**
 * 
 */
package com.wangyin.cds.biz;

import com.wangyin.cds.common.cache.Cache;
import com.wangyin.cds.common.cache.CacheManager;
import com.wangyin.cds.common.cache.CdsCacheUtil;
import com.wangyin.cds.common.event.CdsEvent;

/**
 * @author wymaoxiaoliang
 *
 */
public class CdsEventService {
    /**
     * 向缓存中推入一个事件
     * 
     * @param event
     * @return
     */
    public static boolean pushCdsEvent(CdsEvent event) {
        if (event == null || event.getEventId() == null) {
            return false;
        }

        String key = CdsCacheUtil.genCdsEventCacheKey(event);
        CacheManager.putCache(key, new Cache(event));

        return true;
    }
}

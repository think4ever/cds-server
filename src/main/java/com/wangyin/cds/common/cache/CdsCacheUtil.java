/**
 * 
 */
package com.wangyin.cds.common.cache;

import com.wangyin.cds.common.constant.CdsConstant;
import com.wangyin.cds.common.event.CdsEvent;

/**
 * @author wymaoxiaoliang
 *
 */
public class CdsCacheUtil {
    /**
     * 生成POLL记录缓存KEY
     * 
     * @param sessionId
     * @param event
     * @return
     */
    public static String genPollRecordCacheKey(String sessionId, CdsEvent event) {
        if (sessionId != null && sessionId != "" && event != null) {
            return sessionId + '@' + event.getEventId() + '$' + CdsConstant.CACHE_TYPE_POLL_RECORD;
        }
        return null;
    }

    /**
     * CDS事件缓存KEY
     * 
     * @param sessionId
     * @param event
     * @return
     */
    public static String genCdsEventCacheKey(CdsEvent event) {
        if (event != null) {
            return event.getEventId() + '$' + CdsConstant.CACHE_TYPE_CDS_EVENT;
        }
        return null;
    }
}

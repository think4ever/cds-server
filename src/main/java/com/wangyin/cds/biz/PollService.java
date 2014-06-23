/**
 * 
 */
package com.wangyin.cds.biz;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.util.ByteArrayBuilder;

import com.wangyin.cds.common.cache.Cache;
import com.wangyin.cds.common.cache.CacheManager;
import com.wangyin.cds.common.cache.CdsCacheUtil;
import com.wangyin.cds.common.constant.CdsConstant;
import com.wangyin.cds.common.event.CdsEvent;

/**
 * @author wymaoxiaoliang
 *
 */
public class PollService {
    //private JsonGenerator jsonGenerator = null;
    //private ObjectMapper  objectMapper  = null;

    /**
     * 向缓存中推入一个Poll记录
     * 
     * @param event
     * @return
     */
    public static boolean pushCdsEventPollRecord(String sessionId, CdsEvent event) {
        if (sessionId == null || sessionId == "" || event == null || event.getEventId() == null) {
            return false;
        }

        String key = CdsCacheUtil.genPollRecordCacheKey(sessionId, event);
        CacheManager.putCache(key, new Cache("isPush"));

        return true;
    }
    
    public static byte[] getCdsEventListJson(String sessionId){
        List<CdsEvent> list =  getCdsEventList(sessionId);
        return writeEntityJSON(list);
    }
    
    public static byte[] writeEntityJSON(Object bean) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            /*JsonGenerator jsonGenerator;
            try {
                jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
                System.out.println("jsonGenerator");
                //writeObject可以转换java对象，eg:JavaBean/Map/List/Array等
                jsonGenerator.writeObject(bean);    
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            //System.out.println("ObjectMapper");
            //writeValue具有和writeObject相同的功能
            ByteArrayBuilder json = new ByteArrayBuilder();
            objectMapper.writeValue(json, bean);
            return json.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取需要推送的事件列表
     * 
     * @param sessionId
     * @return
     */
    public static List<CdsEvent> getCdsEventList(String sessionId) {
        List<Object> list = CacheManager.getCacheObjList(CdsConstant.CACHE_TYPE_CDS_EVENT);
        List<CdsEvent> eventList = new ArrayList<CdsEvent>();
        for (Object event : list) {
            if (!(event instanceof CdsEvent)) {
                continue;
            }
            // 未给指定session推送过
            if (!isPushed4Session(sessionId, (CdsEvent) event)) {
                // 加入推送列表
                eventList.add((CdsEvent) event);
                // 加入Poll记录
                pushCdsEventPollRecord(sessionId, (CdsEvent) event);
            }
        }
        return eventList;
    }

    /**
     * 对指定session某个事件是否已经推送
     * 
     * @param sessionId
     * @param event
     * @return
     */
    private static boolean isPushed4Session(String sessionId, CdsEvent event) {
        String key = CdsCacheUtil.genPollRecordCacheKey(sessionId, event);
        Cache pollRecordCache = CacheManager.getCacheInfo(key);
        return pollRecordCache != null;
    }

}

/**
 * 
 */
package com.wangyin.cds.common.cache;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.wangyin.cds.biz.CdsEventService;
import com.wangyin.cds.biz.PollService;
import com.wangyin.cds.common.event.CdsEvent;
import com.wangyin.cds.common.event.EventCode;

/**
 * @author wymaoxiaoliang
 *
 */
public class CaseTest {

    /**
     * @param args
     * @throws UnsupportedEncodingException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        /*CacheManager.putCache("abc", new Cache());
        CacheManager.putCache("def", new Cache());
        CacheManager.putCache("ccc", new Cache());
        CacheManager.clearOnly("");
        Cache c = new Cache();
        for (int i = 0; i < 10; i++) {
            CacheManager.putCache("" + i, c);
        }
        CacheManager.putCache("aaaaaaaa", c);
        CacheManager.putCache("abchcy;alskd", c);
        CacheManager.putCache("cccccccc", c);
        CacheManager.putCache("abcoqiwhcy", c);
        System.out.println("删除前的大小：" + CacheManager.getCacheSize());
        CacheManager.getCacheAllkey();
        CacheManager.clearAll("aaaa");
        System.out.println("删除后的大小：" + CacheManager.getCacheSize());
        CacheManager.getCacheAllkey();
        //System.out.println(CacheManager.getCacheInfo("aaaaaaaa").getValue());*/

        CdsEventService.pushCdsEvent(new CdsEvent(EventCode.DEPOTS_RULE_CHANGE));
        CdsEventService.pushCdsEvent(new CdsEvent(EventCode.DB_DDL_CHANGE));
        System.out.println("poll前===========================================");
        List<String> keys = CacheManager.getCacheAllkey();
        for (String key : keys) {
            System.out.println(key);
        }
        System.out.println("poll1后===========================================");
        List<CdsEvent> list2 = PollService.getCdsEventList("111111111111");
        for (CdsEvent event : list2) {
            System.out.println(event);
        }
        List<String> keys2 = CacheManager.getCacheAllkey();
        for (String key : keys2) {
            System.out.println(key);
        }
        System.out.println("poll2后===========================================");
        List<CdsEvent> list3 = PollService.getCdsEventList("111111111111");
        for (CdsEvent event : list3) {
            System.out.println(event);
        }
        List<String> keys3 = CacheManager.getCacheAllkey();
        for (String key : keys3) {
            System.out.println(key);
        }
        System.out.println("poll2后===========================================");
        String srt2=new String(PollService.getCdsEventListJson("33333333333333"),"UTF-8");
        System.out.println(srt2);
    }

}

/**
 * 
 */
package com.wangyin.cds.common.event;

import java.util.HashMap;
import java.util.Map;

/**
 * CDS事件类
 * 
 * @author wymaoxiaoliang
 */
public class CdsEvent {
    /** 事件ID(作为一次事件的唯一标识) */
    private String              eventId;

    /** 事件信息 */
    private EventCode           eventCode;

    /** 扩展信息 */
    private Map<String, String> extInfo = new HashMap<String, String>();

    //防止默认构造方法被调用
    @SuppressWarnings("unused")
    private CdsEvent() {
    }

    public CdsEvent(EventCode eventCode) {
        if (eventCode != null) {
            this.eventId = generateEventId(eventCode);
            this.eventCode = eventCode;
        }
    }

    private String generateEventId(EventCode eventCode) {
        return eventCode.getType() + System.currentTimeMillis();
    }

    /**
     * Put扩展信息
     * 
     * @param key
     * @param value
     */
    public void putExtField(String key, String value) {
        if (key == null || key == "") {
            return;
        }
        if (this.extInfo.containsKey(key)) {
            this.extInfo.remove(key);
        }
        this.extInfo.put(key, value);
    }

    /**
     * 获取指定扩展字段的值
     * 
     * @param key
     * @return
     */
    public String getExtField(String key) {
        return this.extInfo.get(key);
    }

    /**
     * @return the eventId
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * @param eventId the eventId to set
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * @return the eventCode
     */
    public EventCode getEventCode() {
        return eventCode;
    }

    /**
     * @param eventCode
     *            the eventCode to set
     */
    public void setEventCode(EventCode eventCode) {
        this.eventCode = eventCode;
    }

    /**
     * @return the extInfo
     */
    public Map<String, String> getExtInfo() {
        return extInfo;
    }

    /**
     * @param extInfo
     *            the extInfo to set
     */
    public void setExtInfo(Map<String, String> extInfo) {
        this.extInfo = extInfo;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CdsEvent [eventId=");
        builder.append(eventId);
        builder.append(", eventCode=");
        builder.append(eventCode);
        builder.append(", extInfo=");
        builder.append(extInfo);
        builder.append("]");
        return builder.toString();
    }
}

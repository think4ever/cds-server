/**
 * 
 */
package com.wangyin.cds.common.event;

/**
 * CDS事件
 * 
 * @author wymaoxiaoliang
 * 
 */
public enum EventCode {
    /** 主备切换 */
    DB_MASTER_SLAVE_SWTICH("MASTER_SLAVE_SWTICH", "S", "主备切换"),
    /** 规则变更: 分库规则变更 */
    DEPOTS_RULE_CHANGE("DEPOTS_RULE_CHANGE", "R", "分库分表规则变更"),
    /** DDL: 数据库结构变更 */
    DB_DDL_CHANGE("DB_DDL_CHANGE", "D", "分库分表规则变更"),
    /** 警告: 主机LOAD过高警告 */
    HOST_LOAD_WARN("HOST_LOAD_WARN", "W", "主机LOAD过高");

    EventCode(String code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /** 事件码 */
    private String code;
    /** 事件类型*/
    private String type;
    /** 事件描述 */
    private String desc;
}

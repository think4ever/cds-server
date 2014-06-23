/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-${year} All Rights Reserved.
 */
package com.wangyin.cds.service.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 数据库基本信息视图
 * 
 * @author wymaoxiaoliang
 */
@XmlRootElement(name = "masterOrSlaveSwitchReslt")
public class MasterOrSlaveSwitchReslt implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long      serialVersionUID    = -6067325198886870094L;

    /**
     * 群组ID
     */
    private Long                   groupId;
    /**
     * 群组名称
     */
    private String                 groupName;

    /**
     * 主备库信息
     */
    private Map<String, DbInfoExt> masterOrSlaveDbInfo = new HashMap<String, DbInfoExt>();

    /**
     * 操作是否成功
     */
    private boolean                isSuccess;

    /**
     * 处理结果信息
     */
    private String                 msg;

    /**
     * @return the groupId
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the masterOrSlaveDbInfo
     */
    public Map<String, DbInfoExt> getMasterOrSlaveDbInfo() {
        return masterOrSlaveDbInfo;
    }

    /**
     * @param masterOrSlaveDbInfo the masterOrSlaveDbInfo to set
     */
    public void setMasterOrSlaveDbInfo(Map<String, DbInfoExt> masterOrSlaveDbInfo) {
        this.masterOrSlaveDbInfo = masterOrSlaveDbInfo;
    }

    /**
     * @return the isSuccess
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * @param isSuccess the isSuccess to set
     */
    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MasterOrSlaveSwitchReslt [groupId=");
        builder.append(groupId);
        builder.append(", groupName=");
        builder.append(groupName);
        builder.append(", masterOrSlaveDbInfo=");
        builder.append(masterOrSlaveDbInfo);
        builder.append(", isSuccess=");
        builder.append(isSuccess);
        builder.append(", msg=");
        builder.append(msg);
        builder.append("]");
        return builder.toString();
    }
    
}

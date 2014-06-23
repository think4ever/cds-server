/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model;

import javax.validation.constraints.Size;

/**
 * 群组基本信息
 * 
 * @author wymaoxiaoliang
 */
public class DbGroup extends Base {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7998557191720044649L;
    /**
     * 群组ID
     */
    private Long              id;
    /**
     * 群组名称
     */
    //@NotBlank(message = "群组名称不能为空!")
    @Size(min = 0, max = 30, message = "群组名称长度不能大于30字!")
    private String            groupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DbGroup [id=");
        builder.append(id);
        builder.append(", groupName=");
        builder.append(groupName);
        builder.append("]");
        return builder.toString();
    }
}

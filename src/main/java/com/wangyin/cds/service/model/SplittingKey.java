/**
 * Wangyin.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */
package com.wangyin.cds.service.model;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * 切分键视图页面
 *
 * @author 蒋鲁宾
 * @version v 0.1 2014/5/4 15:43 Exp $$
 */
@XmlRootElement(name = "splittingKey")
public class SplittingKey extends Base{
    /**
     * 切分键ID
     */
    private Long id;
    /**
     * 切分键名称
     */
    private String splitName;
    
    /**
     * dbClusterId
     */
    private Long clusterId;


    public Long getClusterId() {
		return clusterId;
	}

	public void setClusterId(Long clusterId) {
		this.clusterId = clusterId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSplitName() {
        return splitName;
    }

    public void setSplitName(String splitName) {
        this.splitName = splitName;
    }
}

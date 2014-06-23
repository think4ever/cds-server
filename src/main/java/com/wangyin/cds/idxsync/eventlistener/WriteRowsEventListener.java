/**
 * 
 */
package com.wangyin.cds.idxsync.eventlistener;

import com.google.code.or.binlog.impl.event.TableMapEvent;
import com.google.code.or.binlog.impl.event.WriteRowsEvent;

/**
 * Binlog监听程序,回调此接口完成反向索引的创建
 * 
 * @author wymaoxiaoliang
 */
public interface WriteRowsEventListener {
    /**
     * 当发生插入时回调
     * 
     * @param wrEvent
     * @param tblMapEvent
     */
    public void onInsert(WriteRowsEvent wrEvent, TableMapEvent tblMapEvent);
}

/**
 * 
 */
package com.wangyin.cds.idxsync.eventlistener;

import com.google.code.or.binlog.impl.event.TableMapEvent;
import com.google.code.or.binlog.impl.event.UpdateRowsEvent;

/**
 * Binlog监听程序,回调此接口完成反向索引的更新
 * 
 * @author wymaoxiaoliang
 */
public interface UpdateRowsEventListener {
    /**
     * 当发生更新操作时候回调
     * 
     * @param urEvent
     * @param tblMapEvent
     */
    public void onUpdate(UpdateRowsEvent urEvent, TableMapEvent tblMapEvent);
}

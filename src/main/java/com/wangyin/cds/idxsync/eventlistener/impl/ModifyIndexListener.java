/**
 * 
 */
package com.wangyin.cds.idxsync.eventlistener.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.or.binlog.impl.event.TableMapEvent;
import com.google.code.or.binlog.impl.event.UpdateRowsEvent;
import com.wangyin.cds.idxsync.CdsIndexSyncService;
import com.wangyin.cds.idxsync.core.InvertedIndexContext;
import com.wangyin.cds.idxsync.util.IdxSyncEventHelper;

/**
 * 监听更新事件更新反向索引
 * <p>在onUpdate中完成反向索引的更新逻辑</p>
 * 
 * @author wymaoxiaoliang
 */
public class ModifyIndexListener implements com.wangyin.cds.idxsync.eventlistener.UpdateRowsEventListener {
    private static Logger       logger = LoggerFactory.getLogger(ModifyIndexListener.class);

    /**反向索引服务*/
    private CdsIndexSyncService cis    = new CdsIndexSyncService();

    /*
     * (non-Javadoc)
     * @see com.wangyin.wycds.binlogdemo.eventlistener.UpdateRowsEventListener#onUpdate(com.google.code.or.binlog.impl.event.UpdateRowsEvent, com.google.code.or.binlog.impl.event.TableMapEvent)
     */
    public void onUpdate(UpdateRowsEvent event, TableMapEvent tblMapEvent) {
        // TODO Auto-generated method stub
        logger.info("监听到UpdateRowsEvent事件: " + event.toString());
        if (!IdxSyncEventHelper.isNeedSync(tblMapEvent)) {
            logger.error("非分表操作/不具备同步条件[WriteRowsEvent]");
            return;
        }
        try {
            InvertedIndexContext ctx = IdxSyncEventHelper.buildIdxContext(event, tblMapEvent);
            cis.modifyInvertedIdx(ctx);
        } catch (Exception e) {
            logger.error("处理反向索引同步异常[UpdateRowsEvent]", e);
        }
    }

}

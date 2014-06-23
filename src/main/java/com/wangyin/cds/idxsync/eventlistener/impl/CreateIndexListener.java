/**
 * 
 */
package com.wangyin.cds.idxsync.eventlistener.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.or.binlog.impl.event.TableMapEvent;
import com.google.code.or.binlog.impl.event.WriteRowsEvent;
import com.wangyin.cds.idxsync.CdsIndexSyncService;
import com.wangyin.cds.idxsync.core.InvertedIndexContext;
import com.wangyin.cds.idxsync.eventlistener.WriteRowsEventListener;
import com.wangyin.cds.idxsync.util.IdxSyncEventHelper;

/**
 * 监听插入事件创建反向索引
 * <p>在onInsert中完成反向索引的新建逻辑</p>
 * 
 * @author wymaoxiaoliang
 */
public class CreateIndexListener implements WriteRowsEventListener {
    private static Logger       logger = LoggerFactory.getLogger(CreateIndexListener.class);

    /**反向索引服务*/
    private CdsIndexSyncService cis    = new CdsIndexSyncService();

    /*
     * (non-Javadoc)
     * @see com.wangyin.wycds.binlogdemo.eventlistener.WriteRowsEventListener#onInsert(com.google.code.or.binlog.impl.event.WriteRowsEvent, com.google.code.or.binlog.impl.event.TableMapEvent)
     */
    public void onInsert(WriteRowsEvent event, TableMapEvent tblMapEvent) {
        // TODO Auto-generated method stub
        logger.info("监听到WriteRowsEvent事件: " + event.toString());
        if (!IdxSyncEventHelper.isNeedSync(tblMapEvent)) {
            logger.error("非分表操作/不具备同步条件[WriteRowsEvent]");
            return;
        }
        try {
            InvertedIndexContext ctx = IdxSyncEventHelper.buildIdxContext(event, tblMapEvent);
            cis.createInvertedIdx(ctx);
        } catch (Exception e) {
            logger.error("处理反向索引同步异常[WriteRowsEvent]", e);
        }
    }

}

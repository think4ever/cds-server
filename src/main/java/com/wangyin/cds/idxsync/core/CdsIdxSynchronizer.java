/**
 * 
 */
package com.wangyin.cds.idxsync.core;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.or.OpenReplicator;
import com.google.code.or.binlog.BinlogEventListener;
import com.google.code.or.binlog.BinlogEventV4;
import com.google.code.or.binlog.impl.event.TableMapEvent;
import com.google.code.or.binlog.impl.event.UpdateRowsEvent;
import com.google.code.or.binlog.impl.event.WriteRowsEvent;
import com.wangyin.cds.idxsync.eventlistener.UpdateRowsEventListener;
import com.wangyin.cds.idxsync.eventlistener.WriteRowsEventListener;

/**
 * CDS反向索引同步器
 * <p>通过binlog日志监听维护各个分库分表中的索引列的反向索引记录</p>
 * 
 * @author wymaoxiaoliang
 * 
 */
public class CdsIdxSynchronizer {
    private static Logger           logger            = LoggerFactory.getLogger(CdsIdxSynchronizer.class);

    private TableMapEvent           lastTableMapEvent = new TableMapEvent();
    private WriteRowsEventListener  wrListener;
    private UpdateRowsEventListener urListener;

    private final OpenReplicator    or                = new OpenReplicator();

    /**
     * @param user mysql用户名
     * @param password 密码
     * @param host 主机名
     * @param port 端口号
     * @param serverId mysql服务Id
     * @param binlogPos binlog position
     * @param binlogFilename binlog文件名
     */
    public CdsIdxSynchronizer(String user, String password, String host, int port, int serverId,
                              int binlogPos, String binlogFilename) {
        or.setUser(user/* "root" */);
        or.setPassword(password/* "5978222" */);
        or.setHost(host/* "localhost" */);
        or.setPort(port/* 3307 */);
        or.setServerId(serverId/* 10086 */);
        or.setBinlogPosition(binlogPos/* 4 */);
        or.setBinlogFileName(binlogFilename/* "mysqlbinlog.000001" */);
        or.setBinlogEventListener(new BinlogEventListener() {
            public void onEvents(BinlogEventV4 event) {
                synchronized (lastTableMapEvent) {
                    if (event instanceof WriteRowsEvent) {
                        if (lastTableMapEvent == null) {
                            logger.error("lastTableMapEvent为空, 无法处理WriteRowsEvent事件.");
                            return;
                        }
                        // 基于position的事件校验
                        if (event.getHeader().getPosition() != lastTableMapEvent.getHeader().getNextPosition()) {
                            logger.error("位置不对齐,事件解析出错,W-Position=" + event.getHeader().getPosition() + ", TN-Position=" + lastTableMapEvent.getHeader().getNextPosition());
                            return;
                        }
                        wrListener.onInsert((WriteRowsEvent) event, lastTableMapEvent);
                    } else if (event instanceof UpdateRowsEvent) {
                        if (lastTableMapEvent == null) {
                            logger.error("lastTableMapEvent为空, 无法处理UpdateRowsEvent事件.");
                            return;
                        }
                        // 基于position的事件校验
                        if (event.getHeader().getPosition() != lastTableMapEvent.getHeader().getNextPosition()) {
                            logger.error("位置不对齐,事件解析出错,W-Position=" + event.getHeader().getPosition() + ", TN-Position=" + lastTableMapEvent.getHeader().getNextPosition());
                            return;
                        }
                        urListener.onUpdate((UpdateRowsEvent) event, lastTableMapEvent);
                    } else if (event instanceof TableMapEvent) {
                        //在insert/delete/update事件中不记录表的相关信息,
                        //因此每次DML操作都会产生一个TABLE_MAP_EVENT事件,
                        //其中存储了获取数据库名和表名.
                        //这里先用这种简单粗暴的方式处理,处理插入更新的操作数据的获取!
                        lastTableMapEvent = (TableMapEvent) event;
                        //logger.info("TableMapEvent: " + event.toString());
                    } else {
                        //logger.info("监听到事件: " + event.toString());
                    }
                }//END SYNCHRONIZED BLOCK
            }
        });
    }

    /**
     * 开始数据库插入&更新事件的监听
     * 
     * @throws Exception
     */
    public void start() throws Exception {
        if (or == null) {
            logger.info("请初始化OpenReplicator.");
            return;
        }
        if (wrListener == null || urListener == null) {
            logger.info("请初始化监听器.");
            return;
        }
        or.start();
        logger.info("///////////////////////////////开始binlog监听///////////////////////////////");
    }

    /**
     * 关闭binlog事件监听
     * 
     * @throws Exception
     */
    public void shutdown() throws Exception {
        if (or == null || !or.isRunning()) {
            logger.info("OpenReplicator未初始化OpenReplicator,无需关闭.");
            return;
        }
        or.stop(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    /**
     * @param wrListener the wrListener to set
     */
    public void registerWrListener(WriteRowsEventListener wrListener) {
        this.wrListener = wrListener;
    }

    /**
     * @param urListener the urListener to set
     */
    public void registerUrListener(UpdateRowsEventListener urListener) {
        this.urListener = urListener;
    }

}

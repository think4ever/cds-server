package com.wangyin.cds.idxsync;

import com.wangyin.cds.idxsync.core.CdsIdxSynchronizer;
import com.wangyin.cds.idxsync.eventlistener.impl.CreateIndexListener;
import com.wangyin.cds.idxsync.eventlistener.impl.ModifyIndexListener;

/**
 * 反向索引同步测试入口类
 * 
 * @author wymaoxiaoliang
 */
public class IdxSyncMain {
    public static void main(String[] args) throws Exception {
        // 同步器初始化:
        // 通过数据库获取binlog的相关的配置,
        // 在服务器中起一个线程开启同步服务.
        CdsIdxSynchronizer sync = new CdsIdxSynchronizer("root", "5978222", "localhost", 3307, 10086, 4, "mysqlbinlog.000001");
        
        //注册插入和更新事件监听器
        sync.registerWrListener(new CreateIndexListener());
        sync.registerUrListener(new ModifyIndexListener());
        
        // 开始监听
        sync.start();
    }

}

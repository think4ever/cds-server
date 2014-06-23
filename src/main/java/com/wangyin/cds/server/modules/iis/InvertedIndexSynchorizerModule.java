package com.wangyin.cds.server.modules.iis;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangyin.cds.idxsync.core.CdsIdxSynchronizer;
import com.wangyin.cds.idxsync.eventlistener.impl.CreateIndexListener;
import com.wangyin.cds.idxsync.eventlistener.impl.ModifyIndexListener;
import com.wangyin.cds.server.modules.INodeModule;
import com.wangyin.cds.server.modules.ServerModule;
import com.wangyin.cds.service.dal.datainterface.DataSourceDAO;
import com.wangyin.cds.service.dal.dataobject.DataSourceDO;
import com.wangyin.cds.service.dal.ext.datainterface.DbInfoExtDAO;
import com.wangyin.cds.service.model.DbInfoExt;
import com.wangyin.cds.service.util.MybatisUtil;

public class InvertedIndexSynchorizerModule extends ServerModule implements INodeModule {
    private static Logger                     logger       = LoggerFactory.getLogger(CdsIdxSynchronizer.class);

    /**反向索引同步器对象池*/
    protected Map<String, CdsIdxSynchronizer> idxSyncMap   = new ConcurrentHashMap<String, CdsIdxSynchronizer>();
    /**binlog监听线程池*/
    private ExecutorService                   executorPool = null;

    public void configure(Map<String, Object> configuration) {
        
    }

    public void startup() throws Exception {
        DbInfoExtDAO dbInfoExtDAO = MybatisUtil.getMapper(DbInfoExtDAO.class);
        List<DbInfoExt> list = dbInfoExtDAO.getDbInfoListByType("master");
        if (list == null || list.isEmpty()) {
            logger.error("未找到需要同步反向索引的DB.");
            return;
        }
        
        // 放大3个size新建线程池
        executorPool = Executors.newFixedThreadPool(list.size() + 3);
        
        DataSourceDAO dsDao = MybatisUtil.getMapper(DataSourceDAO.class);
        for (DbInfoExt db : list) {
            try {
                //XXX 目前库设计中没有用户名和密码
                //XXX dbinfo库和数据源之前没有强关联
                //XXX 我做了一个假定的约定数据源的name=serverId_dbName
                //XXX 这里的password明文放在数据库中,这里后续需要改正
                String dsName = String.valueOf(db.getServerId()) + '_' + db.getDbName();
                DataSourceDO dsDo = dsDao.getDataSourceByName(dsName);
                if (dsDo == null || StringUtils.isBlank(dsDo.getUserName()) || StringUtils.isBlank(dsDo.getPassWord())) {
                    logger.error("对应的数据源配置有误, 开启反向索引同步失败, " + db + ", dsName=" + dsName);
                    continue;
                }
                this.startupOneIdxSync(dsDo.getUserName(), dsDo.getPassWord(), db.getIp(), Integer.parseInt(db.getPort()), db.getServerId());
            } catch (Exception e) {
                logger.error("Startup IdxSync Exception, " + db, e);
            }
        }
    }

    /**
     * 开始一个库的binlog监听
     * 
     * @throws Exception
     */
    private void startupOneIdxSync(String user, String password, String host, int port, int serverId) throws Exception {
        // 对应的DB服务器实例已经被监控
        if (idxSyncMap.containsKey(serverId + "_" + port)) {
            logger.info("IdxSync existed, serverId:" + serverId + "Host: " + host + ", port:" + port);
            return;
        }
        
        // 同步器初始化:
        // 通过数据库获取binlog的相关的配置,
        // 在服务器中起一个线程开启同步服务.
        // XXX 目前没有地方保存binlogFilename等额外信息,这里使用默认值
        final CdsIdxSynchronizer sync = new CdsIdxSynchronizer(user, password, host, port, 10086, 4, "mysqlbinlog.000001");;
        try {
            executorPool.execute(new Runnable() {
                public void run() {
                    //注册插入和更新事件监听器
                    sync.registerWrListener(new CreateIndexListener());
                    sync.registerUrListener(new ModifyIndexListener());

                    // 开始监听
                    try {
                        sync.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            
            // 加入Map中
            idxSyncMap.put(serverId + "_" + port, sync);
        } catch (Exception e) {
            if (sync != null) {
                sync.shutdown();
            }
        }
    }

    public void shutdown() throws Exception {
        // 关闭所有binlog监听器
        for (CdsIdxSynchronizer sync: idxSyncMap.values()) {
            try {
                sync.shutdown();
                logger.info("InvertedIndexSynchorizer shutdown, sync");
            } catch (Exception e) {
                logger.error("InvertedIndexSynchorizer exception", e);
            }
        }
    }

}

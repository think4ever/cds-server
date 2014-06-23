/**
 * 
 */
package com.wangyin.cds.idxsync.util;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.or.binlog.impl.event.AbstractRowEvent;
import com.google.code.or.binlog.impl.event.TableMapEvent;
import com.google.code.or.binlog.impl.event.UpdateRowsEvent;
import com.google.code.or.binlog.impl.event.WriteRowsEvent;
import com.wangyin.cds.common.db.dbconnection.DataSourcePoolUtils;
import com.wangyin.cds.common.db.util.DbUtils;
import com.wangyin.cds.common.db.util.JdbcUtils;
import com.wangyin.cds.idxsync.core.InvertedIndexContext;
import com.wangyin.cds.service.dal.datainterface.DbGroupDAO;
import com.wangyin.cds.service.dal.datainterface.DbInfoDAO;
import com.wangyin.cds.service.dal.dataobject.DbGroupDO;
import com.wangyin.cds.service.dal.dataobject.DbInfoDO;
import com.wangyin.cds.service.util.MybatisUtil;

/**
 * 反向索引同步时间助手类
 * 
 * @author wymaoxiaoliang
 */
public class IdxSyncEventHelper {
    private static Logger           logger            = LoggerFactory.getLogger(IdxSyncEventHelper.class);
    
    /**
     * 是否需要同步索引
     * 
     * @param tblMapEvent
     * @return
     */
    public static boolean isNeedSync(TableMapEvent tblMapEvent){
        //1.是否是一个分表
        if (!TableNameUtil.isSubTbl(String.valueOf(tblMapEvent.getTableName()))){
            return false;
        }
        
        return true;
    }
    
    /**
     * 是否存在对应的反向索引表 [在指定源中]
     * 
     * @param ds
     * @return
     * @throws SQLException 
     */
    public static boolean hasIdxTbl(DataSource ds, TableMapEvent tblMapEvent) throws SQLException{
        String idxTblName = TableNameUtil.getTblName(String.valueOf(tblMapEvent.getTableName())) + "$lookup";
        return DbUtils.isExistsTbl(ds, idxTblName);
    }
    
    /**
     * 构建反向索引处理上下文对象
     * 
     * @return
     * @throws Exception 
     */
    public static InvertedIndexContext buildIdxContext(AbstractRowEvent event,TableMapEvent tblMapEvent) throws Exception{
        if(event==null||tblMapEvent==null){
            throw new Exception("事件为空无法处理");
        }
        
        InvertedIndexContext ctx = new InvertedIndexContext();
        long serverId = tblMapEvent.getHeader().getServerId();
        ctx.setDbServerId(serverId);
        String dbName = String.valueOf(tblMapEvent.getDatabaseName());
        ctx.setDbName(dbName);
        String tblName = String.valueOf(tblMapEvent.getTableName());
        ctx.setTableName(tblName);
        
        // 1.获取数据源
        DataSource ds = getDataSource(serverId, dbName);
        ctx.setDataSource(ds);
        
        // 2.获取对应表的列名称
        List<String> colums = JdbcUtils.queryColumnLabel(ds.getConnection(), tblName);
        if (colums == null || colums.isEmpty()) {
            logger.error("无法获取对应表的列名称, 同步反向索引失败, context:" + ctx.toString());
            throw new Exception("无法获取对应表的列名称,无法构建反向索引处理上下文");
        }
        ctx.setColums(colums);
        // 3.获取数据库信息
        DbInfoDO dbInfo = getDbInfoByServerId(serverId, dbName);
        if (dbInfo == null) {
            logger.error("对应的数据库不存在, 同步反向索引失败, serverId:" + serverId + ", dbName:" + dbName);
            throw new Exception("对应的数据库不存在,无法构建反向索引处理上下文");
        }
        ctx.setDbInfo(dbInfo);
        
        // 4.获取DB对应的群组信息
        List<DbGroupDO> dbGroups = getDbGroupListByServerId(serverId);
        if (dbGroups == null || dbGroups.isEmpty()) {
            logger.error("对应的群组不存在, 同步反向索引失败, serverId:" + serverId);
            throw new Exception("对应的群组不存在,无法构建反向索引处理上下文");
        }
        ctx.setDbgroups(dbGroups);
        
        // 5.获取对应的反向索引表
        String idxTblName = TableNameUtil.getTblName(ctx.getTableName()) + "$lookup";
        if(!DbUtils.isExistsTbl(ds, idxTblName)){
            logger.error("对应的索引表不存在, 无法同步反向索引, idxTblName=" + idxTblName + ", context:" + ctx.toString());
            throw new Exception("对应的索引表不存在,无法构建反向索引处理上下文");
        }
        ctx.setIdxTblName(idxTblName);
        
        List<?> rows = null;
        if (event instanceof UpdateRowsEvent) {
            UpdateRowsEvent e = (UpdateRowsEvent) event;
            rows = e.getRows();
        } else if (event instanceof WriteRowsEvent) {
            WriteRowsEvent e = (WriteRowsEvent) event;
            rows = e.getRows();
        }
        if(rows == null || rows.isEmpty()){
            logger.error("Rows中无数据, 同步反向索引失败[UpdateRowsEvent], context: " + ctx.toString());
            throw new Exception("Rows中无数据,无法构建反向索引处理上下文");
        }
        ctx.setRows(rows);
        
        return ctx;
    }
    
    /**
     * 获取数据库信息
     * 
     * @param serverId
     * @param dbName
     */
    private static DbInfoDO getDbInfoByServerId(long serverId, String dbName){
        DbInfoDAO dbInfoDAO = MybatisUtil.getMapper(DbInfoDAO.class);
        return dbInfoDAO.getDbInfoByServerId(serverId);
    }
    
    /**
     * 获取对应群组列表
     * 
     * @param serverId
     * @return
     */
    private static List<DbGroupDO> getDbGroupListByServerId(long serverId){
        DbGroupDAO dbGroupDao = MybatisUtil.getMapper(DbGroupDAO.class);
        return dbGroupDao.getDbGroupListByDbinfoId(serverId);
    }
    
    /**
     * 获取对应数据源
     * <p>用于处理反向操作的数据库操作</p>
     * 
     * @throws SQLException
     */
    private static DataSource getDataSource(long serverId, String dbName) throws SQLException{
        //DataSourceDAO dsDao = MybatisUtil.getMapper(DataSourceDAO.class);
        String dsName = String.valueOf(serverId) + '_' + dbName;
        return DataSourcePoolUtils.getDataSource(dsName);
    }
}

/**
 * 
 */
package com.wangyin.cds.idxsync;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.or.common.glossary.Column;
import com.google.code.or.common.glossary.Pair;
import com.google.code.or.common.glossary.Row;
import com.wangyin.cds.common.db.util.JdbcUtils;
import com.wangyin.cds.idxsync.core.CdsIdxSynchronizer;
import com.wangyin.cds.idxsync.core.InvertedIndexContext;
import com.wangyin.cds.idxsync.util.TableNameUtil;
import com.wangyin.cds.service.dal.dataobject.DbGroupDO;

/**
 * CDS反向索引同步服务
 * <p>目前只关注所以的创建和更新,对删除不做操作.<p>
 * @author wymaoxiaoliang
 */
public class CdsIndexSyncService {
    private static Logger logger = LoggerFactory.getLogger(CdsIdxSynchronizer.class);

    /**
     * 索引创建操作
     * 
     * @param ctx
     * @throws Exception 
     */
    public void createInvertedIdx(InvertedIndexContext ctx) throws Exception {
        // TODO Auto-generated method stub
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>BEGIN:反向索引创建逻辑@HERE<<<<<<<<<<<<<<<<<<<<<<<<<");
//            // 1.获取数据源信息
//            DataSourceDAO dsDao = MybatisUtil.getMapper(DataSourceDAO.class);
//            String dsName = String.valueOf(ctx.getDbServerId()) + '_' + ctx.getDbName();
//            DataSourceDO dsDo = dsDao.getDataSourceByName(dsName);
//            if (dsDo == null || StringUtils.isBlank(dsDo.getUserName()) || StringUtils.isBlank(dsDo.getPassWord())) {
//                logger.error("对应的数据源配置有误, 无法同步反向索引, dsName=" + dsName + ", context:" + ctx.toString());
//                return;
//            }
            
        List<?> rows = ctx.getRows();
        for (Object row : rows) {
            Row r = (Row) row;
            List<Column> cs = r.getColumns();
            // 跳过主键列
            for (int i = 1; i < cs.size(); i++) {
                String colName = ctx.getColums().get(i);
                Column c = cs.get(i);
                Object colVal = c.getValue();
                //同步索引
                this.syncIdx(ctx.getDataSource(), ctx.getDbgroups(), ctx.getDbName(), ctx.getTableName(), ctx.getIdxTblName(), colName, colVal);
            }
        }
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>END:反向索引创建操作@HERE<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    /**
     * 索引更新操作
     * 
     * @param ctx
     */
    public void modifyInvertedIdx(InvertedIndexContext ctx) throws Exception {
        // TODO Auto-generated method stub
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>BEGIN:反向索引更新操作@HERE<<<<<<<<<<<<<<<<<<<<<<<<<");
        List<?> rows = ctx.getRows();
        for (Object row : rows) {
            Pair<Row> pr = (Pair<Row>) row;
            Row b = pr.getBefore();
            Row a = pr.getAfter();
            List<Column> cs = a.getColumns();
            // 跳过主键列
            for (int i = 1; i < cs.size(); i++) {
                String colName = ctx.getColums().get(i);
                Column c = cs.get(i);
                Object colVal = c.getValue();
                //同步索引
                this.syncIdx(ctx.getDataSource(), ctx.getDbgroups(), ctx.getDbName(), ctx.getTableName(), ctx.getIdxTblName(), colName, colVal);
            }
        }
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>END:反向索引更新操作@HERE<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
    
    /**
     * 同步索引
     * XXX 这里的插入和更新逻辑有点小问题,但因为设计任由改进余地
     * XXX 此处展示暂不做完善,带最终方案确定后修改.
     * 
     * @param ds
     * @param dbGroups
     * @param dbName
     * @param tblName
     * @param idxTblName
     * @param colName
     * @param colVal
     */
    private void syncIdx(DataSource ds, List<DbGroupDO> dbGroups, String dbName, String tblName, String idxTblName, String colName, Object colVal) {
        try {
            // 查询是否有索引
            String qs = "SELECT * FROM " + idxTblName + " WHERE col_name = ? AND col_value=? ORDER BY id DESC LIMIT 0,1";
            List<Object> p = new ArrayList<Object>();
            p.add(colName);
            p.add(colVal);
            Map<String, Object> rm = JdbcUtils.executeQueryOneRow(ds, qs, p);
            
            if (rm == null || rm.isEmpty()) {
                for(DbGroupDO group: dbGroups){
                    String g = group.getGroupName();
                    if (!TableNameUtil.isSubGroup(g)) {
                        logger.info("非分组无需创建索引, group:" + g + ", tblName;" + tblName + "idxTblName:" + idxTblName + ", colName:" + colName
                                    + ", colVal:" + colVal);
                        continue;
                    }
                    // 创建索引
                    String cs = "INSERT INTO " + dbName + ".`" + idxTblName + "` (`col_name`, `col_value`, `" + g + "`) VALUES (?, ?, ?);";
                    List<Object> cp = new ArrayList<Object>();
                    cp.add(colName);
                    cp.add(colVal);
                    long idx = 2 << (TableNameUtil.getTblNum(tblName) - 1);
                    cp.add(idx);
                    JdbcUtils.execute(ds, cs, cp);
                    logger.info("创建反向索引, group:" + g + ", tblName;" + tblName + "idxTblName:" + idxTblName + ", colName:" + colName + ", colVal:" + colVal + ", idx:" + idx);
                }
            } else {
                for(DbGroupDO group: dbGroups){
                    String g = group.getGroupName();
                    if (!TableNameUtil.isSubGroup(g)) {
                        logger.info("非分组无需更新索引, group:" + g + ", tblName;" + tblName + "idxTblName:" + idxTblName + ", colName:" + colName + ", colVal:" + colVal);
                        continue;
                    }
                    // 更新索引
                    String us = "UPDATE " + dbName + ".`" + idxTblName + "` SET `" + g + "`= ?  WHERE `col_name` = ? AND `col_value` = ?;";
                    List<Object> up = new ArrayList<Object>();
                    long idx = Long.parseLong(rm.get(g).toString());
                    idx = (2 << (TableNameUtil.getTblNum(tblName) - 1)) ^ idx;
                    up.add(idx);
                    up.add(colName);
                    up.add(colVal);
                    JdbcUtils.execute(ds, us, up);
                    logger.info("更新反向索引, group:" + g + ", tblName;" + tblName + "idxTblName:" + idxTblName + ", colName:" + colName + ", colVal:" + colVal + ", idx:" + idx);
                }

            }
        } catch (Exception e) {
            logger.error("同步反向索引失败, idxTblName:" + idxTblName + ", colName:" + colName + ", colVal:" + colVal, e);
        }
    }
}

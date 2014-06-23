/**
 * 
 */
package com.wangyin.cds.idxsync.core;

import java.io.Serializable;
import java.util.List;

import javax.sql.DataSource;

import com.wangyin.cds.service.dal.dataobject.DbGroupDO;
import com.wangyin.cds.service.dal.dataobject.DbInfoDO;

/**
 * 反向索引操作上下文对象
 * 
 * @author wymaoxiaoliang
 */
public class InvertedIndexContext implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4069989260154657384L;
    /**DB服务器*/
    private long              dbServerId;
    /**库名*/
    private String            dbName;
    /**表名*/
    private String            tableName;
    /**对应的反向索引表名称*/
    private String            idxTblName;
    /**变更数据集合*/
    private List<?>           rows;

    /**对应表的列名称*/
    private List<String>      colums;

    /**对应的数据源*/
    private DataSource        dataSource;
    /**对应的DB信息*/
    private DbInfoDO          dbInfo;
    /**对应的群组列表*/
    private List<DbGroupDO>   dbgroups;

    /**
     * @return the dbServer
     */
    public long getDbServerId() {
        return dbServerId;
    }

    /**
     * @param dbServerId the dbServerId to set
     */
    public void setDbServerId(long dbServerId) {
        this.dbServerId = dbServerId;
    }

    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the idxTblName
     */
    public String getIdxTblName() {
        return idxTblName;
    }

    /**
     * @param idxTblName the idxTblName to set
     */
    public void setIdxTblName(String idxTblName) {
        this.idxTblName = idxTblName;
    }

    /**
     * @return the rows
     */
    public List<?> getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    /**
     * @return the colums
     */
    public List<String> getColums() {
        return colums;
    }

    /**
     * @param colums the colums to set
     */
    public void setColums(List<String> colums) {
        this.colums = colums;
    }

    /**
     * @return the dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @return the dbInfos
     */
    public DbInfoDO getDbInfo() {
        return dbInfo;
    }

    /**
     * @param dbInfo the dbInfos to set
     */
    public void setDbInfo(DbInfoDO dbInfo) {
        this.dbInfo = dbInfo;
    }

    /**
     * @return the dbgroups
     */
    public List<DbGroupDO> getDbgroups() {
        return dbgroups;
    }

    /**
     * @param dbgroups the dbgroups to set
     */
    public void setDbgroups(List<DbGroupDO> dbgroups) {
        this.dbgroups = dbgroups;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("IIC [dbServerId=");
        builder.append(dbServerId);
        builder.append(", dbName=");
        builder.append(dbName);
        builder.append(", tableName=");
        builder.append(tableName);
        builder.append(", idxTblName=");
        builder.append(idxTblName);
        builder.append(", rows=");
        builder.append(rows);
        builder.append(", colums=");
        builder.append(colums);
        builder.append(", dataSource=");
        builder.append(dataSource);
        builder.append(", dbInfo=");
        builder.append(dbInfo);
        builder.append(", dbgroups=");
        builder.append(dbgroups);
        builder.append("]");
        return builder.toString();
    }

}

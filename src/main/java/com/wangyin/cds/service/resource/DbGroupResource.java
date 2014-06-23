package com.wangyin.cds.service.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangyin.cds.service.dal.ext.datainterface.DbGroupExtDAO;
import com.wangyin.cds.service.model.DbGroupExt;
import com.wangyin.cds.service.util.MybatisUtil;

/**
 * Root resource (exposed at "dbgroup" path)
 */
@Path("dbgroup")
public class DbGroupResource {
    private static Logger logger = LoggerFactory.getLogger(DbClusterResource.class);
    
    /** 数据库群组信息扩展查询接口DAL */
    private DbGroupExtDAO dbGroupExtDAO;

    /**
     * 根据集群(簇)名称,获取某一集群的全局组信息和所有工作组信息
     * 
     * @return String that will be returned as a application/json response.
     */
    @GET
    @Path("/{clusterName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DbGroupExt> getDbGroupExtInfo(@PathParam("clusterName") String clusterName) {
        dbGroupExtDAO = MybatisUtil.getMapper(DbGroupExtDAO.class);
        if (dbGroupExtDAO == null) {
            logger.error("无法获取dbGroupExtDAO, 获取集群中全局组信息和所有工作组信息失败");
            return null;
        }
        return dbGroupExtDAO.getDbGroupListByClusterName(clusterName);
    }

}

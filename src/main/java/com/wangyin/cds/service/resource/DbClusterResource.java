package com.wangyin.cds.service.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jersey.repackaged.com.google.common.base.Objects.ToStringHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangyin.cds.service.dal.datainterface.DbClusterDAO;
import com.wangyin.cds.service.dal.dataobject.DbClusterDO;
import com.wangyin.cds.service.model.DbCluster;
import com.wangyin.cds.service.model.convert.DbClusterUtil;
import com.wangyin.cds.service.util.MybatisUtil;

/**
 * Root resource (exposed at "dbcluster" path)
 */
@Path("dbcluster")
public class DbClusterResource {
    private static Logger logger = LoggerFactory.getLogger(DbClusterResource.class);
    
    /** 数据库集群信息查询接口DAL */
    private DbClusterDAO dbClusterDAO;

    /**
     * 根据群组名称,获取Group的数据库信息，包含数据库列表，主备
     * 
     * @return String that will be returned as a application/json response.
     */
    @GET
    @Path("/{clusterName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DbCluster> getDbGroupExtInfo(@PathParam("clusterName") String clusterName) {
        dbClusterDAO = MybatisUtil.getMapper(DbClusterDAO.class);
        if (dbClusterDAO == null) {
            logger.error("无法获取dbClusterDAO, 获取数据库集群信息失败");
            return null;
        }
        return DbClusterUtil.convert2VOList(dbClusterDAO.getDbClusterAllList());
    }
    
    
    /**
     * 根据群组名称,获取dbcluster的信息
     * 
     * @return String that will be returned as a application/json response.
     */
    @GET
    @Path("/querysingle/{clusterName}")
    @Produces(MediaType.APPLICATION_JSON)
    public DbCluster getDbCluster(@PathParam("clusterName") String clusterName) {
        dbClusterDAO = MybatisUtil.getMapper(DbClusterDAO.class);
        if (dbClusterDAO == null) {
            logger.error("无法获取dbClusterDAO, 获取数据库集群信息失败");
            return null;
        }
        DbCluster cluster = new DbCluster();
    	cluster.setClusterName(clusterName);
    	List<DbClusterDO> list = dbClusterDAO.getDbClusterAndClass(cluster, 0, 10);
        return DbClusterUtil.convert2VO(list.get(0));
    }
    
    public static void main(String[] args) {
    	DbClusterDAO dbClusterDAO = MybatisUtil.getMapper(DbClusterDAO.class);
    	DbCluster cluster = new DbCluster();
    	cluster.setClusterName("测试集群");
    	List<DbClusterDO> list = dbClusterDAO.getDbClusterAndClass(cluster, 0, 10);
    	for(DbClusterDO clusterDO : list){
    		System.out.println(clusterDO.getId());
    	}
    	
	}

}

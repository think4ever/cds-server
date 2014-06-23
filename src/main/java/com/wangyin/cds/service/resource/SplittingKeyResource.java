package com.wangyin.cds.service.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangyin.cds.service.dal.datainterface.ColumnInfoDAO;
import com.wangyin.cds.service.dal.datainterface.SplittingKeyDAO;
import com.wangyin.cds.service.dal.dataobject.ColumnInfoDO;
import com.wangyin.cds.service.dal.dataobject.SplittingKeyDO;
import com.wangyin.cds.service.dal.ext.datainterface.DepotsTableRulesExtDAO;
import com.wangyin.cds.service.dal.ext.datainterface.SplittingKeyExtDAO;
import com.wangyin.cds.service.model.ColumnInfo;
import com.wangyin.cds.service.model.DepotsTableRulesExt;
import com.wangyin.cds.service.model.SplittingKey;
import com.wangyin.cds.service.model.SplittingKeyExt;
import com.wangyin.cds.service.model.convert.ColumnInfoUtil;
import com.wangyin.cds.service.model.convert.SplittingKeyUtil;
import com.wangyin.cds.service.util.MybatisUtil;

/**
 * Root resource (exposed at "splittingKey" path)
 */
@Path("splitkey")
public class SplittingKeyResource {
    private static Logger      logger = LoggerFactory.getLogger(SplittingKeyResource.class);

    /** 切分键查询DAO */
    private SplittingKeyDAO        splittingKeyDAO;

    /** 切分键扩展查询DAO */
    private SplittingKeyExtDAO     splittingKeyExtDAO;

    /** 分库分表规则查询DAO */
    private DepotsTableRulesExtDAO depotsTableRulesExtDAO;
    
    /** 切分键列定义查询DAO */
    private ColumnInfoDAO columnInfoDAO;
    
    /**
     * 获取所有切分键列表,及切分键包含的表/字段信息
     * 
     * @return String that will be returned as a application/json response.
     */
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SplittingKeyExt> getAllSplitKeyInfoExt() {
        splittingKeyExtDAO = MybatisUtil.getMapper(SplittingKeyExtDAO.class);
        if (splittingKeyExtDAO == null) {
            logger.error("无法获取splittingKeyExtDAO, 查询切分键列表失败");
            return null;
        }

        return splittingKeyExtDAO.getAllSplittingKeyList();
    }
    
    /**
     * 根据切分键ID,获得切分键映射的Group信息，包含规则
     * 
     * @return String that will be returned as a application/json response.
     */
    @GET
    @Path("/ruleinfo/splitKeyId/{splitKeyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DepotsTableRulesExt> depotsTableRulesInfoBySplitKeyId(@PathParam("splitKeyId") int splitKeyId) {
        depotsTableRulesExtDAO = MybatisUtil.getMapper(DepotsTableRulesExtDAO.class);
        if (depotsTableRulesExtDAO == null) {
            logger.error("无法获取depotsTableRulesExtDAO, 查询切分键映射的规则信息失败");
            return null;
        }
        return depotsTableRulesExtDAO.getDepotsTableRuleBySplitKeyId(splitKeyId);
    }
    
    /**
     * 根据切分键名称,获得切分键映射的Group信息，包含规则
     * 
     * @return String that will be returned as a application/json response.
     */
    @GET
    @Path("/ruleinfo/splitName/{splitName}")
    @Produces(MediaType.APPLICATION_JSON)
    public DepotsTableRulesExt depotsTableRulesInfoById(@PathParam("splitName") String splitName) {
        depotsTableRulesExtDAO = MybatisUtil.getMapper(DepotsTableRulesExtDAO.class);
        if (depotsTableRulesExtDAO == null) {
            logger.error("无法获取depotsTableRulesExtDAO, 查询切分键映射的规则信息失败");
            return null;
        }
        return depotsTableRulesExtDAO.getDepotsTableRuleBySplitName(splitName);
    }
    
    /**
     * 根据切分键ID,获得切分键映射的Group信息，包含规则
     * 
     * @return String that will be returned as a application/json response.
     */
    @GET
    @Path("/ruleinfo/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DepotsTableRulesExt depotsTableRulesInfoById(@PathParam("id") int id) {
        depotsTableRulesExtDAO = MybatisUtil.getMapper(DepotsTableRulesExtDAO.class);
        if (depotsTableRulesExtDAO == null) {
            logger.error("无法获取depotsTableRulesExtDAO, 查询切分键映射的规则信息失败");
            return null;
        }
        return depotsTableRulesExtDAO.getDepotsTableRuleById(id);
    }
    
    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     * 
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("/getSplitKeyInfo")
    @Produces(MediaType.APPLICATION_JSON)
    public SplittingKey getSplitKeyInfo(@PathParam("splitKeyId") int splitKeyId) {
        splittingKeyDAO = MybatisUtil.getMapper(SplittingKeyDAO.class);
        if (splittingKeyDAO == null) {
            logger.error("无法获取splittingKeyDAO, 查询切分键失败");
            return null;
        }

        return SplittingKeyUtil.convert2VO(splittingKeyDAO.getSplittingKey("1"));
    }
    
    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     * 
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("/getSplitKeyByClusterId/{clusterId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SplittingKey> getSplitKeyByClusterId(@PathParam("clusterId") int clusterId) {
        splittingKeyDAO = MybatisUtil.getMapper(SplittingKeyDAO.class);
        if (splittingKeyDAO == null) {
            logger.error("无法获取splittingKeyDAO, 查询切分键失败");
            return null;
        }
        
        SplittingKey key = new SplittingKey();
        key.setClusterId(Long.valueOf(clusterId));
        List<SplittingKeyDO> keys = splittingKeyDAO.getSplittingKeyAndClass(key, 0, 20);

        return SplittingKeyUtil.convert2VOList(keys);
    }
    
    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     * 
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("/getColumsByKeyId/{keyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ColumnInfo> getColumsByKeyId(@PathParam("keyId") int keyId) {
    	columnInfoDAO = MybatisUtil.getMapper(ColumnInfoDAO.class);
         	
        if (columnInfoDAO == null) {
            logger.error("无法获取splittingKeyDAO, 查询切分键失败");
            return null;
        }
        ColumnInfo column = new ColumnInfo();
        column.setSplittingKeyId(String.valueOf(keyId));
        List<ColumnInfoDO> columns = columnInfoDAO.getColumnInfoAndClass(column, 0, 20);

        return ColumnInfoUtil.convert2VOList(columns);
    }

}

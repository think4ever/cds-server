package com.wangyin.cds.service.resource;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangyin.cds.biz.CdsEventService;
import com.wangyin.cds.common.event.CdsEvent;
import com.wangyin.cds.common.event.EventCode;
import com.wangyin.cds.service.dal.datainterface.DbInfoDbGroupRelationDAO;
import com.wangyin.cds.service.dal.dataobject.DbInfoDbGroupRelationDO;
import com.wangyin.cds.service.dal.ext.datainterface.DbInfoExtDAO;
import com.wangyin.cds.service.model.DbInfoExt;
import com.wangyin.cds.service.model.MasterOrSlaveSwitchReslt;
import com.wangyin.cds.service.util.MybatisUtil;

/**
 * Root resource (exposed at "dbinfo" path)
 * 
 * @author wymaoxiaoliang
 */
@Path("dbinfo")
public class DbInfoResource {
    private static Logger logger = LoggerFactory.getLogger(DbInfoResource.class);

    /** 数据库扩展信息查询接口 */
    private DbInfoExtDAO  dbInfoExtDAO;

    /**
     * 根据群组ID,获取Group的数据库信息，包含数据库列表，主备
     * 
     * @return String that will be returned as a application/json response.
     */
    @GET
    @Path("/groupName/{groupName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DbInfoExt> getDbInfoListByGroupName(@PathParam("groupName")
    String groupName) {
        dbInfoExtDAO = MybatisUtil.getMapper(DbInfoExtDAO.class);
        if (dbInfoExtDAO == null) {
            logger.error("无法获取dbGroupExtDAO, 获取Group的数据库信息失败");
            return null;
        }
        return dbInfoExtDAO.getDbInfoListByGroupName(groupName);
    }

    /**
     * 根据群组名称,获取Group的数据库信息，包含数据库列表，主备
     * 
     * @return String that will be returned as a application/json response.
     */
    @GET
    @Path("/groupId/{groupId}/m-s-switch")
    @Produces(MediaType.APPLICATION_JSON)
    public MasterOrSlaveSwitchReslt getSwithMasterOrSlaveByGroupId(@PathParam("groupId")
    int groupId) {
        MasterOrSlaveSwitchReslt result = new MasterOrSlaveSwitchReslt();
        if (groupId == 0) {
            logger.error("无法的的groupId,主备切换失败");
            result.setSuccess(false);
            result.setMsg("无法的的groupId主备切换失败");
            return result;
        }
        result.setGroupId(Long.valueOf(groupId));
        DbInfoExtDAO dbInfoExtDAO = MybatisUtil.getMapper(DbInfoExtDAO.class);
        if (dbInfoExtDAO == null) {
            logger.error("无法获取dbGroupExtDAO, 主备切换失败");
            result.setSuccess(false);
            result.setMsg("DAL异常无法操作数据库");
            return result;
        }
        List<DbInfoExt> dbExtList = dbInfoExtDAO.getDbInfoListByGroupId(groupId);
        if (dbExtList.size() != 2) {
            logger.error("Group中的数据库主备配置有问题.");
            result.setSuccess(false);
            result.setMsg("Group中的数据库主备配置有问题");
            return result;
        }
        DbInfoExt diExt1 = dbExtList.get(0);
        DbInfoExt diExt2 = dbExtList.get(1);
        if (diExt1 == null
            || diExt2 == null
            || ("Master".equals(diExt1.getMasterOrSlave()) && "Master".equals(diExt2.getMasterOrSlave()) || "Slave"
                .equals(diExt1.getMasterOrSlave()) && "Slave".equals(diExt2.getMasterOrSlave()))) {
            logger.error("Group中的数据库主备配置有问题.");
            result.setSuccess(false);
            result.setMsg("Group中的数据库主备配置有问题");
            return result;
        }

        //主备切换(没效率没事务没美感,搓到屌炸天:D)
        DbInfoDbGroupRelationDAO dao = MybatisUtil.getMapper(DbInfoDbGroupRelationDAO.class);
        //库1
        DbInfoDbGroupRelationDO do1 = new DbInfoDbGroupRelationDO();
        do1.setDbGroupId(String.valueOf(groupId));
        do1.setDbInfoId(String.valueOf(diExt1.getId()));
        do1.setMasterOrSlave("Master".equals(diExt1.getMasterOrSlave()) ? "Slave" : "Master");
        dao.dbMasterOrSlaveSwitch(do1);
        diExt1.setMasterOrSlave(do1.getMasterOrSlave());
        //库2
        DbInfoDbGroupRelationDO do2 = new DbInfoDbGroupRelationDO();
        do2.setDbGroupId(String.valueOf(groupId));
        do2.setDbInfoId(String.valueOf(diExt2.getId()));
        do2.setMasterOrSlave("Master".equals(diExt2.getMasterOrSlave()) ? "Slave" : "Master");
        dao.dbMasterOrSlaveSwitch(do2);
        diExt2.setMasterOrSlave(do2.getMasterOrSlave());
        
        Map<String, DbInfoExt> extMap = result.getMasterOrSlaveDbInfo();
        extMap.put(diExt1.getMasterOrSlave(), diExt1);
        extMap.put(diExt2.getMasterOrSlave(), diExt2);
        result.setSuccess(true);
        
        //PUSH事件到缓存中
        CdsEventService.pushCdsEvent(buildSwitchEvent(result));
        
        return result;
    }
    
    /**
     * 根据主备切换结果生成事件CdsEvent对象
     * 
     * @param result
     * @return
     */
    private static CdsEvent buildSwitchEvent(MasterOrSlaveSwitchReslt result){
        CdsEvent switchEvent = new CdsEvent(EventCode.DB_MASTER_SLAVE_SWTICH);
        if(result.isSuccess()){
            switchEvent.putExtField("groupId", result.getGroupId().toString());
            switchEvent.putExtField("groupName",  result.getGroupName());
            switchEvent.putExtField("masterDbIp",  result.getMasterOrSlaveDbInfo().get("Master") .getIp());
            switchEvent.putExtField("masterDbPort",  result.getMasterOrSlaveDbInfo().get("Master") .getPort());
            switchEvent.putExtField("masterDbName",  result.getMasterOrSlaveDbInfo().get("Master") .getDbName());
        }
        
        return switchEvent;
    }

    /**
     * 根据群组名称,获取Group的数据库信息，包含数据库列表，主备
     * 
     * @return String that will be returned as a application/json response.
     */
    @GET
    @Path("/groupId/{groupId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DbInfoExt> getDbInfoListByGroupId(@PathParam("groupId")
    int groupId) {
        dbInfoExtDAO = MybatisUtil.getMapper(DbInfoExtDAO.class);
        if (dbInfoExtDAO == null) {
            logger.error("无法获取dbGroupExtDAO, 获取Group的数据库信息失败");
            return null;
        }
        return dbInfoExtDAO.getDbInfoListByGroupId(groupId);
    }
    
    /**
     * 根据主备类型获取所有Group的数据库信息
     * 
     * @return String that will be returned as a application/json response.
     */
    @GET
    @Path("/type/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DbInfoExt> getDbInfoListByGroupId(@PathParam("type")
    String type) {
        dbInfoExtDAO = MybatisUtil.getMapper(DbInfoExtDAO.class);
        if (dbInfoExtDAO == null) {
            logger.error("无法获取dbGroupExtDAO, 获取Group的数据库信息失败");
            return null;
        }
        return dbInfoExtDAO.getDbInfoListByType(type);
    }

}

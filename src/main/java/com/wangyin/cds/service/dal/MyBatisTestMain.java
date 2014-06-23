package com.wangyin.cds.service.dal;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangyin.cds.service.dal.datainterface.DbInfoDbGroupRelationDAO;
import com.wangyin.cds.service.dal.datainterface.SplittingKeyDAO;
import com.wangyin.cds.service.dal.dataobject.DbInfoDbGroupRelationDO;
import com.wangyin.cds.service.dal.dataobject.SplittingKeyDO;
import com.wangyin.cds.service.dal.ext.datainterface.DbGroupExtDAO;
import com.wangyin.cds.service.dal.ext.datainterface.DbInfoExtDAO;
import com.wangyin.cds.service.dal.ext.datainterface.DepotsTableRulesExtDAO;
import com.wangyin.cds.service.dal.ext.datainterface.SplittingKeyExtDAO;
import com.wangyin.cds.service.model.DbGroupExt;
import com.wangyin.cds.service.model.DbInfoExt;
import com.wangyin.cds.service.model.DepotsTableRulesExt;
import com.wangyin.cds.service.model.MasterOrSlaveSwitchReslt;
import com.wangyin.cds.service.model.SplittingKeyExt;
import com.wangyin.cds.service.util.MybatisUtil;

public class MyBatisTestMain {
    private static Logger logger = LoggerFactory.getLogger(MyBatisTestMain.class);

    public static void main(String[] args) {
        try {
            logger.info(">>>>>>>>>>>BEGIN: CDS SERVER DAO测试<<<<<<<<<<<<<<<");
            // 切分键DAO测试
            SplittingKeyDAO splittingKeyDAO = MybatisUtil.getMapper(SplittingKeyDAO.class);
            String splittingKeyId = "1";
            SplittingKeyDO splitKeyDO = splittingKeyDAO.getSplittingKey(splittingKeyId);
            //logger.info("SplitName:" + splitKeyDO.getSplitName());
            //logger.info("CreateBy:" + splitKeyDO.getCreateBy());
            //logger.info("CreationDate:" + splitKeyDO.getCreationDate());
            logger.info("切分键DAO测试" + splitKeyDO);

            // 群组查询DAO测试
            logger.info("-----群组查询DAO测试: 根据集群名称查询群组列表------");
            String clusterName = "33";
            DbGroupExtDAO dbGroupExtDAO = MybatisUtil.getMapper(DbGroupExtDAO.class);
            List<DbGroupExt> dbGroupExtList = dbGroupExtDAO.getDbGroupListByClusterName(clusterName);
            logger.info(String.valueOf(dbGroupExtList));

            // 数据库查询DAO测试
            logger.info("-----数据库查询DAO测试: 1.根据群组名称查询数据库列表(包含主备信息)------");
            String groupName = "group2db";
            DbInfoExtDAO dbInfoExtDAO = MybatisUtil.getMapper(DbInfoExtDAO.class);
            List<DbInfoExt> list = dbInfoExtDAO.getDbInfoListByGroupName(groupName);
            logger.info(String.valueOf(list));

            logger.info("-----数据库查询DAO测试: 2.根据群组ID查询数据库列表(包含主备信息)------");
            int groupId = 12;
            List<DbInfoExt> list2 = dbInfoExtDAO.getDbInfoListByGroupId(groupId);
            logger.info(String.valueOf(list2));

            // 切分键DAO测试
            logger.info("-----切分键DAO测试: 查询所有切分姐列表包含列信息------");
            SplittingKeyExtDAO splittingKeyExtDAO = MybatisUtil.getMapper(SplittingKeyExtDAO.class);
            List<SplittingKeyExt> splittingKeyExtList = splittingKeyExtDAO.getAllSplittingKeyList();
            logger.info(String.valueOf(splittingKeyExtList));

            // 分库分表规则查询DAO测试
            logger.info("-----分库分表规则查询DAO测试: 1.根据分库分表规则ID查询分库分表规则信息(含映射的群组信息)------");
            int depotsTableRuleById = 1;
            DepotsTableRulesExtDAO depotsTableRulesExtDAO = MybatisUtil.getMapper(DepotsTableRulesExtDAO.class);
            DepotsTableRulesExt depotsTableRulesExt = depotsTableRulesExtDAO
                .getDepotsTableRuleById(depotsTableRuleById);
            logger.info(String.valueOf(depotsTableRulesExt));

            logger.info("-----分库分表规则查询DAO测试: 2.根据切分键ID查询分库分表规则信息(含映射的群组信息)------");
            int splitKeyId = 4;
            List<DepotsTableRulesExt> depotsTableRulesExt2 = depotsTableRulesExtDAO.getDepotsTableRuleBySplitKeyId(splitKeyId);
            logger.info(String.valueOf(depotsTableRulesExt2));

            logger.info("-----分库分表规则查询DAO测试: 3.根据切分键名称查询分库分表规则信息(含映射的群组信息)------");
            String splitName = "333";
            DepotsTableRulesExt depotsTableRulesExt3 = depotsTableRulesExtDAO.getDepotsTableRuleBySplitName(splitName);
            logger.info(String.valueOf(depotsTableRulesExt3));
            logger.info(">>>>>>>>>>>END: CDS SERVER DAO测试<<<<<<<<<<<<<<<");

            logger.info(">>>>>>>>>>>B: 主备切换DAO测试<<<<<<<<<<<<<<<");
            MasterOrSlaveSwitchReslt result = swithMasterOrSlaveByGroupId(11);
            logger.info("Result:" + result);
            logger.info(">>>>>>>>>>>E: 主备切换DAO测试<<<<<<<<<<<<<<<");
        } catch (Exception e) {
            logger.error("MyBatis test fail !!!", e);
        }
    }

    /**
     * 测试主备切换
     * 
     * @param groupId
     * @return
     */
    private static MasterOrSlaveSwitchReslt swithMasterOrSlaveByGroupId(int groupId) {
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
        int r1 = dao.dbMasterOrSlaveSwitch(do1);
        diExt1.setMasterOrSlave(do1.getMasterOrSlave());
        logger.info(String.valueOf(r1));
        //库2
        DbInfoDbGroupRelationDO do2 = new DbInfoDbGroupRelationDO();
        do2.setDbGroupId(String.valueOf(groupId));
        do2.setDbInfoId(String.valueOf(diExt2.getId()));
        do2.setMasterOrSlave("Master".equals(diExt2.getMasterOrSlave()) ? "Slave" : "Master");
        int r2 = dao.dbMasterOrSlaveSwitch(do2);
        diExt2.setMasterOrSlave(do2.getMasterOrSlave());
        logger.info(String.valueOf(r2));

        Map<String, DbInfoExt> extMap = result.getMasterOrSlaveDbInfo();
        extMap.put(diExt1.getMasterOrSlave(), diExt1);
        extMap.put(diExt2.getMasterOrSlave(), diExt2);

        return result;
    }

}

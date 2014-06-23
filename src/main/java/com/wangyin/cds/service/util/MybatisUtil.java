package com.wangyin.cds.service.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MybatisUtil {
    private static Logger            logger           = LoggerFactory.getLogger(MybatisUtil.class);
    private final static String      resourceLocation = "mybatis-config.xml";
    private static SqlSessionFactory sqlSessionFactory;

    /**
     * 获取SqlSession
     * 
     * @return
     */
    public static SqlSession getSqlSession() {
        // 实例化session工厂
        if (sqlSessionFactory == null) {
            Reader reader = null;
            try {
                // 从类路径加载  mybatis配置
                reader = Resources.getResourceAsReader(resourceLocation);
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            } catch (IOException e) {
                logger.error("初始化SqlSessionFactory失败", e);
                return null;
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        logger.error("关闭资源文件Reader失败", e);
                    }
                }
            }
        }

        return sqlSessionFactory.openSession();
    }
    
    /**
     * 根据environmentId获取不同的sessionFactory
     * @param reader
     * @param environmentId
     * @return
     */
    private static SqlSessionFactory getSqlSessionFactory(Reader reader, String environmentId) {
        return new SqlSessionFactoryBuilder().build(reader, environmentId);
    }

    /**
     * Retrieves a mapper.
     * 
     * @param <T> the mapper type
     * @param type Mapper interface class
     * @return a mapper bound to this SqlSession
     */
    public static <T> T getMapper(Class<T> type) {
        SqlSession session = MybatisUtil.getSqlSession();
        if (session == null) {
            return null;
        }
        try {
            session.getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("设置事务自动提交异常", e);
        }
        T mapper = session.getMapper(type);
        session.commit();

        return mapper;
    }
}

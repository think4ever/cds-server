package com.wangyin.cds.common.db.util;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Test;

import com.wangyin.cds.common.db.dbconnection.DataSourcePoolUtils;

public class DbUtilsTest {

    @Test
    public void test() {
        try {
            DataSource ds = DataSourcePoolUtils.getDataSource("10086_test");
            System.out.println(DbUtils.isExistsTbl(ds, "mall_users"));
            
            System.out.println(DbUtils.isExistsTbl(ds, "mydb", "mall_users"));
            System.out.println(DbUtils.isExistsTbl(ds, "mydb", "app"));
            System.out.println(DbUtils.isExistsTbl(ds, "test", "mall_users"));
        } catch (Exception e) {
            fail("error");
        }
    }

}

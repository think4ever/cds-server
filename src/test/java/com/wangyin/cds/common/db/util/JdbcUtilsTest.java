/**
 * 
 */
package com.wangyin.cds.common.db.util;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;

import com.wangyin.cds.common.db.dbconnection.DataSourcePoolUtils;

/**
 * @author wymaoxiaoliang
 *
 */
public class JdbcUtilsTest {

    @Test
    public void test01() {
        try {
            String sql = "select * from `mall_users$lookup`";
            DataSource ds = DataSourcePoolUtils.getDataSource("10086_test");
            List<Map<String, Object>>  list = JdbcUtils.executeQuery(ds, sql, null);
            for(Map<String, Object> map: list){
                System.out.println(map);
            }
        } catch (Exception e) {
            // TODO: handle exception
            fail(e.getMessage());
        }
    }
    
    @Test
    public void test02() {
        try {
            String sql = "INSERT INTO `test`.`mall_users$lookup` (`col_name`, `col_value`, `group_7`) VALUES ('Name', 'lim2x', 3);";
            DataSource ds = DataSourcePoolUtils.getDataSource("10086_test");
            JdbcUtils.execute(ds, sql, null);
        } catch (Exception e) {
            // TODO: handle exception
            fail(e.getMessage());
        }
    }
    
    @Test
    public void test03() {
        try {
            DataSource ds = DataSourcePoolUtils.getDataSource("10086_test");
            List<String> list = JdbcUtils.queryColumnLabel(ds.getConnection(), "mall_users");
            System.out.println(list);
        } catch (Exception e) {
            // TODO: handle exception
            fail(e.getMessage());
        }
    }

}

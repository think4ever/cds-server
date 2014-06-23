/**
 * 
 */
package com.wangyin.cds.idxsync.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 分库分表表名&分组名工具类
 * 
 * @author wymaoxiaoliang
 */
public class TableNameUtil {
    /**
     * 根据表名判断是否为分表
     * 
     * @param name 表名
     * @return
     */
    public static boolean isSubTbl(String name) {
        Pattern pattern = Pattern.compile("^\\w+_\\d{2}$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    
    /**
     * 根据表名判断是否为分组
     * 
     * @param name 组名
     * @return
     */
    public static boolean isSubGroup(String name) {
        Pattern pattern = Pattern.compile("^\\w+_\\d{1,2}$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    /**
     * 获取分库分表号
     * 
     * @param tblName
     * @return
     */
    public static int getTblNum(String tblName) {
        String number = StringUtils.substringAfterLast(tblName, "_");
        if (StringUtils.isNumeric(number)) {
            return Integer.parseInt(number);
        }
        return 0;
    }
    
    /**
     * 获取分库分表虚表名称
     * 
     * @param tblName
     * @return
     */
    public static String getTblName(String tblName) {
        return StringUtils.substringBeforeLast(tblName, "_");
    }

    /**
     * Main
     * 
     * @param args
     */
    public static void main(String args[]) {
        System.out.println(isSubTbl("user_test_01"));
        System.out.println(isSubTbl("user_0_01"));
        System.out.println(isSubTbl("user_0_test"));
        
        System.out.println(getTblNum("user_test_01"));
        System.out.println(getTblNum("user_0_012"));
        System.out.println(getTblNum("user_0_test"));
        
        System.out.println(getTblName("user_test_01"));
        System.out.println(getTblName("user_0_012"));
        System.out.println(getTblName("user_0_test"));
        
        System.out.println((2 << 1));
        System.out.println((2 << 1) ^ 1);
    }
}

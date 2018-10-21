package com.lmis.common.util;

import com.lmis.common.dataFormat.ObjectUtils;

/**
 * @ClassName: SqlUtils
 * @Description: TODO(SQL工具类)
 * @author Ian.Huang
 * @date 2018年3月19日 下午4:40:23
 * 
 */
public class SqlUtils{

    private static final String CREATE = " CREATE ";

    private static final String ALTER = " ALTER ";

    private static final String DROP = " DROP ";

    private static final String INSERT = " INSERT ";

    private static final String UPDATE = " UPDATE ";

    private static final String DELETE = " DELETE ";
    
    private static final String TRUNCATE = " TRUNCATE ";
    
    private static final String REPLACE = " REPLACE ";
    
    private static final String LOCK = " LOCK ";



    /**
     * @Title: checkSqlSecurity
     * @Description: TODO(校验sql安全性)
     * @param sql
     * @return: boolean
     * @author: Ian.Huang
     * @date: 2018年3月19日 下午4:40:20
     */
    public static boolean checkSqlSecurity(String sql){

        // 校验create,alter,drop,insert,update,delete不可存在
        if (!ObjectUtils.isNull(sql)){
            sql = " " + sql.toUpperCase();
            sql = sql.replaceAll("\n", " ");
            sql = sql.replaceAll("\t", " ");
            sql = sql.replaceAll("\r", " ");
            if (sql.contains(CREATE) || sql.contains(ALTER) || sql.contains(DROP) 
            		|| sql.contains(INSERT) || sql.contains(UPDATE) 
            		|| sql.contains(DELETE) || sql.contains(TRUNCATE)
            		|| sql.contains(LOCK) || sql.contains(REPLACE)){
                return false;
            }else{
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args){
        System.out.println(checkSqlSecurity("DELETE FROM"));
    }

}

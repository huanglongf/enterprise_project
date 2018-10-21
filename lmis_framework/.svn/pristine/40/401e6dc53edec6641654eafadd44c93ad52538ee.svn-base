package com.lmis.common.util;


public class SqlCombineHelper{
    /***
     * 于用来提供线程内部的局部变量
     * 
     * @author xuyisu
     * @date 2018年05月10日 下午3:40:23
     */
    protected static final ThreadLocal<Boolean> LOCAL_SQLCOMBINE = new ThreadLocal<Boolean>();

    /**
     * 拦截数据权限
     * 
     * @author xuyisu
     * @date 2018年05月10日 下午3:40:23
     * @param params
     */
    public static Boolean startSqlCombine(boolean params){
        SqlCombineHelper.setLocalSqlCombine(params);
        return params;
    }

    /**
     * 设置本地变量
     * 
     * @author xuyisu
     * @date 2018年05月10日 下午3:40:23
     */
    public static void setLocalSqlCombine(Boolean params){
        LOCAL_SQLCOMBINE.set(params);
    }

    /**
     * 获取本地变量
     * 
     * @author xuyisu
     * @date 2018年05月10日 下午3:40:23
     */
    public static Boolean getLocalSqlCombine(){
        return LOCAL_SQLCOMBINE.get();
    }

    /**
     * 移除本地变量
     * 
     * @author xuyisu
     * @date 2018年05月10日 下午3:40:23
     */
    public static void clearLocalPage(){
        LOCAL_SQLCOMBINE.remove();
    }
}

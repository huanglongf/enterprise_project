package com.jumbo.util;



/**
 * ZK开关节点全局常量更改
 * 
 * @author bin.hu
 *
 */
public class ZkRootConstants {


    private String zkRoot;

    public static Boolean createRoot = true;

    /**
     * 设置直连开关
     * 
     * @param zkRoot
     */
    public static void setCreateRoot(String zkRoot) {
        if (!StringUtil.isEmpty(zkRoot)) {
            if (zkRoot.equals("1")) {
                createRoot = true;
            }
            if (zkRoot.equals("0")) {
                createRoot = false;
            }
        }
    }

    public void init() {
        setCreateRoot(zkRoot);
    }

    public String getZkRoot() {
        return zkRoot;
    }

    public void setZkRoot(String zkRoot) {
        this.zkRoot = zkRoot;
    }


}

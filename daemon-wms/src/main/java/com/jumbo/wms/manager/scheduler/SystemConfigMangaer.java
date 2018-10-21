package com.jumbo.wms.manager.scheduler;


public interface SystemConfigMangaer {


    /**
     * 初始化
     * 
     * @param map
     */
    // void init();

    /**
     * 通过key获取配置的值
     * 
     * @param key
     * @return
     */
    String getValue(String key);
}

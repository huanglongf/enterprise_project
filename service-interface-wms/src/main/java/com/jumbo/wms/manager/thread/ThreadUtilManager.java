package com.jumbo.wms.manager.thread;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.config.ThreadConfig;

import loxia.dao.Pagination;
import loxia.dao.Sort;


public interface ThreadUtilManager extends BaseManager {

    /**
     * 获取所有线程池配置
     * 
     * @author jingkai
     * @param sysKey
     * @return
     */
    List<ThreadConfig> getAllThreadPools(String sysKey);

    /**
     * 获取线程池配置
     * 
     * @author jingkai
     * @param id
     * @return
     */
    ThreadConfig getThreadConfigById(Long id);

    /**
     * 更新配置线程数
     * 
     * @author jingkai
     * @param id
     * @param count
     */
    void updateThreadCount(Long id, int count);
    /**
     * 获取所有线程池配置
     * 
     * @author shuailiang
     * @param sysKey
     * @return
     */
    Pagination<ThreadConfig> getAllThreadConfigPools(int start, int pageSize, Sort[] sorts);
    /**
     * 更新或添加线程池
     * 
     * @author shuailiang
     * @param sysKey
     * @return
     */
	String saveThreadConfig(ThreadConfig threadConfig,String path);

    /**
     * 根绝编码获取线程池配置
     * 
     * @author jingkai
     * @param key
     * @param sysKey
     * @return
     */
    ThreadConfig getByCodeAndSysKey(String key, String sysKey);

}

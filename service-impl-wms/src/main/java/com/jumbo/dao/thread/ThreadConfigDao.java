package com.jumbo.dao.thread;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.config.ThreadConfig;
import com.jumbo.wms.model.system.SysSchedulerTask;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;


@Transactional
public interface ThreadConfigDao extends GenericEntityDao<ThreadConfig, Long> {


    /**
     * 查询对应系统下的线程池配置
     * 
     * @author jingkai
     * @param sysKey
     * @return
     */
    @NamedQuery
    List<ThreadConfig> getAllBySysKey(@QueryParam(value = "sysKey") String sysKey);
    
    /**
     * 查询系统下的线程池配置
     * 
     * @author 
     * @param 
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<ThreadConfig> getAllThreadConfig(int start, int pageSize, Sort[] sorts,RowMapper<ThreadConfig> rowMapper);
    /**
     * 获取线程池Id
     * 
     * @author 
     * @param 
     * @return
     */
    @NamedQuery
	Long getThreadId();
    /**
     * 根据id获取线程池
     * 
     * @author 
     * @param id
     * @return
     */
    @NamedQuery
	ThreadConfig checkThreadConfig(@QueryParam(value = "id") Long id);

    /**
     * 查询线程执行线程编码线程池配置
     * 
     * @author jingkai
     * @param code
     * @param sysKey
     * @return
     */
    @NamedQuery
    ThreadConfig getByCodeAndSysKey(@QueryParam(value = "code") String code, @QueryParam(value = "sysKey") String sysKey);

}

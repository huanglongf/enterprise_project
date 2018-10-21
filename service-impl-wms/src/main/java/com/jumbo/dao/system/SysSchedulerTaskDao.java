package com.jumbo.dao.system;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.system.SysSchedulerTask;

@Transactional
public interface SysSchedulerTaskDao extends GenericEntityDao<SysSchedulerTask, Long> {

    @NamedQuery
    List<SysSchedulerTask> getByLifecycle(@QueryParam("lifecycle") Integer lifecycle, @QueryParam("node") Integer node);

    /**
     * 分页查询定时任务配置列表
     * 
     * @param beanName
     * @param methodName
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<SysSchedulerTask> findSysSchedulerTaskList(int start, int pageSize, Sort[] sorts, @QueryParam("beanName") String beanName, @QueryParam("methodName") String methodName, RowMapper<SysSchedulerTask> rowMapper);

    /**
     * 
     * @param whId
     * @return
     */
    @NativeQuery
    List<Long> findSysSchedulerTaskByMethodNameAndWhId(@QueryParam("whId") Long whId, SingleColumnRowMapper<Long> singleColumnRowMapper);
    
    @NativeUpdate
    void updateSysSchedulerTaskByArgs(@QueryParam("whId") String whId, @QueryParam("needCompensate") int needCompensate);
}

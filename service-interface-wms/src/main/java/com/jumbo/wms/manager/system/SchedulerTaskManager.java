package com.jumbo.wms.manager.system;

import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.system.SysSchedulerTask;

public interface SchedulerTaskManager extends BaseManager {

    /**
     * 通过ids批量启用或禁用SchedulerTask 设置lifecycle =0 或 1
     * 
     * @param ids
     * @return
     */
    int enableOrDisableSchedulerTaskByIds(List<Long> ids, Integer state);

    public boolean deleteSkuType(Long id);

    /**
     * 通过ids批量删除SchedulerTask 设置lifecycle =2
     * 
     * @param ids
     * @return
     */
    void removeSchedulerTaskByIds(List<Long> ids);

    public String updateSkuCode(String code);

    /**
     * 获取有效的SchedulerTask列表 lifecycle =1
     * 
     * @param ids
     * @return
     */
    List<SysSchedulerTask> findAllEffectSchedulerTaskList(Integer node);

    SysSchedulerTask findSchedulerTaskById(Long id);

    SysSchedulerTask saveSchedulerTask(SysSchedulerTask schedulerTask);

    public Pagination<SkuCommand> findSkubySkuCode(int start, int pageSize, Sort[] sorts, String code);


    public Pagination<SysSchedulerTask> findSysSchedulerTaskPage(int start, int pageSize, Sort[] sorts, SysSchedulerTask schedulerTask);
}

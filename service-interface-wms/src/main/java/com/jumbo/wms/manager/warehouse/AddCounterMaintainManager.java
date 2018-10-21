package com.jumbo.wms.manager.warehouse;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.system.ChooseOptionCommand;

public interface AddCounterMaintainManager extends BaseManager{

    /**
     * 查询 新增秒杀订单计数器功能维护
     * @param start
     * @param size
     * @param sorts
     * @return
     */
    Pagination<ChooseOptionCommand> findSecKillMaintain(int start, int size, Sort[] sorts);
    
    /**
     * 新增秒杀订单计数器功能维护  修改常量值
     * @param id
     * @param optionValue
     */
    void updateOptionValue( Long id, String optionValue);
}

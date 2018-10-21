package com.jumbo.dao.warehouse;


import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.system.ChooseOptionCommand;

@Transactional
public interface AddCounterMaintainDao extends GenericEntityDao<ChooseOption, Long>{

    /**
     *  查询 新增秒杀订单计数器功能维护
     */
    @NativeQuery(pagable = true)
    Pagination<ChooseOptionCommand> findSecKillMaintain(int start, int size, Sort[] sorts,
            BeanPropertyRowMapper<ChooseOptionCommand> role);
    
    /**
     * 修改常量值
     */
    @NativeUpdate
    void updateOptionValue(@QueryParam("id") Long id,@QueryParam("optionValue") String optionValue);
}

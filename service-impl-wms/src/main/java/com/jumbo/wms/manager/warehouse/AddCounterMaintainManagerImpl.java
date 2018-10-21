package com.jumbo.wms.manager.warehouse;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.warehouse.AddCounterMaintainDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.system.ChooseOptionCommand;

@Transactional
@Service("addCounterMaintainManager")
public class AddCounterMaintainManagerImpl extends BaseManagerImpl implements AddCounterMaintainManager {

    private static final long serialVersionUID = 1L;

    @Autowired
    private AddCounterMaintainDao counterMaintainDao;

    /**
     * 查询 新增秒杀订单计数器功能维护
     * 
     * @param start
     * @param size
     * @param sorts
     * @return
     */
    public Pagination<ChooseOptionCommand> findSecKillMaintain(int start, int size, Sort[] sorts) {

        return counterMaintainDao.findSecKillMaintain(start, size, sorts, new BeanPropertyRowMapper<ChooseOptionCommand>(ChooseOptionCommand.class));

    }

    /**
     * 新增秒杀订单计数器功能维护 修改常量值
     */
    public void updateOptionValue(Long id, String optionValue) {
        counterMaintainDao.updateOptionValue(id, optionValue);

    }

}

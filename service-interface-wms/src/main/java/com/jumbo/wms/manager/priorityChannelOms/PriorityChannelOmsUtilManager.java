package com.jumbo.wms.manager.priorityChannelOms;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.PriorityChannelOms;

import loxia.dao.Pagination;
import loxia.dao.Sort;


public interface PriorityChannelOmsUtilManager extends BaseManager {

    /**
     * 获取所有店铺
     * 
     * @author shuailiang
     * @param sorts 
     * @param j 
     * @param i 
     * @param sysKey
     * @return
     */
	Pagination<PriorityChannelOms> getAllPriorityChannelOms(int i, int j, Sort[] sorts);
    /**
     * 更新或添加店铺
     * 
     * @author shuailiang
     * @param sysKey
     * @return
     */
	String addPriorityChannelOms(PriorityChannelOms priorityChannelOms);
	/**
     * 更新店铺占比
     * 
     * @author shuailiang
     * @param sysKey
     * @return
     */
	String updateChooseOption(String optionValue);
	/**
     * 更新店铺占比
     * 
     * @author shuailiang
     * @param sysKey
     * @return
     */
	ChooseOption buildChooseOption();

}

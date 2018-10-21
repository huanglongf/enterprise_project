package com.jumbo.wms.manager.warehouse;

import java.util.Date;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.ConsumptiveMaterialUseQueryCommand;

/**
 * 耗材使用情况业务逻辑层接口
 * 
 * @author jinlong.ke
 * 
 */
public interface ConsumptiveMaterialUseQueryCommandManager extends BaseManager {

    Pagination<ConsumptiveMaterialUseQueryCommand> findCmUseList(int start, int pageSize, Date date, Date date2, ConsumptiveMaterialUseQueryCommand cm, Long id, Sort[] sorts);

}

package com.jumbo.wms.manager.warehouse;

import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.WhReplenish;
import com.jumbo.wms.model.warehouse.WhReplenishCommand;
import com.jumbo.wms.model.warehouse.WhReplenishLineCommand;

/**
 * 库内补货报表业务逻辑接口方法
 * 
 * @author jinlong.ke
 * 
 */
public interface WhReplenishManager extends BaseManager {
    
    /**
     * 新建库内补货报表头
     * 
     * @param wr
     * @param currentOu
     */
    Integer createNewWhReplenish(WhReplenish wr, OperationUnit currentOu);

    /**
     * 查询当前仓库现有补货报表
     * 
     * @param start
     * @param pageSize
     * @param id
     * @param sorts
     * @return
     */
    Pagination<WhReplenishCommand> findAllReplenishOrder(int start, int pageSize, Long id, Sort[] sorts);

    /**
     * 查询所有需要生成明细的补货单
     * 
     * @return
     */
    List<WhReplenish> findAllNeedDetailOrder();

    void updateWrStatusAndReplenishTime(WhReplenish wr);

    void generateDetail(WhReplenish wr);

    /**
     * 取消补货申请
     * 
     * @param wrId
     */
    void cancelReplenishOrderById(Long wrId);

    /**
     * 根据补货申请id查看补货报表明细
     * 
     */
    List<WhReplenishLineCommand> findWhReplenishLienById(Long wrId);

}

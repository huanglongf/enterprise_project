package com.jumbo.wms.manager.odo;

import java.util.List;
import java.util.Map;

import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.odo.Odo;
import com.jumbo.wms.model.odo.OdoCommand;
import com.jumbo.wms.model.odo.OdoLine;
import com.jumbo.wms.model.warehouse.InventoryStatus;

import loxia.dao.Pagination;

public interface OdoManager {

    public Pagination<OdoCommand> findOdoByParams(int start, int pageSize, Map<String, Object> params);

    /**
     * 根据仓库id获取同公司下的所有仓库
     * 
     * @param ouId
     * @param sorts
     * @return
     */
    public List<OperationUnit> findWarehouseOuListByOuId(Long ouId);


    void saveOdoLine(List<OdoLine> odoLineList, OdoLine odoLine, Odo odo);


    public void updateOdoStatus(String code, int status, Long id);

    /**
     * 根据仓库id获取公司下的所有库存状态
     * 
     * @param ouId
     * @param sorts
     * @return
     */
    public List<InventoryStatus> findInvStatusListByCompany(Long ouId);

    /**
     * 创建出库单
     * 
     * @param id
     */
    public String createOdoOutStaById(Long id);

    /**
     * 修改出库反馈状态
     * 
     * @param intfcCfmId
     * @param odoId
     * @param odoLineList
     */
    public void modifyOdoOutSendStatus(Long intfcCfmId, Long odoId, List<OdoLine> odoLineList);
}

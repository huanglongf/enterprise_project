package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.util.List;

import loxia.support.excel.ReadStatus;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.warehouse.BetweenLabaryMoveCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;

public interface NewWareHouseManager extends BaseManager {
    
    /**
     * 创建库间移动申请作业单
     * 
     * @param staCode
     * @param betwenMoveCmd
     * @param user
     * @param ou
     * @param staLineCmd
     */
    StockTransApplication createBetweenMoveSta(StockTransApplication stacode, BetweenLabaryMoveCommand betwenMoveCmd, User user, OperationUnit ou, List<BetweenLabaryMoveCommand> staLineCmd) throws Exception;
    
    /**
     * 转店创建
     * 
     * @param sta
     * @param file
     * @param ownerid
     * @param addiownerid
     * @param ouid
     * @param cmpOuid
     * @param userid
     * @param invstatusId
     */
    ReadStatus createStaForVMITransfer(StockTransApplication sta, File file, Long ownerid, Long addiownerid, Long ouid, Long cmpOuid, Long userid, Long invstatusId) throws Exception;
}

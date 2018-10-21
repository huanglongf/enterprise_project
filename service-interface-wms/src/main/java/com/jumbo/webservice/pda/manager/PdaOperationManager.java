package com.jumbo.webservice.pda.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.pda.PdaHandOverLine;
import com.jumbo.wms.model.pda.PdaOrder;
import com.jumbo.wms.model.pda.PdaOrderCommand;
import com.jumbo.wms.model.pda.PdaOrderLine;
import com.jumbo.wms.model.pda.PdaOrderType;
import com.jumbo.wms.model.warehouse.Carton;
import com.jumbo.wms.model.warehouse.CartonLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.webservice.pda.uploadHandOverList.UploadHandOverListRequest;
import com.jumbo.webservice.pda.uploadInboundOnShelves.UploadInboundOnShelvesRequest;
import com.jumbo.webservice.pda.uploadInboundReceive.UploadInboundReceiveRequest;
import com.jumbo.webservice.pda.uploadInitiativeMoveInbound.UploadInitiativeMoveInboundRequest;
import com.jumbo.webservice.pda.uploadInitiativeMoveOutbound.UploadInitiativeMoveOutboundRequest;
import com.jumbo.webservice.pda.uploadInventoryCheck.UploadInventoryCheckRequest;
import com.jumbo.webservice.pda.uploadLibraryMovement.UploadLibraryMovementRequest;
import com.jumbo.webservice.pda.uploadReturnOrder.UploadReturnOrderRequest;

public interface PdaOperationManager extends BaseManager {
    /**
     * 上传库内移动明细
     * 
     * @param uploadLibraryMovementRequest
     */
    PdaOrder saveMovementUpload(UploadLibraryMovementRequest uploadLibraryMovementRequest);

    /**
     * 上传盘点数据
     * 
     * @param uploadInventoryCheckRequest
     */
    void saveCheckUpload(UploadInventoryCheckRequest uploadInventoryCheckRequest);

    /**
     * 上传入库数据
     * 
     * @param uploadInboundReceiveRequest
     */
    void saveUploadInbound(UploadInboundReceiveRequest uploadInboundReceiveRequest);

    /**
     * 上传上架数据
     * 
     * @param uploadInboundOnShelvesRequest
     */
    void saveOnShelvesUpload(UploadInboundOnShelvesRequest uploadInboundOnShelvesRequest);

    /**
     * Pda上传交接单数据操作
     * 
     * @param uploadHandOverListRequest
     */
    PdaOrder saveHandOverListUpload(UploadHandOverListRequest uploadHandOverListRequest);

    /**
     * 上传退仓单数据
     * 
     * @param uploadReturnOrderRequest
     */
    void saveReturnOrder(UploadReturnOrderRequest uploadReturnOrderRequest);

    /**
     * 执行主动移库
     * 
     * @param pdaOrder
     */
    void exeInnerMove(PdaOrder pdaOrder) throws Exception;

    /**
     * 更新PDAOrder 状态
     * 
     * @param pdaOrderId
     * @param memo
     */
    void updatePdaOrderStatus(Long pdaOrderId, DefaultStatus status, String memo);

    /**
     * 查询Pda操作日志
     * 
     * @param start
     * @param pageSize
     * @param string 
     * @param defaultStatus 
     * @param date4 
     * @param date3 
     * @param date2 
     * @param date 
     * @param id
     * @param sorts
     * @return
     */
    Pagination<PdaOrderCommand> findStaForPickingByModel(int start, int pageSize, Date date, Date date2, Date date3, Date date4, DefaultStatus defaultStatus,PdaOrderType type,String string, Long id, Sort[] sorts);
    
    

    /**
     * 查询Pda操作日志明细行
     * 
     * @param start
     * @param pageSize
     * @param id
     * @param sorts
     * @return
     */
    Pagination<PdaOrderLine> findPdaLogLine(int start, int pageSize, Long id, Sort[] sorts);
    /**
     * 保存主动移库入库信息
     * @param uploadInitiativeMoveInboundRequest
     */
    void saveUploadInitativeMoveInBound(UploadInitiativeMoveInboundRequest uploadInitiativeMoveInboundRequest);
    /**
     * 保存主动移库出库信息
     * @param uploadInitiativeMoveOutboundRequest
     * @return
     */
    PdaOrder saveUploadInitativeMoveOutBound(UploadInitiativeMoveOutboundRequest uploadInitiativeMoveOutboundRequest);
    /**
     * 执行Pda单据库内移动单据入库
     * @param pdaOrder
     */
    void executeInitiative(PdaOrder pdaOrder);
    /**
     * PDA退仓单装箱
     * @param id
     * @param index
     * @return
     */
    Carton generateCartonByStaId(Long id, String index);
    /**
     * 创箱明细 更新箱状态
     * @param id
     * @param lines
     */
    void packageCartonLine(Long id, List<CartonLineCommand> lines);
    /**
     * 创建箱号和装箱明细
     * @param po 
     * @param sta 
     * @param map
     */
    void createCartonAndCartonLine(PdaOrder po, StockTransApplication sta, Map<String, List<PdaOrderLine>> map);
    /**
     * 执行部分出库
     * @param po
     * @param sta
     */
    void partlyOutBound(PdaOrder po, StockTransApplication sta);
    /**
     * 执行退仓出库
     * @param map 
     * @param sta 
     * @param po 
     */
    void executeOutBound(PdaOrder po, StockTransApplication sta, Map<String, List<PdaOrderLine>> map);
    /**
     * 执行PDA 盘点单据  KJL
     * @param ic
     * @param importData
     */
    void executeInventoryCheck(InventoryCheck ic, Map<String, Long> importData);
    /**
     * SN号占用
     * @param id
     * @param id2
     */
    void occupiedSn(Long id, Long id2);
    /**
     * PDA操作日志明细行 物流交接
     * @param start
     * @param pageSize
     * @param id
     * @param sorts 
     * @param sorts
     * @return
     */
    List<PdaHandOverLine> findPdaHandOverLogLine(Long id, Sort[] sorts);
    /**
     * PDA失败单据查询
     * @param start
     * @param pageSize
     * @param date
     * @param date2
     * @param orderCode
     * @param ouId 
     * @param sorts
     * @return
     */
    Pagination<PdaOrder> getErrorPdaLogList(int start, int pageSize, Date date, Date date2, String orderCode, Long ouId, Sort[] sorts);
    /**
     * 更新PDA单据状态为新建 重新执行
     * @param id
     */
    void updatePdaOrderStatus(Long id);
    /**
     * 根据plID查询错误单据明细
     * @param id
     * @param sorts
     * @return
     */
    List<PdaOrderLine> getErrorPdaLogDetailList(Long id, Sort[] sorts);
    /**
     * 
     * @param id
     * @param orderCode
     */
    void updateErrorPdaLineLocation(Long id, String orderCode);
}

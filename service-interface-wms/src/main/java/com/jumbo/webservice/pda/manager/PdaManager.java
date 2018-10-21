package com.jumbo.webservice.pda.manager;


import com.jumbo.wms.manager.BaseManager;
import com.jumbo.webservice.pda.executeOrder.ExecuteOrderRequest;
import com.jumbo.webservice.pda.executeOrder.ExecuteOrderResponse;
import com.jumbo.webservice.pda.getInboundData.GetInboundDataRequest;
import com.jumbo.webservice.pda.getInboundData.GetInboundDataResponse;
import com.jumbo.webservice.pda.getInboundOnShelves.GetInboundOnShelvesRequest;
import com.jumbo.webservice.pda.getInboundOnShelves.GetInboundOnShelvesResponse;
import com.jumbo.webservice.pda.getInventory.GetInventoryRequest;
import com.jumbo.webservice.pda.getInventory.GetInventoryResponse;
import com.jumbo.webservice.pda.getInventoryCheck.GetInventoryCheckRequest;
import com.jumbo.webservice.pda.getInventoryCheck.GetInventoryCheckResponse;
import com.jumbo.webservice.pda.getLibraryMovement.GetLibraryMovementRequest;
import com.jumbo.webservice.pda.getLibraryMovement.GetLibraryMovementResponse;
import com.jumbo.webservice.pda.getPickingData.GetPickingDataRequest;
import com.jumbo.webservice.pda.getPickingData.GetPickingDataResponse;
import com.jumbo.webservice.pda.getReturnOrder.GetReturnOrderRequest;
import com.jumbo.webservice.pda.getReturnOrder.GetReturnOrderResponse;
import com.jumbo.webservice.pda.getSku.GetSkuRequest;
import com.jumbo.webservice.pda.getSku.GetSkuResponse;
import com.jumbo.webservice.pda.getTransNo.GetTransNoRequest;
import com.jumbo.webservice.pda.getTransNo.GetTransNoResponse;
import com.jumbo.webservice.pda.getWarehouses.GetWarehousesRequest;
import com.jumbo.webservice.pda.getWarehouses.GetWarehousesResponse;
import com.jumbo.webservice.pda.login.LoginRequest;
import com.jumbo.webservice.pda.login.LoginResponse;
import com.jumbo.webservice.pda.uploadHandOverList.UploadHandOverListRequest;
import com.jumbo.webservice.pda.uploadHandOverList.UploadHandOverListResponse;
import com.jumbo.webservice.pda.uploadInboundOnShelves.UploadInboundOnShelvesRequest;
import com.jumbo.webservice.pda.uploadInboundOnShelves.UploadInboundOnShelvesResponse;
import com.jumbo.webservice.pda.uploadInboundReceive.UploadInboundReceiveRequest;
import com.jumbo.webservice.pda.uploadInboundReceive.UploadInboundReceiveResponse;
import com.jumbo.webservice.pda.uploadInitiativeMoveInbound.UploadInitiativeMoveInboundRequest;
import com.jumbo.webservice.pda.uploadInitiativeMoveInbound.UploadInitiativeMoveInboundResponse;
import com.jumbo.webservice.pda.uploadInitiativeMoveOutbound.UploadInitiativeMoveOutboundRequest;
import com.jumbo.webservice.pda.uploadInitiativeMoveOutbound.UploadInitiativeMoveOutboundResponse;
import com.jumbo.webservice.pda.uploadInventoryCheck.UploadInventoryCheckRequest;
import com.jumbo.webservice.pda.uploadInventoryCheck.UploadInventoryCheckResponse;
import com.jumbo.webservice.pda.uploadLibraryMovement.UploadLibraryMovementRequest;
import com.jumbo.webservice.pda.uploadLibraryMovement.UploadLibraryMovementResponse;
import com.jumbo.webservice.pda.uploadPickingOutbound.UploadPickingOutboundRequest;
import com.jumbo.webservice.pda.uploadPickingOutbound.UploadPickingOutboundResponse;
import com.jumbo.webservice.pda.uploadReturnOrder.UploadReturnOrderRequest;
import com.jumbo.webservice.pda.uploadReturnOrder.UploadReturnOrderResponse;

public interface PdaManager extends BaseManager {
    /**
     * 1 Pda登录接口逻辑
     * 
     * @param loginRequest
     * @return
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * 2 PDA获取仓库基础数据
     * 
     * @param getWarehousesRequest
     * @return
     */
    GetWarehousesResponse getWareHouse(GetWarehousesRequest getWarehousesRequest);

    /**
     * 3 PDA获取收货单据数据
     * 
     * @param getInboundDataRequest
     * @return
     */
    GetInboundDataResponse getInboundData(GetInboundDataRequest getInboundDataRequest);

    /**
     * 5 PDA获取上架数据
     * 
     * @param getInboundOnShelvesRequest
     * @return
     */
    GetInboundOnShelvesResponse getInboundOnShelves(GetInboundOnShelvesRequest getInboundOnShelvesRequest);

    /**
     * 7 PDA库存查询
     * 
     * @param getInventoryRequest
     * @return
     */
    GetInventoryResponse getInventory(GetInventoryRequest getInventoryRequest);

    /**
     * 4 PAD上传收货结果数据
     * 
     * @param uploadInboundReceiveRequest
     * @return
     */
    UploadInboundReceiveResponse uploadInboundReceive(UploadInboundReceiveRequest uploadInboundReceiveRequest);

    /**
     * 6 PDA上传上架数据
     * 
     * @param uploadInboundOnShelvesRequest
     * @return
     */
    UploadInboundOnShelvesResponse uploadInboundOnShelves(UploadInboundOnShelvesRequest uploadInboundOnShelvesRequest);

    /**
     * 12 PDA执行单据(入库单/退仓单)
     * 
     * @param executeOrderRequest
     * @return
     */
    ExecuteOrderResponse executeOrder(ExecuteOrderRequest executeOrderRequest);

    /**
     * 9 PDA上传交接单数据
     * 
     * @param uploadHandOverListRequest
     * @return
     */
    UploadHandOverListResponse uploadHandOverList(UploadHandOverListRequest uploadHandOverListRequest);

    /**
     * 8 PDA获取拣货单数据
     * 
     * @param getPickingDataRequest
     * @return
     */
    GetPickingDataResponse getPickingData(GetPickingDataRequest getPickingDataRequest);

    /**
     * 16 PDA获取库内移动单据
     * 
     * @param getLibraryMovementRequest
     * @return
     */
    GetLibraryMovementResponse getLibraryMovement(GetLibraryMovementRequest getLibraryMovementRequest);

    /**
     * 10 PDA获取退仓单数据
     * 
     * @param getReturnOrderRequest
     * @return
     */
    GetReturnOrderResponse getReturnOrder(GetReturnOrderRequest getReturnOrderRequest);

    /**
     * 17 PDA上传库内移动明细
     * 
     * @param uploadLibraryMovementRequest
     * @return
     */
    UploadLibraryMovementResponse uploadLibraryMovement(UploadLibraryMovementRequest uploadLibraryMovementRequest);

    /**
     * 11 PDA上传退仓单数据
     * 
     * @param uploadReturnOrderRequest
     * @return
     */
    UploadReturnOrderResponse uploadReturnOrder(UploadReturnOrderRequest uploadReturnOrderRequest);

    /**
     * 15 PDA上传盘点数据
     * 
     * @param uploadInventoryCheckRequest
     * @return
     */
    UploadInventoryCheckResponse uploadInventoryCheck(UploadInventoryCheckRequest uploadInventoryCheckRequest);

    /**
     * 13 PDA执行销售出库(只针对团购订单)
     * 
     * @param uploadPickingOutboundRequest
     * @return
     */
    UploadPickingOutboundResponse uploadPickingOutbound(UploadPickingOutboundRequest uploadPickingOutboundRequest);

    /**
     * 14 PDA下载盘点数据
     * 
     * @param getInventoryCheckRequest
     * @return
     */
    GetInventoryCheckResponse getInventoryCheck(GetInventoryCheckRequest getInventoryCheckRequest);

    GetSkuResponse getSku(GetSkuRequest getSkuRequest);

    
    /**
     * 18 PDA获取物流单号和物流关系
     * 
     * @param getTransNoRequest
     * @return
     */
    GetTransNoResponse getTransNo(GetTransNoRequest getTransNoRequest);

    /**
     * 19 PDA上传主动移库出库数据
     * 
     * @param uploadInitiativeMoveOutboundRequest
     * @return
     */
    UploadInitiativeMoveOutboundResponse uploadInitiativeMoveOutbound(UploadInitiativeMoveOutboundRequest uploadInitiativeMoveOutboundRequest);

    /**
     * 20 PDA上传主动移库入库数据
     * 
     * @param uploadInitiativeMoveInboundRequest
     * @return
     */
    UploadInitiativeMoveInboundResponse uploadInitiativeMoveInbound(UploadInitiativeMoveInboundRequest uploadInitiativeMoveInboundRequest);
}

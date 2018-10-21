/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.webservice.nike.manager;

import java.io.File;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.excel.ReadStatus;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.command.NikeVMIInboundCommand;
import com.jumbo.wms.model.vmi.nikeData.NikeCheckReceiveCommand;
import com.jumbo.wms.model.warehouse.StaCreateQueueCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.webservice.nike.command.InventoryObj;
import com.jumbo.webservice.nike.command.NikeOrderObj;
import com.jumbo.webservice.nike.command.NikeOrderResultObj;

public interface NikeOrderManager extends BaseManager {
    public static final Integer CANCEL_EXCEPTION = 99;
    public static final Integer CANCEL_HAVEDO = 10;
    public static final Integer CANCEL_CANNOTDO = 15;
    public static final Integer CANCEL_SUCCESS = 5;
    public static final Integer CANCEL_NOTFOUND = 0;

    /**
     * 创建VMI入库单
     * 
     * @param map
     */
    void createNikeVMIInbound(String slipCode, List<NikeVMIInboundCommand> list, BiChannel shop, OperationUnit whOu, User userId);

    /**
     * 接收NIKE订单
     * 
     * @param nikeOrderObj
     * @param shopName
     * @param warehouseCode
     * @return
     */
    Integer receiveNikeOrder(NikeOrderObj nikeOrderObj, String shopName, String warehouseCode);

    /**
     * 反馈NIKE订单
     * 
     * @param warehouseCode
     * @param orderCode
     * @param opType
     * @return
     */
    NikeOrderResultObj getNikeOrderResultByCode(String warehouseCode, String orderCode, String opType);

    /***
     * 反馈库存信息
     * 
     * @param warehouseCode
     * @return
     */
    List<InventoryObj> getAvailableInventoryByWarehouse(String warehouseCode);

    /***
     * 根据商品反馈库存信息
     * 
     * @param warehouseCode
     * @return
     */
    List<InventoryObj> getAvailableInventoryBySku(String supplierCode);

    /**
     * Nike 库存变化接口
     * 
     * @param id
     * @return
     */
    // NikeInventoryLogObj[] getNikeChangeInventoryLogById(long id);

    /***
     * 接收NIKE订单信息
     */
    Integer saveNikeOrder(NikeOrderObj nikeOrderObj);

    /**
     * 创建销售订单
     * 
     * @param sta
     */
    void createNikeSalesSta(StockTransApplication sta);

    void deleteCreateFinishQueue();


    /**
     * 查询NIKE过单信息
     * 
     * @param sta
     */
    Pagination<StaCreateQueueCommand> findNikeOrder(int start, int page, StaCreateQueueCommand cmd, Long ouid, Sort[] sorts);


    void forUpdateStatus(List<Long> idList, DefaultStatus status);

    /**
     * NIKE同步调整单查询
     * 
     * @param start
     * @param page
     * @param cmd
     * @param sorts
     * @return
     */
    Pagination<NikeCheckReceiveCommand> findNikeCheckReceive(int start, int page, NikeCheckReceiveCommand cmd, Sort[] sorts, Long ouId);

    /**
     * NIKE手动调整单导入
     * 
     * @param staFile
     * @param ouid
     * @param creator
     * @return
     * @throws Exception
     */
    ReadStatus importNikecheckReceive(File staFile,int manualType,String remark, Long ouid, User creator) throws Exception;

    /**
     * 取消Nike官网订单
     * 
     * @param unionId
     * @param orderCode
     * @param type
     * @return
     */
    Integer cancelOrder(String unionId, String orderCode, Integer type);

    /**
     * 根据店铺id查找 任意为收发仓的仓库
     * 
     * @param shopid
     * @return
     */
    OperationUnit findWarehouseOuByShopid(Long shopid);
    /**
     * 
     */
    void insertNikeStockReceivePac();
}

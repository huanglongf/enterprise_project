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
package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.jumbo.webservice.biaogan.command.RtnOutBoundCommand;
import com.jumbo.webservice.sfOrder.command.OrderFilterResult;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Brand;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.AutoPlConfigCommand;
import com.jumbo.wms.model.warehouse.ImportFileLog;
import com.jumbo.wms.model.warehouse.SfOrderFilterLog;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.WhAddStatusSource;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONArray;

public interface WareHouseManagerProxy extends BaseManager {
    int SHEET_0 = 1;
    int SHEET_1 = 2;
    public static final String CODE_SUCCESS = "success";
    public static final String CODE_ERROR = "error";

    /**
     * 外部订单导入
     * 
     * @param file
     * @param ouId
     * @param userId
     * @return
     */
    ReadStatus outSalesOrderImport(File file, Long ouId, Long userId, List<StockTransApplication> resultSta);

    SfOrderFilterLog saveSfOrderFilter(OrderFilterResult rs);

    void callVmiSalesStaOutBound(long msgId) throws Exception;

    String outboundToBzProxy(RtnOutBoundCommand rtnOutBound);

    void outboundToBzProxyTask();

    void outboundToBzProxyByWlbTask();

    void outboundToBzProxyTask(String source);

    void msgInBoundWareHouse(MsgRtnInboundOrder rntorder) throws Exception;

    void inboundToBz(List<MsgRtnInboundOrder> rntorderList);

    void uaInboundToBz(MsgRtnInboundOrder rntorder);

    /**
     * 占用库存创建包裹
     * 
     * @param msgId
     * @return
     */
    void vmiSalesCreatePageInfo(long msgId);

    /**
     * gdv 库存调整
     * 
     * @param adjId
     * @param godLine
     */
    void godivaInventoryAdjustment(String message);

    /**
     * 设置StvLine 的生产日期 和 过期时间
     * 
     * @param stvLine 需要设置的StvLine
     * @param strProductionDate 需要转换的生产日期
     * @param strExpireDate 需要过期时间
     */
    void setStvLineProductionDateAndExpireDate(StvLine stvLine, String strProductionDate, String strExpireDate);

    /**
     * 获取格式化后的过期时间
     * 
     * @param productionDate 生成日期
     * @param expireDate 过期时间
     * @param sku 商品
     * @return
     */
    Date getFormatExpireDate(Date productionDate, Date expireDate, Sku sku);

    /**
     * gdv移动入库
     * 
     * @param message
     */
    void godivaInventoryMovement(String message);

    /**
     * SF订单取消
     */
    void exeSfCancelOrderQueue();

    /**
     * 销售出库
     * 
     * @param msgId
     * @return
     */
    void salesStaOutBound(long msgId);

    /**
     * SF确认订单
     */
    // void exeSfConfirmOrderQueue();

    // void proadjustment(GodivaInventoryAdjustment ajust);
    void gdvinBound(MsgRtnOutbound rtnOutbound);

    void gdvOutboundconfirm(String message);

    void executeGodvInbound();

    void executeGodvRtnOubound();

    /**
     * 根据条件查询商品信息
     * 
     * @param start
     * @param pageSize
     * @param proCmd
     * @param sorts
     * @return
     */
    Pagination<SkuCommand> findAllProduct(int start, int pageSize, SkuCommand proCmd, Sort[] sorts);

    /**
     * 根据条件查询商品信息2
     * 
     * @param start
     * @param pageSize
     * @param proCmd
     * @param sorts
     * @return
     */
    Pagination<SkuCommand> findAllProduct2(int start, int pageSize, SkuCommand proCmd, Sort[] sorts);

    /**
     * 导入商品基本信息维护
     * 
     * @param file
     * @return
     */
    ReadStatus pdaPurchaseSnImport(File file);

    /**
     * 导入商品基本信息维护
     * 
     * @param file
     * @return
     */
    ReadStatus nikeSkuInfoImport(File file, String brandName);

    /**
     * 配货清单占用库存
     * 
     * @param plid
     * @param ouId
     * @param userId
     */
    void occupiedInventoryByPickinglist(Long plid, Long userId, Long ouId);

    /**
     * 作业单库存占用
     * 
     * @param staId
     * @param userId void
     * @throws
     */
    void occupiedInventoryBySta(Long staId, Long userId);

    /**
     * 格式配货模式计数器计算
     * 
     * @param plid
     * @param ouId
     */
    void pickingModeSkuCounter(Long plid, Long ouId);

    /**
     * 查询所有品牌名称
     * 
     * @return
     */
    List<Brand> selectAllBrandName();

    /**
     * 查询所有分类名称
     * 
     * @return
     */
    JSONArray selectAllCategoriesName();

    /**
     * 分页查询所有品牌名称
     * 
     * @return
     */
    public Pagination<Brand> findBrandByPage(int start, int pagesize, Sort[] sorts);

    public void exeZtoConfirmOrderQueue();

    /**
     * 匹配集货口
     * 
     * @param ouId
     * @param staIdList
     */
    public void mateShiipingPoint(Long ouId, List<Long> staIdList);

    public void newOccupiedInventoryBySta(Long staId, Long userId, String wooCode);
    
    
    public void newOccupiedInventoryByStaShou(String staCode,Long ouId);


    String exeExlFile(ImportFileLog files);

    /**
     * update Warehouse
     * 
     * @param warehouse
     * @return
     */
    void updateWarehouse(Warehouse warehouse, OperationUnit ou, WhAddStatusSource wss, String transIds, AutoPlConfigCommand apc);
}

/**
 * 
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
package com.jumbo.wms.manager.pickZone;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.automaticEquipment.Zoon;
import com.jumbo.wms.model.automaticEquipment.ZoonOcpSort;
import com.jumbo.wms.model.command.pickZone.WhOcpOrderCommand;
import com.jumbo.wms.model.command.pickZone.WhOcpOrderLineCommand;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.WarehouseDistrictType;

public interface NewInventoryOccupyManager extends BaseManager {

    /**
     * 根据sku占用库存
     */
    Long ocpInvBySku(Long skuId, Long qty, Long whId, String owner, String ocpCode, String sourceOcpCode, Long locationId, Long invStatusId, Boolean isForSales, Date fromExpDate, Date endExpDate, List<WarehouseDistrictType> wdtList,
            List<Long> transactionTypes, String ocpAreaCode);

    /**
     * 根据sta创建占用批
     * 
     * @param stacList
     * @param ouId
     * @return
     */
    WhOcpOrderCommand createOcpOrder(String ocpCode, Long ouId, Boolean isNew);

    void updateStaOcpByIdList(String ocpCode, List<Long> List);

    /**
     * 根据占用批明细ID更新库存实际占有量
     * 
     * @param woolId
     * @param conformQty
     */
    void updateWoolConformQtyById(Long woolId, Long conformQty);

    /**
     * 根据占用批ID获取
     * 
     * @param wooId
     * @param qtyCheck
     * @return
     */
    List<WhOcpOrderLineCommand> findWhOcpOrderLineByWooId(Long wooId, Long skuId, String qtyCheck);

    /**
     * 找出占用失败的作业单并释放多余的库存
     * 
     * @param skuStaMap
     */
    void staQtyCheck(Long wooId);

    /**
     * 根据订单和占用批释放库存
     * 
     * @param staId
     * @param wooId
     */
    void releaseQtyByStaAndOcp(Set<Long> staId, Long wooId, Map<Long, List<StaLineCommand>> staStalines, List<WhOcpOrderLineCommand> woolcList);

    /**
     * 根据skuID、占用批编码、仓库ID和需要释放的库存数量来释放库存
     * 
     * @param skuReleaseQty
     */
    void releaseQty(Map<WhOcpOrderLineCommand, Long> skuReleaseQty, Long whId);

    /**
     * 修改占用批状态
     * 
     * @param wooId
     * @param status
     */
    void updateWhOcpOrderStatus(Long wooId, Integer status);

    void updateStaIsNeedOcpByOcpId(Long values, Long ocpId);

    void updateStaIsNeedOcpByOcpCode(Long values, String Code);

    /**
     * 根据参数查找占用批
     * 
     * @param ouId
     * @param status
     * @return
     */
    List<WhOcpOrderCommand> findOcpOrderByParams(Long ouId, List<Integer> status);


    List<WhOcpOrderCommand> findOcpOrderStatus(Long ouId, Integer status);

    /**
     * 根据占用批明细占用库存
     * 
     * @param woolId
     */
    void ocpInvByWoolId(Long woolId);

    /**
     * 占用批创建异常处理
     * 
     * @param stacList
     */
    void ocpBatchCreateExceptionHandle(String ocpCode);

    /**
     * 占用批重新处理
     * 
     * @param ocpCode
     */
    void ocpBatchCreateExceptionHandleAgain(String ocpCode);

    /**
     * 将一定数量的订单设为一批
     * 
     * @param ocpBatchCode
     * @param ocpCode
     * @param ouId
     */
    List<Long> setOcpCodeForSta(String ocpBatchCode, String ocpCode, Long ouId, Integer amount, String saleModle, String isYs, String areaCode);

    /**
     * 最后校验占用批明细里的占用量是否与实际占用量相等
     * 
     * @param wooId
     */
    void checkOcpData(Long wooId);

    /**
     * 处理异常未完成的占用批
     */
    WhOcpOrderCommand exceptionOcpOrderDispose(Long ouId);

    /**
     * 根据状态获取所有的占用批ID
     * 
     * @param ouId
     * @param status
     * @return
     */
    List<Long> findOcpOrderIdsByStatus(Long ouId, Integer status);

    /**
     * 订单占用异常处理
     * 
     * @param wooId
     */
    void staOcpException(Long wooId);

    /**
     * 将超过一定占用失败次数的占用批以邮件的形式通知相关人员
     */
    void exceptionOcpOrderInformEmail();

    /**
     * 根据占用批编码释放库存
     * 
     * @param ocpCode
     */
    void releaseInventoryByOcpCode(String ocpCode);

    /**
     * 释放所有异常单据
     */
    void releaseAllOcpCode();

    WhOcpOrderCommand getWhocpOrder(Long wooId);

    List<Long> findOcpStaIdByOcpCod(Long ouId, String wooCode);

    List<Long> findNeedOcpOrderByOuId(Long ouId2);

    Long queryInventoryByOcpCode(String ocpCode);

    /**
     * 查询规则区域列表
     * 
     * @param ouId
     * @return
     */
    List<String> findOcpAreaByOuId(Long ouId);

    // List<ZoonOcpSort> findZoonOcpSortListByOuId(Long ouId);

    /**
     * 作业单发送mq
     * 
     * @param staId
     */
    void sendStaToMqForAreaOcp(Long staId);

    Pagination<ZoonOcpSort> findZoonOcpSortListByZoonCodeAndOuid(int start, int pageSize, Sort[] sorts, Long ouId, String zoonCode);

    List<Zoon> getZoonList(Long ouId);

    /**
     * 更新/修改
     * 
     * @param zoonOcpSort
     */
    public String updateZoonOcpSort(ZoonOcpSort zoonOcpSort, Long ouId);

    public void deleteZoonOcpSort(Long id);

    List<String> findAllZoon(Long ouId);

    void occupiedInventoryByStaToMq(Long staId);

    void occupiedInventoryByStaToMq2(Long staId);

    void clearOccupiedInventoryByStaToMq(Long staId);


}

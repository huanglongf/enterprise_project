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

package com.jumbo.wms.manager.listener;




import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.warehouse.StockTransApplication;

public interface StaListenerManager extends BaseManager {

    /**
     * 作业单创建监听器处理
     * 
     * @param sta
     * @param wh
     */
    void staCreaded(StockTransApplication sta, Warehouse wh);

    /**
     * 作业单占用库存监听器处理
     * 
     * @param sta
     * @param wh
     */
    void staOccupied(StockTransApplication sta, Warehouse wh);

    /**
     * 作业单取消完成监听器处理
     * 
     * @param sta
     * @param wh
     */
    void staCanceled(StockTransApplication sta, Warehouse wh);

    /**
     * 作业单部分入库监听器处理
     * 
     * @param sta
     * @param wh
     */
    void staPartlyReturned(StockTransApplication sta, Warehouse wh);

    /**
     * 作业单取消未处理监听器处理
     * 
     * @param sta
     * @param wh
     */
    void staCancelUndo(StockTransApplication sta, Warehouse wh);

    /**
     * 作业单完成存监听器处理
     * 
     * @param sta
     * @param wh
     */
    void staFinished(StockTransApplication sta, Warehouse wh);

    /**
     * 作业单核对存监听器处理
     * 
     * @param sta
     * @param wh
     */
    void staChecked(StockTransApplication sta, Warehouse wh);

    /**
     * 作业单已核对监听器处理
     * 
     * @param sta
     * @param wh
     */
    void staIntransit(StockTransApplication sta, Warehouse wh);

    /**
     * 通知外包仓数据
     * 
     * @param sta
     * @param wh
     */
    void generateMsgForVmiOut(StockTransApplication sta, Warehouse wh);
    
    /**
     * 销售出库、退换货出库 类型 oms出库接口 pac
     */
    void transferOmsOutBound(Long staId, Long id);
    
    /**
     * 销售出库、退换货出库 类型 oms出库接口 oms
     */
    void statusOmsOutBound(Long id);

    /**
     * 直连 创单 反馈
     */
    void wmsOrderFinishOms(Long id);

    void wmsRtnOutBountMq(Long id);

    void wmsCommonMessageProducerErrorMq(Long id);


//	void runThreadOutBound();

    /**
     * 将已出库的作业单的物流信息同步到快递雷达中
     * 
     * @param sta
     */
    void extractFromStaInfo(StockTransApplication sta);

    /**
     * 设置快递的预期到达时间
     * 
     * @param sta
     */
    void expressPlanTime(StockTransApplication sta);

    /**
     * STA finish notify PMS parcel delivery
     * @param sta
     */
    void staFinishNotifyPMS(StockTransApplication sta);
    
    /**
     * 
     * @param sta1
     */
    void staInTransitNotifyPMS(StockTransApplication sta1);
    
    /**
     * 获取o2o订单
     * @param sta
     */
    void getO2oSta(StockTransApplication sta);

    public void vmiStatusSpeedo(StockTransApplication sta, Warehouse wh);
}

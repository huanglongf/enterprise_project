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
 */
package com.jumbo.wms.manager.vmi.espData;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.vmi.esprit.xml.espDispatch.EspDispatch;
import com.jumbo.wms.model.vmi.espData.ESPDeliveryCommand;
import com.jumbo.wms.model.vmi.espData.ESPDeliveryReceive;
import com.jumbo.wms.model.vmi.espData.ESPDeliveryReceiveCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StvLineCommand;

/**
 * @author lichuan
 *
 */
public interface ESPReceiveDeliveryManager extends BaseManager {
    /**
     * 保存Esprit Dispatch接收指令
     * 
     * @param root
     */
    void saveEspritDispatchDate(EspDispatch root, File sourceFile, String backupPath) throws IOException;
    
    /**
     * 根据状态获取接收数据
     * 
     * @param status
     * @return List<ESPDeliveryReceiveCommand>
     * @throws
     */
    List<ESPDeliveryReceiveCommand> getReceiveDatasGroupByBatchNoAndPN(String status);
    
    /**
     * 创入库单
     * 
     * @param espComd
     * @param vmiCode void
     * @throws
     */
    void generateNewInboundSta(ESPDeliveryReceiveCommand espComd, String vmiCode);
    
    /**
     * 创入库单
     * 
     * @param espComd
     * @param vmiCode void
     * @throws
     */
    void generateInboundSta();

    /**
     * 
     *@param batchNo
     *@param po
     *@param status
     *@return List<ESPDeliveryReceive> 
     *@throws
     */
    List<ESPDeliveryReceive> findReceiveOrdersByBatchNoAndPN(String batchNo, String pickNo, String status);
    
    /**
     * 更新接收数据状态
     * 
     * @param batchNo
     * @param po
     * @param status void
     * @throws
     */
    void updateReceiveOrdersStatus(Long staId, String batchNo, String pickNo, String status);
    
    /**
     * 根据staId查找所有接收数据
     * 
     * @param staId
     * @return List<ESPDeliveryReceive>
     * @throws
     */
    List<ESPDeliveryReceiveCommand> findReceiveOrdersByStaId(Long staId);
    
    /**
     * 根据stvId查找所有上架数据
     * 
     * @param staId
     * @return List<ESPDeliveryReceive>
     * @throws
     */
    List<ESPDeliveryReceiveCommand> findReceiveOrdersByShelveStvId(Long stvId);
    
    /**
     * 根据staId查找所有上架数据
     * 
     * @param staId
     * @return List<ESPDeliveryReceiveCommand>
     * @throws
     */
    List<ESPDeliveryReceiveCommand> findShelveReceiveOrdersByStaId(Long staId);
    
    /**
     * 根据关单staId查找所有未收货数据
     * 
     * @param staId
     * @return List<ESPDeliveryReceiveCommand>
     * @throws
     */
    List<ESPDeliveryReceiveCommand> findReceiveOrdersByCloseStaId(Long staId);
    
    /**
     * 生成全量入库xml反馈数据
     * 
     * @param list
     * @param sta
     * @param transDate
     * @return String
     * @throws
     */
    String marshallerInboundDeliveryData(List<ESPDeliveryReceiveCommand> list, StockTransApplication sta, Date transDate);
    
    /**
     * 生成执行出库xml反馈数据
     * 
     * @param list
     * @param sta
     * @param transDate
     * @return
     * @throws Exception String
     * @throws
     */
    String marshallerOutboundDeliveryData(List<StvLineCommand> list, StockTransApplication sta, Date transDate);

    /**
     * 
     * @param staCode
     * @return List<ESPDeliveryCommand>
     * @throws
     */
    List<ESPDeliveryCommand> findDeliveryDatasGroupByStaCode();
    
    /**
     * 
     *@return List<ESPDeliveryCommand> 
     *@throws
     */
    List<ESPDeliveryCommand> findCloseDeliveryDatasGroupByStaCode();
    
    /**
     * 转入转出反馈 void
     * 
     * @throws
     */
    void inOutBoundRtn();
    
    /**
     * 转入转出反馈 void2
     * 
     * @throws
     */
    void inOutBoundRtn2();


    /**
     * 转出反馈Pacs void
     * 
     * @throws
     */
    void outBoundRtn();
    


    /**
     * 转入反馈 void
     * 
     * @throws
     */
    void inBoundFeedback(String locationUploadPath);
    
    /**
     * 收货单关闭反馈 void
     * 
     * @throws
     */
    void inBoundCloseFeedback(String locationUploadPath);
    
    /**
     * 收货单关闭反馈 void
     * 
     * @throws
     */
    void inBoundCloseRtn();
    
    /**
     * 收货单关闭反馈 void 2
     * 
     * @throws
     */
    void inBoundCloseRtn2();

    /**
     * 转入转出反馈
     * 
     * @param staCode void
     * @throws
     */
    void inOutBoundDeliveryRtn(String staCode);
    
    /**
     * 转入关单反馈
     * 
     * @param staCode void
     * @throws
     */
    void inBoundDeliveryCloseRtn(String staCode);
    
    /**
     * 
     * @param staCode
     * @return List<ESPDeliveryCommand>
     * @throws
     */
    List<ESPDeliveryCommand> findDeliveryDatasByStaCode(String staCode, String batchCode);
   
    /**
     * 关单反馈数据
     * 
     * @param staCode
     * @return List<ESPDeliveryCommand>
     * @throws
     */
    List<ESPDeliveryCommand> findCloseDeliveryDatasByStaCode(String staCode, String batchCode);
    
    /**
     * 生成入库出库xml反馈数据
     * 
     * @param list
     * @param sta
     * @return String
     * @throws
     */
    String marshallerInOutBoundDeliveryData(List<ESPDeliveryCommand> list, StockTransApplication sta, Date transDate);

    List<ESPDeliveryCommand> findDeliveryDatasGroupByStaCode2();

    List<ESPDeliveryCommand> findCloseDeliveryDatasGroupByStaCode2();

}

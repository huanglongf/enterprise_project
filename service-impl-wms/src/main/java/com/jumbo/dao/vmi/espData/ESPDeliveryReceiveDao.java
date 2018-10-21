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
package com.jumbo.dao.vmi.espData;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.espData.ESPDeliveryReceive;
import com.jumbo.wms.model.vmi.espData.ESPDeliveryReceiveCommand;

/**
 * @author lichuan
 *
 */
@Transactional
public interface ESPDeliveryReceiveDao extends GenericEntityDao<ESPDeliveryReceive, Long> {
    
    /**
     * 根据状态获取所有接收数据
     * 
     * @param status
     * @param rowMapper
     * @return List<ESPDeliveryReceiveCommand>
     * @throws
     */
    @NativeQuery
    List<ESPDeliveryReceiveCommand> findReceiveDatasGroupByBatchNoAndPN(@QueryParam("status") String status, RowMapper<ESPDeliveryReceiveCommand> rowMapper);
    
    /**
     * 获取具体一批的接收数据
     * 
     * @param batchNo
     * @param status
     * @param pickNo
     * @return List<ESPDeliveryReceive>
     * @throws
     */
    @NamedQuery
    List<ESPDeliveryReceive> findReceiveOrdersByBatchNoAndPN(@QueryParam("batchNo") String batchNo, @QueryParam("pickNo") String pickNo);
    
    /**
     * 更新该批次接收数据已创入库单
     * 
     * @param batchNo
     * @param status
     * @param po void
     * @throws
     */
    @NativeUpdate
    void updateReceiveOrdersStatus(@QueryParam("staId") Long staId, @QueryParam("batchNo") String batchNo, @QueryParam("status") String status, @QueryParam("pickNo") String pickNo);
    
    /**
     * 根据staId查找所有接收数据
     * 
     * @param staId
     * @param rowMapper
     * @return List<ESPDeliveryReceiveCommand>
     * @throws
     */
    @NativeQuery
    List<ESPDeliveryReceiveCommand> findReceiveOrdersByStaId(@QueryParam("staId") Long staId, RowMapper<ESPDeliveryReceiveCommand> rowMapper);
    
    /**
     * 根据stvId查找当前上架数据
     * 
     * @param staId
     * @param rowMapper
     * @return List<ESPDeliveryReceiveCommand>
     * @throws
     */
    @NativeQuery
    List<ESPDeliveryReceiveCommand> findReceiveOrdersByShelveStvId(@QueryParam("stvId") Long stvId, RowMapper<ESPDeliveryReceiveCommand> rowMapper);
    
    /**
     * 根据staId查找所有上架数据
     * 
     * @param staId
     * @param rowMapper
     * @return List<ESPDeliveryReceiveCommand>
     * @throws
     */
    @NativeQuery
    List<ESPDeliveryReceiveCommand> findShelveReceiveOrdersByStaId(@QueryParam("staId") Long staId, RowMapper<ESPDeliveryReceiveCommand> rowMapper);
    
    /**
     * 根据staId查询收货数据
     */
    @NativeQuery
    ESPDeliveryReceiveCommand findReceiveOrdersByStaId2(@QueryParam("staId") Long staId, RowMapper<ESPDeliveryReceiveCommand> rowMapper);


    /**
     * 根据关单staId查找所有未收货数据
     * 
     * @param staId
     * @param rowMapper
     * @return List<ESPDeliveryReceiveCommand>
     * @throws
     */
    @NativeQuery
    List<ESPDeliveryReceiveCommand> findReceiveOrdersByCloseStaId(@QueryParam("staId") Long staId, RowMapper<ESPDeliveryReceiveCommand> rowMapper);

}

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
package com.jumbo.dao.automaticEquipment;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.automaticEquipment.ZoonOcpOrderLine;


/**
 * 区域待占用列表明细
 * 
 * @author xiaolong.fei
 *
 */
@Transactional
public interface ZoonOcpOrderLineDao extends GenericEntityDao<ZoonOcpOrderLine, Long> {

    /**
     * 查询区域待占用列表订单
     * 
     * @param ouId
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<ZoonOcpOrderLine> findZoonOcpOrderLine(@QueryParam("orderId") Long orderId, @QueryParam("status") Integer status, RowMapper<ZoonOcpOrderLine> rowMap);

    /**
     * 删除
     * 
     * @param staid
     */
    @NativeUpdate
    int deleteOcpOrderLineByStaId(@QueryParam(value = "staId") Long staId);

    /**
     * 查询队列中所有的区域
     * 
     * @param ouId
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<String> findAllAreaCode(@QueryParam(value = "ouId") Long ouId, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 查询队列中一定数量的作业单
     * 
     * @param ouId
     * @param account
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findOcpStaByOuId(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "account") Integer account, SingleColumnRowMapper<Long> singleColumnRowMapper);

}

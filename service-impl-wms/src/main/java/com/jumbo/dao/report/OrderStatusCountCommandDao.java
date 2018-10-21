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

package com.jumbo.dao.report;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.report.OrderStatusCountCommand;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface OrderStatusCountCommandDao extends GenericEntityDao<OrderStatusCountCommand, Long> {

    /**
     * 根据仓库组织ID查询指定时间断内所有状态订单数量
     * 
     * @param ouid 仓库组织ID
     * @param startdate 起始时间 格式:yyyyMMddhh24miss
     * @param enddate 结束时间 格式:yyyyMMddhh24miss
     * @return
     */
    @NativeQuery(model = OrderStatusCountCommand.class)
    List<OrderStatusCountCommand> findOrderStatusByOuId(@QueryParam("ouid") Long ouid, @QueryParam("startdate") String startdate, @QueryParam("enddate") String enddate);
    
    /**
     * 根据仓库组织ID查询指定时间断内快递所有状态订单数量
     * 
     * @param ouid 仓库组织ID
     * @param startdate 起始时间 格式:yyyyMMddhh24miss
     * @param enddate 结束时间 格式:yyyyMMddhh24miss
     * @return
     */
    @NativeQuery(model = OrderStatusCountCommand.class)
    List<OrderStatusCountCommand> findTransOrderStatusByOuId(@QueryParam("ouid") Long ouid, @QueryParam("startdate") String startdate, @QueryParam("enddate") String enddate);
}

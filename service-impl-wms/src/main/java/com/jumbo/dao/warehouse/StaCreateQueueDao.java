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

package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.model.warehouse.StaCreateQueue;
import com.jumbo.wms.model.warehouse.StaCreateQueueCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;

@Transactional
public interface StaCreateQueueDao extends GenericEntityDao<StaCreateQueue, Long> {

    @NamedQuery
    StaCreateQueue findBySlipCode(@QueryParam("slipCode") String slipCode, @QueryParam("source") String source);

    @NamedQuery
    StaCreateQueue findBySlipCodeByType(@QueryParam("slipCode") String slipCode, @QueryParam("source") String source, @QueryParam("type") StockTransApplicationType staType);

    @NativeQuery
    List<String> findToCreateCode(@QueryParam("source") String source, @QueryParam("type") Integer type, SingleColumnRowMapper<String> r);

    @NativeUpdate
    void updateStatusBySlipCode(@QueryParam("status") Integer status, @QueryParam("error") String error, @QueryParam("slipCode") String slipCode, @QueryParam("type") Integer type);

    @NativeUpdate
    void updateStatusByIds(@QueryParam("status") Integer status, @QueryParam("ids") List<Long> ids);

    @NativeUpdate
    void deleteFinishLine();

    @NativeUpdate
    void deleteFinish();


    /**
     * 查寻过单信息
     * 
     * @param start
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param barCode
     * @param jmCode
     * @param skuName
     * @param supplierSkuCode
     * @param invOwner
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<StaCreateQueueCommand> findPageBySource(int start, int pageSize, @QueryParam("source") String source, @QueryParam("slipCode") String slipCode, @QueryParam("status") Integer barCode, @QueryParam("statusList") List<Integer> statusList,
            RowMapper<StaCreateQueueCommand> rowMapper, Sort[] sorts);
    /**
     * 根据orderCode查询过单信息
     * @param orderCode
     * @param beanPropertyRowMapper 
     * @return
     */
    @NativeQuery
    StaCreateQueue findBySlipCodeNike(@QueryParam("slipCode")String orderCode,@QueryParam("source")String source, BeanPropertyRowMapper<StaCreateQueue> beanPropertyRowMapper);
}

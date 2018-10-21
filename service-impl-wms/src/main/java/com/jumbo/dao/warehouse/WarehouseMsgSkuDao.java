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

import java.util.Date;
import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WarehouseMsgSku;
import com.jumbo.wms.model.warehouse.WarehouseMsgSkuCommand;

@Transactional
public interface WarehouseMsgSkuDao extends GenericEntityDao<WarehouseMsgSku, Long> {
    @NamedQuery
    WarehouseMsgSku getMsgSkuByCode(@QueryParam(value = "code") String code, @QueryParam(value = "sourceWh") String sourceWh);

    @NativeQuery
    List<WarehouseMsgSku> getMsgSkuByStatus(@QueryParam(value = "code") String code, RowMapper<WarehouseMsgSku> rowMap);

    @NamedQuery
    List<WarehouseMsgSku> getMsgSkuByErrorCount(@QueryParam(value = "errorCount") Long errorCount);

    @NativeUpdate
    void updateMsgSkuById(@QueryParam(value = "status") Long status, @QueryParam(value = "id") Long id);

    @NamedQuery
    WarehouseMsgSku getMsgSkuBySkuCodeAndWh(@QueryParam(value = "code") String code, @QueryParam(value = "source") String source);

    @NamedQuery
    WarehouseMsgSku getMsgSkuByWlbIdAndWh(@QueryParam(value = "wlbId") String wlbId, @QueryParam(value = "whCode") String whCode);

    /**
     * 根据时间段查询品牌的商品信息
     * 
     * @param startTime
     * @param endTime
     * @param code
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<WarehouseMsgSkuCommand> findMsgSKuByTime(@QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "source") String source,
            BeanPropertyRowMapperExt<WarehouseMsgSkuCommand> beanPropertyRowMapper);

    @NativeQuery
    Long getThreePlSeq(SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    List<WarehouseMsgSku> findMsgSKuByZdy(BeanPropertyRowMapperExt<WarehouseMsgSku> beanPropertyRowMapper);
}

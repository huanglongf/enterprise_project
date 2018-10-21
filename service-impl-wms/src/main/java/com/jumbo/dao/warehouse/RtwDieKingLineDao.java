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
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.RtwDiekingLine;
import com.jumbo.wms.model.warehouse.VMIBackOrder;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.support.BeanPropertyRowMapperExt;

@Transactional
public interface RtwDieKingLineDao extends GenericEntityDao<RtwDiekingLine, Long> {

    @NativeUpdate
    void updateRtwDieKingLine(@QueryParam(value = "skuId") Long skuId, @QueryParam(value = "locationCode") String locationCode, @QueryParam(value = "diekingId") Long diekingId, @QueryParam(value = "qty") Long qty);

    @NativeQuery(pagable = true)
    Pagination<RtwDiekingLine> getOutboundDetailListCollection(int start, int pageSize, @QueryParam("ouid") Long ouid, @QueryParam("staid") Long staid, BeanPropertyRowMapperExt<RtwDiekingLine> beanPropertyRowMapperExt);

    @NativeQuery
    List<VMIBackOrder> findDiekingLineListByDiekingId(@QueryParam("id") Long id, BeanPropertyRowMapper<VMIBackOrder> beanPropertyRowMapper);

}

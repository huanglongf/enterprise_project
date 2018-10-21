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
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaAdditionalLineCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

@Transactional
public interface StaAdditionalLineDao extends GenericEntityDao<StaAdditionalLine, Long> {

    @NativeUpdate
    void deleteStaAddLineByStaId(@QueryParam("staid") Long staid);

    @NativeUpdate
    void deleteAddLineByTrackingNo(@QueryParam("staid") Long staid, @QueryParam("trackingNo") String trackingNo);

    @NamedQuery
    List<StaAdditionalLine> findByStaId(@QueryParam("staid") Long staid);

    @NativeQuery
    List<StaAdditionalLineCommand> findTrackingAndSkuByStuId(@QueryParam("staId") Long staId, BeanPropertyRowMapperExt<StaAdditionalLineCommand> beanPropertyRowMapperExt);

    @NativeUpdate
    void updateTrackingAndSkuByStuId(@QueryParam("trackingAndSku") String trackingAndSku, @QueryParam("staId") Long staId);

    @NativeQuery
    List<StaAdditionalLineCommand> findEdwTwhStaAddLine(RowMapper<StaAdditionalLineCommand> rowMapper);

    @NativeQuery
    List<StaAdditionalLineCommand> getOneAddLineByTrackingNo(@QueryParam("staid") Long staid, @QueryParam("trackingNo") String trackingNo, BeanPropertyRowMapper<StaAdditionalLineCommand> beanPropertyRowMapperExt);

    @NativeQuery
    StaAdditionalLineCommand findAddLineByTrackingNo(@QueryParam("trackingNo") String trackingNo, @QueryParam("lpCode") String lpCode, BeanPropertyRowMapper<StaAdditionalLineCommand> beanPropertyRowMapperExt);

    @NativeUpdate
    void deleteStaAdditionalLineByStaId(@QueryParam("staId") Long staId);
}

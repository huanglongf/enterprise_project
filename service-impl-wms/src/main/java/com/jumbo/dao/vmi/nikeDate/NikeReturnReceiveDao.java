/**
 * DimensionImpl.java com.erry.model.it.impl
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

package com.jumbo.dao.vmi.nikeDate;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.nikeData.NikeReturnReceive;

@Transactional
public interface NikeReturnReceiveDao extends GenericEntityDao<NikeReturnReceive, Long> {

    // 转店退仓 || 退大仓
    @NativeUpdate
    void updateToWriteStatus(@QueryParam(value = "type") Integer type, @QueryParam(value = "toWriteStatus") Integer toWriteStatus, @QueryParam(value = "createStatus") Integer createStatus);

    @NativeQuery
    List<NikeReturnReceive> findToWriteFile(@QueryParam(value = "type") Integer type, @QueryParam(value = "toWriteStatus") Integer toWriteStatus, BeanPropertyRowMapperExt<NikeReturnReceive> beanPropertyRowMapperExt);

    @NativeUpdate
    void updateToFinishStatus(@QueryParam(value = "type") Integer type, @QueryParam(value = "referenceNo") String referenceNo, @QueryParam(value = "toWriteStatus") Integer toWriteStatus, @QueryParam(value = "finishStatus") Integer finishStatus);

    @NativeQuery
    List<String> findIsExistsSequence(@QueryParam("seq") String seq, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeUpdate
    void createTransferOutFeedbackForNikePac(@QueryParam("upc") String upc, @QueryParam("quantity") Long quantity, @QueryParam("nike") String nike, @QueryParam("referenceNo") String referenceNo, @QueryParam("receiveDate") String receiveDate,
            @QueryParam("status") Integer status, @QueryParam("type") Integer type, @QueryParam("toLocation") String toLocation, @QueryParam("fromLocation") String fromLocation);

    @NativeUpdate
    void updateFileName(@QueryParam(value = "type") Integer type, @QueryParam(value = "referenceNo") String referenceNo, @QueryParam(value = "toWriteStatus") Integer toWriteStatus, @QueryParam(value = "fileName") String fileName);

    @NativeQuery
    List<NikeReturnReceive> unitTestGetNikeReturnReceiveByStaId(@QueryParam(value = "staId") Long staId, BeanPropertyRowMapperExt<NikeReturnReceive> beanPropertyRowMapperExt);

}

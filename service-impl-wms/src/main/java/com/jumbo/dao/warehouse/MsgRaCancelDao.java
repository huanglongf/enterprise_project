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

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgRaCancel;

@Transactional
public interface MsgRaCancelDao extends GenericEntityDao<MsgRaCancel, Long> {

    @NamedQuery
    List<MsgRaCancel> findBySource(@QueryParam(value = "source") String source, @QueryParam(value = "status") DefaultStatus status);

    @NativeQuery
    List<MsgRaCancel> findNewMsgBySource(@QueryParam(value = "source") String source, RowMapper<MsgRaCancel> rowMapper);

    @NativeUpdate
    void updateStatusById(@QueryParam(value = "msgId") Long msgId, @QueryParam(value = "status") Integer status);

    @NativeQuery
    Long findRaCancelBatchNo(RowMapper<Long> batchNo);

    @NamedQuery
    MsgRaCancel getByStaCode(@QueryParam("staCode") String staCode);

    @NativeUpdate
    void updateStaByRaCode(@QueryParam("staCode") String staCode, @QueryParam("sta") int status);
    
    @NativeUpdate
	void insertAfterReturn(@QueryParam(value = "slipCode") String slipCode);
}

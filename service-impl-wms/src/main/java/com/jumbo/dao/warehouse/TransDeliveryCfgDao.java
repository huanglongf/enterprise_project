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
import java.util.Map;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.MapRowMapper;
import com.jumbo.wms.model.warehouse.TransDeliveryCfg;
import com.jumbo.wms.model.warehouse.TransDeliveryCfgCommand;

@Transactional
public interface TransDeliveryCfgDao extends GenericEntityDao<TransDeliveryCfg, Long> {

    @NativeQuery
    List<TransDeliveryCfgCommand> findTransCfgByWhOuId(@QueryParam(value = "whouid") Long whouid, RowMapper<TransDeliveryCfgCommand> r);

    @NamedQuery
    TransDeliveryCfg findByWhOuAndTrans(@QueryParam(value = "whOuId") Long whOuId, @QueryParam(value = "transId") Long transId);

    @NativeUpdate
    void updateTransDeliveryCfg(@QueryParam(value = "whOuId") Long whOuId, @QueryParam(value = "transId") Long transId, @QueryParam(value = "qty") Long qty);

    @NativeQuery
    Map<String, Long> findUsingByWhou(@QueryParam(value = "whOuId") Long whOuId, MapRowMapper r);
    
    @NativeQuery
    List<TransDeliveryCfgCommand> findTransCfgByWhOuIdAndLpCode(@QueryParam(value = "whouid") Long whouid, @QueryParam(value = "lpCode") String lpCode, RowMapper<TransDeliveryCfgCommand> r);
}

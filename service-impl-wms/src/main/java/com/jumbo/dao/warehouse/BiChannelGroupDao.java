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
package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.BiChannelGroup;
import com.jumbo.wms.model.warehouse.BiChannelRef;
import com.jumbo.wms.model.warehouse.BiChannelRefCommand;

@Transactional
public interface BiChannelGroupDao extends GenericEntityDao<BiChannelGroup, Long> {

    @NamedQuery
    BiChannelRef getBiChannelGroupByBiChannelID(@QueryParam(value = "id") Long id);

    @NativeQuery
    List<BiChannelGroup> getBiChannelGroupByOuId(@QueryParam(value = "id") Long id, RowMapper<BiChannelGroup> r);

    @NativeQuery
    List<BiChannelRefCommand> getBiChannelRefByCgId(@QueryParam(value = "id") Long id, RowMapper<BiChannelRefCommand> r);
}

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
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.model.vmi.nikeData.NikeCheckReceive;
import com.jumbo.wms.model.vmi.nikeData.NikeCheckReceiveCommand;

@Transactional
public interface NikeCheckReceiveDao extends GenericEntityDao<NikeCheckReceive, Long> {

    @NativeUpdate
    void updateToWriteStatus(@QueryParam(value = "toWriteStatus") Integer toWriteStatus, @QueryParam(value = "writeStatus") Integer writeStatus);
    
    @NativeUpdate
    void updateToWriteFinishDate(@QueryParam(value = "status") Integer status);
    
    @NamedQuery
    List<NikeCheckReceive> findToWriteFile(@QueryParam(value = "status") Integer toWriteStatus);

    @NativeQuery(pagable = true)
    Pagination<NikeCheckReceiveCommand> findNikeCheckReceive(int start, int size,@QueryParam Map<String, Object> params, Sort[] sorts, RowMapper<NikeCheckReceiveCommand> rowMapper);
    
    @NativeQuery
    Long findNikeCheckReceiveFileNO(RowMapper<Long> rowMapper);

}

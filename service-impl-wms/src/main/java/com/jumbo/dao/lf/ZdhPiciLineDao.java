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
package com.jumbo.dao.lf;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.lf.ZdhPiciLine;
import com.jumbo.wms.model.lf.ZdhPiciLineCommand;

@Transactional
public interface ZdhPiciLineDao extends GenericEntityDao<ZdhPiciLine, Long> {

    @NativeQuery(pagable = true)
    Pagination<ZdhPiciLineCommand> getHistoricalCodeListLine(int start, int pageSize, Sort[] sorts, @QueryParam("id") Long id, RowMapper<ZdhPiciLineCommand> rowMapper);

    @NativeQuery
    List<ZdhPiciLineCommand> getZdhPiciLineByCode(@QueryParam("code") String code, RowMapper<ZdhPiciLineCommand> r);


    @NativeQuery
    List<ZdhPiciLineCommand> getWmsZdhPiciLineCommand(@QueryParam("id") Long id, RowMapper<ZdhPiciLineCommand> r);
}

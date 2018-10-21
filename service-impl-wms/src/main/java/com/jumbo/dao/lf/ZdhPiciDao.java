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

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.lf.ZdhPici;
import com.jumbo.wms.model.lf.ZdhPiciCommand;

@Transactional
public interface ZdhPiciDao extends GenericEntityDao<ZdhPici, Long> {

    @NativeQuery
    String getZdhPiciCode(SingleColumnRowMapper<String> r);


    @NativeQuery(pagable = true)
    Pagination<ZdhPiciCommand> getHistoricalCodeList(int start, int pageSize, Sort[] sorts, RowMapper<ZdhPiciCommand> rowMapper);

    @NativeQuery
    ZdhPici getZdhPiciByCode(@QueryParam("code") String code, RowMapper<ZdhPici> r);


}

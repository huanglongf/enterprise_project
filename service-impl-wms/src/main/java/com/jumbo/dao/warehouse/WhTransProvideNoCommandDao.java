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

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WhTransProvideNoCommand;

@Transactional
public interface WhTransProvideNoCommandDao extends GenericEntityDao<WhTransProvideNoCommand, Long> {
    /**
     * 查询可用电子面单
     * 
     * @param lpcode
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<WhTransProvideNoCommand> findProvide(int start, int pagesize, @QueryParam("lpcode") String lpcode, BeanPropertyRowMapperExt<WhTransProvideNoCommand> r, Sort[] sorts);

    @NativeQuery
    Long findProvideByLpcode(@QueryParam("lpcode") String lpcode, @QueryParam("owner") String owner, @QueryParam("checkboxSf") Boolean checkboxSf, @QueryParam("jcustid") String jcustid, SingleColumnRowMapper<Long> r);
}

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

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.EspritStoreCommand;
import com.jumbo.wms.model.warehouse.EspritStore;

@Transactional
public interface EspritStoreDao extends GenericEntityDao<EspritStore, Long> {
    // esprit店铺查询
    @NativeQuery(pagable = true)
    Pagination<EspritStoreCommand> findEspritStoreByParams(int start, int pagesize, @QueryParam("ouId") long ouId, @QueryParam("name") String name, @QueryParam("code") String code, @QueryParam("cityCode") String cityCode,
            @QueryParam("contacts") String contacts, @QueryParam("telephone") String telephone, @QueryParam("userName") String userName, @QueryParam("gln") String gln, @QueryParam("cityGln") String cityGln, RowMapper<EspritStoreCommand> r,
            Sort[] sorts);

    // 查询esprit
    @NativeQuery
    EspritStoreCommand findEspritEn(@QueryParam("name") String name, @QueryParam("code") String code, @QueryParam("cityCode") String cityCode, @QueryParam("gln") String gln, @QueryParam("id") Long id, RowMapper<EspritStoreCommand> r);
}

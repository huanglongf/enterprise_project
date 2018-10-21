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
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceSnLine;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceSnLineCommand;

@Transactional
public interface InventoryCheckDifferenceSnLineDao extends GenericEntityDao<InventoryCheckDifferenceSnLine, Long> {

    @NativeUpdate
    void deleteByIc(@QueryParam(value = "icId") Long icId);

    @NativeQuery(pagable = true)
    Pagination<InventoryCheckDifferenceSnLineCommand> findSnDiffLineByIc(int start, int pageSize, @QueryParam(value = "icid") Long icId, RowMapper<InventoryCheckDifferenceSnLineCommand> r, Sort[] sorts);

    /**
     * SN号不修改，商品被修改
     * 
     * @param start
     * @param pageSize
     * @param invCkId
     * @param sort
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<InventoryCheckDifferenceSnLineCommand> findSnDiffLineSkuChangeByIc(int start, int pageSize, @QueryParam(value = "icid") Long icId, RowMapper<InventoryCheckDifferenceSnLineCommand> r, Sort[] sorts);
}

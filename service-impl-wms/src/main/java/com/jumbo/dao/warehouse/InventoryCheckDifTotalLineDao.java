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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.MapRowMapper;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLine;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLineCommand;

@Transactional
public interface InventoryCheckDifTotalLineDao extends GenericEntityDao<InventoryCheckDifTotalLine, Long> {

    @NamedQuery
    List<InventoryCheckDifTotalLine> findByInventoryCheck(@QueryParam("icId") Long icId);

    @NativeQuery(pagable = true)
    Pagination<InventoryCheckDifTotalLineCommand> findvmiicLineByInvCheckId(int start, int pageSize, @QueryParam("invcheckid") Long invcheckid, RowMapper<InventoryCheckDifTotalLineCommand> rowMapper, Sort[] sorts);

    @NativeQuery(withGroupby = true)
    List<InventoryCheckDifTotalLineCommand> findvmiicLineByInvCheckIdAndQty(@QueryParam("invcheckid") Long invcheckid, @QueryParam("qtyStatus") boolean qtyStatus, RowMapper<InventoryCheckDifTotalLineCommand> rowMapper, Sort[] sorts);

    @NativeQuery
    Map<String, Long> findVmiAdjSkuQuantity(@QueryParam(value = "invCKID") Long invCKID, @QueryParam(value = "type") boolean type, MapRowMapper r);

    @NativeQuery
    List<InventoryCheckDifTotalLineCommand> findLineNotInOrders(@QueryParam("icId") Long icId, @QueryParam("poNum") String poNum, RowMapper<InventoryCheckDifTotalLineCommand> rowMapper);

    @NativeUpdate
    void generateInventoryCheckDifTotalLine(@QueryParam(value = "quantity") Long quantity, @QueryParam(value = "invCheckId") Long invCheckId, @QueryParam(value = "skuId") Long skuId, @QueryParam(value = "invStatusId") Long invStatusId,
            @QueryParam(value = "skuCost") BigDecimal skuCost);

}

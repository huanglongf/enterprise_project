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
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.MapRowMapper;
import com.jumbo.wms.model.warehouse.InventoryCheckLine;
import com.jumbo.wms.model.warehouse.InventoryCheckLineCommand;

@Transactional
public interface InventoryCheckLineDao extends GenericEntityDao<InventoryCheckLine, Long> {

    @NamedQuery
    List<InventoryCheckLine> findByInventoryCheck(@QueryParam("icId") Long icId);

    @NativeQuery(pagable = true)
    Pagination<InventoryCheckLineCommand> findinvCheckLineDetialByInvCheckId(int start, int pageSize, @QueryParam("invcheckid") Long invcheckid, RowMapper<InventoryCheckLineCommand> rowMapper, Sort[] sorts);

    /**
     * 查询盘点批中库位
     * 
     * @param icId
     * @param r
     * @return
     */
    @NativeQuery
    Map<String, Long> findLocMap(@QueryParam("icId") Long icId, MapRowMapper r);

    /**
     * 更新库区
     * 
     * @param icId
     */
    @NativeUpdate
    void updateDistrictByInvCheck(@QueryParam("icId") Long icId);

    @NativeUpdate
    void insertLineByIc(@QueryParam("icId") Long icId, @QueryParam("locId") Long locId);
}

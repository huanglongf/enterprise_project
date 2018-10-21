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
package com.jumbo.dao.pickZone;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.pickZone.WhOcpOrderLineCommand;
import com.jumbo.wms.model.pickZone.WhOcpOrderLine;


/**
 * @author lihui
 *
 *         2015年7月7日 下午4:08:53
 */
@Transactional
public interface WhOcpOrderLineDao extends GenericEntityDao<WhOcpOrderLine, Long> {
    @NativeQuery
    List<WhOcpOrderLineCommand> findWhOcpOrderLineByWooId(@QueryParam(value = "wooId") Long wooId, @QueryParam(value = "skuId") Long skuId, @QueryParam(value = "qtyCheck") String qtyCheck, @QueryParam(value = "wooLineId") Long wooLineId,
            @QueryParam(value = "succeedOcp") String succeedOcp, RowMapper<WhOcpOrderLineCommand> rowMapper);

    @NativeUpdate
    void updateConformQtyByWoolId(@QueryParam(value = "woolId") Long woolId, @QueryParam(value = "conformQty") Long conformQty);
}

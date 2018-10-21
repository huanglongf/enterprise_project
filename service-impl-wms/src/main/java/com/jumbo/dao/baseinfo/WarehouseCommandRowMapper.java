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
package com.jumbo.dao.baseinfo;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import loxia.dao.support.BaseRowMapper;

import com.jumbo.wms.model.baseinfo.WarehouseManageMode;
import com.jumbo.wms.model.baseinfo.WarehouseOpMode;
import com.jumbo.wms.model.command.WarehouseCommand;

/***
 * 
 */
public class WarehouseCommandRowMapper extends BaseRowMapper<WarehouseCommand> {
    public WarehouseCommand mapRow(ResultSet rs, int arg1) throws SQLException {
        WarehouseCommand obj = new WarehouseCommand();

        obj.setIsShare(getResultFromRs(rs, "isshare", Boolean.class));
        obj.setQty(getResultFromRs(rs, "qty", Long.class));
        obj.setAvailQty(getResultFromRs(rs, "availQty", Long.class));
        obj.setId(getResultFromRs(rs, "id", Long.class));
        obj.setPic(getResultFromRs(rs, "pic", String.class));

        obj.setCode(getResultFromRs(rs, "code", String.class));
        obj.setName(getResultFromRs(rs, "name", String.class));
        obj.setIsAvailable(getResultFromRs(rs, "isAvailable", Boolean.class));

        obj.setManageMode(WarehouseManageMode.valueOf(getResultFromRs(rs, "manageMode", BigDecimal.class).intValue()));
        obj.setOpMode(WarehouseOpMode.valueOf(getResultFromRs(rs, "opMode", BigDecimal.class).intValue()));
        return obj;
    }

}

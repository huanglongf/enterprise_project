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
package com.jumbo.dao.authorization;

import java.sql.ResultSet;
import java.sql.SQLException;

import loxia.dao.support.BaseRowMapper;

import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;

public class OperationUnitRowMapper extends BaseRowMapper<OperationUnit> {

    public OperationUnit mapRow(ResultSet rs, int arg1) throws SQLException {
        OperationUnit obj = new OperationUnit();
        obj.setCode(getResultFromRs(rs, "CODE", String.class));
        obj.setFullName(getResultFromRs(rs, "FULL_NAME", String.class));
        obj.setId(getResultFromRs(rs, "ID", Long.class));
        obj.setIsAvailable(getResultFromRs(rs, "IS_AVAILABLE", Boolean.class));
        obj.setName(getResultFromRs(rs, "NAME", String.class));
        obj.setComment(getResultFromRs(rs, "OU_COMMENT", String.class));
        Long ou_type_id = getResultFromRs(rs, "OU_TYPE_ID", Long.class);
        if (ou_type_id != null) {
            OperationUnitType out = new OperationUnitType();
            out.setId(ou_type_id);
            out.setDescription(getResultFromRs(rs, "out_description", String.class));
            out.setDisplayName(getResultFromRs(rs, "out_display_name", String.class));
            out.setName(getResultFromRs(rs, "out_name", String.class));
            obj.setOuType(out);
        }
        Long parent_ou_id = getResultFromRs(rs, "PARENT_OU_ID", Long.class);
        if (parent_ou_id != null) {
            OperationUnit parent = new OperationUnit();
            parent.setId(parent_ou_id);
            obj.setParentUnit(parent);
        }

        return obj;
    }

}

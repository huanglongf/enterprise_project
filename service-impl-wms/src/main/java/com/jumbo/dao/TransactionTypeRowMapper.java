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

package com.jumbo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import loxia.dao.support.BaseRowMapper;

import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.TransactionControl;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;

/**
 * @author wanghua
 * 
 */
public class TransactionTypeRowMapper extends BaseRowMapper<TransactionType> {

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
     */
    public TransactionType mapRow(ResultSet arg0, int arg1) throws SQLException {
        TransactionType target = new TransactionType();
        target.setCode(getResultFromRs(arg0, "CODE", String.class));
        target.setControl(TransactionControl.valueOf(getResultFromRs(arg0, "CONTROL", Integer.class)));
        target.setDescription(getResultFromRs(arg0, "DESCRIPTION", String.class));
        target.setDirection(TransactionDirection.valueOf(getResultFromRs(arg0, "DIRECTION", Integer.class)));
        target.setId(getResultFromRs(arg0, "ID", Long.class));
        target.setIsAvailable(getResultFromRs(arg0, "IS_AVAILABLE", Boolean.class));
        target.setIsInCost(getResultFromRs(arg0, "IS_INCOST", Boolean.class));
        target.setIsSystem(getResultFromRs(arg0, "OU_ID", Boolean.class));
        target.setName(getResultFromRs(arg0, "NAME", String.class));
        OperationUnit ou = new OperationUnit();
        ou.setId(getResultFromRs(arg0, "OU_ID", Long.class));
        target.setOu(ou);
        target.setVersion(getResultFromRs(arg0, "VERSION", Integer.class));
        return target;
    }

}

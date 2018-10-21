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
package com.jumbo.wms.manager.warehouse;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.command.OperationUnitCommand;

/**
 * @author lichuan
 * 
 */
@Transactional
@Service("operationUnitManager")
public class OperationUnitManagerImpl implements OperationUnitManager {

    private static final long serialVersionUID = -1530266805238018230L;
    @Autowired
    private OperationUnitDao operationUnitDao;

    /** 
     *
     */
    @Override
    public List<OperationUnit> selectWarehouseByCenid(Long cenid) {
        List<OperationUnit> listopc = operationUnitDao.selectWarehouseByCenid(cenid, new BeanPropertyRowMapper<OperationUnit>(OperationUnit.class));
        return listopc;
    }

    @Override
    public OperationUnit getOperationUnitById(Long id) {
        OperationUnit unit = operationUnitDao.getByPrimaryKey(id);
        OperationUnitCommand cmd = new OperationUnitCommand();
        BeanUtils.copyProperties(unit, cmd);
        cmd.setChildrenUnits(null);
        cmd.setOuType(null);
        cmd.setParentUnit(null);
        return cmd;
    }

    @Override
    public OperationUnit getByCode(String code) {
        OperationUnit ou = operationUnitDao.getByCode(code);
        if (ou == null) {
            return null;
        }
        OperationUnit tmp = new OperationUnit();
        BeanUtils.copyProperties(ou, tmp, new String[] {"ouType", "parentUnit", "childrenUnits"});
        return tmp;
    }

    @Override
    public OperationUnit getById(Long id) {
        OperationUnit ou = operationUnitDao.getByPrimaryKey(id);
        return ou;
    }

}

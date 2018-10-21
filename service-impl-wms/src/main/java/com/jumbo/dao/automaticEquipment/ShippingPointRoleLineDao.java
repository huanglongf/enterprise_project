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
package com.jumbo.dao.automaticEquipment;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.automaticEquipment.ShippingPointRoleLine;
import com.jumbo.wms.model.command.automaticEquipment.ShippingPointRoleLineCommand;


/**
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
public interface ShippingPointRoleLineDao extends GenericEntityDao<ShippingPointRoleLine, Long> {
    /**
     * 查找规则ID
     * 
     * @param roleId
     * @param r
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<ShippingPointRoleLineCommand> findRoleLineByRoleId(int start, int pageSize, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "name") String name, BeanPropertyRowMapper<ShippingPointRoleLineCommand> r, Sort[] sorts);

    @NamedQuery
    ShippingPointRoleLine getPointRoleLineBySort(@QueryParam(value = "sort") Long sort, @QueryParam(value = "ouId") Long ouId);


    /**
     * 根据店铺获取规则
     * 
     * @param owner
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<ShippingPointRoleLineCommand> findShippingRoleByOuId(@QueryParam(value = "ouid") Long ouid, RowMapper<ShippingPointRoleLineCommand> rowMapper);
}

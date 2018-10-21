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
import java.util.Map;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.automaticEquipment.InboundRole;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.command.automaticEquipment.InboundRoleCommand;
import com.jumbo.wms.model.warehouse.WarehouseLocation;


/**
 * @author lihui
 *
 * @createDate 2016年1月19日 下午9:00:16
 */
@Transactional
public interface InboundRoleDao extends GenericEntityDao<InboundRole, Long> {
    @NativeQuery(pagable = true)
    Pagination<InboundRoleCommand> findInboundRoleByParams(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<InboundRoleCommand> rowMapper);

    /*
     * @NamedQuery InboundRole getInboundRoleByCode(@QueryParam(value = "code") String code);
     */


    @NativeQuery
    List<BiChannel> findChannelByAutoWh(@QueryParam(value = "ouId") Long ouId, RowMapper<BiChannel> rowMapper);

    @NativeQuery
    List<WarehouseLocation> findLocationByZoon(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "popId") Long popId, RowMapper<WarehouseLocation> rowMapper);

    @NamedQuery
    List<InboundRole> getInboundRoleByPopUpAreaId(@QueryParam(value = "popUpAreaId") Long popUpAreaId);
}

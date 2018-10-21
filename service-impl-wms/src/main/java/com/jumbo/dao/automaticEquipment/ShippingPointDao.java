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
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.automaticEquipment.ShippingPoint;
import com.jumbo.wms.model.command.automaticEquipment.ShippingPointCommand;


/**
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
public interface ShippingPointDao extends GenericEntityDao<ShippingPoint, Long> {

    @NativeQuery(pagable = true)
    Pagination<ShippingPointCommand> findShippingPointList(int start, int pageSize, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "name") String name, @QueryParam(value = "code") String code, @QueryParam(value = "wscCode") String wcs_code,
            BeanPropertyRowMapper<ShippingPointCommand> r, Sort[] sorts);

    @NativeQuery
    List<ShippingPoint> findPointByOuId(@QueryParam(value = "ouid") Long ouid, BeanPropertyRowMapper<ShippingPoint> beanPropertyRowMapper);

    @NamedQuery
    ShippingPoint getPointByName(@QueryParam(value = "name") String name);

    @NamedQuery
    ShippingPoint getPointByCode(@QueryParam(value = "code") String code);

    @NamedQuery
    ShippingPoint getPointByWcsCode(@QueryParam(value = "wcsCode") String wcscode);

    @NamedQuery
    ShippingPoint getPointById(@QueryParam(value = "id") String id);

    @NativeQuery(model = ShippingPoint.class)
    List<ShippingPoint> getPointByIds(@QueryParam(value = "ids") List<String> ids, BeanPropertyRowMapper<ShippingPoint> beanPropertyRowMapper);

    @NativeQuery(model = ShippingPoint.class)
    List<ShippingPoint> getPointByRefShippingPoint(@QueryParam(value = "refShippingPoint") String refShippingPoint, BeanPropertyRowMapper<ShippingPoint> beanPropertyRowMapper);

    @NativeQuery(pagable = true)
    Pagination<ShippingPoint> findAssumeShippingPointList(int start, int pageSize, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "refShippingPoint") String refShippingPoint, BeanPropertyRowMapper<ShippingPoint> r, Sort[] sorts);
}

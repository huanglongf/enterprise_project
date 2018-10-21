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

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.MapRowMapper;
import com.jumbo.wms.model.baseinfo.District;
import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface DistrictDao extends GenericEntityDao<District, Long> {

    @NamedQuery
    List<District> findAll();

    @NativeQuery
    Map<String, Long> findAllMap(MapRowMapper r);

    @NativeQuery
    List<District> findAllProvince(RowMapper<District> r);

    @NativeQuery
    List<District> findProvince(RowMapper<District> r);

    @NativeQuery
    List<District> findCity(@QueryParam(value = "province") String province, RowMapper<District> r);

    @NativeQuery
    List<District> findDistrict(@QueryParam(value = "province") String province, @QueryParam(value = "city") String city, RowMapper<District> r);

    @NamedQuery
    District findDistrictForSf(@QueryParam(value = "province") String province, @QueryParam(value = "city") String city);
    @NamedQuery
    District findProvince(@QueryParam(value = "province") String province);
}

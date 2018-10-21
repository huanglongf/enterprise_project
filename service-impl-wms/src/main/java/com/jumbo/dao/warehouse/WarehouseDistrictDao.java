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

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseDistrictType;

@Transactional
public interface WarehouseDistrictDao extends GenericEntityDao<WarehouseDistrict, Long> {
    /**
     * 根据组织ID获取库区
     * 
     * @param ouId
     * @return
     */
    @NamedQuery
    List<WarehouseDistrict> findDistrictListByOuId(@QueryParam(value = "ouId") Long ouId, Sort[] sorts);

    /**
     * 根据组织ID获取库区
     * 
     * @param ouId
     * @return
     */
    @NamedQuery
    List<WarehouseDistrict> findDistrictListByType(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "type") WarehouseDistrictType type, Sort[] sorts);

    /**
     * 建议当前仓库的库区编码的唯一性
     * 
     * @param code
     * @return
     */
    @NamedQuery
    WarehouseDistrict findDistrictByCodeAndOu(@QueryParam(value = "code") String code, @QueryParam(value = "ouId") Long ouId);

    /**
     * 
     * @param location
     * @param ouid
     * @return
     */
    @NativeQuery
    WarehouseDistrict findDistrictByLocationOuId(@QueryParam(value = "location") String location, @QueryParam(value = "ouid") Long ouid, BeanPropertyRowMapper<WarehouseDistrict> beanPropertyRowMapper);

    @NativeQuery(pagable = true)
    Pagination<WarehouseDistrict> findValidDistrictByouid(int start, int pageSize, @QueryParam(value = "code") String code, @QueryParam(value = "ouid") Long ouid, BeanPropertyRowMapperExt<WarehouseDistrict> beanPropertyRowMapperExt, Sort[] sorts);

    @NativeQuery
    String findDistrictCodeByid(@QueryParam(value = "id") Long id, SingleColumnRowMapper<String> singleColumnRowMapper);
    
    /**
     * 初始化库区下拉列表
     * 
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<WarehouseDistrict> findDistrictList(@QueryParam("ouId") Long ouId, RowMapper<WarehouseDistrict> rowMapper);


}

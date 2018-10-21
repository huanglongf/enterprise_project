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

import java.util.Map;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.automaticEquipment.SkuType;


/**
 * @author lihui
 *
 * @createDate 2016年1月19日 下午9:00:16
 */
@Transactional
public interface SkuTypeDao extends GenericEntityDao<SkuType, Long> {

    @NativeQuery(pagable = true)
    Pagination<SkuType> findSkuTypeByParams(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<SkuType> rowMapper);

    @NamedQuery
    SkuType getSkuTypeByName(@QueryParam(value = "skuTypeName") String skuTypeName);

    @NativeUpdate
    void updateSkuBySkuType(@QueryParam(value = "skuId") Long skuId, @QueryParam(value = "skuTypeId") Long skuTypeId);

}

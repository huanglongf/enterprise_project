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
package com.jumbo.dao.vmi.coachData;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.vmi.coachData.CoachProductData;

@Transactional
public interface CoachProductDataDao extends GenericEntityDao<CoachProductData, Long> {
    @NamedQuery
    CoachProductData getCPByStyleColorSize(@QueryParam("style") String style, @QueryParam("color") String color, @QueryParam("size") String size);

    @NamedQuery
    CoachProductData getCPByBarCode(@QueryParam("skuCode") String skuCode);

    @NativeUpdate
    int updateProductStatusByBarCode(@QueryParam("newStatus") Integer newStatus, @QueryParam("skuCode") String skuCode);

    @NativeUpdate
    int deleteProductStatusByBarCode(@QueryParam("skuCode") String skuCode);

    @NativeQuery
    List<String> findBarCodeByStyle(@QueryParam("styleNo") String styleNo, RowMapper<String> rowMapper);
}

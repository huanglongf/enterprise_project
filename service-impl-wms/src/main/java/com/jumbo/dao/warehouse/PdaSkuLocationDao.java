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

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.jumbo.wms.model.warehouse.PdaSkuLocation;

@Transactional
public interface PdaSkuLocationDao extends GenericEntityDao<PdaSkuLocation, Long> {

    @NativeQuery
    List<String> findLocationByBarcode(@QueryParam(value = "code") String code, @QueryParam(value = "barcode") String barcode, SingleColumnRowMapper<String> s);


    @NativeUpdate
    void deleteLocationBySkuIdAndCode(@QueryParam(value = "skuId") Long skuId, @QueryParam(value = "code") String code);
}

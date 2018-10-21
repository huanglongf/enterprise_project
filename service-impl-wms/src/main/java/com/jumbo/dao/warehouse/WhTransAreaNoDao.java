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
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WhTransAreaNo;
import com.jumbo.wms.model.warehouse.WhTransAreaNoCommand;

@Transactional
public interface WhTransAreaNoDao extends GenericEntityDao<WhTransAreaNo, Long> {
    /**
     * 查询物流省份编码
     * 
     * @param start
     * @param pagesize
     * @param lpcode
     * @param province
     * @param areaNumber
     * @param r
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<WhTransAreaNoCommand> findProvince(int start, int pagesize, @QueryParam("lpcode") String lpcode, @QueryParam("province") String province, @QueryParam("areaNumber") String areaNumber, BeanPropertyRowMapperExt<WhTransAreaNoCommand> r,
            Sort[] sorts);

    @NativeQuery
    List<WhTransAreaNo> getTransAreaByLpcode(@QueryParam("lpcode") String lpcode, BeanPropertyRowMapperExt<WhTransAreaNo> r);

    @NativeUpdate
    void deleteAreaByLpcode(@QueryParam("lpcode") String lpcode);

    @NativeUpdate
    void deleteAreaByAera(@QueryParam("lpcode") String lpcode, @QueryParam("province") String province);
}

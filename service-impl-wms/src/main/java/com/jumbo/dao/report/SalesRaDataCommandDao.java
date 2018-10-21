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

package com.jumbo.dao.report;

import java.util.Date;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.report.SalesRaDataCommand;
import com.jumbo.wms.model.report.SalesReportCommand;

@Transactional
public interface SalesRaDataCommandDao extends GenericEntityDao<SalesRaDataCommand, Long> {

    @NativeQuery
    List<SalesRaDataCommand> findRaData(@QueryParam("fromDate") Date fromDate, @QueryParam("toDate") Date toDate, @QueryParam("productCode") String productCode, @QueryParam("productCate") String productCate, @QueryParam("productLine") String productLine,
            @QueryParam("consumerGroup") String consumerGroup, @QueryParam("ouid") Long ouid, RowMapper<SalesRaDataCommand> r);

    /**
     * 查询当前仓库尚未完成的单据
     * 
     * @return
     */
    @NativeQuery
    List<SalesReportCommand> findByOuId(@QueryParam("ouId") Long ouId, RowMapper<SalesReportCommand> r);

}

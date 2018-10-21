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
package com.jumbo.dao.operationcenter;

import java.util.Date;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.ScanSkuRecordCommand;
import com.jumbo.wms.model.operationcenter.ScanSkuRecord;

/**
 * @author lichuan
 * 
 */
@Transactional
public interface ScanSkuRecordDao extends GenericEntityDao<ScanSkuRecord, Long> {

    /**
     * 查找扫描记录
     * 
     * @param start
     * @param pageSize
     * @param createTime
     * @param finishTime
     * @param batchCode
     * @param skuBarcode
     * @param skuCode
     * @param skuName
     * @param locationCode
     * @param operator
     * @param rowMapper
     * @param sort
     * @return Pagination<ScanSkuRecordCommand>
     * @throws
     */
    @NativeQuery(pagable = true)
    Pagination<ScanSkuRecordCommand> findScanRecordsByPage(int start, int pageSize, @QueryParam("createTime") Date createTime, @QueryParam("finishTime") Date finishTime, @QueryParam("batchCode") String batchCode,
            @QueryParam("skuBarcode") String skuBarcode, @QueryParam("skuCode") String skuCode, @QueryParam("skuName") String skuName, @QueryParam("locationCode") String locationCode, @QueryParam("operator") String operator,
            RowMapper<ScanSkuRecordCommand> rowMapper, Sort[] sort);


}

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
package com.jumbo.wms.manager.operationcenter;

import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.ScanSkuRecordCommand;

/**
 * @author lichuan
 * 
 */
public interface ScanSkuRecordManager extends BaseManager {
    /**
     * 保存
     * 
     * @param list
     * @param operatorId void
     * @throws
     */
    void saveScanRecords(List<ScanSkuRecordCommand> list, Long operatorId);

    /**
     * 查找记录列表
     * 
     * @param start
     * @param pageSize
     * @param ssrCmd
     * @param sort
     * @return Pagination<ScanSkuRecordCommand>
     * @throws
     */
    Pagination<ScanSkuRecordCommand> findScanRecordsByPage(int start, int pageSize, ScanSkuRecordCommand ssrCmd, Sort[] sort);

    /**
     * 获取批次号
     * 
     * @return String
     * @throws
     */
    String getScanRecordBatchCode();

}

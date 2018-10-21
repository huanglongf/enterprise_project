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
package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.util.Map;

import loxia.support.excel.ReadStatus;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.User;

/**
 * @author lichuan
 * 
 */
public interface BatchReceivingManagerProxy extends BaseManager {

    /**
     * 读取Excel所有数据
     * 
     * @param staFile
     * @param beans
     * @return
     * @throws Exception
     */
    public Map<String, Object> readAllDataFromExcel(File staFile, Map<String, Object> beans) throws Exception;

    /**
     * 批量收货
     * 
     * @param beans
     * @return
     * @throws Exception
     */
    public ReadStatus batchReceiving(Map<String, Object> beans, User creator) throws Exception;

    /**
     * 读取Excel所有数据
     * 
     * @param staFile
     * @param beans
     * @return
     * @throws Exception
     */
    public Map<String, Object> readPutawayDataFromExcel(File staFile, Map<String, Object> beans) throws Exception;

    /**
     * 负向采购退货入库批量上架
     * 
     * @param beans
     * @return
     * @throws Exception
     */
    public ReadStatus procurementBatchPutaway(Map<String, Object> beans, User creator, Long ouId) throws Exception;

    /**
     * 保质期读取Excel所有数据
     * 
     * @param staFile
     * @param beans
     * @return
     * @throws Exception
     */
    public Map<String, Object> readAllShelfLifeDataFromExcel(File staFile, Map<String, Object> beans) throws Exception;



    /**
     * 保质期商品批量收货
     * 
     * @param beans
     * @return
     * @throws Exception
     */
    public ReadStatus batchShelfLifeReceiving(Map<String, Object> beans, User creator) throws Exception;

}

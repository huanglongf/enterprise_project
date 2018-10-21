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
package com.jumbo.wms.manager.task.ttk;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.TtkConfirmOrderQueue;

/**
 * @author lichuan
 * 
 */
public interface TtkOrderTaskManager extends BaseManager {
    /**
     * 设置大头笔信息
     *@param staId void 
     *@throws
     *@throws Exception 
     */
    void setTransBigWord(Long staId) throws Exception;
    
    /**
     * 匹配运单号与设置大头笔信息
     *@param staId
     *@throws Exception void 
     *@throws
     */
    void setTransNoAndTransBigWord(Long staId) throws Exception;

    /**
     * 执行订单信息回传，出库通知TTK
     *@param q void 
     *@throws 
     */
    void exeTtkConfirmOrder(TtkConfirmOrderQueue q);

}

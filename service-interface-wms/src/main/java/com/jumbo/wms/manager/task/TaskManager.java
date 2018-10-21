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
package com.jumbo.wms.manager.task;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;

/**
 * @author wanghua
 * 
 */
public interface TaskManager extends BaseManager {
    static final String MAINLAND = "Mainland";
    static final String OVERSEAS = "Overseas";

    // /**
    // * task定时器入口
    // */
    // void execute();

    /**
     * 大陆站生成报表
     */
    void taskStatisticsMainland();

    /**
     * 海外站生成报表
     */
    void taskStatisticsOverseas();

    /**
     * 生成报表数据上传FTP
     */
    void taskStatistics(String district);

    /**
     * 跟新外包仓出库单状态
     * 
     * @param msgId
     * @param status
     */
    void updateUpdateCancleOrder(Long msgId, MsgOutboundOrderCancelStatus status);
}

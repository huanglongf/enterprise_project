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

package com.jumbo.wms.manager.trans;

import java.util.List;
import java.util.Map;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.trans.TransRoleCommand;

/**
 * @author lingyun.dai
 * 
 */
public interface TransSuggestManager extends BaseManager {

    /**
     * 居于渠道编码获取规则
     * 
     * @param owner
     * @return
     */
    List<TransRoleCommand> getTransRole(String owner);

    /**
     * 作业单物流推荐
     * 
     * @param staId
     */
    void suggestTransService(Long staId);

    /**
     * 判断直连订单物流商是否正确
     * 
     * @param id
     * @return
     */
    Boolean isCanUseLp(Long id);

    /**
     * 为直连订单推荐物流商
     * 
     * @param id
     * @return
     */
    Map<Long, String> suggestTransServiceForOrder(Long id);

    /**
     * 预售订单物流推荐
     */
    void suggestTransServicePreSale(Long id);

}

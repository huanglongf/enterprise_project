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
package com.jumbo.wms.manager.warehouse.card;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * @author lichuan
 * 
 */
public interface CardClientManager extends BaseManager {
    /**
     * 激活卡
     * 
     * @param cardno 卡号
     * @throws
     */
    CardResult activeCard(String cardno, String childCardno, Integer spType, StockTransApplication sta, Sku sku);

    /**
     * 解冻卡
     * 
     * @param cardno 卡号
     * @throws
     */
    CardResult unlockCard(String cardno);

    /**
     * 作废卡
     * 
     * @param cardno 卡号
     * @throws
     */
    CardResult cancelCard(String cardno, Integer spType, StockTransApplication sta);

    /**
     * 查询卡明细
     * 
     * @param cardno 卡号
     * @return String
     * @throws
     */
    String findCardDetial(String cardno);

}

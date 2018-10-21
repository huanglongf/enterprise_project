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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * @author lichuan
 * 
 */
@Transactional
@Service("defaultClient")
public class DefaultClientManagerImpl extends BaseManagerImpl implements CardClientManager {

    private static final long serialVersionUID = 5357639942777553076L;

    /**
     * 激活卡
     */
    @Override
    public CardResult activeCard(String cardno, String childCardno, Integer spType, StockTransApplication sta, Sku sku) {
        return null;
    }

    /**
     * 解冻卡
     */
    @Override
    public CardResult unlockCard(String cardno) {
        return null;
    }

    /**
     * 作废卡
     */
    @Override
    public CardResult cancelCard(String cardno, Integer spType, StockTransApplication sta) {
        return null;
    }

    /**
     * 查询卡明细
     */
    @Override
    public String findCardDetial(String cardno) {
        return null;
    }

}

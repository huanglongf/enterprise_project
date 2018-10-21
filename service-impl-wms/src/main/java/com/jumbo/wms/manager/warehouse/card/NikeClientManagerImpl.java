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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.bizhub.manager.nike.NikeActivateManager;
import com.baozun.bizhub.model.nike.ActivateRequest;
import com.baozun.bizhub.model.nike.ActivateResponse;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * @author lzb
 * 
 */
@Transactional
@Service("nikeClient")
public class NikeClientManagerImpl extends DefaultClientManagerImpl {

    private static final long serialVersionUID = -4464232939043220846L;

    private static final String ACTIVATE = "0";

    @SuppressWarnings("unused")
    private static final String UPDATE = "1";

    @Autowired
    private NikeActivateManager nikeActivate;
    /**
     * 激活卡
     */
    @Override
    public CardResult activeCard(String cardno, String childCardno, Integer spType, StockTransApplication sta, Sku sku) {
        log.debug("nikeClient activeCard begin=======================");
        CardResult cr = new CardResult();
        ActivateRequest ac = new ActivateRequest();
        ac.setAmount(sku.getListPrice().doubleValue());
        ac.setGivexNumber(cardno);
        ac.setLanguageCode("en");
        ac.setTransactionCode(sta.getCode());
        ActivateResponse re = nikeActivate.nikeActivate(ac);
        cr.setStatus(Integer.valueOf(re.getSuccess()));
        /*
         * 
         * if ("1".equals(re.getSuccess())) { cr.setStatus(1); } else { cr.setStatus(0); }
         */
        log.debug("nikeClient activeCard end=======================");
        return cr;
    }

    /**
     * 解冻卡
     */
    @Override
    public CardResult unlockCard(String cardno) {
        CardResult cr = new CardResult();
        // cr.setStatus(CardResult.STATUS_SUCCESS);
        return cr;
    }

    /**
     * 作废卡
     */
    @Override
    public CardResult cancelCard(String cardno, Integer spType, StockTransApplication sta) {
        CardResult cr = new CardResult();
        // cr.setStatus(CardResult.STATUS_SUCCESS);
        return cr;
    }

    /**
     * 查询卡明细
     */
    @Override
    public String findCardDetial(String cardno) {
        return null;
    }

}

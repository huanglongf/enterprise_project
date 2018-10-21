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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.bizhub.manager.starbucks.StarbucksManager;
import com.baozun.bizhub.model.starbucks.CodeActiveRequest;
import com.baozun.bizhub.model.starbucks.CodeActiveRequest.CodeActiveRequestLine;
import com.baozun.bizhub.model.starbucks.CodeActiveResponse;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * @author lzb
 * 
 */
@Transactional
@Service("starbucksBenClient")
public class StarbucksBenClientManagerImpl extends DefaultClientManagerImpl {

    private static final long serialVersionUID = -4464232939043220846L;

    @SuppressWarnings("unused")
    private static final String UPDATE = "1";


    @Autowired
    private StarbucksManager starbucksManager;


    /**
     * 激活卡
     */
    @Override
    public CardResult activeCard(String cardno, String childCardno, Integer spType, StockTransApplication sta, Sku sku) {
        log.debug("starbucksBenClient activeCard begin=======================");
        CardResult cr = new CardResult();
        CodeActiveRequest request = new CodeActiveRequest();
        List<CodeActiveRequestLine> codeActiveRequestLineList = new ArrayList<CodeActiveRequest.CodeActiveRequestLine>();
        CodeActiveRequestLine e = new CodeActiveRequestLine();
        e.setPackage_no(cardno);
        e.setOrder_no(sta.getCode());
        e.setTrade_type("ABPCode");
        e.setVersion_number("01");
        e.setOpt_type(null);
        e.setIp("112.110.110.15");
        e.setValid_end(null);
        e.setValid_start(null);
        codeActiveRequestLineList.add(e);
        request.setCodeActiveRequestLineList(codeActiveRequestLineList);
        CodeActiveResponse response = starbucksManager.hubCodeActive(request);
        cr.setStatus(response.getResults());
        cr.setMsg(response.getErrMsg());
        log.debug("starbucksBenClient activeCard end=======================");
        return cr;
    }

    /**
     * 解冻卡
     */
    @Override
    public CardResult unlockCard(String cardno) {
        CardResult cr = new CardResult();
        return cr;
    }

    /**
     * 作废卡
     */
    @Override
    public CardResult cancelCard(String cardno, Integer spType, StockTransApplication sta) {
        CardResult cr = new CardResult();
        return cr;
    }



}

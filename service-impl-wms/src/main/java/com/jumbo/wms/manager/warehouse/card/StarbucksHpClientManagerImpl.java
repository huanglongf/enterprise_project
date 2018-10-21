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
import com.baozun.bizhub.model.starbucks.TicketActiveRequest;
import com.baozun.bizhub.model.starbucks.TicketActiveRequest.TicketActiveRequestLine;
import com.baozun.bizhub.model.starbucks.TicketActiveRequest.TicketActiveRequestLine.Card;
import com.baozun.bizhub.model.starbucks.TicketActiveResponse;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * @author lichuan
 * 
 */
@Transactional
@Service("starbucksHpClient")
public class StarbucksHpClientManagerImpl extends DefaultClientManagerImpl {

    private static final long serialVersionUID = -4464232939043220846L;

    private static final String ACTIVATE = "0";

    @SuppressWarnings("unused")
    private static final String UPDATE = "1";

    @Autowired
    private StarbucksManager starbucksManager;

    /**
     * 激活卡
     */
    @Override
    public CardResult activeCard(String cardno, String childCardno, Integer spType, StockTransApplication sta, Sku sku) {
        log.debug("StarbucksHpClient activeCard begin=======================");
        CardResult cr = new CardResult();
        TicketActiveRequest tar = new TicketActiveRequest();
        TicketActiveRequestLine line = new TicketActiveRequestLine();
        List<Card> cList = new ArrayList<Card>();
        Card card = new Card();
        List<TicketActiveRequestLine> lineList = new ArrayList<TicketActiveRequestLine>();
        card.setCard_no(cardno);
        card.setOrder_no(sta.getSlipCode2());// 淘宝订单号
        cList.add(card);
        line.setActiveList(cList);
        line.setOpt_type(ACTIVATE);
        line.setIp("0.0.0.0");
        lineList.add(line);
        tar.setTicketActiveRequestLineList(lineList);
        tar.setMarket_code("4000105990");
        TicketActiveResponse ta = starbucksManager.hubTicketActive(tar);
        cr.setStatus(ta.getResults());
        cr.setMsg(ta.getErrMsg());
        // ca.getResults();// -1系统异常 1 成功 0激活失败
        // ca.getErrMsg();// 失败信息
        log.debug("StarbucksHpClient activeCard end=======================");
        return cr;
    }

    /**
     * 解冻卡
     */
    @Override
    public CardResult unlockCard(String cardno) {
        CardResult cr = new CardResult();
        /*
         * CodeStatusChangeRequest req = new CodeStatusChangeRequest(); CodeStatusChangeRequestLine
         * line = new CodeStatusChangeRequestLine(); List<CodeStatusChangeRequestLine> lineList =
         * new ArrayList<CodeStatusChangeRequestLine>(); line.setCode_no(cardno);
         * line.setChange_type("Unfreeze"); line.setIp("0.0.0.0"); lineList.add(line);
         * req.setCodeStatusChangeRequestLineList(lineList); CodeStatusChangeResponse ca =
         * hubDubboService.hubCodeStatusChange(req);// 智和信 cr.setStatus(ca.getResults());
         * cr.setMsg(ca.getErrMsg());
         */
        cr.setStatus(CardResult.STATUS_SUCCESS);
        return cr;
    }

    /**
     * 作废卡
     */
    @Override
    public CardResult cancelCard(String cardno, Integer spType, StockTransApplication sta) {
        CardResult cr = new CardResult();
        /*
         * CodeStatusChangeRequest req = new CodeStatusChangeRequest(); CodeStatusChangeRequestLine
         * line = new CodeStatusChangeRequestLine(); List<CodeStatusChangeRequestLine> lineList =
         * new ArrayList<CodeStatusChangeRequestLine>(); line.setCode_no(cardno);
         * line.setChange_type("  ");//作废 line.setIp("0.0.0.0"); lineList.add(line);
         * req.setCodeStatusChangeRequestLineList(lineList); CodeStatusChangeResponse ca =
         * hubDubboService.hubCodeStatusChange(req);// 智和信 cr.setStatus(ca.getResults());
         * cr.setMsg(ca.getErrMsg());
         */
        cr.setStatus(CardResult.STATUS_SUCCESS);
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

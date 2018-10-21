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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baozun.bizhub.manager.starbucks.StarbucksManager;
import com.baozun.bizhub.model.starbucks.CodeActiveRequest;
import com.baozun.bizhub.model.starbucks.CodeActiveRequest.CodeActiveRequestLine;
import com.baozun.bizhub.model.starbucks.CodeActiveResponse;
import com.baozun.bizhub.model.starbucks.CodeStatusChangeRequest;
import com.baozun.bizhub.model.starbucks.CodeStatusChangeRequest.CodeStatusChangeRequestLine;
import com.baozun.bizhub.model.starbucks.CodeStatusChangeResponse;
import com.baozun.bizhub.model.starbucks.CodeUnActiveRequest;
import com.baozun.bizhub.model.starbucks.CodeUnActiveRequest.CodeUnActiveRequestLine;
import com.baozun.bizhub.model.starbucks.CodeUnActiveResponse;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSpType;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * @author lichuan
 * 
 */
@Transactional
@Service("starbucksZhxClient")
public class StarbucksZhxClientManagerImpl extends DefaultClientManagerImpl {

    private static final long serialVersionUID = 5357639942777553076L;

    private static final String ACTIVATE = "0";

    @SuppressWarnings("unused")
    private static final String UPDATE = "1";

    @Autowired
    private StarbucksManager starbucksManager;
    @Autowired
    private SkuDao skuDao;

    /**
     * 激活卡
     */
    @Override
    public CardResult activeCard(String cardno, String childCardno, Integer spType, StockTransApplication sta, Sku sku) {
        log.debug("StarbucksZhxClient activeCard begin=======================");
        CardResult cr = new CardResult();
        CodeActiveRequest car = new CodeActiveRequest();
        CodeActiveRequestLine line = new CodeActiveRequestLine();
        List<CodeActiveRequestLine> lineList = new ArrayList<CodeActiveRequestLine>();

        line.setOpt_type(ACTIVATE);
        line.setIp("0.0.0.0");
        if (StringUtils.hasText(childCardno)) {
            line.setCode_no(childCardno);
        } else {
            line.setCode_no(cardno);
        }
        SkuCommand staLine = skuDao.findStaLineByStaIdCard(cardno, sta.getId(), new BeanPropertyRowMapperExt<SkuCommand>(SkuCommand.class));
        if (null != staLine && null != staLine.getListPrice()) {
            if (staLine.getChildSnQty() != null) {
                line.setSelling_price(staLine.getListPrice().doubleValue() / staLine.getChildSnQty());
            } else {
                line.setSelling_price(staLine.getListPrice().doubleValue());
            }
            if (null != staLine.getOrderTotalBfDiscount()) {
                BigDecimal bigQuantity = new BigDecimal(staLine.getQuantity());
                BigDecimal costPrice = staLine.getOrderTotalBfDiscount().divide(bigQuantity, 2, BigDecimal.ROUND_HALF_EVEN);
                if (staLine.getChildSnQty() != null) {
                    line.setCost_price(costPrice.doubleValue() / staLine.getChildSnQty());
                } else {
                    line.setCost_price(costPrice.doubleValue());
                }
            } else if (null == staLine.getOrderTotalBfDiscount()) {
                if (staLine.getChildSnQty() != null) {
                    line.setCost_price(line.getSelling_price() / staLine.getChildSnQty());
                } else {
                    line.setCost_price(line.getSelling_price());
                }
            }
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        if (spType != null) {
            if (SkuSpType.STARBUCKS_SVC.getValue().equals(spType) || SkuSpType.STARBUCKS_MSR.getValue().equals(spType)) {
                line.setTrade_type("ACode");// 卡
                line.setOrder_no(sta.getSlipCode2());// 平台订单号
            }
            if (SkuSpType.STARBUCKS_SVC_T.getValue().equals(spType)) {
                line.setTrade_type("APCode");// 卡券
            }
        }

        lineList.add(line);
        car.setCodeActiveRequestLineList(lineList);
        car.setMarket_code("4000105990");
        CodeActiveResponse ca = starbucksManager.hubCodeActive(car);// 智和信
        cr.setStatus(ca.getResults());
        cr.setMsg(ca.getErrMsg());
        cr.setErrorCode(ca.getErrCode());

        // ca.getResults();// -1系统异常 1 成功 0激活失败
        // ca.getErrMsg();// 失败信息
        log.debug("StarbucksZhxClient activeCard end=======================");
        return cr;
    }

    /**
     * 解冻卡
     */
    @Override
    public CardResult unlockCard(String cardno) {
        CardResult cr = new CardResult();
        CodeStatusChangeRequest req = new CodeStatusChangeRequest();
        CodeStatusChangeRequestLine line = new CodeStatusChangeRequestLine();
        List<CodeStatusChangeRequestLine> lineList = new ArrayList<CodeStatusChangeRequestLine>();
        line.setCode_no(cardno);
        line.setChange_type("unfreeze");
        line.setIp("0.0.0.0");
        lineList.add(line);
        req.setCodeStatusChangeRequestLineList(lineList);
        CodeStatusChangeResponse ca = starbucksManager.hubCodeStatusChange(req);// 智和信
        cr.setStatus(ca.getResults());
        cr.setMsg(ca.getErrMsg());
        cr.setErrorCode(ca.getErrCode());
        return cr;
    }

    /**
     * 作废卡
     */
    @Override
    public CardResult cancelCard(String cardno, Integer spType, StockTransApplication sta) {
        CardResult cr = new CardResult();
        CodeUnActiveRequest req = new CodeUnActiveRequest();
        CodeUnActiveRequestLine line = new CodeUnActiveRequestLine();
        List<CodeUnActiveRequestLine> lineList = new ArrayList<CodeUnActiveRequestLine>();
        line.setCode_no(cardno);
        line.setIp("0.0.0.0");
        if (null != spType && SkuSpType.STARBUCKS_SVC.getValue().equals(spType)) {
            line.setOrder_no(sta.getSlipCode1());// 平台订单号
        }
        lineList.add(line);
        req.setMarket_code("4000105990");
        req.setCodeUnActiveRequestLineList(lineList);
        CodeUnActiveResponse ca = starbucksManager.hubCodeUnActive(req);// 智和信,取消激活
        cr.setStatus(ca.getResults());
        cr.setMsg(ca.getErrMsg());
        cr.setErrorCode(ca.getErrCode());
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

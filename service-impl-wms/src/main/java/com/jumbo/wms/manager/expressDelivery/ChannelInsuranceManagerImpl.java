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
package com.jumbo.wms.manager.expressDelivery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.BiChannelSpecialActionDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialAction;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialActionStatus;
import com.jumbo.wms.model.warehouse.BiChannelSpecialActionCommand;

/**
 * @author lichuan
 * 
 */
@Transactional
@Service("channelInsuranceManager")
public class ChannelInsuranceManagerImpl extends BaseManagerImpl implements ChannelInsuranceManager {
    // 渠道保价信息池
    private static Map<String, BiChannelSpecialActionCommand> insuranceInfoPool = new HashMap<String, BiChannelSpecialActionCommand>();
    private static final long serialVersionUID = 1L;
    @Autowired
    private BiChannelSpecialActionDao biChannelSpecialActionDao;


    /**
     * 渠道快递保价维护与计算
     * 
     * @param owner 渠道编码
     * @param insuranceAmount 保价金额（StaDeliveryInfo.insuranceAmount）
     * @return 最终保价金额
     */
    @Override
    public BigDecimal getInsurance(String owner, BigDecimal insuranceAmount) {
        BigDecimal insurance = insuranceAmount;
        if (null == owner || "".equals(owner)) throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        if (null == insuranceAmount) return insurance;// 不保价
        if (insuranceAmount.longValue() <= 0L) return null;// 不保价
        BiChannelSpecialActionCommand currentChannelInsurance = getChannelInsuranceInfo(owner);
        // 计算保价金额
        if (null != currentChannelInsurance) {
            Long isInsurance = currentChannelInsurance.getIsInsurance();
            if (isInsurance == null) {
                isInsurance = 0l;
            }
            BigDecimal minInsurance = currentChannelInsurance.getMinInsurance();
            BigDecimal maxInsurance = currentChannelInsurance.getMaxInsurance();
            if (BiChannelSpecialActionStatus.Insurance.getValue() == isInsurance) {
                if (null == minInsurance && null == maxInsurance)// 保价上下限均不设置全额保价
                {
                    insurance = insuranceAmount;
                } else if (null == minInsurance && null != maxInsurance)// 仅设置保价上限
                {
                    BigDecimal subtract = maxInsurance.subtract(insuranceAmount);
                    if (subtract.floatValue() >= 0)// 小于等于保价上限时保价
                        insurance = insuranceAmount;
                    else
                        insurance = null;// 不保价
                } else if (null != minInsurance && null == maxInsurance)// 仅设置保价下限
                {
                    BigDecimal subtract = insuranceAmount.subtract(minInsurance);
                    if (subtract.floatValue() >= 0)// 大于等于保价下限时保价
                        insurance = insuranceAmount;
                    else
                        insurance = null;
                } else// 保价上下限均设置
                {
                    BigDecimal s1 = maxInsurance.subtract(insuranceAmount);
                    BigDecimal s2 = insuranceAmount.subtract(minInsurance);
                    if (s1.floatValue() >= 0 && s2.floatValue() >= 0)
                        insurance = insuranceAmount;
                    else
                        insurance = null;

                }
            } else {
                insurance = null;
            }
        }

        return insurance;
    }

    private BiChannelSpecialActionCommand getChannelInsuranceInfo(String channelCode) {
        BiChannelSpecialActionCommand info = insuranceInfoPool.get(channelCode);
        if (null == info) {
            info = findChannelInsuranceInfo(channelCode);
            insuranceInfoPool.put(channelCode, info);
        } else {
            Date expDate = info.getExpDate();
            long expTimes = expDate.getTime();
            Date currentDate = new Date();
            long currentTimes = currentDate.getTime();
            if (expTimes < currentTimes) {
                info = findChannelInsuranceInfo(channelCode);
                insuranceInfoPool.put(channelCode, info);
            } else
                info = insuranceInfoPool.get(channelCode);

        }
        return info;
    }

    private BiChannelSpecialActionCommand findChannelInsuranceInfo(String channelCode) {
        BiChannelSpecialActionCommand info = new BiChannelSpecialActionCommand();
        // 获取保价金额设置信息
        BiChannelSpecialAction sa = biChannelSpecialActionDao.getChannelActionByCodeAndType(channelCode, 30, new BeanPropertyRowMapperExt<BiChannelSpecialAction>(BiChannelSpecialAction.class));
        if (null == sa) return null;
        Date date = new Date();
        // 一小时后过期
        long time = date.getTime();
        long expTime = 60 * 60 * 1000;
        time += expTime;
        Date expDate = new Date(time);
        info.setExpDate(expDate);
        info.setIsInsurance(sa.getIsInsurance());
        info.setMinInsurance(sa.getMinInsurance());
        info.setMaxInsurance(sa.getMaxInsurance());
        return info;
    }

    @Override
    public BiChannelSpecialActionCommand getInsuranceEn(String owner) {
        BiChannelSpecialActionCommand currentChannelInsurance = findChannelInsuranceInfo(owner);
        return currentChannelInsurance;
    }

}

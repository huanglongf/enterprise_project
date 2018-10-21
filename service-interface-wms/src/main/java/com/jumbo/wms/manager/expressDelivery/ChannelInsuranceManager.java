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

import com.jumbo.wms.model.warehouse.BiChannelSpecialActionCommand;

/**
 * @author lichuan
 * 
 */
public interface ChannelInsuranceManager {
    /**
     * 
     *@Description: 渠道快递保价维护与计算
     *@param owner
     *@param orderTotalActual
     *@return BigDecimal
     *@throws
     */
    public BigDecimal getInsurance(String owner, BigDecimal orderTotalActual);
    /**
     * 
     * @Description: 根据店铺code查询该实体
     * @param owner
     * @param orderTotalActual
     * @return BigDecimal
     * @throws
     */
    public BiChannelSpecialActionCommand getInsuranceEn(String owner);


}

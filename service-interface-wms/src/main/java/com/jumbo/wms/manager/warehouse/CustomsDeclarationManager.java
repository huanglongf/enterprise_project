/**
 * 
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
package com.jumbo.wms.manager.warehouse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.SkuDeclaration;
import com.jumbo.wms.model.vmi.nikeData.NikeVmiStockInCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;

public interface CustomsDeclarationManager extends BaseManager {

    /**
     * 创建报关信息
     * 
     * @param sta
     */
    public void newCustomsDeclaration(StockTransApplication sta, Boolean recommendCar);

    /**
     * 获取推荐车辆车牌号
     * 
     * @param staId
     * @return
     */
    public String findLicensePlateNumber(Long staId);

    /**
     * 获取重量
     * 
     * @param staId
     * @return
     */
    public Map<String, BigDecimal> findWeight(Long staId);

    public List<NikeVmiStockInCommand> findNikeVmiStockInByDeclaration(String nikeVmiCode);


    public StockTransApplication findStaById(Long staId);

    public void changeIsCustomsDeclaration(StockTransApplication stockTransApplication, NikeVmiStockInCommand nikeVmiStockInCommand);

    void changeCustomsDeclarationStatus(Long id, Integer status, String errorMsg);

    void changeSkuDeclarationStatus(Long id, Integer status, String errorMsg);

    void changeCustomsDeclarationDeclarationStatus(Long id, Integer status);

    void changeSkuDeclarationDeclarationStatus(List<SkuDeclaration> sdList, Integer status, Integer ciqStatus);

    void newSkuDeclaration(String msg);
}

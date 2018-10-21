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

package com.jumbo.wms.model.command;

import com.jumbo.wms.model.BaseModel;


/**
 * 
 * 
 * @author lihui
 * 
 */
public class JohnsonInventoryCommand extends BaseModel {
   

    
    
    /**
     * 
     */
    private static final long serialVersionUID = 1276144857092829072L;

    /*
     * 库存日期
     */
    private String stockDate;
    
    /*
     * 产品编码
     */
    private String prodCode;
    
    /*
     * 产品名称
     */
    private String prodName;
    
    /*
     * 产品数量
     */
    private Integer prodQuantity;
    
    /*
     * 产品单位
     */
    private String prodUnit;
    
    /*
     * 产品批号
     */
    private String prodNum;
    
    /*
     * 仓库类型
     */
    private String stockType;
    
    /*
     * 备注
     */
    private String remark;

    /**
     * 店铺
     */
    private String owner;

    public String getStockDate() {
        return stockDate;
    }

    public void setStockDate(String stockDate) {
        this.stockDate = stockDate;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Integer getProdQuantity() {
        return prodQuantity;
    }

    public void setProdQuantity(Integer prodQuantity) {
        this.prodQuantity = prodQuantity;
    }

    public String getProdUnit() {
        return prodUnit;
    }

    public void setProdUnit(String prodUnit) {
        this.prodUnit = prodUnit;
    }

    public String getProdNum() {
        return prodNum;
    }

    public void setProdNum(String prodNum) {
        this.prodNum = prodNum;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    
}

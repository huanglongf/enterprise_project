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
package com.jumbo.wms.model.warehouse;

import com.jumbo.wms.model.BaseModel;

/**
 * @author jumbo
 * 
 */
public class WarehousingCommand extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4765635492431633602L;

    private StockTransApplication sta;
    private StockTransVoucher stv;
    private Long noPerformNum;
    private Long shelvesNum;
    private Long total;

    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    public Long getNoPerformNum() {
        return noPerformNum;
    }

    public void setNoPerformNum(Long noPerformNum) {
        this.noPerformNum = noPerformNum;
    }

    public Long getShelvesNum() {
        return shelvesNum;
    }

    public void setShelvesNum(Long shelvesNum) {
        this.shelvesNum = shelvesNum;
    }

    public StockTransVoucher getStv() {
        return stv;
    }

    public void setStv(StockTransVoucher stv) {
        this.stv = stv;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }



}

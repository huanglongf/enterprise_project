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
package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;
import java.util.List;

public class PickingListLineObj implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5861156917659649979L;

    private String location;
    private String barCode;
    private String jmCode;
    private String skuName;
    private String keyProperty;
    private Integer quantity;

    private List<PickingListSubLineObj> sublines;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getJmCode() {
        return jmCode;
    }

    public void setJmCode(String jmCode) {
        this.jmCode = jmCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getKeyProperty() {
        return keyProperty;
    }

    public void setKeyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<PickingListSubLineObj> getSublines() {
        return sublines;
    }

    public void setSublines(List<PickingListSubLineObj> sublines) {
        this.sublines = sublines;
    }
}

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
package com.jumbo.wms.model.warehouse;

/**
 * @author lichuan
 *
 */
public class PickingListPackageCommand extends PickingListPackage {

    private static final long serialVersionUID = -2096676836185253493L;
    private Long plId;
    private String plCode;
    private String lpcode;
    private Integer checkQty;
    private Integer planQty;
    private Boolean isNeedWrapstuff;
	private Boolean isManualWeighing;
    public Long getPlId() {
        return plId;
    }
    
    public void setPlId(Long plId) {
        this.plId = plId;
    }
    public String getPlCode() {
        return plCode;
    }
    public void setPlCode(String plCode) {
        this.plCode = plCode;
    }
    public String getLpcode() {
        return lpcode;
    }
    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }
    public Integer getCheckQty() {
        return checkQty;
    }
    public void setCheckQty(Integer checkQty) {
        this.checkQty = checkQty;
    }
    public Integer getPlanQty() {
        return planQty;
    }
    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

	public Boolean getIsNeedWrapstuff() {
		return isNeedWrapstuff;
	}

	public void setIsNeedWrapstuff(Boolean isNeedWrapstuff) {
		this.isNeedWrapstuff = isNeedWrapstuff;
	}

	public Boolean getIsManualWeighing() {
		return isManualWeighing;
	}

	public void setIsManualWeighing(Boolean isManualWeighing) {
		this.isManualWeighing = isManualWeighing;
	}

}

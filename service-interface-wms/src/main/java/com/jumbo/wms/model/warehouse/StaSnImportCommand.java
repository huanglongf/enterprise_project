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

import java.io.Serializable;

/**
 * @author xiaolong.fei 用于 退换货入库 SN号导入存放json数据
 */
public class StaSnImportCommand implements Serializable {
	    /**
     * 
     */
    private static final long serialVersionUID = 3604581952349306790L;

    /**
     * sku中barCode
     */
	private String barCode;

	/**
	 * 库存状态
	 */
	private String intInvstatusName;

	/**
	 * sn号
	 */
	private String snNumber;

	/**
	 * 导入失败的sn号
	 */
	private String errorSnList;
	/**
	 * 导入失败的barCode
	 */
	private String errorBarCode;
	/**
	 * 库存状态导入失败的barCode
	 */
	private String errorStatus;

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getIntInvstatusName() {
		return intInvstatusName;
	}

	public void setIntInvstatusName(String intInvstatusName) {
		this.intInvstatusName = intInvstatusName;
	}

	public String getSnNumber() {
		return snNumber;
	}

	public void setSnNumber(String snNumber) {
		this.snNumber = snNumber;
	}

	public String getErrorSnList() {
		return errorSnList;
	}

	public void setErrorSnList(String errorSnList) {
		this.errorSnList = errorSnList;
	}

	public String getErrorBarCode() {
		return errorBarCode;
	}

	public void setErrorBarCode(String errorBarCode) {
		this.errorBarCode = errorBarCode;
	}

	public String getErrorStatus() {
		return errorStatus;
	}

	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
	}

}

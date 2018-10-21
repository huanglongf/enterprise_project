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

import java.math.BigDecimal;

import com.jumbo.wms.model.baseinfo.Warehouse;



public class WarehouseCommand extends Warehouse {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6514349983945816352L;
    /**
     * 仓库实际库存总数(件)
     */
    private Long qty;
    /**
     * 仓库实际可用量总数(件)
     */
    private Long availQty;

    private String code;
    private String name;
	private Boolean isAvailable;
    private int ouId;
    private String isSupportCod;
    private Integer isShareInt;
    private Integer isNeedWrapStuffInt;
    private Integer isManualWeighingInt;
    private Integer isCheckedBarcodeInt;
    private Integer isSfOlOrderInt;
    private Integer isOutinvoiceInt;
    /**
     * SO_COUNT_MODEL_ID(OMS字段保留) Edw
     */
    private Integer soCountModelId;
    /**
     * IS_AUTO_COMMIT(OMS字段保留)
     */
    private Integer isAutoCommit;
    /**
     * IS_AUTO_COMMIT(OMS字段保留)
     */
    private Integer isCloseRollover;
	                    /**
     * 客户ID
     */
    private Integer customerId;
    private Integer opModeInt;
    private Integer manageModeInt;
    private Integer saleOcpTypeInt;
    private Integer isMqInvoiceInt;
    private Integer isEmsOlOrderInt;
    private Integer isSupportSeckillInt;
    private Integer isOlStoInt;
    private Integer isZtoOlOrderInt;
    /**
     * 订单核算模式-名称
     */
    private String soCountModelName;

    private BigDecimal whSize;
    // private Integer isAvailable;

    private String ouName;// 仓库组织名称

    public String getOuName() {
        return ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getAvailQty() {
        return availQty;
    }

    public void setAvailQty(Long availQty) {
        this.availQty = availQty;
    }

    public String getSoCountModelName() {
        return soCountModelName;
    }

    public void setSoCountModelName(String soCountModelName) {
        this.soCountModelName = soCountModelName;
    }
    public int getOuId() {
		return ouId;
	}

	public void setOuId(int ouId) {
		this.ouId = ouId;
	}

    public String getIsSupportCod() {
        return isSupportCod;
    }

    public void setIsSupportCod(String isSupportCod) {
        this.isSupportCod = isSupportCod;
    }

	public Integer getSoCountModelId() {
		return soCountModelId;
	}

	public void setSoCountModelId(Integer soCountModelId) {
		this.soCountModelId = soCountModelId;
	}

	public Integer getIsAutoCommit() {
		return isAutoCommit;
	}

	public void setIsAutoCommit(Integer isAutoCommit) {
		this.isAutoCommit = isAutoCommit;
	}

	public Integer getIsCloseRollover() {
		return isCloseRollover;
	}

	public void setIsCloseRollover(Integer isCloseRollover) {
		this.isCloseRollover = isCloseRollover;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getWhSize() {
		return whSize;
	}

	public void setWhSize(BigDecimal whSize) {
		this.whSize = whSize;
	}

	public Integer getOpModeInt() {
		return opModeInt;
	}

	public void setOpModeInt(Integer opModeInt) {
		this.opModeInt = opModeInt;
	}

	public Integer getManageModeInt() {
		return manageModeInt;
	}

	public void setManageModeInt(Integer manageModeInt) {
		this.manageModeInt = manageModeInt;
	}

	public Integer getSaleOcpTypeInt() {
		return saleOcpTypeInt;
	}

	public void setSaleOcpTypeInt(Integer saleOcpTypeInt) {
		this.saleOcpTypeInt = saleOcpTypeInt;
	}

	public Integer getIsShareInt() {
		return isShareInt;
	}

	public void setIsShareInt(Integer isShareInt) {
		this.isShareInt = isShareInt;
	}

	public Integer getIsNeedWrapStuffInt() {
		return isNeedWrapStuffInt;
	}

	public void setIsNeedWrapStuffInt(Integer isNeedWrapStuffInt) {
		this.isNeedWrapStuffInt = isNeedWrapStuffInt;
	}

	public Integer getIsManualWeighingInt() {
		return isManualWeighingInt;
	}

	public void setIsManualWeighingInt(Integer isManualWeighingInt) {
		this.isManualWeighingInt = isManualWeighingInt;
	}

	public Integer getIsCheckedBarcodeInt() {
		return isCheckedBarcodeInt;
	}

	public void setIsCheckedBarcodeInt(Integer isCheckedBarcodeInt) {
		this.isCheckedBarcodeInt = isCheckedBarcodeInt;
	}

	public Integer getIsSfOlOrderInt() {
		return isSfOlOrderInt;
	}

	public void setIsSfOlOrderInt(Integer isSfOlOrderInt) {
		this.isSfOlOrderInt = isSfOlOrderInt;
	}

	public Integer getIsMqInvoiceInt() {
		return isMqInvoiceInt;
	}

	public void setIsMqInvoiceInt(Integer isMqInvoiceInt) {
		this.isMqInvoiceInt = isMqInvoiceInt;
	}

	public Integer getIsOutinvoiceInt() {
		return isOutinvoiceInt;
	}

	public void setIsOutinvoiceInt(Integer isOutinvoiceInt) {
		this.isOutinvoiceInt = isOutinvoiceInt;
	}

	public Integer getIsEmsOlOrderInt() {
		return isEmsOlOrderInt;
	}

	public void setIsEmsOlOrderInt(Integer isEmsOlOrderInt) {
		this.isEmsOlOrderInt = isEmsOlOrderInt;
	}

	public Integer getIsSupportSeckillInt() {
		return isSupportSeckillInt;
	}

	public void setIsSupportSeckillInt(Integer isSupportSeckillInt) {
		this.isSupportSeckillInt = isSupportSeckillInt;
	}

	public Integer getIsOlStoInt() {
		return isOlStoInt;
	}

	public void setIsOlStoInt(Integer isOlStoInt) {
		this.isOlStoInt = isOlStoInt;
	}

	public Integer getIsZtoOlOrderInt() {
		return isZtoOlOrderInt;
	}

	public void setIsZtoOlOrderInt(Integer isZtoOlOrderInt) {
		this.isZtoOlOrderInt = isZtoOlOrderInt;
	}
    
	
	
}

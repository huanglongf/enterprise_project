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

/**
 * sku sn号
 */
public class SkuSnCommand extends SkuSn {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2233502755061988955L;

    private String cStatus;

    private String snType;

    private Integer interfaceType;

    private Long skuid;

    private Long loctionid;

    private Integer countNumber;
    
    private Long ouId;
    
    private Long stvId;
    /**
     * 外包仓反馈Id
     */
    private Long rtnSnId;
    /**
     * 临时变量是否已经设置
     */
    private boolean isSeted;

    private Integer spType;

    private StockTransApplication sta;

    private Integer skuSnStatus;

    public Integer getSkuSnStatus(){
        return skuSnStatus;
    }

    public void setSkuSnStatus(Integer skuSnStatus){
        this.skuSnStatus = skuSnStatus;
    }

    public StockTransApplication getSta(){
        return sta;
    }

    public void setSta(StockTransApplication sta){
        this.sta = sta;
    }

    public String getcStatus() {
        return cStatus;
    }

    public void setcStatus(String cStatus) {
        this.cStatus = cStatus;
    }

    public String getSnType() {
        return snType;
    }

    public Long getSkuid() {
        return skuid;
    }

    public void setSkuid(Long skuid) {
        this.skuid = skuid;
    }

    public void setSnType(String snType) {
        this.snType = snType;
    }


    public Integer getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(Integer interfaceType) {
        this.interfaceType = interfaceType;
    }


    /**
     * sku 条码
     */
    private String barcode;
    /**
     * sku 商品编码
     */
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取maSku编号
     */
    private Long maSkuId;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Long getMaSkuId() {
        return maSkuId;
    }

    public void setMaSkuId(Long maSkuId) {
        this.maSkuId = maSkuId;
    }

    public boolean getIsSeted() {
        return isSeted;
    }

    public void setIsSeted(boolean isSeted) {
        this.isSeted = isSeted;
    }

    public Long getLoctionid() {
        return loctionid;
    }

    public void setLoctionid(Long loctionid) {
        this.loctionid = loctionid;
    }

    public Integer getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(Integer countNumber) {
        this.countNumber = countNumber;
    }

    public void setSeted(boolean isSeted) {
        this.isSeted = isSeted;
    }

    public Integer getSpType() {
        return spType;
    }

    public void setSpType(Integer spType) {
        this.spType = spType;
    }

	public Long getOuId() {
		return ouId;
	}

	public void setOuId(Long ouId) {
		this.ouId = ouId;
	}

	public Long getStvId() {
		return stvId;
	}

	public void setStvId(Long stvId) {
		this.stvId = stvId;
	}

	public Long getRtnSnId() {
		return rtnSnId;
	}

	public void setRtnSnId(Long rtnSnId) {
		this.rtnSnId = rtnSnId;
	}

}

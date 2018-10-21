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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.jumbo.util.StringUtils;
import com.jumbo.wms.model.BaseModel;

/**
 * SKU 补货
 */
public class SkuReplenishmentCommand extends BaseModel implements Cloneable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3893663454540593806L;

    /**
     * skuId
     */
    private Long skuId;
    /**
     * sku 编码
     */
    private String skuCode;

    /**
     * 商品条码
     */
    private String barCode;
    /**
     * 货号
     */
    private String supplierCode;
    /**
     * 商品编码
     */
    private String jmCode;
    /**
     * 整箱数
     */
    private Long boxQty;
    /**
     * 库区编码
     */
    private String districtCode;
    /**
     * 库位编码
     */
    private String locationCode;
    /**
     * 推荐补货库区编码
     */
    private String recommenDistrictCode;
    /**
     * 库区最大存储数
     */
    private Long maxQty;
    /**
     * 安全警戒线
     */
    private BigDecimal warningPre;
    /**
     * 当前库存数量
     */
    private Long invQty;
    /**
     * 补货数量
     */
    private Long replenishmentQty;
    /**
     * 最大补货量
     */
    private Long maxReplenishmentQty;

    /**
     * sta作业单号
     */
    private String staCode;
    /**
     * sta相关单据号
     */
    private String staSlipCode;
    /**
     * 店铺code
     */
    private String owner;

    /**
     * 店铺name
     */
    private String name;

    /**
     * 最大值
     */
    private Long maxnumber;
    /**
     * 商品大小
     */
    private String skuSize;

    /**
     * 出库库位实际库存
     */
    private Long stock;
    /**
     * 系统推荐入库库位
     */
    private String groomCode;

    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    public Long getMaxnumber() {
        return maxnumber;
    }

    public void setMaxnumber(Long maxnumber) {
        this.maxnumber = maxnumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getJmCode() {
        return jmCode;
    }

    public void setJmCode(String jmCode) {
        this.jmCode = jmCode;
    }

    public Long getBoxQty() {
        return boxQty;
    }

    public void setBoxQty(Long boxQty) {
        this.boxQty = boxQty;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public Long getMaxQty() {
        return maxQty;
    }

    public void setMaxQty(Long maxQty) {
        this.maxQty = maxQty;
    }

    public BigDecimal getWarningPre() {
        return warningPre;
    }

    public void setWarningPre(BigDecimal warningPre) {
        this.warningPre = warningPre;
    }

    public Long getInvQty() {
        return invQty;
    }

    public void setInvQty(Long invQty) {
        this.invQty = invQty;
    }

    public Long getReplenishmentQty() {
        return replenishmentQty;
    }

    public void setReplenishmentQty(Long replenishmentQty) {
        this.replenishmentQty = replenishmentQty;
    }

    public Long getMaxReplenishmentQty() {
        return maxReplenishmentQty;
    }

    public void setMaxReplenishmentQty(Long maxReplenishmentQty) {
        this.maxReplenishmentQty = maxReplenishmentQty;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getStaSlipCode() {
        return staSlipCode;
    }

    public void setStaSlipCode(String staSlipCode) {
        this.staSlipCode = staSlipCode;
    }

    public String getRecommenDistrictCode() {
        return recommenDistrictCode;
    }

    public void setRecommenDistrictCode(String recommenDistrictCode) {
        this.recommenDistrictCode = recommenDistrictCode;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getGroomCode() {
        return groomCode;
    }

    public void setGroomCode(String groomCode) {
        this.groomCode = groomCode;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getQueryLikeParam() {
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.hasText(this.getSkuCode())) {
            params.put("skuCode", this.getSkuCode());
        }
        if (StringUtils.hasText(this.getBarCode())) {
            params.put("barCode", this.getBarCode());
        }
        if (StringUtils.hasText(this.getSupplierCode())) {
            params.put("supplierCode", this.getSupplierCode());
        }
        if (StringUtils.hasText(this.getJmCode())) {
            params.put("jmCode", this.getJmCode());
        }
        if (StringUtils.hasText(this.getDistrictCode())) {
            params.put("districtCode", this.getDistrictCode());
        }
        if (StringUtils.hasText(this.getLocationCode())) {
            params.put("locationCode", this.getLocationCode());
        }
        if (this.getSkuId() != null) {
            params.put("skuId", this.getSkuId());
        }
        if (this.getBoxQty() != null) {
            params.put("boxQty", this.getBoxQty());
        }
        if (this.getMaxQty() != null) {
            params.put("maxQty", this.getMaxQty());
        }
        if (this.getWarningPre() != null) {
            params.put("warningPre", this.getWarningPre());
        }
        if (this.getInvQty() != null) {
            params.put("invQty", this.getInvQty());
        }
        if (this.getBoxQty() != null) {
            params.put("replenishmentQty", this.getReplenishmentQty());
        }
        if (StringUtils.hasText(this.getStaCode())) {
            params.put("staCode", this.getStaCode());
        }
        if (StringUtils.hasText(this.getStaSlipCode())) {
            params.put("staSlipCode", this.getStaSlipCode());
        }
        if (StringUtils.hasText(this.getOwner())) {
            params.put("owner", this.getOwner());
        }
        if (StringUtils.hasText(this.getName())) {
            params.put("name", this.getName());
        }
        if (this.getMaxnumber() != null) {
            params.put("maxnumber", this.getMaxnumber());
        }
        if (StringUtils.hasText(this.getSkuSize())) {
            params.put("skuSize", this.getSkuSize());
        }
        return params;
    }

    @Override
    public SkuReplenishmentCommand clone() {
        SkuReplenishmentCommand bean = new SkuReplenishmentCommand();
        bean.skuId = this.skuId;
        bean.skuCode = this.skuCode;
        bean.barCode = this.barCode;
        bean.supplierCode = this.supplierCode;
        bean.jmCode = this.jmCode;
        bean.boxQty = this.boxQty;
        bean.districtCode = this.districtCode;
        bean.locationCode = this.locationCode;
        bean.maxQty = this.maxQty;
        bean.maxnumber = this.maxnumber;
        bean.warningPre = this.warningPre;
        bean.invQty = this.invQty;
        bean.replenishmentQty = this.replenishmentQty;
        bean.maxReplenishmentQty = this.maxReplenishmentQty;
        bean.staCode = this.staCode;
        bean.staSlipCode = this.staSlipCode;
        bean.owner = this.owner;
        bean.name = this.name;
        bean.skuSize = this.skuSize;
        bean.recommenDistrictCode = this.recommenDistrictCode;
        return bean;
    }
}

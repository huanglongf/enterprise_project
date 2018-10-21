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

/**
 * 补寄发票DTO
 * 
 * @author jinlong.ke
 * 
 */
public class WmsInvoiceOrderCommand extends WmsInvoiceOrder {

    private static final long serialVersionUID = -2724556516803505854L;

    private String endCreateTime;

    private String slipCode;

    private Integer intStatus;

    private Long staId;

    private Long quantity;

    private String trackingNo;

    private String receverTel;

    private String sender;

    private String senderAddress;

    private String senderTel;

    private String refSlipCode;

    private String remark;

    private BigDecimal amount;

    private Boolean isRailway;

    private String isSupportCod;

    private String strAmount;

    private String skuList;

    private String transmemo;

    private String transTimeTypeB;

    private String transTypeB;

    private String transCityCode;

    private String jcustid;

    private String insuranceAmount;

    private String isTransCodPos;

    private String sfTrackingNo;

    private String areaNumber;// 省份编码

    private String barCode;


    public Map<String, Object> setQueryLikeParam() {
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.hasText(this.getOrderCode())) {
            this.setOrderCode("%" + this.getOrderCode() + "%");
            params.put("orderCode", this.getOrderCode());
        } else {
            this.setOrderCode(null);
        }
        if (StringUtils.hasText(this.getOwner())) {
            this.setOwner("%" + this.getOwner() + "%");
            params.put("owner", this.getOwner());
        } else {
            this.setOwner(null);
        }
        if (StringUtils.hasText(this.getLpCode())) {
            this.setLpCode("%" + this.getLpCode() + "%");
            params.put("lpCode", this.getLpCode());
        } else {
            this.setLpCode(null);
        }
        if (StringUtils.hasText(this.getReceiver())) {
            this.setReceiver("%" + this.getReceiver() + "%");
            params.put("receiver", this.getReceiver());
        } else {
            this.setReceiver(null);
        }
        if (StringUtils.hasText(this.getTelephone())) {
            this.setTelephone("%" + this.getTelephone() + "%");
            params.put("telephone", this.getTelephone());
        } else {
            this.setTelephone(null);
        }
        if (StringUtils.hasText(this.getMobile())) {
            this.setMobile("%" + this.getMobile() + "%");
            params.put("mobile", this.getMobile());
        } else {
            this.setMobile(null);
        }
        if (StringUtils.hasText(this.getBatchCode())) {
            this.setBatchCode("%" + this.getBatchCode() + "%");
            params.put("batchCode", this.getBatchCode());
        } else {
            this.setBatchCode(null);
        }
        if (StringUtils.hasText(this.getTransNo())) {
            this.setTransNo("%" + this.getTransNo() + "%");
            params.put("transNo", this.getTransNo());
        } else {
            this.setTransNo(null);
        }
        if (StringUtils.hasText(this.getSlipCode())) {
            this.setSlipCode("%" + this.getSlipCode() + "%");
            params.put("slipCode", this.getSlipCode());
        } else {
            this.setSlipCode(null);
        }
        if (StringUtils.hasText(this.getProvince())) {
            this.setProvince("%" + this.getProvince() + "%");
            params.put("province", this.getProvince());
        } else {
            this.setProvince(null);
        }
        if (StringUtils.hasText(this.getCity())) {
            this.setCity("%" + this.getCity() + "%");
            params.put("city", this.getCity());
        } else {
            this.setCity(null);
        }
        if (StringUtils.hasText(this.getDistrict())) {
            this.setDistrict("%" + this.getDistrict() + "%");
            params.put("district", this.getDistrict());
        } else {
            this.setDistrict(null);
        }
        if (StringUtils.hasText(this.getAddress())) {
            this.setAddress("%" + this.getAddress() + "%");
            params.put("address", this.getAddress());
        } else {
            this.setAddress(null);
        }
        if (this.getCreateTime() != null) {
            params.put("createTime", this.getCreateTime());
        }
        if (this.getEndCreateTime() != null) {
            params.put("endCreateTime", this.getEndCreateTime());
        }
        if (this.getStatus() != null) {
            params.put("status", this.getStatus());
        }
        return params;
    }


    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }
    @Override
    public Integer getIntStatus() {
        return intStatus;
    }
    @Override
    public void setIntStatus(Integer intStatus) {
        this.intStatus = intStatus;
    }


    public Long getStaId() {
        return staId;
    }


    public void setStaId(Long staId) {
        this.staId = staId;
    }


    public Long getQuantity() {
        return quantity;
    }


    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }


    public String getTrackingNo() {
        return trackingNo;
    }


    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }


    public String getReceverTel() {
        return receverTel;
    }


    public void setReceverTel(String receverTel) {
        this.receverTel = receverTel;
    }


    public String getSender() {
        return sender;
    }


    public void setSender(String sender) {
        this.sender = sender;
    }


    public String getSenderAddress() {
        return senderAddress;
    }


    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }


    public String getSenderTel() {
        return senderTel;
    }


    public void setSenderTel(String senderTel) {
        this.senderTel = senderTel;
    }


    public String getRefSlipCode() {
        return refSlipCode;
    }


    public void setRefSlipCode(String refSlipCode) {
        this.refSlipCode = refSlipCode;
    }


    /*
     * public String getPgindex() { return pgindex; }
     * 
     * 
     * public void setPgindex(String pgindex) { this.pgindex = pgindex; }
     */


    public String getRemark() {
        return remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }


    public BigDecimal getAmount() {
        return amount;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public Boolean getIsRailway() {
        return isRailway;
    }


    public void setIsRailway(Boolean isRailway) {
        this.isRailway = isRailway;
    }


    public String getIsSupportCod() {
        return isSupportCod;
    }


    public void setIsSupportCod(String isSupportCod) {
        this.isSupportCod = isSupportCod;
    }


    public String getStrAmount() {
        return strAmount;
    }


    public void setStrAmount(String strAmount) {
        this.strAmount = strAmount;
    }


    public String getSkuList() {
        return skuList;
    }


    public void setSkuList(String skuList) {
        this.skuList = skuList;
    }


    public String getTransmemo() {
        return transmemo;
    }


    public void setTransmemo(String transmemo) {
        this.transmemo = transmemo;
    }


    public String getTransTimeTypeB() {
        return transTimeTypeB;
    }


    public void setTransTimeTypeB(String transTimeTypeB) {
        this.transTimeTypeB = transTimeTypeB;
    }


    public String getTransTypeB() {
        return transTypeB;
    }


    public void setTransTypeB(String transTypeB) {
        this.transTypeB = transTypeB;
    }

    @Override
    public String getTransCityCode() {
        return transCityCode;
    }

    @Override
    public void setTransCityCode(String transCityCode) {
        this.transCityCode = transCityCode;
    }


    public String getJcustid() {
        return jcustid;
    }


    public void setJcustid(String jcustid) {
        this.jcustid = jcustid;
    }


    public String getInsuranceAmount() {
        return insuranceAmount;
    }


    public void setInsuranceAmount(String insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }


    public String getIsTransCodPos() {
        return isTransCodPos;
    }


    public void setIsTransCodPos(String isTransCodPos) {
        this.isTransCodPos = isTransCodPos;
    }


    public String getSfTrackingNo() {
        return sfTrackingNo;
    }


    public void setSfTrackingNo(String sfTrackingNo) {
        this.sfTrackingNo = sfTrackingNo;
    }


    public String getAreaNumber() {
        return areaNumber;
    }


    public void setAreaNumber(String areaNumber) {
        this.areaNumber = areaNumber;
    }


    public String getBarCode() {
        return barCode;
    }


    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }


}

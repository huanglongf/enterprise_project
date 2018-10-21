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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jumbo.util.StringUtils;

/**
 * 出库装箱头信息
 * 
 */
public class CartonCommand extends Carton {

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CartonCommand other = (CartonCommand) obj;
        if (this.getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!this.getId().equals(other.getId())) {
            return false;
        }
        return true;
    }

    private static final long serialVersionUID = -5765094830767951775L;

    private String staCode;

    private String staSlipCode;

    private String slipCode2;

    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    private Integer intStatus;

    private Date startCreateTime;

    private Date endCreateTime;

    private Integer completeQty;

    private String receiver;
    private String address;
    private String mobile;
    private String telephone;

    // t_wh_sta_delivery_info信息
    private String lpCode;
    private String trackno;

    private String staType;

    private String skus;// 退仓耗材

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    public String getStaType() {
        return staType;
    }

    public void setStaType(String staType) {
        this.staType = staType;
    }

    public String getTrackno() {
        return trackno;
    }

    public void setTrackno(String trackno) {
        this.trackno = trackno;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
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

    public Integer getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(Integer intStatus) {
        this.intStatus = intStatus;
    }

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, Object> getQueryParam() {
        Map<String, Object> param = new HashMap<String, Object>();
        if (StringUtils.hasText(getStaCode())) {
            param.put("staCode", getStaCode() + "%");
        }
        if (StringUtils.hasText(getStaSlipCode())) {
            param.put("staSlipCode", getStaSlipCode() + "%");
        }
        if (StringUtils.hasText(getSlipCode2())) {
            param.put("slipCode2", getSlipCode2() + "%");
        }
        if (StringUtils.hasText(getCode())) {
            param.put("code", getCode() + "%");
        }
        if (StringUtils.hasText(getSeqNo())) {
            param.put("seqNo", getSeqNo() + "%");
        }
        if (getStartCreateTime() != null) {
            param.put("startCreateTime", getStartCreateTime());
        }
        if (getEndCreateTime() != null) {
            param.put("endCreateTime", getEndCreateTime());
        }
        return param;
    }

    public Integer getCompleteQty() {
        return completeQty;
    }

    public void setCompleteQty(Integer completeQty) {
        this.completeQty = completeQty;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}

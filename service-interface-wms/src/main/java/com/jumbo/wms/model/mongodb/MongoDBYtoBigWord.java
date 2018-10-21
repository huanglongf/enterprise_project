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
package com.jumbo.wms.model.mongodb;

import javax.persistence.Column;

import com.jumbo.wms.model.BaseModel;

/**
 * @author lichuan
 * 
 */
public class MongoDBYtoBigWord extends BaseModel {

    private static final long serialVersionUID = 1829085006307268704L;
    /**
     * 目的省
     */
    private String province;
    /**
     * 目的地
     */
    private String district;
    /**
     * 打包编码
     */
    private String packNo;
    /**
     * 优先级
     */
    private Integer priority;

    @Column(name = "Province", length = 100)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "district", length = 100)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "pack_no", length = 50)
    public String getPackNo() {
        return packNo;
    }

    public void setPackNo(String packNo) {
        this.packNo = packNo;
    }

    @Column(name = "priority", length = 10)
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }



}

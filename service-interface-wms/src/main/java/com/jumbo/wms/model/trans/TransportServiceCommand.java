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

package com.jumbo.wms.model.trans;


public class TransportServiceCommand extends TransportService {


    /**
     * 
     */
    private static final long serialVersionUID = 7718447339831756770L;
    private String serviceType;// 服务类型
    private String timeTypes;// 时效类型
    private String statuss; // 状态
    private String num;// 数量
    private String timeTypeKey;// 时效类型对应的key值

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTimeTypeKey() {
        return timeTypeKey;
    }

    public void setTimeTypeKey(String timeTypeKey) {
        this.timeTypeKey = timeTypeKey;
    }


    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getTimeTypes() {
        return timeTypes;
    }

    public void setTimeTypes(String timeTypes) {
        this.timeTypes = timeTypes;
    }

    public String getStatuss() {
        return statuss;
    }

    public void setStatuss(String statuss) {
        this.statuss = statuss;
    }

}

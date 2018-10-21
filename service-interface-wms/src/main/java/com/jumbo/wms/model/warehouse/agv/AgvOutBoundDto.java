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

package com.jumbo.wms.model.warehouse.agv;

import java.util.ArrayList;
import java.util.List;

/**
 * AGV出库中间表
 * 
 * @author lzb
 * 
 */


public class AgvOutBoundDto extends AgvOutBound {
    private static final long serialVersionUID = -2279998389533374224L;

    private String shipToName;// 宝尊要传收件人姓名
    private String shipToPhone;// 宝尊要传收件人联系方式
    private String shipToAddress;// 宝尊要传收件人地址
    private String shipFromPhone;// 店铺电话。作为打印装箱单取值
    private String shipFromAddress;// 店铺地址。作为打印装箱单取值
    private String logisticsCompany;// 快递公司
    private String tmallbillNumber;// 淘宝单号
    private Integer invoicequantity;// 发票数量
    private String ownerCode;// 宝尊传店铺名称
    private String sourcenumber;// 上位系統單號
    private String pickingListCode;// 配货批次号



    public String getSourcenumber() {
        return sourcenumber;
    }

    public void setSourcenumber(String sourcenumber) {
        this.sourcenumber = sourcenumber;
    }

    public Integer getInvoicequantity() {
        return invoicequantity;
    }

    public void setInvoicequantity(Integer invoicequantity) {
        this.invoicequantity = invoicequantity;
    }

    public String getShipToName() {
        return shipToName;
    }

    public void setShipToName(String shipToName) {
        this.shipToName = shipToName;
    }

    public String getShipToPhone() {
        return shipToPhone;
    }

    public void setShipToPhone(String shipToPhone) {
        this.shipToPhone = shipToPhone;
    }

    public String getShipToAddress() {
        return shipToAddress;
    }

    public void setShipToAddress(String shipToAddress) {
        this.shipToAddress = shipToAddress;
    }

    public String getShipFromPhone() {
        return shipFromPhone;
    }

    public void setShipFromPhone(String shipFromPhone) {
        this.shipFromPhone = shipFromPhone;
    }

    public String getShipFromAddress() {
        return shipFromAddress;
    }

    public void setShipFromAddress(String shipFromAddress) {
        this.shipFromAddress = shipFromAddress;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getTmallbillNumber() {
        return tmallbillNumber;
    }

    public void setTmallbillNumber(String tmallbillNumber) {
        this.tmallbillNumber = tmallbillNumber;
    }


    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    private List<AgvOutBoundLineDto> list = new ArrayList<AgvOutBoundLineDto>();


    public List<AgvOutBoundLineDto> getList() {
        return list;
    }

    public void setList(List<AgvOutBoundLineDto> list) {
        this.list = list;
    }

    public String getPickingListCode() {
        return pickingListCode;
    }

    public void setPickingListCode(String pickingListCode) {
        this.pickingListCode = pickingListCode;
    }



}

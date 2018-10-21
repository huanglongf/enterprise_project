package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author jinggang.chen 退仓装箱信息
 * 
 */
public class ReturnWarehousePackingInfo implements Serializable {

    private static final long serialVersionUID = -7523968612977781070L;

    private String returnCode;// 退仓装箱单号

    private String customer;// 客户

    private String address;// 地址

    private Date returnDate;// 日期

    private Date expectedTime;// 预计抵达日期

    private String linkman;// 联系人

    private String telephone;// 电话

    private List<ReturnWarehousePackingLine> lines;// 退仓装箱明细

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(Date expectedTime) {
        this.expectedTime = expectedTime;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<ReturnWarehousePackingLine> getLines() {
        return lines;
    }

    public void setLines(List<ReturnWarehousePackingLine> lines) {
        this.lines = lines;
    }

}

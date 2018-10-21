package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.List;

public class InvoinceItem implements Serializable {

    private static final long serialVersionUID = 698391414720619798L;
    /**
     * 发票类型(1营业税发票（普通）3增值税发票)
     */
    private String billType;
    /**
     * 发票ID，发票去重使用，一个订单可以有多张发票，LBX+ billId是唯一的
     */
    private Long billId;
    /**
     * 发票抬头
     */
    private String billTitle;
    /**
     * 发票金额，单位：分
     */
    private String billAccount;
    /**
     * 购方纳税人识别号
     */
    private String buyerNo;
    /**
     * 购方地址、电话
     */
    private String buyerAddrPhone;
    /**
     * 购方开户行及帐号
     */
    private String buyerBankAccount;
    /**
     * 发票内容,发票内容和明细二选一
     */
    private String billContent;
    /**
     * 发票明细列表,发票内容和明细二选一
     */
    private List<ItemDetail> detailList;

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getBillTitle() {
        return billTitle;
    }

    public void setBillTitle(String billTitle) {
        this.billTitle = billTitle;
    }

    public String getBillAccount() {
        return billAccount;
    }

    public void setBillAccount(String billAccount) {
        this.billAccount = billAccount;
    }

    public String getBuyerNo() {
        return buyerNo;
    }

    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    public String getBuyerAddrPhone() {
        return buyerAddrPhone;
    }

    public void setBuyerAddrPhone(String buyerAddrPhone) {
        this.buyerAddrPhone = buyerAddrPhone;
    }

    public String getBuyerBankAccount() {
        return buyerBankAccount;
    }

    public void setBuyerBankAccount(String buyerBankAccount) {
        this.buyerBankAccount = buyerBankAccount;
    }

    public String getBillContent() {
        return billContent;
    }

    public void setBillContent(String billContent) {
        this.billContent = billContent;
    }

    public List<ItemDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<ItemDetail> detailList) {
        this.detailList = detailList;
    }

}

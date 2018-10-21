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
package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PackingObj implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1762034829686016265L;

    private Integer index;// 批次号序列
    private String pgIndex;// 批次号序列


    private String lpCode; // 物流供应商编码
    private String soCode; // 订单号
    private String slipCode1;// 销售订单号
    private String slipCode2;// 订单号
    private String soCode2;// 天猫订单号
    private String receiver; // 收货人
    private String telephone; // 收货人联系电话
    private String logoImg;
    private String zipcode; // 邮编
    private BigDecimal transferFee; // 运费
    private BigDecimal totalAmount; // 货款
    private BigDecimal amt; // 包装费
    private String orderDateNew;

    public String getOrderDateNew() {
        return orderDateNew;
    }

    public void setOrderDateNew(String orderDateNew) {
        this.orderDateNew = orderDateNew;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    private String sn;// 商品sn号
    private String receiverAddress; // 收货地址
    private String cppAddress;
    private String rtnAddress; // 退货地址
    private String shopTelephone; // 店铺售后联系电话
    private String remark; // 送货要求时间备注
    private String diliveryWhName; // 发货仓库
    private String returnTransno; // 退货运单号
    private String returnTransnoUrl; // 退货运单号二维码
    private String returnTime; // 退货时间

    private String orderDate; // 订单日期
    private String orderDate1; // 订单日期(斜杠分隔符)
    private Date outboundTime;// 出库日期
    private String batchIndex; // 自动化出库1级2级批次

    public String getPrintTimeHk() {
        return printTimeHk;
    }

    public void setPrintTimeHk(String printTimeHk) {
        this.printTimeHk = printTimeHk;
    }

    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    private String title1 = "宝尊电商送货单";
    private String title2 = "宝尊电商包裹装箱清单";


    private String batchNo; // 批次号
    private Integer detailSize;

    private Date printTime; // 打印时间
    private String printshopname; // 打印店铺名称
    private String printshopname2;// 打印店铺名称(无送货单)

    public String getPrintshopname2() {
        return printshopname2;
    }

    public void setPrintshopname2(String printshopname2) {
        this.printshopname2 = printshopname2;
    }

    private List<PackingLineObj> lines;

    private String trunkTemplateName; // 装箱清单打印Ireport模板名字
    /**
     * isNotDisplaySum 默认为空 : 显示金额 设置成1 : 不显示金额 设置成0 : 显示金额
     */
    private Boolean isNotDisplaySum; // 装箱清单-是否打印金额
    private String soOutCode; // 外部订单号


    private Boolean isSeedInvoice; // 是否开发票
    private Integer invoiceNum; // 发票数量
    private String invoiceNumber; // 发票数量

    private String code; // sta作业单号

    private String staMemo;// 作业单备注

    private String barcodeImg;// 二维条码图片
    private String imgLogoTmall; // 天猫logo

    private String country; // 国家
    private String province; // 省
    private String city;// 市
    private String district;// 区
    private String address;// 送货地址

    private String payType;// 支付方式

    private String printTime1;
    private String printTimeHk;
    private String batchNo1;
    private String batchNo2;

    private String rtnContacts;// 退货收件人

    private String imgSemacode;// IMG_SEMACODE

    private String orderUserCode;// 会员名

    private String locationCode;// 仓库编码LOCATIONCODE

    private String lpCodeHkMain;

    private int totalQty; // 总计数量

    private int totalQty2; // 总计数量 ad

    private Long staId;// 作业单ID

    private String nikeHKPic;// NikeHK装箱清单文案图片

    private List<String> nikeHKList;// NikeHK装箱清单文案图片

    private String qrCode;// Ralph Lauren加密二维码

    private BigDecimal totalSkuListPrice;// 合计吊牌价

    /**
     * 防伪编码
     */
    private String antiFake;

    private String returnTranckingNo;// Nike逆向运单号

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }


    public int getTotalQty2() {
        return totalQty2;
    }

    public void setTotalQty2(int totalQty2) {
        this.totalQty2 = totalQty2;
    }

    public String getSoCode2() {
        return soCode2;
    }

    public void setSoCode2(String soCode2) {
        this.soCode2 = soCode2;
    }

    public String getPgIndex() {
        return pgIndex;
    }

    public void setPgIndex(String pgIndex) {
        this.pgIndex = pgIndex;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getRtnAddress() {
        return rtnAddress;
    }

    public void setRtnAddress(String rtnAddress) {
        this.rtnAddress = rtnAddress;
    }

    public String getShopTelephone() {
        return shopTelephone;
    }

    public void setShopTelephone(String shopTelephone) {
        this.shopTelephone = shopTelephone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDiliveryWhName() {
        return diliveryWhName;
    }

    public void setDiliveryWhName(String diliveryWhName) {
        this.diliveryWhName = diliveryWhName;
    }

    public Date getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Date printTime) {
        this.printTime = printTime;
    }

    public List<PackingLineObj> getLines() {
        return lines;
    }

    public void setLines(List<PackingLineObj> lines) {
        this.lines = lines;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getDetailSize() {
        return detailSize;
    }

    public void setDetailSize(Integer detailSize) {
        this.detailSize = detailSize;
    }

    public String getTrunkTemplateName() {
        return trunkTemplateName;
    }

    public void setTrunkTemplateName(String trunkTemplateName) {
        this.trunkTemplateName = trunkTemplateName;
    }

    public Boolean getIsNotDisplaySum() {
        return isNotDisplaySum;
    }

    public void setIsNotDisplaySum(Boolean isNotDisplaySum) {
        this.isNotDisplaySum = isNotDisplaySum;
    }

    public String getPrintshopname() {
        return printshopname;
    }

    public void setPrintshopname(String printshopname) {
        this.printshopname = printshopname;
    }

    public String getSoOutCode() {
        return soOutCode;
    }

    public void setSoOutCode(String soOutCode) {
        this.soOutCode = soOutCode;
    }

    public Integer getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(Integer invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getBarcodeImg() {
        return barcodeImg;
    }

    public void setBarcodeImg(String barcodeImg) {
        this.barcodeImg = barcodeImg;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    public Boolean getIsSeedInvoice() {
        return isSeedInvoice;
    }

    public void setIsSeedInvoice(Boolean isSeedInvoice) {
        this.isSeedInvoice = isSeedInvoice;
    }

    public String getPrintTime1() {
        return printTime1;
    }

    public void setPrintTime1(String printTime1) {
        this.printTime1 = printTime1;
    }

    public String getBatchNo1() {
        return batchNo1;
    }

    public void setBatchNo1(String batchNo1) {
        this.batchNo1 = batchNo1;
    }

    public String getBatchNo2() {
        return batchNo2;
    }

    public void setBatchNo2(String batchNo2) {
        this.batchNo2 = batchNo2;
    }

    public String getRtnContacts() {
        return rtnContacts;
    }

    public void setRtnContacts(String rtnContacts) {
        this.rtnContacts = rtnContacts;
    }

    public String getImgSemacode() {
        return imgSemacode;
    }

    public void setImgSemacode(String imgSemacode) {
        this.imgSemacode = imgSemacode;
    }

    public String getStaMemo() {
        return staMemo;
    }

    public void setStaMemo(String staMemo) {
        this.staMemo = staMemo;
    }

    public String getOrderUserCode() {
        return orderUserCode;
    }

    public void setOrderUserCode(String orderUserCode) {
        this.orderUserCode = orderUserCode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLpCodeHkMain() {
        return lpCodeHkMain;
    }

    public void setLpCodeHkMain(String lpCodeHkMain) {
        this.lpCodeHkMain = lpCodeHkMain;
    }

    public String getImgLogoTmall() {
        return imgLogoTmall;
    }

    public void setImgLogoTmall(String imgLogoTmall) {
        this.imgLogoTmall = imgLogoTmall;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public String getBatchIndex() {
        return batchIndex;
    }

    public void setBatchIndex(String batchIndex) {
        this.batchIndex = batchIndex;
    }

    public String getReturnTransno() {
        return returnTransno;
    }

    public void setReturnTransno(String returnTransno) {
        this.returnTransno = returnTransno;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }


    public String getNikeHKPic() {
        return nikeHKPic;
    }

    public void setNikeHKPic(String nikeHKPic) {
        this.nikeHKPic = nikeHKPic;
    }

    public String getCppAddress() {
        return cppAddress;
    }

    public void setCppAddress(String cppAddress) {
        this.cppAddress = cppAddress;
    }

    public List<String> getNikeHKList() {
        return nikeHKList;
    }

    public void setNikeHKList(List<String> nikeHKList) {
        this.nikeHKList = nikeHKList;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public BigDecimal getTotalSkuListPrice() {
        return totalSkuListPrice;
    }

    public void setTotalSkuListPrice(BigDecimal totalSkuListPrice) {
        this.totalSkuListPrice = totalSkuListPrice;
    }

    public String getOrderDate1() {
        return orderDate1;
    }

    public void setOrderDate1(String orderDate1) {
        this.orderDate1 = orderDate1;
    }

    public String getAntiFake() {
        return antiFake;
    }

    public void setAntiFake(String antiFake) {
        this.antiFake = antiFake;
    }

    public String getReturnTranckingNo() {
        return returnTranckingNo;
    }

    public void setReturnTranckingNo(String returnTranckingNo) {
        this.returnTranckingNo = returnTranckingNo;
    }

    public String getReturnTransnoUrl() {
        return returnTransnoUrl;
    }

    public void setReturnTransnoUrl(String returnTransnoUrl) {
        this.returnTransnoUrl = returnTransnoUrl;
    }

}

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

package com.jumbo.wms.model.baseinfo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

/**
 * 店铺信息
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_BI_CHANNEL_SPECIAL_ACTION")
public class BiChannelSpecialAction extends BaseModel {

    private static final long serialVersionUID = 8038835991785248183L;
    /**
     * PK
     */
    private Long id;
    /**
     * 特殊处理类型
     */
    private BiChannelSpecialActionType type;
    /**
     * 是否默认特殊处理
     */
    private Boolean isDefaultSpecialPackage;
    /**
     * 模板类型
     */
    private String templateType;
    /**
     * 装箱清单默认模板是否显示总金额
     */
    private Boolean isNotDisplaySum;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 退货地址
     */
    private String rtnAddress;
    /**
     * 联系电话
     */
    private String contactsPhone;
    /**
     * 联系电话1
     */
    private String contactsPhone1;
    /**
     * 联系人
     */
    private String contacts;
    /**
     * 发货仓名称
     */
    private String sendWarehouseName;
    /**
     * 陆运类型
     */
    private BiChannelSpecialActionRouteType routeType;
    /**
     * 扩展1
     */
    private String ext1;
    /**
     * 扩展2
     */
    private String ext2;
    /**
     * 扩展3
     */
    private String ext3;
    /**
     * 渠道
     */
    private BiChannel channcel;
    /**
     * 是否保价
     */
    private Long isInsurance;
    /**
     * 报价金额范围-最低金额
     */
    private BigDecimal minInsurance;
    /**
     * 报价金额范围-最高金额
     */
    private BigDecimal maxInsurance;
    /**
     * 发票类型
     */
    private Long invoiceType;
    /**
     * 快递面单附加信息
     */
    private String transAddMemo;
    
    /**
     * 仓库自定义打印模板
     */
    private String customPrintCode;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CMPSHOP", sequenceName = "S_T_BI_CHANNEL_SPECIAL_ACTION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CMPSHOP")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.baseinfo.BiChannelSpecialActionType")})
    public BiChannelSpecialActionType getType() {
        return type;
    }

    public void setType(BiChannelSpecialActionType type) {
        this.type = type;
    }

    @Column(name = "IS_DEFAULT_SPECIAL_PACKAGE")
    public Boolean getIsDefaultSpecialPackage() {
        return isDefaultSpecialPackage;
    }

    public void setIsDefaultSpecialPackage(Boolean isDefaultSpecialPackage) {
        this.isDefaultSpecialPackage = isDefaultSpecialPackage;
    }

    @Column(name = "TEMPLATE_TYPE", length = 100)
    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    @Column(name = "IS_NOT_DISPLAY_SUM")
    public Boolean getIsNotDisplaySum() {
        return isNotDisplaySum;
    }

    public void setIsNotDisplaySum(Boolean isNotDisplaySum) {
        this.isNotDisplaySum = isNotDisplaySum;
    }

    @Column(name = "SHOP_NAME", length = 50)
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Column(name = "RTN_ADDRESS", length = 100)
    public String getRtnAddress() {
        return rtnAddress;
    }

    public void setRtnAddress(String rtnAddress) {
        this.rtnAddress = rtnAddress;
    }

    @Column(name = "CONTACTS_PHONE", length = 50)
    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    @Column(name = "CONTACTS", length = 50)
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Column(name = "SEND_WAREHOUSE_NAME", length = 50)
    public String getSendWarehouseName() {
        return sendWarehouseName;
    }

    public void setSendWarehouseName(String sendWarehouseName) {
        this.sendWarehouseName = sendWarehouseName;
    }

    @Column(name = "ROUTE_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.baseinfo.BiChannelSpecialActionRouteType")})
    public BiChannelSpecialActionRouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(BiChannelSpecialActionRouteType routeType) {
        this.routeType = routeType;
    }

    @Column(name = "EXT1", length = 50)
    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    @Column(name = "EXT2", length = 50)
    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    @Column(name = "EXT3", length = 50)
    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    @Column(name = "CONTACTS_PHONE1", length = 50)
    public String getContactsPhone1() {
        return contactsPhone1;
    }

    public void setContactsPhone1(String contactsPhone1) {
        this.contactsPhone1 = contactsPhone1;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHANNEL_ID")
    public BiChannel getChanncel() {
        return channcel;
    }

    public void setChanncel(BiChannel channcel) {
        this.channcel = channcel;
    }

    @Column(name = "is_insurance")
    public Long getIsInsurance() {
        return isInsurance;
    }

    public void setIsInsurance(Long isInsurance) {
        this.isInsurance = isInsurance;
    }

    @Column(name = "min_insurance")
    public BigDecimal getMinInsurance() {
        return minInsurance;
    }

    public void setMinInsurance(BigDecimal minInsurance) {
        this.minInsurance = minInsurance;
    }

    @Column(name = "max_insurance")
    public BigDecimal getMaxInsurance() {
        return maxInsurance;
    }

    public void setMaxInsurance(BigDecimal maxInsurance) {
        this.maxInsurance = maxInsurance;
    }

    @Column(name = "INVOICE_TYPE")
	public Long getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Long invoiceType) {
		this.invoiceType = invoiceType;
	}

	@Column(name = "TRANS_ADD_MEMO")
	public String getTransAddMemo() {
		return transAddMemo;
	}

	public void setTransAddMemo(String transAddMemo) {
		this.transAddMemo = transAddMemo;
	}
	
	@Column(name="CUSTOM_PRINT_CODE")
    public String getCustomPrintCode() {
        return customPrintCode;
    }

    public void setCustomPrintCode(String customPrintCode) {
        this.customPrintCode = customPrintCode;
    }
    
	
}

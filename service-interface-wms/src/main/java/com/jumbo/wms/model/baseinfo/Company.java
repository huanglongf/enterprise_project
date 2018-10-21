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
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 公司，对应组织类型是Company的组织实体，记录有公司的附加信息
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_BI_COMPANY", uniqueConstraints = {@UniqueConstraint(columnNames = {"OU_ID"})})
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class Company extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5681922149779522353L;

    /**
     * PK
     */
    private Long id;

    /**
     * 税号
     */
    private String taxNo;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 银行帐号
     */
    private String bankAccount;

    /**
     * 法人
     */
    private String legalRep;

    /**
     * 主营业务范围
     */
    private String scopeOfBusiness;
    private int version;

    /**
     * 相关组织
     */
    private OperationUnit ou;

    /**
     * 公司k3编码
     */
    private String k3Code;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CP", sequenceName = "S_T_BI_COMPANY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CP")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TAX_NO", length = 50)
    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    @Column(name = "BANK_NAME", length = 255)
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "BANK_ACCOUNT", length = 50)
    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Column(name = "LEGAL_REP", length = 50)
    public String getLegalRep() {
        return legalRep;
    }

    public void setLegalRep(String legalRep) {
        this.legalRep = legalRep;
    }

    @Column(name = "SCOPE_OF_BUSI", length = 1000)
    public String getScopeOfBusiness() {
        return scopeOfBusiness;
    }

    public void setScopeOfBusiness(String scopeOfBusiness) {
        this.scopeOfBusiness = scopeOfBusiness;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @Column(name = "K3_CODE", length = 50)
    public String getK3Code() {
        return k3Code;
    }

    public void setK3Code(String k3Code) {
        this.k3Code = k3Code;
    }


}

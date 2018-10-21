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

package com.jumbo.wms.model.print;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.PrintCustomizeType;

/**
 * 店铺订制打印
 * 
 * @author bin.hu
 * 
 */
@Entity
@Table(name = "T_BI_PRINT_CUSTOMIZE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class PrintCustomize extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 7950160482621406742L;
    /**
     * PK
     */
    private Long id;

    /**
     * 打印类型
     */
    private PrintCustomizeType printType;

    /**
     * 数据源类型
     */
    private String dataType;

    /**
     * 店铺OWNER
     */
    private String owner;

    /**
     * 主模板
     */
    private String masterTemplet;

    /**
     * 子模板
     */
    private String subTemplet;

    /**
     * 备注
     */
    private String memo;
    
    /**
     * 模板编码
     */
    private String templetCode;
    
    /**
     * 路径
     */
    private  String fileUrl;

    private int version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_BI_PRINT_CUSTOMIZE", sequenceName = "S_T_BI_PRINT_CUSTOMIZE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_BI_PRINT_CUSTOMIZE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "PRINT_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.PrintCustomizeType")})
    public PrintCustomizeType getPrintType() {
        return printType;
    }

    public void setPrintType(PrintCustomizeType printType) {
        this.printType = printType;
    }

    @Column(name = "OWNER")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "MASTER_TEMPLET")
    public String getMasterTemplet() {
        return masterTemplet;
    }

    public void setMasterTemplet(String masterTemplet) {
        this.masterTemplet = masterTemplet;
    }

    @Column(name = "SUB_TEMPLET")
    public String getSubTemplet() {
        return subTemplet;
    }

    public void setSubTemplet(String subTemplet) {
        this.subTemplet = subTemplet;
    }

    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "DATA_TYPE")
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    
    @Column(name="TEMPLET_CODE")
    public String getTempletCode() {
        return templetCode;
    }

    public void setTempletCode(String templetCode) {
        this.templetCode = templetCode;
    }
    
    @Column(name="FILE_URL")
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    

}

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

package com.jumbo.wms.model.warehouse.baoShui;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 报关头表
 * @author lzb
 * CustomsDeclarationLine
 */

@Entity
@Table(name = "T_WH_CUSTOMS_DECLARATION_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CustomsDeclarationLine extends BaseModel {
    private static final long serialVersionUID = 2534062054450730771L;
    
    private Long id;// 主键
    
    
    private Long customsDeclarationId;// 报关头ID CUSTOMS_DECLARATION_ID

    // private Long skuDeclarationId;// 报关商品ID  SKU_DECLARATION_ID
    
    private String skuCode;//商品编码
    
     private String upc;//UPC
     //COUNTRY_OF_ORIGIN
     private String countryOfOrigin;//产地
     
    private Long planQty;//计划数量
     
    private Long gqty;// 申报数量
     
    private String gunit;// 申报单位
     
     private BigDecimal declPrice;//单价

     private Boolean isManualAdd;//是否手工添加 0:否 1:是
     
     //private String curr;//币制
     
     private String notes;// 备注

    private String hsCode;// 备注
    
     
    @Column(name = "CUSTOMS_DECLARATION_ID")
    public Long getCustomsDeclarationId() {
        return customsDeclarationId;
    }
    public void setCustomsDeclarationId(Long customsDeclarationId) {
        this.customsDeclarationId = customsDeclarationId;
    }
    
    @Column(name = "UPC")
    public String getUpc() {
        return upc;
    }
    public void setUpc(String upc) {
        this.upc = upc;
    }
    
    @Column(name = "G_QTY")
    public Long getGqty() {
        return gqty;
    }

    public void setGqty(Long gqty) {
        this.gqty = gqty;
    }
    @Column(name = "DECL_PRICE")
    public BigDecimal getDeclPrice() {
        return declPrice;
    }
    public void setDeclPrice(BigDecimal declPrice) {
        this.declPrice = declPrice;
    }
    
    @Column(name = "NOTE_S")
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "S_T_WH_CUSTOMS_DECLARATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }

    
    @Column(name = "SKU_CODE")
    public String getSkuCode() {
       return skuCode;
   }
    
   public void setSkuCode(String skuCode) {
       this.skuCode = skuCode;
   }
   
   @Column(name = "COUNTRY_OF_ORIGIN")
   public String getCountryOfOrigin() {
       return countryOfOrigin;
   }
   public void setCountryOfOrigin(String countryOfOrigin) {
       this.countryOfOrigin = countryOfOrigin;
   }
   
   @Column(name = "PLAN_QTY")
   public Long getPlanQty() {
       return planQty;
   }
   public void setPlanQty(Long planQty) {
       this.planQty = planQty;
   }
   
   @Column(name = "G_UNIT")
    public String getGunit() {
        return gunit;
   }

    public void setGunit(String gunit) {
        this.gunit = gunit;
   }
   
   @Column(name = "IS_MANUAL_ADD")
   public Boolean getIsManualAdd() {
       return isManualAdd;
   }
   public void setIsManualAdd(Boolean isManualAdd) {
       this.isManualAdd = isManualAdd;
   }

    @Column(name = "HS_CODE")
    public String getHsCode() {
        return hsCode;
    }

    public void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }


    
}

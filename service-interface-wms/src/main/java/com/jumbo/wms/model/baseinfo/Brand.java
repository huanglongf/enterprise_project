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

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import org.hibernate.annotations.OptimisticLockType;
import com.jumbo.wms.model.BaseModel;

/**
 * 品牌
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_BI_BRAND")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class Brand extends BaseModel {
    private static final long serialVersionUID = 4685790941672255385L;
    /**
     * PK
     */
    private Long id;
    /**
     * version
     */
    private Timestamp version;
    /**
     * 品牌编码code
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 英文名
     */
    private String nameEng;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否有效
     */
    private Boolean isAvailable;
    /**
     * 最近修改时间
     */
    private Date lastModifyTime;

    private Integer isAvailableInt;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SKU", sequenceName = "S_T_BI_BRAND", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SKU")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    @Column(name = "CODE", length = 30)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME", length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "EN_NAME", length = 50)
    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "IS_AVAILABLE")
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Transient
    public Integer getIsAvailableInt() {
        return isAvailableInt;
    }

    public void setIsAvailableInt(Integer isAvailableInt) {
        this.isAvailableInt = isAvailableInt;
    }
}

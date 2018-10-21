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

package com.jumbo.wms.model.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 库区
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_WH_DISTRICT", uniqueConstraints = {@UniqueConstraint(columnNames = {"OU_ID", "CODE"})})
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class WarehouseDistrict extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2153793397707618379L;
    public static final String DEFAULT_DISTRICT = "REL";

    private static List<WarehouseDistrictType> SYSTEM_TYPE = new ArrayList<WarehouseDistrictType>();

    public WarehouseDistrict() {
        super();
    }

    public WarehouseDistrict(Long id) {
        super();
        this.id = id;
    }

    /**
     * PK
     */
    private Long id;

    /**
     * 库区编码
     */
    private String code;

    /**
     * 库区名称
     */
    private String name;

    /**
     * 是否有效
     */
    private Boolean isAvailable = true;
    /**
     * 是否是差异库区
     */
    private Boolean isDisputable = false;
    /**
     * 是否被锁定(盘点锁定)
     */
    private Boolean isLocked = false;
    /**
     * 是否系统定义
     */
    private Boolean isSystem = false;
    /**
     * 库区类型
     */
    private WarehouseDistrictType type = WarehouseDistrictType.PICKING;
    /**
     * 备注
     */
    private String memo;
    private int version;

    /**
     * 相关仓库组织
     */
    private OperationUnit ou;

    /**
     * 库位列表
     */
    private List<WarehouseLocation> locations = new ArrayList<WarehouseLocation>();

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHD", sequenceName = "S_T_WH_DISTRICT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHD")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE", length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "IS_AVAILABLE")
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Column(name = "MEMO", length = 255)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_WHD_OU")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "district", orphanRemoval = true)
    @OrderBy("id")
    public List<WarehouseLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<WarehouseLocation> locations) {
        this.locations = locations;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.WarehouseDistrictType")})
    public WarehouseDistrictType getType() {
        return type;
    }

    public void setType(WarehouseDistrictType type) {
        this.type = type;
    }

    @Transient
    public int getIntDistrictType() {
        return type.getValue();
    }

    public void setIntDistrictType(int intDistrictType) {
        setType(WarehouseDistrictType.valueOf(intDistrictType));
    }

    @Column(name = "IS_DISPUTABLE")
    public Boolean getIsDisputable() {
        return isDisputable;
    }

    public void setIsDisputable(Boolean isDisputable) {
        this.isDisputable = isDisputable;
    }

    @Column(name = "IS_LOCKED")
    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    @Column(name = "IS_SYSTEM")
    public Boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    public static List<WarehouseDistrictType> getSystemType() {
        if (SYSTEM_TYPE.size() == 0) {
            SYSTEM_TYPE.add(WarehouseDistrictType.RECEIVING);
        }
        return SYSTEM_TYPE;
    }
}

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
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.PopUpArea;
import com.jumbo.wms.model.pickZone.WhPickZoon;

/**
 * 仓库库位
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_WH_LOCATION", uniqueConstraints = {@UniqueConstraint(columnNames = {"OU_ID", "CODE"})})
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class WarehouseLocation extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1606514189534838933L;
    public static final String DEFAULT_LOCATION = "REL_01_01_01";

    public WarehouseLocation() {
        super();
    }

    public WarehouseLocation(Long id) {
        super();
        this.id = id;
    }

    /**
     * PK
     */
    private Long id;

    /**
     * 库位编码
     */
    private String code;

    /**
     * 手工编制库位编码
     */
    private String manualCode;

    /**
     * 系统编制库位编码
     */
    private String sysCompileCode;

    /**
     * 行标识（X）
     */
    private String dimX;

    /**
     * 行标识（Y）
     */
    private String dimY;

    /**
     * 行标识（Z）
     */
    private String dimZ;

    /**
     * 行标识（C）
     */
    private String dimC;

    /**
     * 库位条码
     */
    private String barCode;

    /**
     * 库位容积
     */
    private Long capacity = 0L;

    /**
     * 容积率
     */
    private int capaRatio = 100;

    /**
     * 检货方式
     */
    private ParcelSortingMode sortingMode = ParcelSortingMode.ANY;

    /**
     * 是否有效
     */
    private Boolean isAvailable = true;
    /**
     * 是否是差异库区(保留)
     */
    private Boolean isDisputable = false;
    /**
     * 是否被锁定(盘点锁定)
     */
    private Boolean isLocked = false;
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
     * 相关库区
     */
    private WarehouseDistrict district;

    /**
     * 可支持的事务类型
     */
    private List<TransactionType> transactionTypes = new ArrayList<TransactionType>();

    public static final int C_R_PERCENT = 100;

    /**
     * 库存安全警告数
     */
    public Long warningNumber;

    /**
     * 最近修改时间
     */
    private Date lastModifyTime;

    /**
     * 拣货区域
     */
    private WhPickZoon whPickZoon;

    /**
     * 拣货顺序
     */
    private Integer sort;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private User creatorUser;

    /**
     * 弹出口区域
     */
    private PopUpArea popUpArea;

    /**
     * 弹出口区域编码
     */
    private String popUpAreaCode;


    private String isMixTime;// 库位是够混效期 0或空： 不可以混 1：可以混

    @Column(name = "is_mixtime")
    public String getIsMixTime() {
        return isMixTime;
    }

    public void setIsMixTime(String isMixTime) {
        this.isMixTime = isMixTime;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "S_T_WH_LOCATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE", length = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "MANUAL_CODE", length = 100)
    public String getManualCode() {
        return manualCode;
    }

    public void setManualCode(String manualCode) {
        this.manualCode = manualCode;
    }

    @Column(name = "SYS_COMPILE_CODE", length = 100)
    public String getSysCompileCode() {
        return sysCompileCode;
    }

    public void setSysCompileCode(String sysCompileCode) {
        this.sysCompileCode = sysCompileCode;
    }

    @Column(name = "DIM_X", length = 30)
    public String getDimX() {
        return dimX;
    }

    public void setDimX(String dimX) {
        this.dimX = dimX;
    }

    @Column(name = "DIM_Y", length = 30)
    public String getDimY() {
        return dimY;
    }

    public void setDimY(String dimY) {
        this.dimY = dimY;
    }

    @Column(name = "DIM_Z", length = 30)
    public String getDimZ() {
        return dimZ;
    }

    public void setDimZ(String dimZ) {
        this.dimZ = dimZ;
    }

    @Column(name = "DIM_C", length = 30)
    public String getDimC() {
        return dimC;
    }

    public void setDimC(String dimC) {
        this.dimC = dimC;
    }

    @Column(name = "BARCODE", length = 100)
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name = "CAPACITY")
    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    @Column(name = "CAPACITY_RATIO")
    public int getCapaRatio() {
        return capaRatio;
    }

    public void setCapaRatio(int capaRatio) {
        this.capaRatio = capaRatio;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.ParcelSortingMode")})
    public ParcelSortingMode getSortingMode() {
        return sortingMode;
    }

    public void setSortingMode(ParcelSortingMode sortingMode) {
        this.sortingMode = sortingMode;
    }

    public void setIntSortingMode(int sortingMode) {
        setSortingMode(ParcelSortingMode.valueOf(sortingMode));
    }

    @Transient
    public int getIntSortingMode() {
        return sortingMode.getValue();
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
    @Index(name = "IDX_WHL_OU")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISTRICT_ID")
    @Index(name = "IDX_WHL_WHD")
    public WarehouseDistrict getDistrict() {
        return district;
    }

    public void setDistrict(WarehouseDistrict district) {
        this.district = district;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "T_WH_LOCATION_TRANSTYPE", joinColumns = @JoinColumn(name = "LOCATION_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "TRANSTYPE_ID", referencedColumnName = "ID"))
    @OrderBy("id")
    public List<TransactionType> getTransactionTypes() {
        return transactionTypes;
    }

    public void setTransactionTypes(List<TransactionType> transactionTypes) {
        this.transactionTypes = transactionTypes;
    }

    /**
     * 绑定的作业类型的数量
     */
    private int transactionTypesCount;

    @Transient
    public int getTransactionTypesCount() {
        return transactionTypesCount;
    }

    public void setTransactionTypesCount(int transactionTypesCount) {
        this.transactionTypesCount = transactionTypesCount;
    }

    private Long inventoryNum;

    @Transient
    public Long getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(Long inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    private Long availableNum;

    @Transient
    public Long getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(Long availableNum) {
        this.availableNum = availableNum;
    }

    private Long stvlineQuantity;

    @Transient
    public Long getStvlineQuantity() {
        return stvlineQuantity;
    }

    public void setStvlineQuantity(Long stvlineQuantity) {
        this.stvlineQuantity = stvlineQuantity;
    }

    private StaLine staLine;

    @Transient
    public StaLine getStaLine() {
        return staLine;
    }

    public void setStaLine(StaLine staLine) {
        this.staLine = staLine;
    }

    private Long stalineId;

    @Transient
    public Long getStalineId() {
        return stalineId;
    }

    public void setStalineId(Long stalineId) {
        this.stalineId = stalineId;
    }

    private Long stvlineId;

    @Transient
    public Long getStvlineId() {
        return stvlineId;
    }

    public void setStvlineId(Long stvlineId) {
        this.stvlineId = stvlineId;
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

    @Column(name = "WARNING_NUMBER")
    public Long getWarningNumber() {
        return warningNumber;
    }

    public void setWarningNumber(Long warningNumber) {
        this.warningNumber = warningNumber;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZOON_ID")
    public WhPickZoon getWhPickZoon() {
        return whPickZoon;
    }

    public void setWhPickZoon(WhPickZoon whPickZoon) {
        this.whPickZoon = whPickZoon;
    }

    @Column(name = "SORT")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATOR_ID")
    public User getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(User creatorUser) {
        this.creatorUser = creatorUser;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POP_UP_ID")
    public PopUpArea getPopUpArea() {
        return popUpArea;
    }

    public void setPopUpArea(PopUpArea popUpArea) {
        this.popUpArea = popUpArea;
    }

    @Transient
    public String getPopUpAreaCode() {
        return popUpAreaCode;
    }

    public void setPopUpAreaCode(String popUpAreaCode) {
        this.popUpAreaCode = popUpAreaCode;
    }



}

package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
import java.util.Date;

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

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;

/**
 * 退换包裹表
 * 
 * @author dly
 * 
 */

@Entity
@Table(name = "T_WH_RETURN_PACKAGE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class ReturnPackage extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 6529460211390283970L;

    /**
     * PK
     */
    private Long id;

    /**
     * 批次
     */
    private String batchCode;
    /**
     * 物流商
     */
    private String lpcode;
    /**
     * 物流单号
     */
    private String trackingNo;
    /**
     * 拒收理由
     */
    private String rejectionReasons;
    /**
     * 状态
     */
    private ReturnPackageStatus status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private User creator;
    /**
     * 最后修改时间
     */
    private Date lastModifyTime;
    /**
     * t_wh_return_application(id) 退换货申请
     */
    private Long returnApplicationId;

    /**
     * t_wh_sta(ID) 换货作业单作业单
     */
    private Long staId;

    /**
     * 备注
     */
    private String remarksb;

    /**
     * 仓库
     */
    private OperationUnit whOu;

    /**
     * 物理仓
     */
    private PhysicalWarehouse pwOu;
    
    /**
     * 
     * 包裹重量
     */
    private BigDecimal weight;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_RETURN_PACKAGE", sequenceName = "S_T_WH_RETURN_PACKAGE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RETURN_PACKAGE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "batch_code")
    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Column(name = "lpCode")
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Column(name = "tracking_no")
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Column(name = "status", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.ReturnPackageStatus")})
    public ReturnPackageStatus getStatus() {
        return status;
    }

    public void setStatus(ReturnPackageStatus status) {
        this.status = status;
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
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "rp_id")
    public Long getReturnApplicationId() {
        return returnApplicationId;
    }

    public void setReturnApplicationId(Long returnApplicationId) {
        this.returnApplicationId = returnApplicationId;
    }

    @Column(name = "sta_id")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "remarksb")
    public String getRemarksb() {
        return remarksb;
    }

    public void setRemarksb(String remarksb) {
        this.remarksb = remarksb;
    }

    @Column(name = "Rejection_Reasons")
    public String getRejectionReasons() {
        return rejectionReasons;
    }

    public void setRejectionReasons(String rejectionReasons) {
        this.rejectionReasons = rejectionReasons;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wh_ou_id")
    public OperationUnit getWhOu() {
        return whOu;
    }

    public void setWhOu(OperationUnit whOu) {
        this.whOu = whOu;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pw_id")
    public PhysicalWarehouse getPwOu() {
        return pwOu;
    }

    public void setPwOu(PhysicalWarehouse pwOu) {
        this.pwOu = pwOu;
    }
    
    @Column(name="weight")
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    
}

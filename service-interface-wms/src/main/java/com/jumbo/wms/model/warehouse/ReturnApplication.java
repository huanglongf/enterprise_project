package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

/**
 * 表格仓库退货申请表
 * 
 * @author xiaolong.fei
 * 
 */
@Entity
@Table(name = "t_wh_return_application")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class ReturnApplication extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8415506466636405564L;

    /**
     * PK
     */
    private Long id;
    /**
     * 仓库组织ID
     */
    private Long whOuId;
    /**
     * 版本
     */
    private Long version;
    /**
     * 创建人ID
     */
    private Long createUserId;
    /**
     * 店铺
     */
    private String owner;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 反馈相关单据号
     */
    private String returnSlipCode;
    /**
     * 反馈相关单据号1
     */
    private String returnSlipCode1;
    /**
     * 反馈相关单据号2
     */
    private String returnSlipCode2;
    /**
     * 完成时间
     */
    private Date finishTime;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 提交人ID
     */
    private Long commitUserId;
    /**
     * 最后修改时间
     */
    private Date lastModiftTime;
    /**
     * 物流商
     */
    private String lpCode;
    /**
     * 状态
     */
    private ReturnApplicationStatus satus;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 相关单据号
     */
    private String slipCode;
    /**
     * 销售出的相关单据号
     */
    private String slipCode1;
    /**
     * 相关单据号2
     */
    private String slipCode2;
    /**
     * 确认时间
     */
    private Date commitTime;
    /**
     * 编码 自动生成 格式： W+当前日期转String
     */
    private String code;
    /**
     * 运单号
     */
    private String trankNo;
    /**
     * 退货人
     */
    private String returnUser;
    /**
     * 库位编码
     */
    private String locationCode;
    /**
     * 商品总数量 明细行数量的总和
     */
    private Long skuQty;
    /**
     * 备注
     */
    private String memo;

    /**
     * oms状态
     */
    private Integer omsStatus;

    /**
     * oms备注
     */
    private String omsRemark;

    /**
     * 备用字段 后用于存储staCode
     */
    private String extended;

    /**
     * 订单来源
     */
    private String source;

    /**
     * 备用字段2 后用于 是否有附件 1代表是，0 代表否
     */
    private String extended2;


    /**
     * 反馈时间
     */
    private Date feedBackTime;
    
    private String brand;//1：ad包裹 null：非

    @Column(name = "brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "feed_back_time")
    public Date getFeedBackTime() {
        return feedBackTime;
    }

    public void setFeedBackTime(Date feedBackTime) {
        this.feedBackTime = feedBackTime;
    }


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_RETURN_APP", sequenceName = "S_T_WH_RETURN_APPLICATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RETURN_APP")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "WH_OU_ID")
    public Long getWhOuId() {
        return whOuId;
    }

    public void setWhOuId(Long whOuId) {
        this.whOuId = whOuId;
    }

    @Column(name = "VERSION")
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Column(name = "CREATE_USER_ID")
    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "RETURN_SLIP_CODE")
    public String getReturnSlipCode() {
        return returnSlipCode;
    }

    public void setReturnSlipCode(String returnSlipCode) {
        this.returnSlipCode = returnSlipCode;
    }

    @Column(name = "RETURN_SLIP_CODE1")
    public String getReturnSlipCode1() {
        return returnSlipCode1;
    }

    public void setReturnSlipCode1(String returnSlipCode1) {
        this.returnSlipCode1 = returnSlipCode1;
    }

    @Column(name = "RETURN_SLIP_CODE2")
    public String getReturnSlipCode2() {
        return returnSlipCode2;
    }

    public void setReturnSlipCode2(String returnSlipCode2) {
        this.returnSlipCode2 = returnSlipCode2;
    }

    @Column(name = "FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Column(name = "MOBILE")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "COMMIT_USER_ID")
    public Long getCommitUserId() {
        return commitUserId;
    }

    public void setCommitUserId(Long commitUserId) {
        this.commitUserId = commitUserId;
    }

    @Column(name = "LAST_MODIFT_TIME")
    public Date getLastModiftTime() {
        return lastModiftTime;
    }

    public void setLastModiftTime(Date lastModiftTime) {
        this.lastModiftTime = lastModiftTime;
    }

    @Column(name = "LPCODE")
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.ReturnApplicationStatus")})
    public ReturnApplicationStatus getSatus() {
        return satus;
    }

    public void setSatus(ReturnApplicationStatus satus) {
        this.satus = satus;
    }

    @Column(name = "TELEPHONE")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "SLIP_CODE")
    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    @Column(name = "SLIP_CODE1")
    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    @Column(name = "SLIP_CODE2")
    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    @Column(name = "COMMIT_TIME")
    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "TRANK_NO")
    public String getTrankNo() {
        return trankNo;
    }

    public void setTrankNo(String trankNo) {
        this.trankNo = trankNo;
    }

    @Column(name = "RETURN_USER")
    public String getReturnUser() {
        return returnUser;
    }

    public void setReturnUser(String returnUser) {
        this.returnUser = returnUser;
    }

    @Column(name = "LOCATION_CODE")
    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    @Column(name = "SKU_QTY")
    public Long getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(Long skuQty) {
        this.skuQty = skuQty;
    }

    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "OWNER")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "OMS_STATUS")
    public Integer getOmsStatus() {
        return omsStatus;
    }

    public void setOmsStatus(Integer omsStatus) {
        this.omsStatus = omsStatus;
    }

    @Column(name = "OMS_REMARK")
    public String getOmsRemark() {
        return omsRemark;
    }

    public void setOmsRemark(String omsRemark) {
        this.omsRemark = omsRemark;
    }

    @Column(name = "EXTENDED")
    public String getExtended() {
        return extended;
    }

    public void setExtended(String extended) {
        this.extended = extended;
    }

    @Column(name = "SOURCE", length = 50)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "EXTENDED2", length = 100)
    public String getExtended2() {
        return extended2;
    }

    public void setExtended2(String extended2) {
        this.extended2 = extended2;
    }


}

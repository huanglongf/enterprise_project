package com.jumbo.wms.model.vmi.nikeData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_NIKE_CHECK_RECEIVE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class NikeCheckReceive extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 5779795682820919601L;
    /**
     * 系统调整类型 1
     */
    public static final Integer SYS_CHECK_TYPE = 1;
    /**
     * 人为调整类型 2
     */
    public static final Integer MANUAL_CHECK_TYPE = 2;

    /**
     * RSO 调整
     */
    public static final Integer MANUAL_TYPE_RSO = 11;
    
    /**
     * JO 调整
     */
    public static final Integer MANUAL_TYPE_J0 = 13;
    
    /**
     * 财务调整
     */
    public static final Integer MANUAL_TYPE_FINANCE = 15;
    
    /**
     * 其他调整
     */
    public static final Integer MANUAL_TYPE_OTHER = 99;
    /**
     * 新建状态 1
     */
    public static final Integer CREATE_STATUS = 1;
    /**
     * 完成状态 10
     */
    public static final Integer FINISH_STATUS = 10;
    /**
     * 准备写文件状态
     */
    public static final Integer TO_WRITE_STATUS = 2;


    private Long id;
    /**
     * 调整单编号
     */
    private String checkCode;

    /**
     * 店铺Code
     */
    private String ownerCode;

    /**
     * 调整时间
     */
    private Date receiveDate;

    /**
     * UPC
     */
    private String upc;
    
    /**
     * 数量
     */
    private Long quantity;

    /**
     * 1 是新建 10 是完成
     */
    private Integer status;
    /**
     * 收货入库类型 1 转店入库类型 2
     */
    private Integer type;
    /**
     * 手工调整类型
     */
    private Integer manualType;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 完成时间
     */
    private Date finishDate;
    
    /**
     * 操作人
     */
    private String operator;

    /**
     * version
     */
    private int version;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 文件行
     */
    private String fileCode;
    
    /**
     * 文件名
     */
    private String fileName;

    @Column(name = "type", length = 8)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "status", length = 10)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "CREATE_DATE", length = 8)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "FINISH_DATE", length = 8)
    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_NikeCheckReceive", sequenceName = "S_T_NIKE_CHECK_RECEIVE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NikeCheckReceive")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "RECEIVE_DATE")
    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "CHECK_CODE", length = 30)
    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    @Column(name = "OWNER_CODE", length = 30)
    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    @Column(name = "UPC", length = 30)
    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    @Column(name = "quantity")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    
    @Column(name = "operator")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(name = "manual_type", length = 8)
    public Integer getManualType() {
        return manualType;
    }

    public void setManualType(Integer manualType) {
        this.manualType = manualType;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "file_code")
    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
}

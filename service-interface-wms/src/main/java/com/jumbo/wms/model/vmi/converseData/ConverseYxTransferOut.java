package com.jumbo.wms.model.vmi.converseData;

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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;

/**
 * Converse永兴退仓反馈
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_CONVERSE_YX_TRANSFER_OUT")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ConverseYxTransferOut extends BaseModel {
    private static final long serialVersionUID = 1026878990299347965L;
    /*
     * PK
     */
    private Long id;
    /*
     * 创建时间
     */
    private Date createTime;
    /*
     * 版本
     */
    private Date version;
    /*
     * 最后修改时间
     */
    private Date lastModifyTime;
    /*
     * 状态
     */
    private DefaultStatus status;
    /*
     * 来源店铺标识（一般同 from_location）
     */
    private String transferPrefix;
    /*
     * 箱号
     */
    private String cartonNumber;
    /*
     * 转出日期
     */
    private String receiveDate;
    /*
     * 转出仓库
     */
    private String fromLocation;
    /*
     * 转入仓库
     */
    private String toLocation;
    private String cs2000ItemCode;
    private String colorCode;
    private String sizeCode;
    private String inseamCode;
    /*
     * 商品唯一编码
     */
    private String upc;
    /*
     * 数量
     */
    private Long quantity;
    /*
     * 行号
     */
    private Long lineSequenceNo;
    private Long totalLineSequenceNo;
    /*
     * 转出单据号
     */
    private String transferNo;
    private String sapCarton;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CONVERSE_YX_TRANSFER_OUT", sequenceName = "S_T_CONVERSE_YX_TRANSFER_OUT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONVERSE_YX_TRANSFER_OUT")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Version
    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "TRANSFER_PREFIX", length = 10)
    public String getTransferPrefix() {
        return transferPrefix;
    }

    public void setTransferPrefix(String transferPrefix) {
        this.transferPrefix = transferPrefix;
    }

    @Column(name = "CARTON_NUMBER", length = 26)
    public String getCartonNumber() {
        return cartonNumber;
    }

    public void setCartonNumber(String cartonNumber) {
        this.cartonNumber = cartonNumber;
    }

    @Column(name = "RECEIVE_DATE", length = 8)
    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    @Column(name = "FROM_LOCATION", length = 10)
    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    @Column(name = "TO_LOCATION", length = 10)
    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    @Column(name = "CS2000_ITEM_CODE", length = 26)
    public String getCs2000ItemCode() {
        return cs2000ItemCode;
    }

    public void setCs2000ItemCode(String cs2000ItemCode) {
        this.cs2000ItemCode = cs2000ItemCode;
    }

    @Column(name = "COLOR_CODE", length = 4)
    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    @Column(name = "SIZE_CODE", length = 10)
    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }

    @Column(name = "INSEAM_CODE", length = 10)
    public String getInseamCode() {
        return inseamCode;
    }

    public void setInseamCode(String inseamCode) {
        this.inseamCode = inseamCode;
    }

    @Column(name = "UPC", length = 26)
    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    @Column(name = "QUANTITY")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Column(name = "LINE_SEQUENCE_NO")
    public Long getLineSequenceNo() {
        return lineSequenceNo;
    }

    public void setLineSequenceNo(Long lineSequenceNo) {
        this.lineSequenceNo = lineSequenceNo;
    }

    @Column(name = "TOTAL_LINE_SEQUENCE_NO")
    public Long getTotalLineSequenceNo() {
        return totalLineSequenceNo;
    }

    public void setTotalLineSequenceNo(Long totalLineSequenceNo) {
        this.totalLineSequenceNo = totalLineSequenceNo;
    }

    @Column(name = "TRANSFER_NO", length = 10)
    public String getTransferNo() {
        return transferNo;
    }

    public void setTransferNo(String transferNo) {
        this.transferNo = transferNo;
    }

    @Column(name = "SAP_CARTON", length = 30)
    public String getSapCarton() {
        return sapCarton;
    }

    public void setSapCarton(String sapCarton) {
        this.sapCarton = sapCarton;
    }



}

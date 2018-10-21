package com.jumbo.wms.model.vmi.levisData;

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

/**
 * Levis童装收货单
 * 
 * @author jinggang.chen
 * 
 */
@Entity
@Table(name = "T_LEVISSHOES_STOCKIN")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class LevisShoesStockin extends BaseModel {

    private static final long serialVersionUID = 5180892954059440623L;
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
     * 作业单ID
     */
    private Long staId;
    /*
     * 箱号（仓库按箱发货）
     */
    private String cartonNumber;
    /*
     * 发货日期
     */
    private String receiveDate;
    /*
     * 来源仓库
     */
    private String fromLocation;
    /*
     * 目的仓库
     */
    private String toLocation;
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
    private String lineSequenceNo;
    /*
     * ASN单号
     */
    private String transferNo;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_LEVISSHOES_STOCKIN", sequenceName = "S_T_LEVISSHOES_STOCKIN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LEVISSHOES_STOCKIN")
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

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
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

    @Column(name = "LINE_SEQUENCE_NO", length = 26)
    public String getLineSequenceNo() {
        return lineSequenceNo;
    }

    public void setLineSequenceNo(String lineSequenceNo) {
        this.lineSequenceNo = lineSequenceNo;
    }

    @Column(name = "TRANSFER_NO", length = 10)
    public String getTransferNo() {
        return transferNo;
    }

    public void setTransferNo(String transferNo) {
        this.transferNo = transferNo;
    }
}

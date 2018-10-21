package com.jumbo.wms.model.vmi.cathKidstonData;

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
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Entity
@Table(name = "T_CK_RECEIVE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class CKReceive extends BaseModel {

    private static final long serialVersionUID = 8364513112774001839L;

    public static final String vmiCode = "5751";

    public static final String from_Loaction = "36400";

    public static final String to_Loaction = "30002";

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     *版本
     */
    private int version;

    /**
     * 最后修改时间
     */
    private Date lastModifyTime;

    /**
     * 外键STA
     */
    private StockTransApplication staId;

    /**
     * 相关单据号1
     */
    private String deliveryNo;// DELIVERY_NO

    /**
     * 相关单据号
     */
    private String cartonId;// CARTON_ID

    private String dateTime;

    /**
     * 来源仓库
     */
    private String fromLocation;

    /**
     * 目地仓库
     */
    private String toLocation;

    private String store;

    /**
     * sku = ext_code2
     */
    private String upc;

    /**
     * 数量
     */
    private Long quantity;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CK_RECEIVE", sequenceName = "S_T_CK_RECEIVE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CK_RECEIVE")
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
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "INDEX_T_CK_RECEIVE")
    public StockTransApplication getStaId() {
        return staId;
    }

    public void setStaId(StockTransApplication staId) {
        this.staId = staId;
    }

    @Column(name = "DELIVERY_NO", length = 50)
    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    @Column(name = "CARTON_ID", length = 50)
    public String getCartonId() {
        return cartonId;
    }

    public void setCartonId(String cartonId) {
        this.cartonId = cartonId;
    }

    @Column(name = "DATE_TIME", length = 50)
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Column(name = "FROM_LOCATION", length = 50)
    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    @Column(name = "TO_LOCATION", length = 50)
    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    @Column(name = "STORE", length = 50)
    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    @Column(name = "UPC", length = 50)
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

}

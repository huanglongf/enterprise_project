package com.jumbo.wms.model.warehouse;

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

import com.jumbo.wms.model.baseinfo.Sku;

@Entity
@Table(name = "t_nike_turn_create")
public class NikeTurnCreate {
    private Long id;
    /*
     * 创建时间
     */
    private Date createTime;
    /*
     * 转出店铺
     */
    private String fromLocation;
    /*
     * 转入店铺
     */
    private String toLocation;
    /*
     * 销售作业单号
     */
    private String staCode;
    /*
     * 转店编码
     */
    private String referenceNo;
    /*
     * 品牌对接编码
     */
    private Sku sku;
    /*
     * 转店数量
     */
    private Long quantity;
    private Long version;
    /*
     * 数量
     */
    private Integer status;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_turn_create", sequenceName = "S_t_nike_turn_create", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_turn_create")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "create_Time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "from_location")
    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    @Column(name = "to_location")
    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    @Column(name = "sta_code")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "reference_No")
    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @Column(name = "quantity")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Version
    @Column(name = "VERSION")
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}

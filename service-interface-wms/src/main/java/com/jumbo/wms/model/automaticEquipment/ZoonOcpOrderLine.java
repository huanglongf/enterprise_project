package com.jumbo.wms.model.automaticEquipment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * @author xiaolong.fei 区域待分配订单明细
 */
@Entity
@Table(name = "T_WH_ZOON_OCP_ORDER_LINE")
public class ZoonOcpOrderLine extends BaseModel {

    private static final long serialVersionUID = -517007945461464933L;

    private Long id;

    /**
     * 订单头ID
     */
    private Long orderId;
    /**
     * 店铺
     */
    private String owner;
    /**
     * 商品ID
     */
    private Long skuId;
    /**
     * 待数量
     */
    private Integer quantity;
    /**
     * 占用数量
     */
    private Integer ocpQty;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 占用标记
     */
    private Boolean cflg;
    /**
     * 仓库ID
     */
    private Long ouId;
    /**
     * 销售模式
     */
    private String saleModle;
    /**
     * 区域名称
     */
    private String zoonCode;
    /**
     * 优先级
     */
    private Integer sort;
    /**
     * staLine行Id
     */
    private Long staLineId;
    /**
     * 备注
     */
    private String mome;

    /**
     * 创建时间
     */
    private Date createTime;

    @Id
    @SequenceGenerator(name = "s_T_WH_ZOON_OCP_ORDER_LINE_GEN", sequenceName = "s_T_WH_ZOON_OCP_ORDER_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_T_WH_ZOON_OCP_ORDER_LINE_GEN")
    @Column(name = "ID", unique = true, nullable = false, scale = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "order_id")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "sku_id")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "ocp_qty")
    public Integer getOcpQty() {
        return ocpQty;
    }

    public void setOcpQty(Integer ocpQty) {
        this.ocpQty = ocpQty;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "cflg")
    public Boolean getCflg() {
        return cflg;
    }

    public void setCflg(Boolean cflg) {
        this.cflg = cflg;
    }

    @Column(name = "mome")
    public String getMome() {
        return mome;
    }

    public void setMome(String mome) {
        this.mome = mome;
    }

    @Column(name = "ou_id")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "sale_modle")
    public String getSaleModle() {
        return saleModle;
    }

    public void setSaleModle(String saleModle) {
        this.saleModle = saleModle;
    }

    @Column(name = "zoon_code")
    public String getZoonCode() {
        return zoonCode;
    }

    public void setZoonCode(String zoonCode) {
        this.zoonCode = zoonCode;
    }

    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Column(name = "sta_line_id")
    public Long getStaLineId() {
        return staLineId;
    }

    public void setStaLineId(Long staLineId) {
        this.staLineId = staLineId;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}

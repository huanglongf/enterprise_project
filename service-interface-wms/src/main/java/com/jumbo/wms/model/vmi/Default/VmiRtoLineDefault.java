package com.jumbo.wms.model.vmi.Default;

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

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Entity
@Table(name = "T_VMI_RTO_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class VmiRtoLineDefault extends BaseModel {

    private static final long serialVersionUID = -3068435456190277328L;

    /**
     * id
     */
    private Long id;

    /**
     * 商品唯一编码t_bi_inv_sku.ext_code2
     */
    private String upc;

    /**
     * 数量t_wh_sta_line.quantity
     */
    private Long qty;

    /**
     * 入库单箱号
     */
    private String cartonNo;

    /**
     * 扩展字段信息
     */
    private String extMemo;

    /**
     * t_vmi_asn外键
     */
    private VmiRtoDefault rto;

    /**
     * sta
     */
    private StockTransApplication sta;

    /**
     * 状态
     */
    private VmiGeneralStatus status;

    /**
     * 行号
     */
    private String lineNo;

    /**
     * 收货方
     */
    private String consigneeKey;

    /**
     * 原始数量
     */
    private Long originalQty;

    /**
     * 单位
     */
    private String uom;

    /**
     * 版本
     */
    private int version;

    /**
     * 库存状态
     */
    private String invStatus;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_VMI_RTO_LINE", sequenceName = "S_T_VMI_RTO_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VMI_RTO_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "UPC")
    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "CARTON_NO")
    public String getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(String cartonNo) {
        this.cartonNo = cartonNo;
    }

    @Column(name = "EXT_MEMO")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RTO_ID")
    public VmiRtoDefault getRto() {
        return rto;
    }

    public void setRto(VmiRtoDefault rto) {
        this.rto = rto;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.Default.VmiGeneralStatus")})
    public VmiGeneralStatus getStatus() {
        return status;
    }

    public void setStatus(VmiGeneralStatus status) {
        this.status = status;
    }

    @Column(name = "LINE_NO")
    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    @Column(name = "CONSIGNEE_KEY")
    public String getConsigneeKey() {
        return consigneeKey;
    }

    public void setConsigneeKey(String consigneeKey) {
        this.consigneeKey = consigneeKey;
    }

    @Column(name = "ORIGINAL_QTY")
    public Long getOriginalQty() {
        return originalQty;
    }

    public void setOriginalQty(Long originalQty) {
        this.originalQty = originalQty;
    }

    @Column(name = "UOM")
    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "inv_status")
    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

}

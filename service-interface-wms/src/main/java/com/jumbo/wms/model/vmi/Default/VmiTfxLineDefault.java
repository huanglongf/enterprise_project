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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_VMI_TFX_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class VmiTfxLineDefault extends BaseModel {

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
     * 行序号
     */
    private String lineSeq;

    /**
     * 库存状态
     */
    private String invStatus;

    /**
     *扩展字段信息
     */
    private String extMemo;

    /**
     * t_vmi_tfx外键
     */
    private VmiTfxDefault tfxId;

    /**
     * 版本
     */
    private int version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_VMI_TFX_LINE", sequenceName = "S_T_VMI_TFX_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VMI_TFX_LINE")
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

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "LINE_SEQ")
    public String getLineSeq() {
        return lineSeq;
    }

    public void setLineSeq(String lineSeq) {
        this.lineSeq = lineSeq;
    }

    @Column(name = "INV_STATUS")
    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TFX_ID")
    @Index(name = "INDEX_T_VMI_TFX_LINE_TFX")
    public VmiTfxDefault getTfxId() {
        return tfxId;
    }

    public void setTfxId(VmiTfxDefault tfxId) {
        this.tfxId = tfxId;
    }

}

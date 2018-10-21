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
import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.Sku;

@Entity
@Table(name = "T_WH_PDA_POST_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PdaPostLog extends BaseModel {

    private static final long serialVersionUID = 3767618952108270738L;

    /**
     * PK
     */
    private Long id;

    /**
     * 相关单据编码
     */
    private String code;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * pda机器号
     */
    private String pdaCode;

    /**
     * 库存状态
     */
    private InventoryStatus invStatus;
    /**
     * SKU
     */
    private Sku sku;

    /**
     * 库位
     */
    private WarehouseLocation loc;

    /**
     * 修改操作员
     */
    private User operator;

    /**
     * 状态
     */
    private DefaultStatus status;
    /**
     * 商品编码/SN号
     */
    private String postCode;
    /**
     * 库位编码
     */
    private String locationCode;

    /**
     * 数量
     */
    private Long qty;

    /**
     * 类型
     */
    private PdaPostLogType type;

    /**
     * Sn
     */
    private String sn;

    /**
     * VERSION
     */
    private int version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PDA_LG", sequenceName = "S_T_WH_PDA_POST_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PDA_LG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE", length = 30)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "PAD_CODE", length = 50)
    public String getPdaCode() {
        return pdaCode;
    }

    public void setPdaCode(String pdaCode) {
        this.pdaCode = pdaCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_STATUS_ID")
    @Index(name = "IDX_PDA_LG_INV_STS_ID")
    public InventoryStatus getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(InventoryStatus invStatus) {
        this.invStatus = invStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_PDA_LG_SKU_ID")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    @Index(name = "IDX_PDA_LG_LOC_ID")
    public WarehouseLocation getLoc() {
        return loc;
    }

    public void setLoc(WarehouseLocation loc) {
        this.loc = loc;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @Index(name = "IDX_PDA_LG_USER_ID")
    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.PdaPostLogType")})
    public PdaPostLogType getType() {
        return type;
    }

    public void setType(PdaPostLogType type) {
        this.type = type;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "SN", length = 60)
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Column(name = "POST_CODE", length = 50)
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Column(name = "LOCATION_CODE", length = 50)
    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }
}

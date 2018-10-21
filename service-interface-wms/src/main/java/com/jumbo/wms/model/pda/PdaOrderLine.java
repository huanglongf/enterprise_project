package com.jumbo.wms.model.pda;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.warehouse.TransactionDirection;

/**
 * PDA 上传单据头明细
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_WH_PDA_ORDER_LINE")
public class PdaOrderLine extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 92316177442047217L;
    /**
     * PK
     */
    private Long id;
    /**
     * 商品编码
     */
    private String skuCode;
    /**
     * 库位编码
     */
    private String locationCode;
    /**
     * 库存状态
     */
    private String invStatus;
    /**
     * 数量
     *  最终执行量
     */
    private Long qty;
    
    /**
     * 调整量
     */
    private Long adjustQty;
    
    /**
     * PDA 上传记录
     */
    private Long pdaUploadQty;
    
    /**
     * 操作人
     */
    private User operateUser;
    
    /**
     * 生产日期
     */
    private String mDate;
    /**
     * 事务方向
     */
    private TransactionDirection direction;
    /**
     * SKU ID
     */
    private Long skuId;
    /**
     * LOCATION ID
     */
    private Long locationId;
    /**
     * 库存状态ID
     */
    private Long invStatusId;
    /**
     * 箱号 退仓接口
     */
    private String index;

    /**
     * 状态
     */
    private DefaultStatus status;
    
    private PdaOrder pdaOrder;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PDA_ORDER", sequenceName = "S_T_WH_PDA_ORDER_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PDA_ORDER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SKU_CODE", length = 50)
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "LOCATION_CODE", length = 50)
    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    @Column(name = "INV_STATUS", length = 30)
    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "MDATE", length = 30)
    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    @Column(name = "DIRECTION", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransactionDirection")})
    public TransactionDirection getDirection() {
        return direction;
    }

    public void setDirection(TransactionDirection direction) {
        this.direction = direction;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "LOCATION_ID")
    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Column(name = "INV_STATUS_ID")
    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PDA_ORDER_ID")
    @Index(name = "IDX_PDA_ODERR_L_ID")
    public PdaOrder getPdaOrder() {
        return pdaOrder;
    }

    public void setPdaOrder(PdaOrder pdaOrder) {
        this.pdaOrder = pdaOrder;
    }

    @Transient
    public int getIntDirection() {
        return direction == null ? -2 : direction.getValue();
    }

    public void setIntDirection(int intDirection) {
        setDirection(intDirection==0?null:TransactionDirection.valueOf(intDirection));
    }

    @Column(name = "PG_INDEX", length = 50)
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Column(name = "ADJUST_QTY")
    public Long getAdjustQty() {
        return adjustQty;
    }

    public void setAdjustQty(Long adjustQty) {
        this.adjustQty = adjustQty;
    }

    @Column(name = "PDA_UPLOAD_QTY")
    public Long getPdaUploadQty() {
        return pdaUploadQty;
    }

    public void setPdaUploadQty(Long pdaUploadQty) {
        this.pdaUploadQty = pdaUploadQty;
    }

    @Column(name = "STATUS")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATE_USER_ID")
    public User getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(User operateUser) {
        this.operateUser = operateUser;
    }
}

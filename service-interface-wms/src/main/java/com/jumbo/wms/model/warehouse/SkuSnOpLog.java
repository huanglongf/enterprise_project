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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSnCardStatus;

/**
 * sku sn卡号操作日志
 */
@Entity
@Table(name = "T_WH_SKU_SN_OP_LOG")
public class SkuSnOpLog extends BaseModel {

    private static final long serialVersionUID = 4826032885606013700L;
    /**
     * PK
     */
    private Long id;
    /**
     * sku sn号
     */
    private String sn;

    /**
     * sku
     */
    private Sku sku;

    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * 操作人ID
     */
    private User user;

    /**
     * 卡状态
     */
    private SkuSnCardStatus cardStatus;

    /**
     * 备注
     */
    private String memo;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SN_OP_LOG", sequenceName = "S_T_WH_SKU_SN_OP_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SN_OP_LOG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SN")
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "INDEX_T_SKU_SN_OP_LOG_SKU")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OP_USER_ID")
    @Index(name = "INDEX_T_SKU_SN_OP_LOG_USER")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "CARD_STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.baseinfo.SkuSnCardStatus")})
    public SkuSnCardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(SkuSnCardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


}

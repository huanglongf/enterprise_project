package com.jumbo.wms.model.baseinfo;

import java.sql.Timestamp;
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

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 店铺仓库关联
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_BI_CHANNEL_WH_REF_LOG")
public class ChannelWhRefLog extends BaseModel {

    private static final long serialVersionUID = 6547036500135142432L;

    /**
     * PK
     */
    private Long id;
    /**
     * 仓库
     */
    private OperationUnit wh;
    /**
     * 店铺
     */
    private BiChannel shop;
    /**
     * 是否默认收货仓
     */
    private Boolean isDefaultInboundWh = false;
    /**
     * 是否后置装箱清单
     */
    private Boolean isPostPackingPage = false;
    /**
     * 是否运单后置
     */
    private Boolean isPostExpressBill = false;
    /**
     * version
     */
    private Timestamp version;
    /**
     * 操作时间
     */
    private Date operateTime;
    /**
     * 操作人
     */
    private Long operateUserId;
    /**
     * 是否维护QS商品
     */
    private Boolean isPostQs = false;

    /**
     * 顺丰结算月结账号
     */
    private String sfJcustid;

    @Column(name = "sf_jcustid")
    public String getSfJcustid() {
        return sfJcustid;
    }

    public void setSfJcustid(String sfJcustid) {
        this.sfJcustid = sfJcustid;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WS_REF_LOG", sequenceName = "S_T_BI_CHANNEL_WH_REF_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WS_REF_LOG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WH_OU_ID")
    public OperationUnit getWh() {
        return wh;
    }

    public void setWh(OperationUnit wh) {
        this.wh = wh;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHANNEL_ID")
    public BiChannel getShop() {
        return shop;
    }

    public void setShop(BiChannel shop) {
        this.shop = shop;
    }

    @Column(name = "IS_DEFAULT_INBOUND_WH")
    public Boolean getIsDefaultInboundWh() {
        return isDefaultInboundWh;
    }

    public void setIsDefaultInboundWh(Boolean isDefaultInboundWh) {
        this.isDefaultInboundWh = isDefaultInboundWh;
    }
    
    @Column(name = "IS_POSTPOSITION_PACKING_PAGE")
    public Boolean getIsPostPackingPage() {
        return isPostPackingPage;
    }

    public void setIsPostPackingPage(Boolean isPostPackingPage) {
        this.isPostPackingPage = isPostPackingPage;
    }

    @Column(name = "IS_POSTPOSITION_EXPRESS_BILL")
    public Boolean getIsPostExpressBill() {
        return isPostExpressBill;
    }

    public void setIsPostExpressBill(Boolean isPostExpressBill) {
        this.isPostExpressBill = isPostExpressBill;
    }

    @Version
    @Column(name = "VERSION")
    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }
    @Column(name = "OPERATE_TIME")
    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
    
    @Column(name = "OPERATE_USER_ID")
    public Long getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(Long operateUserId) {
        this.operateUserId = operateUserId;
    }

    @Column(name = "IS_POST_QS")
    public Boolean getIsPostQs() {
        return isPostQs;
    }

    public void setIsPostQs(Boolean isPostQs) {
        this.isPostQs = isPostQs;
    }


}


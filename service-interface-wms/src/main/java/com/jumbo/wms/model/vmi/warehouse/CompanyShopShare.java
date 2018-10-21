package com.jumbo.wms.model.vmi.warehouse;


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
import com.jumbo.wms.model.baseinfo.BiChannel;

@Entity
@Table(name = "T_MA_SHOP_SHARE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class CompanyShopShare extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3512424286768350362L;

    private int id;

    /**
     * 版本号
     */
    private Date version;

    private BiChannel shop;


    private String groupCode;

    /**
     * 优先级
     */
    private Long sort;

    /**
     * 入库比率
     */
    private Long inboundRatio;

    /**
     * 出库比率
     */
    private Long outboundRatio;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_MA_SHOP_SHARE", sequenceName = "S_T_MA_SHOP_SHARE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_MA_SHOP_SHARE")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "VERSION")
    @Version
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOP_ID")
    @Index(name = "IDX_OMS_INV_SHOPID")
    public BiChannel getShop() {
        return shop;
    }

    public void setShop(BiChannel shop) {
        this.shop = shop;
    }

    @Column(name = "GROUP_CODE", length = 20)
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    @Column(name = "SORT")
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Column(name = "INBOUND_RATIO")
    public Long getInboundRatio() {
        return inboundRatio;
    }

    public void setInboundRatio(Long inboundRatio) {
        this.inboundRatio = inboundRatio;
    }

    @Column(name = "OUTBOUND_RATIO")
    public Long getOutboundRatio() {
        return outboundRatio;
    }

    public void setOutboundRatio(Long outboundRatio) {
        this.outboundRatio = outboundRatio;
    }

}

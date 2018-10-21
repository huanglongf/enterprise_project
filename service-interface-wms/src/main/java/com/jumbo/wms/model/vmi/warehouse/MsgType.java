package com.jumbo.wms.model.vmi.warehouse;

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
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.BiChannel;

@Entity
@Table(name = "T_WH_MSG_TYPE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgType extends BaseModel {

    private static final long serialVersionUID = 3275321201902270523L;

    public static final String TYPE_ORDER_TYPE = "orderType";

    private Long id;

    private String Source;

    private Long staType;

    private String SourceType;

    private String type;

    private BiChannel shop;
    /**
     * 活动
     */
    private String activitySource;
    /**
     * 优先排序
     */
    private Integer sort;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_MSG_TYPE", sequenceName = "S_T_WH_MSG_TYPE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_MSG_TYPE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SOURCE")
    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    @Column(name = "STA_TYPE")
    public Long getStaType() {
        return staType;
    }

    public void setStaType(Long staType) {
        this.staType = staType;
    }

    @Column(name = "SOURCE_TYPE")
    public String getSourceType() {
        return SourceType;
    }

    public void setSourceType(String sourceType) {
        SourceType = sourceType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOPINFO_ID")
    @Index(name = "IDX_MSG_TYPE_SHOPID")
    public BiChannel getShop() {
        return shop;
    }

    public void setShop(BiChannel shop) {
        this.shop = shop;
    }

    @Column(name = "TYPE", length = 20)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "ACTIVITY_SOURCE", length = 30)
    public String getActivitySource() {
        return activitySource;
    }

    public void setActivitySource(String activitySource) {
        this.activitySource = activitySource;
    }

    @Column(name = "SORT")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}

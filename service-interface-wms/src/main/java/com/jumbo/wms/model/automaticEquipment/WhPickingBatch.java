package com.jumbo.wms.model.automaticEquipment;

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
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.warehouse.PickingList;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_PICKING_BATCH")
public class WhPickingBatch extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 2911747188006740397L;
    /*
     * PK 主键
     */
    private Long id;

    /**
     * 编码
     */
    private String code;
    /*
     * 
     */
    private PickingList pickingList;
    /*
     * 仓库区域
     */
    private Zoon zoon;
    /*
     * 周转箱目前状态 1:新建 2:完成
     */
    private DefaultStatus status;

    /**
     * 扫描条码
     */
    private String barCode;

    /**
     * 小批次拣货顺序排序
     */
    private Integer sort;

    private Long userId;// 用户id

    private Date startTime;// 创建时间

    private Date endTime;// 结束时间

    private Date createTime;// 创建时间

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }


    @Column(name = "START_TIME")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "END_TIME")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_PICKING_BATCH", sequenceName = "S_T_WH_PICKING_BATCH", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_PICKING_BATCH")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PICKING_ID")
    @Index(name = "IDX_WPB_PICKING_ID")
    public PickingList getPickingList() {
        return pickingList;
    }

    public void setPickingList(PickingList pickingList) {
        this.pickingList = pickingList;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZOON_ID")
    @Index(name = "IDX_WPB_ZOON_ID")
    public Zoon getZoon() {
        return zoon;
    }

    public void setZoon(Zoon zoon) {
        this.zoon = zoon;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "BAR_CODE")
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name = "SORT")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }



}

package com.jumbo.wms.model.vmi.warehouse;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

//
@Entity
@Table(name = "T_WH_THREEPL_AREA_INFO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class WhThreePlAreaInfo extends BaseModel {

    private static final long serialVersionUID = 6881955603524063203L;

    private Long id;

    /**
     * 區域名
     */
    private String areaName;

    /**
     * 區域ID
     */
    private Long areaId;

    /**
     * 父節點ID
     */
    private Long parentId;

    /**
     * 創建時間
     */
    private Date createTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_THREEPL_AREA_INFO", sequenceName = "pk_T_WH_THREEPL_AREA_INFO_id ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_THREEPL_AREA_INFO")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "AREA_NAME")
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Column(name = "AREA_ID")
    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    @Column(name = "PARENT_ID")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}

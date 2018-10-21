package com.jumbo.wms.model.automaticEquipment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jumbo.wms.model.BaseModel;

/**
 * @author lihui
 * 
 * @createDate 2016年1月19日 下午7:21:46
 */
@Entity
@Table(name = "T_WH_POP_UP_AREA")
public class PopUpArea extends BaseModel {


    private static final long serialVersionUID = 6569382717777236659L;

    private Long id;
    /**
     * 弹出口编码
     */
    private String code;
    /**
     * 弹出口条码
     */
    private String barcode;
    /**
     * 弹出口名称
     */
    private String name;
    /**
     * 弹出口状态（0可用1禁用）
     */
    private Boolean status = false;
    /**
     * WCS编码
     */
    private String wscPopCode;
    /**
     * 优先级
     */
    private Long sort;
    /**
     * 创建时间
     */
    private Date createTime;

    @Id
    @SequenceGenerator(name = "S_T_WH_POP_UP_AREA_ID_GENERATOR", sequenceName = "S_T_WH_POP_UP_AREA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_T_WH_POP_UP_AREA_ID_GENERATOR")
    @Column(name = "ID", unique = true, nullable = false, scale = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "status")
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Column(name = "barcode")
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "WSC_POP_CODE")
    public String getWscPopCode() {
        return wscPopCode;
    }

    public void setWscPopCode(String wscPopCode) {
        this.wscPopCode = wscPopCode;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "SORT", length = 6)
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }
}

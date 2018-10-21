package com.jumbo.wms.model.automaticEquipment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * @author xiaolong.fei
 *
 */
@Entity
@Table(name = "t_wh_zoon_ocp_sort")
public class ZoonOcpSort extends BaseModel {

    private static final long serialVersionUID = -517007945461464933L;

    private Long id;

    /**
     * 仓库ID
     */
    private Long ouId;
    /**
     * 销售模式
     */
    private String saleModle;
    /**
     * 区域ID
     */
    private String zoonId;
    /**
     * 区域编码
     */
    private String zoonCode;
    /**
     * 区域编码
     */
    private String zoonName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Long createId;
    /**
     * 最后修改时间
     */
    private Date lastModifyTime;
    /**
     * 修改人
     */
    private Long updateId;
    /**
     * 备注
     */
    private String mome;
    /**
     * 优先级
     */
    private Integer sort;

    @Id
    @SequenceGenerator(name = "s_t_wh_zoon_ocp_sort_GENERATOR", sequenceName = "s_t_wh_zoon_ocp_sort", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_t_wh_zoon_ocp_sort_GENERATOR")
    @Column(name = "ID", unique = true, nullable = false, scale = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ou_id")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "sale_modle")
    public String getSaleModle() {
        return saleModle;
    }

    public void setSaleModle(String saleModle) {
        this.saleModle = saleModle;
    }

    @Column(name = "zoon_id")
    public String getZoonId() {
        return zoonId;
    }

    public void setZoonId(String zoonId) {
        this.zoonId = zoonId;
    }

    @Column(name = "zoon_name")
    public String getZoonName() {
        return zoonName;
    }

    public void setZoonName(String zoonName) {
        this.zoonName = zoonName;
    }

    @Column(name = "zoon_code")
    public String getZoonCode() {
        return zoonCode;
    }

    public void setZoonCode(String zoonCode) {
        this.zoonCode = zoonCode;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "create_id")
    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    @Column(name = "last_modify_time")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "update_id")
    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    @Column(name = "mome")
    public String getMome() {
        return mome;
    }

    public void setMome(String mome) {
        this.mome = mome;
    }

    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}

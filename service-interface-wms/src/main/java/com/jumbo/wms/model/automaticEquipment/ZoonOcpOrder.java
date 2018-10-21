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
 * @author xiaolong.fei 区域待分配订单
 */
@Entity
@Table(name = "T_WH_ZOON_OCP_ORDER")
public class ZoonOcpOrder extends BaseModel {

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
     * 区域名称
     */
    private String zoonCode;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 作业单ID
     */
    private Long staId;
    /**
     * 作业单号
     */
    private String staCode;

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
    private Integer lev;

    /**
     * 状态。 新建： 1, 已计算：5, 计算完成 10
     */
    private Integer status;

    @Id
    @SequenceGenerator(name = "s_T_WH_ZOON_OCP_ORDER_GENERATOR", sequenceName = "s_T_WH_ZOON_OCP_ORDER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_T_WH_ZOON_OCP_ORDER_GENERATOR")
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


    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    @Column(name = "zoon_code")
    public String getZoonCode() {
        return zoonCode;
    }

    public void setZoonCode(String zoonCode) {
        this.zoonCode = zoonCode;
    }

    @Column(name = "sta_id")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "sta_Code")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "lev")
    public Integer getLev() {
        return lev;
    }

    public void setLev(Integer lev) {
        this.lev = lev;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}

package com.jumbo.wms.model.mongodb;

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

/**
 * 
 * SN装箱明细
 */
@Entity
@Table(name = "t_wh_sta_carton_line_sn")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class StaCartonLineSn extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 4020985130305282204L;

    private Long id;

    private StaCartonLine staCartonLine; // 箱明细ID

    private String sn; // sn号

    private String dmgType; // 残次类型

    private String dmgReason; // 残次原因

    private String dmgCode; // 条码

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_CONTAINER_LINE_SN", sequenceName = "S_T_WH_CONTAINER_LINE_SN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_CONTAINER_LINE_SN")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cl_id")
    @Index(name = "IDX_STA_CARTON_LINE_SN_ID")
    public StaCartonLine getStaCartonLine() {
        return staCartonLine;
    }

    public void setStaCartonLine(StaCartonLine staCartonLine) {
        this.staCartonLine = staCartonLine;
    }

    @Column(name = "sn")
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Column(name = "dmg_type")
    public String getDmgType() {
        return dmgType;
    }

    public void setDmgType(String dmgType) {
        this.dmgType = dmgType;
    }

    @Column(name = "dmg_reason")
    public String getDmgReason() {
        return dmgReason;
    }

    public void setDmgReason(String dmgReason) {
        this.dmgReason = dmgReason;
    }

    @Column(name = "dmg_code")
    public String getDmgCode() {
        return dmgCode;
    }

    public void setDmgCode(String dmgCode) {
        this.dmgCode = dmgCode;
    }

}

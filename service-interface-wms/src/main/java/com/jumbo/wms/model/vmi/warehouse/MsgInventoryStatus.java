package com.jumbo.wms.model.vmi.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_WH_MSG_INVENTORY_STATUS")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgInventoryStatus extends BaseModel {

    private static final long serialVersionUID = -6873733407373030531L;

    private Long id;

    /**
     * 仓库名字
     */
    private String source;

    /**
     * 宝尊库存状态
     */
    private Long whStatus;

    /**
     * 外部仓库库存状态
     */
    private String vmiStatus;
    
    /**
     * 外部仓指定仓库库存状态
     */
    private String sourceWh;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_MSG_INVENTORY_STATUS", sequenceName = "S_T_WH_MSG_INVENTORY_STATUS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_MSG_INVENTORY_STATUS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SOURCE", length = 30)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "WHSTATUS")
    public Long getWhStatus() {
        return whStatus;
    }

    public void setWhStatus(Long whStatus) {
        this.whStatus = whStatus;
    }

    @Column(name = "VMI_STATUS", length = 30)
    public String getVmiStatus() {
        return vmiStatus;
    }

    public void setVmiStatus(String vmiStatus) {
        this.vmiStatus = vmiStatus;
    }

    @Column(name = "sourcewh", length = 30)
    public String getSourceWh() {
        return sourceWh;
    }

    public void setSourceWh(String sourceWh) {
        this.sourceWh = sourceWh;
    }
}

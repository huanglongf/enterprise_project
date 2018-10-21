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

@Entity
@Table(name = "T_WH_IDS_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class IdsLog extends BaseModel {

    private static final long serialVersionUID = 4500282746146028858L;

    private int id;
    private Date workTime;
    private String opCode;
    private String payLoad;
    private Long status;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_IDS_LOG", sequenceName = "S_IDSLOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_IDS_LOG")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "WORK_TIME")
    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    @Column(name = "OPCODE", length = 50)
    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    @Column(name = "PAYLOAD", length = 4000)
    public String getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }

    @Column(name = "STATUS")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

}

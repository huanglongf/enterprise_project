package com.jumbo.wms.model.vmi.espData;

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
@Table(name = "T_ESPRIT_SALES_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ESPSales extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6782965759065242453L;

    public static final Integer STATUS_5 = 5;

    private Long id;
    private Long soId;
    private Long batch;
    private Long requestId;
    private String key;
    private Integer event;
    private String line;
    private String ulf;
    private Integer status;

    private Date version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ESP_SALES_DATA", sequenceName = "S_T_ESP_SALES_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESP_SALES_DATA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SO_ID")
    public Long getSoId() {
        return soId;
    }

    public void setSoId(Long soId) {
        this.soId = soId;
    }

    @Column(name = "BATCH")
    public Long getBatch() {
        return batch;
    }

    public void setBatch(Long batch) {
        this.batch = batch;
    }

    @Column(name = "REQUEST_ID")
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    @Column(name = "XXKEY")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "XXEVENT")
    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    @Column(name = "XXLINE")
    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    @Column(name = "XXULF")
    public String getUlf() {
        return ulf;
    }

    public void setUlf(String ulf) {
        this.ulf = ulf;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }


}

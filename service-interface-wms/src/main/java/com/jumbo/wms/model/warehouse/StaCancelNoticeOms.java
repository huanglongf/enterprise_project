package com.jumbo.wms.model.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * sta取法送达取消OMS通知
 * 
 */
@Entity
@Table(name = "t_wh_sta_cancel_notice_oms")
public class StaCancelNoticeOms extends BaseModel {


    public static final long MAX_ERROR_COUNT = 5;
    /**
     * 
     */
    private static final long serialVersionUID = -6901632256222570836L;
    /**
     * PK
     */
    private Long id;
    /**
     * 作业单号
     */
    private Long staId;
    /**
     * 错误次数
     */
    private Long errorCount = 0L;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SN", sequenceName = "s_t_wh_sta_cancel_notice_oms", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SN")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "ERROR_COUNT")
    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }
}

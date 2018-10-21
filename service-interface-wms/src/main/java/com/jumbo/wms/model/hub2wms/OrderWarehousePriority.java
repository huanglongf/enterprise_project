package com.jumbo.wms.model.hub2wms;

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
import javax.persistence.Transient;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_WH_SO_WAREHOUSE")
public class OrderWarehousePriority extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -28854941779341997L;
    private Long id;
    private Long ouId;
    private int no;
    private Boolean flag;
    private String lpCode;
    private WmsSalesOrderQueue wmsSalesOrderQueue;
    private String ouCode;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_SO_WAREHOUSE", sequenceName = "S_T_WH_SO_WAREHOUSE", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_WH_SO_WAREHOUSE", strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "OU_ID")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "ORDER_NO")
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    @Column(name = "FLAG")
    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SO_ID")
    public WmsSalesOrderQueue getWmsSalesOrderQueue() {
        return wmsSalesOrderQueue;
    }

    public void setWmsSalesOrderQueue(WmsSalesOrderQueue wmsSalesOrderQueue) {
        this.wmsSalesOrderQueue = wmsSalesOrderQueue;
    }

    @Column(name = "LP_CODE")
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    @Transient
    public String getOuCode() {
        return ouCode;
    }

    public void setOuCode(String ouCode) {
        this.ouCode = ouCode;
    }
}

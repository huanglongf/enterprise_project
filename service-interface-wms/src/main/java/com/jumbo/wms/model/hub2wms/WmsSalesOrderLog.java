package com.jumbo.wms.model.hub2wms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_WH_SO_LOG")
public class WmsSalesOrderLog extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 3529978917303941434L;
    /*
     * PK
     */
    private Long id;
    /*
     * 单据号
     */
    private String orderCode;
    /*
     * 日志信息
     */
    private String jsonInfo;
    /*
     * 创建时间
     */
    private Date createTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_SO_LOG", sequenceName = "S_T_WH_SO_LOG", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_WH_SO_LOG", strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "orderCode", length = 30)
    @Index(name = "IDX_SO_LOG_ORDERCODE")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Lob
    @Column(name = "JSON_INFO", columnDefinition = "CLOB")
    public String getJsonInfo() {
        return jsonInfo;
    }

    public void setJsonInfo(String jsonInfo) {
        this.jsonInfo = jsonInfo;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}

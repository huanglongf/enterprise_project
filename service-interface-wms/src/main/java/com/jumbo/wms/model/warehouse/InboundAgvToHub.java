package com.jumbo.wms.model.warehouse;

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
@Table(name="t_wh_in_agv_to_hub")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class InboundAgvToHub extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 882876991916592946L;
    
    private Long id;
    
    /**
     * 接口名称
     */
    private String apiName;
    
    private Long staId; 
    
    /**
     * 单据类型
     */
    private String type;
    
    private Long status;
    
    private Long errorCount;
    
    private String errorMsg;
    
    private Date createTime;
    
  

    @Id
    @Column(name="ID")
    @SequenceGenerator(name = "seq_t_wh_in_agv_to_hub", sequenceName = "seq_t_wh_in_agv_to_hub", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_t_wh_in_agv_to_hub")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="API_NAME")
    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    @Column(name="STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name="TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name="STATUS")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

   
    @Column(name="ERROR_COUNT")
    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name="ERROR_MSG")
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Column(name="CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

   
    
    
    
}

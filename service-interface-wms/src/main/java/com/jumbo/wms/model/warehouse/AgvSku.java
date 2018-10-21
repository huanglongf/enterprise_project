package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;


@Entity
@Table(name="t_bi_inv_sku_agv")
public class AgvSku extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -1825184075929200840L;
    
    private Long id;
    
    /**
     * 接口名称
     */
    private String apiName;
    
    private Long skuId;
   
    private Long status;
    
    private Long errorCount;
    
    private String errorMsg;
    
    private Date createTime;
    
    /**
     * 1 新建  2修改
     */
    private Long createOrUpdate;

    @Id
    @Column(name="ID")
    @SequenceGenerator(name = "seq_t_bi_inv_sku_agv", sequenceName = "seq_t_bi_inv_sku_agv", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_t_bi_inv_sku_agv")
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

    @Column(name="STATUS")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Column(name="SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
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

    @Column(name="CREATE_OR_UPDATE")
    public Long getCreateOrUpdate() {
        return createOrUpdate;
    }

    public void setCreateOrUpdate(Long createOrUpdate) {
        this.createOrUpdate = createOrUpdate;
    }

   
}

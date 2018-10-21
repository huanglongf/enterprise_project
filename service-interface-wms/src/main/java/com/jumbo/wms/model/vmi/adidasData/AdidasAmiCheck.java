package com.jumbo.wms.model.vmi.adidasData;

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
 * 
 * @author jinggang.chen
 *  ad出库调用AMI取消接口验证
 */
@Entity
@Table(name = "T_WH_ADIDAS_AMI_CHECK")
public class AdidasAmiCheck extends BaseModel{
    private static final long serialVersionUID = -309789951722175535L;

    private Long id;
    
    private Date createTime;
    
    
    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}

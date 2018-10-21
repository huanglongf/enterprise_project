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
/**
 * 库存日志信息
 * 
 * @author lingyun.dai
 * 
 */
@Entity
@Table(name = "T_SYS_LOG")
public class SystemLog extends BaseModel {
	
    private static final long serialVersionUID = -7970773870766323794L;
    private Long ID;
	private String type;
	private String node;
	private String status;
	private Date createTime;
	
    @Id
    @Column(name="id")
    @SequenceGenerator(name = "SEQ_SYS_LOG", sequenceName = "S_T_SYS_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SYS_LOG")
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	@Column(name="CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name="TYPE")
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    @Column(name="NODE")
    public String getNode() {
        return node;
    }
    public void setNode(String node) {
        this.node = node;
    }
    @Column(name="STATUS")
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}

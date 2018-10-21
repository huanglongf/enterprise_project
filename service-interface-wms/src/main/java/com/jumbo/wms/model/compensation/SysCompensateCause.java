package com.jumbo.wms.model.compensation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;


/**
 * 索赔原因
 * 
 * @author lihui
 *
 * @createDate 2015年10月9日 下午6:56:56
 */
@Entity
@Table(name = "T_SYS_COMPENSATE_CAUSE")
public class SysCompensateCause extends BaseModel {
    
    private static final long serialVersionUID = 6706878808633715872L;
    private Long id;
    private String name;
   
    private Integer code;
   
    private String remark;
    
    private SysCompensateType sysCompensateType;
   

    @Id
    @Column(name = "ID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

    @Column(name = "CODE")
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPENSATE_TYPE_ID")
    public SysCompensateType getSysCompensateType() {
        return sysCompensateType;
    }

    public void setSysCompensateType(SysCompensateType sysCompensateType) {
        this.sysCompensateType = sysCompensateType;
    }


	
}

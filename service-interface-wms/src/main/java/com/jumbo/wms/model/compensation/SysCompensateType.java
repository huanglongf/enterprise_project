package com.jumbo.wms.model.compensation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;


/**
 * 索赔分类
 * 
 * @author lihui
 *
 * @createDate 2015年10月9日 下午6:56:56
 */
@Entity
@Table(name = "T_SYS_COMPENSATE_TYPE")
public class SysCompensateType extends BaseModel {
	
    private static final long serialVersionUID = -7586629917930285557L;
    private Long id;
    private String name;
   
    private String code;
   
    private String remark;
   

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
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


	
}

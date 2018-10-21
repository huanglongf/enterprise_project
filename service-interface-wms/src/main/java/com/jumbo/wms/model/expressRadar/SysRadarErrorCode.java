package com.jumbo.wms.model.expressRadar;

// Generated 2015-5-25 15:27:48 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;


/**
 * @author gianni.zhang
 * 
 *         2015年5月25日 下午5:07:01
 */
@Entity
@Table(name = "T_SYS_RD_ERROR_CODE")
public class SysRadarErrorCode extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4994675820742303920L;

    /**
     * id
     */
    private Long id;

    /**
     * 原因
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    @Id
    @Column(name = "ID")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "CODE", length = 100)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}

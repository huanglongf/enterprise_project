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
 *         2015年5月25日 下午5:07:06
 */
@Entity
@Table(name = "T_SYS_RD_WARNING_LV")
public class SysRadarWarningLv extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 9016938100520535574L;

    /**
     * id
     */
    private Long id;

    /**
     * 编码名称
     */
    private String name;

    /**
     * 预警级别
     */
    private Integer lv;

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

    @Column(name = "LV")
    public Integer getLv() {
        return this.lv;
    }

    public void setLv(Integer lv) {
        this.lv = lv;
    }

}

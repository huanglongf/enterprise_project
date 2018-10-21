package com.jumbo.wms.model.expressRadar;

// Generated 2015-5-25 15:27:48 by Hibernate Tools 3.4.0.CR1

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
 * @author gianni.zhang
 *
 * 2015年5月25日 下午5:06:55
 */
@Entity
@Table(name = "T_SYS_RADAR_AREA")
public class SysRadarArea extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7438414512879712824L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 省
	 */
	private String province;

	/**
	 * 状态
	 */
	private Long status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date lastModifyTime;

	/**
	 * 市
	 */
	private String city;

	public SysRadarArea() {
	}


	@Id
	@SequenceGenerator(name="T_SYS_RADAR_AREA_ID_GENERATOR", sequenceName="S_T_SYS_RADAR_AREA", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_SYS_RADAR_AREA_ID_GENERATOR")
	@Column(name = "ID", unique = true, nullable = false, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PROVINCE", length = 100)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "STATUS", precision = 4, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "LAST_MODIFY_TIME")
	public Date getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Column(name = "CITY", length = 100)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}

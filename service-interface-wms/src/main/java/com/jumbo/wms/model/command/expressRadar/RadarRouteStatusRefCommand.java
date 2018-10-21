package com.jumbo.wms.model.command.expressRadar;

import java.io.Serializable;

import com.jumbo.wms.model.authorization.User;


/**
 * @author lihui
 * 快递状态代码查询页面
 * 2015年5月25日
 */
public class RadarRouteStatusRefCommand implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7081863077035342216L;

	/**
     * id
     */
	private Long id;
	
	/**
	 * 物流商名称
	 */
	private String logisticsName;
	
	private String expCode;
	
	/**
	 * 系统路由状态编码ID
	 */
	private Long sysRscId;
	
	/**
	 * 代码
	 */
	private String code;
	
	/**
	 * 描述
	 */
	private String describe;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 创建人
	 */
	private User user;
	
	

	public RadarRouteStatusRefCommand() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public String getExpCode() {
		return expCode;
	}

	public void setExpCode(String expCode) {
		this.expCode = expCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	public Long getSysRscId() {
		return sysRscId;
	}

	public void setSysRscId(Long sysRscId) {
		this.sysRscId = sysRscId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
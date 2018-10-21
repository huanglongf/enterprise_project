package com.jumbo.wms.model.trans;

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
 * 通用物流商扩展信息
 * 
 * @author kai.zhu
 * 
 */
@Entity
@Table(name = "T_WH_TRANS_INFO")
public class TransInfo extends BaseModel {

	private static final long serialVersionUID = -1881129882456121874L;
	/**
     * PK
     */
    private Long id;
    /**
     * 快递供应商提供帐号信息
     */
    private String account;
    /**
     * 快递供应商提供帐号对应验证码
     */
    private String password;
    /**
     * 物流商编码
     */
    private String lpcode;
    /**
     * 区域编码
     */
    private String regionCode;
    /**
     * 是否可用
     */
    private Boolean isAvailable;
    /**
     * 是否请求物流商获取运单号
     */
    private Boolean isOltransno;
    /**
     * 对接编码
     */
    private String systemKey;
    
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_TRANS_INFO", sequenceName = "S_T_WH_TRANS_INFO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_TRANS_INFO")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "ACCOUNT", length = 100)
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	@Column(name = "PASSWORD", length = 100)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "LPCODE")
	public String getLpcode() {
		return lpcode;
	}
	public void setLpcode(String lpcode) {
		this.lpcode = lpcode;
	}
	
	@Column(name = "REGIONCODE")
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	@Column(name = "IS_AVAILABLE")
	public Boolean getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	@Column(name = "IS_OLTRANSNO")
	public Boolean getIsOltransno() {
		return isOltransno;
	}
	public void setIsOltransno(Boolean isOltransno) {
		this.isOltransno = isOltransno;
	}
	@Column(name="systemKey")
	public String getSystemKey() {
		return systemKey;
	}
	public void setSystemKey(String systemKey) {
		this.systemKey = systemKey;
	}
	
	
}

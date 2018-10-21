package com.jumbo.pms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 网点库
 * 
 * @author ShengGe
 * 
 */
@Entity
@Table(name = "T_MA_BRANCH_LIBRRARY")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class BranchLibrary extends BaseModel {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6489447409669429299L;
	
    /**
     * 付款方式
     */
    public static final Long PAYMETHOD_SHIP = 1L;
    public static final Long PAYMETHOD_DEBIT_PAID = 2L;
    public static final Long PAYMETHOD_THIRD_PARTY = 3L;
    
	/**
     * PK
     */
    private Long id;

    /**
     * Version
     */
    private Date version;
    
    /**
     * 网点编码
     */
    private String slipCode;
    
    /**
     * 网点名称
     */
    private String branchName;
    
    /**
     * 网点名称英文
     */
    private String branchNameEn;
    
    /**
     * 付款方式
			1:寄方付
			2:收方付
			3:第三方付
     */
    private Integer payMethod = 1;
    
    /**
     * 月结卡号
     */
    private String custid;
    
    /**
     * 物流商分配给品牌的账号
     */
    private String accountNo;
    
    /**
     * 国家
     */
    private String country;
    
    /**
     * 省
     */
    private String province;
    
    /**
     * 市
     */
    private String city;
    
    /**
     * 所在县 /区，必须是标准的县 /区称谓，示例： “福 田区 ”（区字不要省略） （区字不要省略）
     */
    private String county;
    
    /**
     * 街道
     */
    private String street;
    
    /**
     * 送货地址
     */
    private String address;
    
    /**
     * 送货地址英文
     */
    private String addressEn;
    
    /**
     * 手机
     */
    private String mobile;
    
    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 邮编
     */
    private String zipcode;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 网点区域码
     */
    private String regionCode;
    
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_BRANCH_LIBRRARY", sequenceName = "S_T_MA_BRANCH_LIBRRARY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BRANCH_LIBRRARY")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
    @Version
    @Column(name = "VERSION")
	public Date getVersion() {
		return version;
	}
	
	public void setVersion(Date version) {
		this.version = version;
	}

    @Column(name = "SLIP_CODE")
	public String getSlipCode() {
		return slipCode;
	}

	public void setSlipCode(String slipCode) {
		this.slipCode = slipCode;
	}

	@Column(name = "BRANCH_NAME")
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

    @Column(name = "PAY_METHOD")
	public Integer getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}

    @Column(name = "CUSTID")
	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

    @Column(name = "ACCOUNT_NO")
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

    @Column(name = "COUNTRY")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

    @Column(name = "PROVINCE")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

    @Column(name = "CITY")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

    @Column(name = "COUNTY")
	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

    @Column(name = "STREET")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

    @Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

    @Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

    @Column(name = "TELEPHONE")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

    @Column(name = "ZIPCODE")
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

    @Column(name = "CONTACT")
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

    @Column(name = "REGION_CODE")
	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

    @Column(name = "BRANCH_NAME_EN")
	public String getBranchNameEn() {
		return branchNameEn;
	}

	public void setBranchNameEn(String branchNameEn) {
		this.branchNameEn = branchNameEn;
	}

    @Column(name = "ADDRESS_EN")
	public String getAddressEn() {
		return addressEn;
	}

	public void setAddressEn(String addressEn) {
		this.addressEn = addressEn;
	}

}

package com.jumbo.wms.model.archive;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;
/**
 * 订单出入库状态归档
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "t_wh_Order_Status_OMS_ARCHIVE")
public class WmsOrderStatusOmsArchive extends BaseModel {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 3401749798040116703L;
	/**
     * ID
     */
    private Long id;
    /**
     * 操作时间
     */
    private Date createtime;
    /**
     * 订单号(唯一对接标识)
     */
    private String orderCode;
    /**
     * 单据号，Wms单据唯一标识
     */
    private String shippingCode;
    /**
     * 订单类型
     */
    private int type;
    /**
     * 状态
     */
    private int status;
    /**
     * 系统来源标识
     */
    private String systemKey;
    /**
     * 日志记录时间
     */
    private Date logTime;
    /**
     * 登陆人名称
     */
    private String loginName;

    private Boolean isOp;

    /**
     * 店铺
     */
    private String owner;

    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "orderCode")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "shippingCode")
    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "systemKey")
    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    @Column(name = "create_time")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Column(name = "log_time")
    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    @Column(name = "login_name")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(name = "is_op")
    public Boolean getIsOp() {
        return isOp;
    }

    public void setIsOp(Boolean isOp) {
        this.isOp = isOp;
    }

    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}

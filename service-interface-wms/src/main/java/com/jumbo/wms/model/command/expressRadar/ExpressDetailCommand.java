package com.jumbo.wms.model.command.expressRadar;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 快递明细查看界面
 * 
 * @author gianni.zhang
 *
 * 2015年6月8日 下午2:52:19
 */
public class ExpressDetailCommand implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -4161663056126186993L;
    
    /**
     * id
     */
    private Long id;
    
    /**
     * 快递单号
     */
    private String expressCode;
    
    /**
     * 快递状态
     */
    private String status;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 预警类型
     */
    private String warningType;
    
    /**
     * 预警级别
     */
    private String warningLv;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 预警创建人
     */
    private String modifyUser;
    
    /**
     * SKU编码
     */
    private String code;
    
    /**
     * 条形码
     */
    private String barCode;
    
    /**
     * 商品名称
     */
    private String name;
    
    /**
     * 扩展属性
     */
    private String keyProperties;
    
    /**
     * 商品整箱件数
     */
    private Long count;
    
    /**
     * 商品吊牌价
     */
    private BigDecimal listPrice;
    
    /**
     * 物流操作时间
     */
    private Date operateTime;
    
    /**
     * 物流明细
     */
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getWarningType() {
        return warningType;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    public String getWarningLv() {
        return warningLv;
    }

    public void setWarningLv(String warningLv) {
        this.warningLv = warningLv;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

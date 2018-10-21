package com.jumbo.wms.model.warehouse;

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
 * ad异常包裹处理
 * 
 * @author qian.Wang
 * 
 */
@Entity
@Table(name = "t_ad_package_line_deal")
public class AdPackageLineDeal extends BaseModel{

    /**
     * 
     */
    private static final long serialVersionUID = 7862788686290565460L;
    /**
     * PK
     */
    private Long id;

    /**
     * 800ts工单编号
     */
    private String adOrderId;

    /**
     * 退货指令
     */
    private String returnInstruction;

    /**
     * 作业单号
     */
    private String extended;

    /**
     * 物流商
     */
    private String lpCode;

    /**
     * 运单号
     */
    private String trankNo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    /**
     * 800ts工单类型
     */
    private String adOrderType;

    /**
     * 800ts处理结果
     */
    private String adStatus;
    /**
     * 状态 1新建 2处理中3:完成
     */
    private Integer status;

   // private String statusName;

    /**
     * 操作人
     */
    private String opUser;
    /**
     * skuId
     */
    private Long skuId;

    /**
     * 商品数量
     */
    private Long quantity;

    /**
     * wsm处理结果
     */
    private String wmsStatus;

    /**
     * wsm工单类型
     */
    private String wmsOrderType;

    /**
     * 备注
     */
    private String remark;

    /**
     * wms事件编码
     */
    private String wmsOrderId;
    /**
     * 是否已操作 1是0否
     */
    private Integer opStatus;
    
   // private String opStatusName;

    ///ad
        private String adRemark;//ad反馈备注
        private int isSend;//是否已经推送 0:否 1：是
        private Integer num;//错误次数
    ////

        @Column(name = "ad_remark")
        public String getAdRemark() {
                return adRemark;
            }

        public void setAdRemark(String adRemark) {
            this.adRemark = adRemark;
        }

        @Column(name = "is_send")
        public int getIsSend() {
            return isSend;
        }

        public void setIsSend(int isSend) {
            this.isSend = isSend;
        }
        @Column(name = "num")
        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "s_t_ad_package_line_deal", sequenceName = "s_t_ad_package_line_deal", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_t_ad_package_line_deal")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "AD_ORDER_ID")
    public String getAdOrderId() {
        return adOrderId;
    }

    public void setAdOrderId(String adOrderId) {
        this.adOrderId = adOrderId;
    }

    @Column(name = "RETURN_INSTRUCTION")
    public String getReturnInstruction() {
        return returnInstruction;
    }

    public void setReturnInstruction(String returnInstruction) {
        this.returnInstruction = returnInstruction;
    }

    @Column(name = "EXTENDED")
    public String getExtended() {
        return extended;
    }

    public void setExtended(String extended) {
        this.extended = extended;
    }

    @Column(name = "LP_CODE")
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    @Column(name = "TRANK_NO")
    public String getTrankNo() {
        return trankNo;
    }

    public void setTrankNo(String trankNo) {
        this.trankNo = trankNo;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "LAST_UPDATE_TIME")
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Column(name = "AD_ORDER_TYPE")
    public String getAdOrderType() {
        return adOrderType;
    }

    public void setAdOrderType(String adOrderType) {
        this.adOrderType = adOrderType;
    }

    @Column(name = "AD_STATUS")
    public String getAdStatus() {
        return adStatus;
    }

    public void setAdStatus(String adStatus) {
        this.adStatus = adStatus;
    }


    @Column(name = "OP_USER")
    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "QUANTITY")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Column(name = "WMS_STATUS")
    public String getWmsStatus() {
        return wmsStatus;
    }

    public void setWmsStatus(String wmsStatus) {
        this.wmsStatus = wmsStatus;
    }

    @Column(name = "WMS_ORDER_TYPE")
    public String getWmsOrderType() {
        return wmsOrderType;
    }

    public void setWmsOrderType(String wmsOrderType) {
        this.wmsOrderType = wmsOrderType;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "WMS_ORDER_ID")
    public String getWmsOrderId() {
        return wmsOrderId;
    }

    public void setWmsOrderId(String wmsOrderId) {
        this.wmsOrderId = wmsOrderId;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "OP_STATUS")
    public Integer getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(Integer opStatus) {
        this.opStatus = opStatus;
    }

}

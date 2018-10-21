package com.jumbo.wms.model.inventorySnapShot;

import java.util.Date;

import com.jumbo.wms.model.BaseModel;


/**
 * 同步库存TO IM
 * 
 * @author bin.hu
 *
 */
public class InvWhFullInventory extends BaseModel {


    private static final long serialVersionUID = 8177771985729911900L;

    /** 库存主体代码 */
    private String ownerCode;
    /** 存货点代码 */
    private String binCode;
    /** SKU编码 */
    private String skuCode;
    /** 执行数量 有正负 */
    private Long qty;
    /** 库存状态代码 10：良品 20：残次品 30：报废品 40：待处理品 */
    private String invStatusCode;
    /** 来源系统代码 */
    private String sourceSys;
    /** 类型 1：初始化数据，但需比对存货表数量，以增量更新 2：库存比对 */
    private Integer type;
    /** 存储存货系统出入库流水ID unique_key + source_sys = 唯一键；用于去重 */
    private String uniqueKey;
    /** UUID */
    private String batch;
    /** 库存事务时间、实际出入库时间 */
    private Date transactionTime;

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getInvStatusCode() {
        return invStatusCode;
    }

    public void setInvStatusCode(String invStatusCode) {
        this.invStatusCode = invStatusCode;
    }

    public String getSourceSys() {
        return sourceSys;
    }

    public void setSourceSys(String sourceSys) {
        this.sourceSys = sourceSys;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }



}

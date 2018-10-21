/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.jumbo.rmi.warehouse.vmi;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lichuan
 * 
 */
public class VmiInventory implements Serializable {

    private static final long serialVersionUID = -450169156026584556L;
    private String uuid;
    /**
     * 品牌店铺编码t_bi_channel.vmi_code
     */
    private String storeCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 品牌来源，渠道vmi_source
     */
    private String vmiSource;

    /**
     * 商品唯一编码t_bi_inv_sku.ext_code2
     */
    private String upc;

    /**
     * 数量t_wh_sta_line.quantity
     */
    private Long qty;

    /**
     * 占用量
     */
    private Long blockQty;

    /**
     * 不可销售库存量
     */
    private Long onHoldQty;

    /**
     * 库存状态
     */
    private String invStatus;

    /**
     * 扩展字段信息
     */
    private String extMemo;

    /**
     * 生产批次
     */
    private String batchNo;

    private String whCode;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVmiSource() {
        return vmiSource;
    }

    public void setVmiSource(String vmiSource) {
        this.vmiSource = vmiSource;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getBlockQty() {
        return blockQty;
    }

    public void setBlockQty(Long blockQty) {
        this.blockQty = blockQty;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public Long getOnHoldQty() {
        return onHoldQty;
    }

    public void setOnHoldQty(Long onHoldQty) {
        this.onHoldQty = onHoldQty;
    }


}

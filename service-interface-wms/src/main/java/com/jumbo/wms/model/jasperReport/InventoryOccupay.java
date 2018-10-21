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
 * 
 */
package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InventoryOccupay implements Serializable {
    private static final long serialVersionUID = 1762034829686016265L;

    private Date createTime;
    /**
     * 作业单号
     */
    private String code;
    /**
     * 目标仓库
     */
    private String targetWarehouse;
    /**
     * 执行情况
     */
    private String executeStatus;
    /**
     * 计划执行数量
     */
    private Integer planQty;
    /**
     * 占用明细
     */
    private List<InventoryOccLine> lines;

    // private Integer index;
    private Integer detailSize;


    // GETTER && SETTER
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTargetWarehouse() {
        return targetWarehouse;
    }

    public void setTargetWarehouse(String targetWarehouse) {
        this.targetWarehouse = targetWarehouse;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public Integer getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

    public List<InventoryOccLine> getLines() {
        return lines;
    }

    public void setLines(List<InventoryOccLine> lines) {
        this.lines = lines;
    }

    public Integer getDetailSize() {
        return detailSize;
    }

    public void setDetailSize(Integer detailSize) {
        this.detailSize = detailSize;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

/**
 * Copyright (c) 2015 Jumbomart All Rights Reserved.
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
package com.jumbo.wms.model.warehouse.O2oEsprit;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
 * lzb
 */

public class EspDeliveryCommand implements Serializable {

    private static final long serialVersionUID = 1685354482209355166L;

    /** 宝尊的GLN */
    private String fromGLN;
    
    /** 门店的companyGLN */
    private String toGLN;
    
    /** 宝尊的CODE */
    private String fromNode;
    
    /** 门店的companyCode */
    private String toNode;
    
    /** 序列号，从1开始 */
    private int sequenceNumber;
    
    /** 有销售的门店数量 */
    private int numberOfRecords;
    
    /** 生成时间 */
    private Date generationDateTime;

    /** Esprit销售出库明细行 */
    private List<EspDeliveryLineCommand> deliveries;
    
    /**
     * 批次
     * 
     * @return
     */
    private String batchCode;

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getFromGLN() {
        return fromGLN;
    }

    public void setFromGLN(String fromGLN) {
        this.fromGLN = fromGLN;
    }

    public String getToGLN() {
        return toGLN;
    }

    public void setToGLN(String toGLN) {
        this.toGLN = toGLN;
    }

    public String getFromNode() {
        return fromNode;
    }

    public void setFromNode(String fromNode) {
        this.fromNode = fromNode;
    }

    public String getToNode() {
        return toNode;
    }

    public void setToNode(String toNode) {
        this.toNode = toNode;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(int numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }

    public Date getGenerationDateTime() {
        return generationDateTime;
    }

    public void setGenerationDateTime(Date generationDateTime) {
        this.generationDateTime = generationDateTime;
    }

    public List<EspDeliveryLineCommand> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<EspDeliveryLineCommand> deliveries) {
        this.deliveries = deliveries;
    }
    
}

package com.jumbo.wms.model.vmi.GucciData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jumbo.wms.model.BaseModel;

/**
 * Gucci 退大仓反馈（接口数据）
 * 
 */
public class GucciVMIRtnFeedback extends BaseModel {

    private static final long serialVersionUID = -4582567515192871330L;

    /**
     * JDA退仓批次号
     */
    private String JDABatchNumber;

    /**
     * PD-Number
     */
    private String PDNumber;

    /**
     * 转移地址
     */
    private String toJDALocation;

    /**
     * 文档类型
     */
    private String documentType;

    /**
     * 仓库拣货完成时间
     */
    private Date goodsIssueDate;

    /**
     * JDA系统的仓库编码
     */
    private String JDAWarehouseCode;

    /**
     * 品牌编码
     */
    private String brandCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 退大仓明细
     */
    private List<GucciVMIRtnFeedbackLine> gucciVMIRtnFeedbackLines;

    public String getJDABatchNumber() {
        return JDABatchNumber;
    }

    public void setJDABatchNumber(String jDABatchNumber) {
        JDABatchNumber = jDABatchNumber;
    }

    public String getPDNumber() {
        return PDNumber;
    }

    public void setPDNumber(String pDNumber) {
        PDNumber = pDNumber;
    }

    public String getToJDALocation() {
        return toJDALocation;
    }

    public void setToJDALocation(String toJDALocation) {
        this.toJDALocation = toJDALocation;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Date getGoodsIssueDate() {
        return goodsIssueDate;
    }

    public void setGoodsIssueDate(Date goodsIssueDate) {
        this.goodsIssueDate = goodsIssueDate;
    }

    public String getJDAWarehouseCode() {
        return JDAWarehouseCode;
    }

    public void setJDAWarehouseCode(String jDAWarehouseCode) {
        JDAWarehouseCode = jDAWarehouseCode;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<GucciVMIRtnFeedbackLine> getGucciVMIRtnFeedbackLines() {
        if (this.gucciVMIRtnFeedbackLines == null) {
            this.gucciVMIRtnFeedbackLines = new ArrayList<GucciVMIRtnFeedbackLine>();
        }
        return gucciVMIRtnFeedbackLines;
    }

    public void setGucciVMIRtnFeedbackLines(List<GucciVMIRtnFeedbackLine> gucciVMIRtnFeedbackLines) {
        this.gucciVMIRtnFeedbackLines = gucciVMIRtnFeedbackLines;
    }
}

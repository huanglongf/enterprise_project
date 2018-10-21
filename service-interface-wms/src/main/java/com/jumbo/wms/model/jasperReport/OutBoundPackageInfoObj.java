package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;
import java.util.List;

/**
 * 退仓清单bin.hu
 * 
 * @author jumbo
 * 
 */
public class OutBoundPackageInfoObj implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1437781822108128073L;

    private String owner;// 店铺
    private String staCode;// 作业单号
    private String slipCode;// 相关单据号
    private String printTime;// 打印时间
    private List<OutBoundPackageInfoLinesObj> lines;
    private Integer detailSize;
    private String seQno;// 箱号
    private Double completeQty;
    private String weight;// 重量
    private String pickingListNumber;
    private String code;// 箱号编码

    public Double getCompleteQty() {
        return completeQty;
    }

    public void setCompleteQty(Double completeQty) {
        this.completeQty = completeQty;
    }



    public String getSeQno() {
        return seQno;
    }

    public void setSeQno(String seQno) {
        this.seQno = seQno;
    }

    public Integer getDetailSize() {
        return detailSize;
    }

    public void setDetailSize(Integer detailSize) {
        this.detailSize = detailSize;
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getPrintTime() {
        return printTime;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public List<OutBoundPackageInfoLinesObj> getLines() {
        return lines;
    }

    public void setLines(List<OutBoundPackageInfoLinesObj> lines) {
        this.lines = lines;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPickingListNumber() {
        return pickingListNumber;
    }
    public void setPickingListNumber(String pickingListNumber) {
        this.pickingListNumber = pickingListNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

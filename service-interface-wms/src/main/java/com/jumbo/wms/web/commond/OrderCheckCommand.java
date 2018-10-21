package com.jumbo.wms.web.commond;

import java.util.List;

import com.jumbo.wms.model.BaseModel;

/**
 * 页面作业单信息
 * 
 * @author sjk
 * 
 */
public class OrderCheckCommand extends BaseModel implements Comparable<OrderCheckCommand> {

    /**
	 * 
	 */
    private static final long serialVersionUID = -542955413669080399L;

    // 作业单ID
    private Long staId;
    // 作业单号
    private String staCode;
    // 单据号(slip_code)
    private String orderCode;
    // 平台订单号(slip_code1)
    private String slipCode1;
    // 作业单状态
    private String status;
    // 作业单状态
    private Integer intStatus;
    // 店铺
    private String owner;
    // 物流商
    private String lpcode;
    // 运单号
    private String transNo;
    // 箱号
    private String pgIndex;
    // 批次1
    private String idx1;
    // 批次2
    private String idx2;
    // 计划商品总数量
    private Long skuQty;
    // 配货类型
    private Integer pickingType;
    // 推荐耗材条码
    private String cmCode;
    // 明细行
    private List<OrderCheckLineCommand> lines;
    // 物流单号和重量
    private List<String> tns;
    /**
     * 商品产地
     */
    private String skuOrigin;

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(Integer intStatus) {
        this.intStatus = intStatus;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public List<OrderCheckLineCommand> getLines() {
        return lines;
    }

    public void setLines(List<OrderCheckLineCommand> lines) {
        this.lines = lines;
    }

    public String getPgIndex() {
        return pgIndex;
    }

    public void setPgIndex(String pgIndex) {
        this.pgIndex = pgIndex;
    }

    public String getIdx1() {
        return idx1;
    }

    public void setIdx1(String idx1) {
        this.idx1 = idx1;
    }

    public String getIdx2() {
        return idx2;
    }

    public void setIdx2(String idx2) {
        this.idx2 = idx2;
    }

    public Long getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(Long skuQty) {
        this.skuQty = skuQty;
    }

    public Integer getPickingType() {
        return pickingType;
    }

    public void setPickingType(Integer pickingType) {
        this.pickingType = pickingType;
    }

    public List<String> getTns() {
        return tns;
    }

    public void setTns(List<String> tns) {
        this.tns = tns;
    }

    public String getCmCode() {
        return cmCode;
    }

    public void setCmCode(String cmCode) {
        this.cmCode = cmCode;
    }


    public String getSkuOrigin() {
        return skuOrigin;
    }

    public void setSkuOrigin(String skuOrigin) {
        this.skuOrigin = skuOrigin;
    }

    @Override
    public int compareTo(OrderCheckCommand o) {
        Long vt = Long.valueOf(this.getPgIndex() == null ? "0" : this.getPgIndex());
        Long vo = Long.valueOf(o.getPgIndex() == null ? "0" : o.getPgIndex());
        return vt.compareTo(vo);
    }
}

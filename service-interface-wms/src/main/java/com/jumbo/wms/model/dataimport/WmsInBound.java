package com.jumbo.wms.model.dataimport;

import java.util.List;

import com.jumbo.wms.model.BaseModel;

public class WmsInBound extends BaseModel {
    private static final long serialVersionUID = 6589534547594358098L;
    /** 数据唯一标识 */
    private String uuid;
    /** 上位系统入库单据号 */
    private String extPoCode;
    /** 上位系统平台单据号 */
    private String extCode;
    /** 原始上位系统出库单号 */
    private String originalExtOdoCode;
    /** 原始电商平台出库单号 */
    private String originalEcOrderCode;
    /** 店铺编码 */
    private String storeCode;
    /** 客户编码 */
    private String customerCode;
    /** 来源地 */
    private String fromLocation;
    /** 目的地 */
    private String toLocation;
    /** 计划到货时间格式：年（4位）月（2位）日（2位）时（2位）分（2位）秒（2位） */
    private String eta;
    /** 仓库编码 */
    private String whCode;
    /** 单据类型 */
    private String poType;
    /** 上位系统单据类型 */
    private String extPoType;
    /** 是否质检 默认否 */
    private Boolean isIqc = false;
    /** 计划数量 */
    private Double qtyPlanned;
    /** 计划箱数 */
    private Integer ctnPlanned;
    /** 扩展字段信息 */
    private String extMemo;
    /** 数据来源 区分上位系统 */
    private String dataSource;
    /** 收货信息明细行 */
    private List<WmsInBoundLine> wmsInBoundLines;
    /** 收货运输信息管理 */
    private WmsInBoundTransportMgmt wmsInBoundTransportMgmt;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getExtPoCode() {
        return extPoCode;
    }

    public void setExtPoCode(String extPoCode) {
        this.extPoCode = extPoCode;
    }

    public String getExtCode() {
        return extCode;
    }

    public void setExtCode(String extCode) {
        this.extCode = extCode;
    }

    public String getOriginalExtOdoCode() {
        return originalExtOdoCode;
    }

    public void setOriginalExtOdoCode(String originalExtOdoCode) {
        this.originalExtOdoCode = originalExtOdoCode;
    }

    public String getOriginalEcOrderCode() {
        return originalEcOrderCode;
    }

    public void setOriginalEcOrderCode(String originalEcOrderCode) {
        this.originalEcOrderCode = originalEcOrderCode;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getPoType() {
        return poType;
    }

    public void setPoType(String poType) {
        this.poType = poType;
    }

    public String getExtPoType() {
        return extPoType;
    }

    public void setExtPoType(String extPoType) {
        this.extPoType = extPoType;
    }

    public Boolean getIsIqc() {
        return isIqc;
    }

    public void setIsIqc(Boolean isIqc) {
        this.isIqc = isIqc;
    }

    public Double getQtyPlanned() {
        return qtyPlanned;
    }

    public void setQtyPlanned(Double qtyPlanned) {
        this.qtyPlanned = qtyPlanned;
    }

    public Integer getCtnPlanned() {
        return ctnPlanned;
    }

    public void setCtnPlanned(Integer ctnPlanned) {
        this.ctnPlanned = ctnPlanned;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public List<WmsInBoundLine> getWmsInBoundLines() {
        return wmsInBoundLines;
    }

    public void setWmsInBoundLines(List<WmsInBoundLine> wmsInBoundLines) {
        this.wmsInBoundLines = wmsInBoundLines;
    }

    public WmsInBoundTransportMgmt getWmsInBoundTransportMgmt() {
        return wmsInBoundTransportMgmt;
    }

    public void setWmsInBoundTransportMgmt(WmsInBoundTransportMgmt wmsInBoundTransportMgmt) {
        this.wmsInBoundTransportMgmt = wmsInBoundTransportMgmt;
    }

}

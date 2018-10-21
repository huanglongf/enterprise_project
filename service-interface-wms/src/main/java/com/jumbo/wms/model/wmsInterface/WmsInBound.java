package com.jumbo.wms.model.wmsInterface;

import java.io.Serializable;
import java.util.List;

/**
 * WMS通用收货头信息
 * 
 */
public class WmsInBound implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 46756482375861276L;


    /**
     * 上位系统入库单据号
     */
    private String extCode;

    /**
     * 上位系统平台单据号
     */
    private String extPoCode;

    /**
     * 店铺CODE
     */
    private String storeCode;

    /**
     * WMS单据类型
     */
    private Integer wmsType;

    /**
     * 前置单据类型
     */
    private Integer refSlipType;

    /**
     * 上位系统单据类型
     */
    private String extPoType;

    /**
     * 发货地
     */
    private String fromLocation;

    /**
     * 收货地
     */
    private String toLocation;

    /**
     * 计划到货时间格式：年（4位）月（2位）日（2位）时（2位）分（2位）秒（2位）
     */
    private String eta;

    /**
     * 计划数量
     */
    private Long qtyPlanned;

    /**
     * 定制逻辑使用json格式传递所有定制字段
     */
    private String extMemo;

    /**
     * 仓库编码
     */
    private String whCode;

    /**
     * 数据来源 区分上位系统
     */
    private String dataSource;

    /**
     * 入库单明细
     */
    private List<WmsInBoundLine> wmsInBoundLines;

    public String getExtCode() {
        return extCode;
    }

    public void setExtCode(String extCode) {
        this.extCode = extCode;
    }

    public String getExtPoCode() {
        return extPoCode;
    }

    public void setExtPoCode(String extPoCode) {
        this.extPoCode = extPoCode;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Integer getWmsType() {
        return wmsType;
    }

    public void setWmsType(Integer wmsType) {
        this.wmsType = wmsType;
    }

    public String getExtPoType() {
        return extPoType;
    }

    public void setExtPoType(String extPoType) {
        this.extPoType = extPoType;
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

    public Long getQtyPlanned() {
        return qtyPlanned;
    }

    public void setQtyPlanned(Long qtyPlanned) {
        this.qtyPlanned = qtyPlanned;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Integer getRefSlipType() {
        return refSlipType;
    }

    public void setRefSlipType(Integer refSlipType) {
        this.refSlipType = refSlipType;
    }

    public List<WmsInBoundLine> getWmsInBoundLines() {
        return wmsInBoundLines;
    }

    public void setWmsInBoundLines(List<WmsInBoundLine> wmsInBoundLines) {
        this.wmsInBoundLines = wmsInBoundLines;
    }



}

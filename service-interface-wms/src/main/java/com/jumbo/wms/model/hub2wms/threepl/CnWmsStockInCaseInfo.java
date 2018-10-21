package com.jumbo.wms.model.hub2wms.threepl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 菜鸟入库通知--菜鸟装箱信息
 */
@Entity
@Table(name = "T_WH_CN_STOCK_IN_CASE_INFO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnWmsStockInCaseInfo extends BaseModel {

    private static final long serialVersionUID = 7020623631774624466L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 菜鸟入库通知单
     */
    private CnWmsStockInOrderNotify stockInOrderNotify;
    /**
     * 箱号
     */
    private String caseNumber;
    /**
     * 托盘号
     */
    private String palletNumebr;
    /**
     * 集装箱编号
     */
    private String containerNumber;
    /**
     * 重量 单位克
     */
    private Long weight;
    /**
     * 体积 单位立方厘米
     */
    private Integer volume;
    /**
     * 长 单位 mm
     */
    private Integer length;
    /**
     * 宽 单位 mm
     */
    private Integer width;
    /**
     * 高 单位 mm
     */
    private Integer height;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WCSI", sequenceName = "S_T_WH_CN_STOCK_IN_CASE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WCSI")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_IN_ORDER_ID")
    public CnWmsStockInOrderNotify getStockInOrderNotify() {
        return stockInOrderNotify;
    }

    public void setStockInOrderNotify(CnWmsStockInOrderNotify stockInOrderNotify) {
        this.stockInOrderNotify = stockInOrderNotify;
    }

    @Column(name = "CASE_NUMBER")
    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    @Column(name = "PALLET_NUMEBR")
    public String getPalletNumebr() {
        return palletNumebr;
    }

    public void setPalletNumebr(String palletNumebr) {
        this.palletNumebr = palletNumebr;
    }

    @Column(name = "CONTAINER_NUMBER")
    public String getContainerNumber() {
        return containerNumber;
    }

    public void setContainerNumber(String containerNumber) {
        this.containerNumber = containerNumber;
    }

    @Column(name = "WEIGHT")
    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    @Column(name = "VOLUME")
    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    @Column(name = "LENGTH")
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Column(name = "WIDTH")
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Column(name = "HEIGHT")
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

}

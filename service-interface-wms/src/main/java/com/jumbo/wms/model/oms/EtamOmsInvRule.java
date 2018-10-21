package com.jumbo.wms.model.oms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "t_etam_inv_rule")
public class EtamOmsInvRule extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -9028083131374763852L;
    /**
     * PK
     */
    private Long id;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 尺码
     */
    private String sizes;
    /**
     * 款号
     */
    private String bar9;
    /**
     * 颜色
     */
    private String color;
    /**
     * 类型 (1.尺码 2.款号 款号规则优先于尺码)
     */
    private Integer ruleType;
    /**
     * 数量
     */
    private Long qty;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ETM_OMS_INVR", sequenceName = "S_t_etam_inv_rule", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ETM_OMS_INVR")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "BRAND", length = 100)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "SIZES", length = 100)
    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    @Column(name = "BAR9", length = 100)
    public String getBar9() {
        return bar9;
    }

    public void setBar9(String bar9) {
        this.bar9 = bar9;
    }

    @Column(name = "COLOR", length = 100)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "RULE_TYPE")
    public Integer getRuleType() {
        return ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }


}

package com.jumbo.wms.model.nikeLogistics;

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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 记录推送信息表
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "t_nike_CartonNo_line")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class NikeCartonNoLine extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 3644326910914335161L;

    private Long id;
    private String UPC;
    private String style;
    private String color;
    private String size;
    private Long qty;
    private NikeCartonNo cartonNo;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_NIKE_CARTONNO_LINE", sequenceName = "S_T_NIKE_CARTONNO_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NIKE_CARTONNO_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "UPC")
    public String getUPC() {
        return UPC;
    }

    public void setUPC(String uPC) {
        UPC = uPC;
    }

    @Column(name = "style")
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Column(name = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "sku_size")
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Column(name = "qty")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartonNo_id")
    @Index(name = "IDX_cartonNo_id")
    public NikeCartonNo getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(NikeCartonNo cartonNo) {
        this.cartonNo = cartonNo;
    }



}

package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_IDS_INBOUND_SKU")
public class IdsInboundSku extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 3302660392279263371L;

    private Long id;

    private Date creatDate;

    private String type;

    private String columnType;

    private String storerkey;

    private String sku;

    private String descr;

    private String susr;

    private String manufacturersku;

    private String retailsku;

    private String packkey;

    private String stdgrosswgt;

    private String active;

    private String skugroup;

    private String lottablelabel;

    private String notes;

    private String strategykey;

    private String cartongroup;

    private String style;

    private String colour;

    private String packuom;

    private String casecnt;

    private String packuom3;

    private String qty;

    private String lengthuom3;

    private String sizes;

    private String headerflag;

    private String interfaceactionflag;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CARTON", sequenceName = "s_t_ids_inbound_sku", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CARTON")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREATDATE")
    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "COLUMNTYPE")
    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    @Column(name = "STORERKEY")
    public String getStorerkey() {
        return storerkey;
    }

    public void setStorerkey(String storerkey) {
        this.storerkey = storerkey;
    }

    @Column(name = "SKU")
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Column(name = "DESCR")
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Column(name = "SUSR")
    public String getSusr() {
        return susr;
    }

    public void setSusr(String susr) {
        this.susr = susr;
    }

    @Column(name = "MANUFACTURERSKU")
    public String getManufacturersku() {
        return manufacturersku;
    }

    public void setManufacturersku(String manufacturersku) {
        this.manufacturersku = manufacturersku;
    }

    @Column(name = "RETAILSKU")
    public String getRetailsku() {
        return retailsku;
    }

    public void setRetailsku(String retailsku) {
        this.retailsku = retailsku;
    }

    @Column(name = "PACKKEY")
    public String getPackkey() {
        return packkey;
    }

    public void setPackkey(String packkey) {
        this.packkey = packkey;
    }

    @Column(name = "STDGROSSWGT")
    public String getStdgrosswgt() {
        return stdgrosswgt;
    }

    public void setStdgrosswgt(String stdgrosswgt) {
        this.stdgrosswgt = stdgrosswgt;
    }

    @Column(name = "ACTIVE")
    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Column(name = "SKUGROUP")
    public String getSkugroup() {
        return skugroup;
    }

    public void setSkugroup(String skugroup) {
        this.skugroup = skugroup;
    }

    @Column(name = "LOTTABLELABEL")
    public String getLottablelabel() {
        return lottablelabel;
    }

    public void setLottablelabel(String lottablelabel) {
        this.lottablelabel = lottablelabel;
    }

    @Column(name = "NOTES")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "STRATEGYKEY")
    public String getStrategykey() {
        return strategykey;
    }

    public void setStrategykey(String strategykey) {
        this.strategykey = strategykey;
    }

    @Column(name = "CARTONGROUP")
    public String getCartongroup() {
        return cartongroup;
    }

    public void setCartongroup(String cartongroup) {
        this.cartongroup = cartongroup;
    }

    @Column(name = "STYLE")
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Column(name = "COLOUR")
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Column(name = "PACKUOM")
    public String getPackuom() {
        return packuom;
    }

    public void setPackuom(String packuom) {
        this.packuom = packuom;
    }

    @Column(name = "CASECNT")
    public String getCasecnt() {
        return casecnt;
    }

    public void setCasecnt(String casecnt) {
        this.casecnt = casecnt;
    }

    @Column(name = "PACKUOM3")
    public String getPackuom3() {
        return packuom3;
    }

    public void setPackuom3(String packuom3) {
        this.packuom3 = packuom3;
    }

    @Column(name = "QTY")
    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    @Column(name = "LENGTHUOM3")
    public String getLengthuom3() {
        return lengthuom3;
    }

    public void setLengthuom3(String lengthuom3) {
        this.lengthuom3 = lengthuom3;
    }

    @Column(name = "SIZES")
    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    @Column(name = "HEADERFLAG")
    public String getHeaderflag() {
        return headerflag;
    }

    public void setHeaderflag(String headerflag) {
        this.headerflag = headerflag;
    }

    @Column(name = "INTERFACEACTIONFLAG")
    public String getInterfaceactionflag() {
        return interfaceactionflag;
    }

    public void setInterfaceactionflag(String interfaceactionflag) {
        this.interfaceactionflag = interfaceactionflag;
    }



}

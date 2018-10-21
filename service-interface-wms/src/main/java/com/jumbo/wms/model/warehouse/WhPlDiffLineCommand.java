package com.jumbo.wms.model.warehouse;


public class WhPlDiffLineCommand extends WhPlDiffLine {

    /**
     * 
     */
    private static final long serialVersionUID = -44899968443767175L;

    private Long id;
    private Long skuid;
    private Integer planQty;
    private Integer qty;
    private Integer discrepancy;
    private String name;
    private String barCode;
    private String code;
    private String extCode1;
    private Integer pgIndex;
    private String staCode;

    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuid() {
        return skuid;
    }

    public void setSkuid(Long skuid) {
        this.skuid = skuid;
    }
    @Override
    public Integer getPlanQty() {
        return planQty;
    }
    @Override
    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }
    @Override
    public Integer getQty() {
        return qty;
    }
    @Override
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getDiscrepancy() {
        return discrepancy;
    }

    public void setDiscrepancy(Integer discrepancy) {
        this.discrepancy = discrepancy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExtCode1() {
        return extCode1;
    }

    public void setExtCode1(String extCode1) {
        this.extCode1 = extCode1;
    }
    @Override
    public Integer getPgIndex() {
        return pgIndex;
    }
    @Override
    public void setPgIndex(Integer pgIndex) {
        this.pgIndex = pgIndex;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }


}

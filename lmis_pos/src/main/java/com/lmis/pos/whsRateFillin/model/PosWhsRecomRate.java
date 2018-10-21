package com.lmis.pos.whsRateFillin.model;

import java.math.BigDecimal;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

public class PosWhsRecomRate extends PersistentObject{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "仓库代码")
    private String whsCode;

    @ApiModelProperty(value = "仓库名称")
    private String whsName;

    @ApiModelProperty(value = "商品类型")
    private String proType;

    @ApiModelProperty(value = "商品名称")
    private String proName;

    @ApiModelProperty(value = "商品颜色")
    private String proColor;

    @ApiModelProperty(value = "商品尺码")
    private String skuSize;

    @ApiModelProperty(value = "商品条码")
    private String barCode;

    @ApiModelProperty(value = "近三个月仓库覆盖区域出库量")
    private BigDecimal outByWhs;

    @ApiModelProperty(value = "近三个月全国出库量")
    private BigDecimal outByAll;

    @ApiModelProperty(value = "出库占比")
    private BigDecimal outrate;

    @ApiModelProperty(value = "扩展编码")
    private String extCode;
    
    @ApiModelProperty(value = "SKU编码")
    private String skuCode;
 

    public String getSkuCode(){
        return skuCode;
    }

    
    public void setSkuCode(String skuCode){
        this.skuCode = skuCode;
    }

    public String getWhsCode(){
        return whsCode;
    }

    public void setWhsCode(String whsCode){
        this.whsCode = whsCode;
    }

    public String getWhsName(){
        return whsName;
    }

    public void setWhsName(String whsName){
        this.whsName = whsName;
    }

    public String getProType(){
        return proType;
    }

    public void setProType(String proType){
        this.proType = proType;
    }

    public String getProName(){
        return proName;
    }

    public void setProName(String proName){
        this.proName = proName;
    }

    public String getProColor(){
        return proColor;
    }

    public void setProColor(String proColor){
        this.proColor = proColor;
    }

    public String getSkuSize(){
        return skuSize;
    }

    public void setSkuSize(String skuSize){
        this.skuSize = skuSize;
    }

    public String getBarCode(){
        return barCode;
    }

    public void setBarCode(String barCode){
        this.barCode = barCode;
    }

    public BigDecimal getOutByWhs(){
        return outByWhs;
    }

    public void setOutByWhs(BigDecimal outByWhs){
        this.outByWhs = outByWhs;
    }

    public BigDecimal getOutByAll(){
        return outByAll;
    }

    public void setOutByAll(BigDecimal outByAll){
        this.outByAll = outByAll;
    }

    public BigDecimal getOutrate(){
        return outrate;
    }

    public void setOutrate(BigDecimal outrate){
        this.outrate = outrate;
    }

    public static long getSerialversionuid(){
        return serialVersionUID;
    }


    
    public String getExtCode(){
        return extCode;
    }

    public void setExtCode(String extCode){
        this.extCode = extCode;
    }



}

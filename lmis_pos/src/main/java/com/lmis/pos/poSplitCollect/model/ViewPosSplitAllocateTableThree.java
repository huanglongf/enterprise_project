package com.lmis.pos.poSplitCollect.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: ViewPosSplitAllocateTableThree
 * @Description: TODO(VIEW 查询PO拆分汇总表格3数据)
 * @author codeGenerator
 * @date 2018-05-29 15:36:56
 * 
 */
public class ViewPosSplitAllocateTableThree {
	
	
	@ApiModelProperty(value = "仓库名称")
	private String whsName;
	
	@ApiModelProperty(value = "计划入库数")
    private BigDecimal planNum;
	
	@ApiModelProperty(value = "计划入库数占比")
	private BigDecimal planNumRatio;
	
	@ApiModelProperty(value = "天猫-L商品总数")
	private BigDecimal tmlNum;
	
	@ApiModelProperty(value = "天猫-F商品总数")
	private BigDecimal tmfNum;
	
	@ApiModelProperty(value = "天猫-其他商品总数")
    private BigDecimal tmOtherNum;
	
	@ApiModelProperty(value = "天猫商品数量合计")
    private BigDecimal tmNum;
	
	@ApiModelProperty(value = "官网-L商品总数")
    private BigDecimal gwlNum;
	
	@ApiModelProperty(value = "官网-F商品总数")
    private BigDecimal gwfNum;
	
	@ApiModelProperty(value = "官网-其他商品总数")
    private BigDecimal gwOtherNum;
	
	@ApiModelProperty(value = "官网商品数量合计")
    private BigDecimal gwNum;
	

    
    public String getWhsName(){
        return whsName;
    }

    
    public void setWhsName(String whsName){
        this.whsName = whsName;
    }

    
    public BigDecimal getPlanNum(){
        return planNum;
    }

    
    public void setPlanNum(BigDecimal planNum){
        this.planNum = planNum;
    }

    
    public BigDecimal getPlanNumRatio(){
        return planNumRatio;
    }

    
    public void setPlanNumRatio(BigDecimal planNumRatio){
        this.planNumRatio = planNumRatio;
    }

    
    public BigDecimal getTmlNum(){
        return tmlNum;
    }

    
    public void setTmlNum(BigDecimal tmlNum){
        this.tmlNum = tmlNum;
    }

    
    public BigDecimal getTmfNum(){
        return tmfNum;
    }

    
    public void setTmfNum(BigDecimal tmfNum){
        this.tmfNum = tmfNum;
    }

    
    public BigDecimal getTmOtherNum(){
        return tmOtherNum;
    }

    
    public void setTmOtherNum(BigDecimal tmOtherNum){
        this.tmOtherNum = tmOtherNum;
    }

    
    public BigDecimal getTmNum(){
        return tmNum;
    }

    
    public void setTmNum(BigDecimal tmNum){
        this.tmNum = tmNum;
    }

    
    public BigDecimal getGwlNum(){
        return gwlNum;
    }

    
    public void setGwlNum(BigDecimal gwlNum){
        this.gwlNum = gwlNum;
    }

    
    public BigDecimal getGwfNum(){
        return gwfNum;
    }

    
    public void setGwfNum(BigDecimal gwfNum){
        this.gwfNum = gwfNum;
    }

    
    public BigDecimal getGwOtherNum(){
        return gwOtherNum;
    }

    
    public void setGwOtherNum(BigDecimal gwOtherNum){
        this.gwOtherNum = gwOtherNum;
    }

    
    public BigDecimal getGwNum(){
        return gwNum;
    }

    
    public void setGwNum(BigDecimal gwNum){
        this.gwNum = gwNum;
    }
    
    
	
	
	
}

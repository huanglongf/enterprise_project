package com.lmis.pos.poSplitCollect.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: ViewPosSplitAllocateTableFour
 * @Description: TODO(VIEW 查询PO拆分汇总表格4数据)
 * @author codeGenerator
 * @date 2018-05-29 15:36:56
 * 
 */
public class ViewPosSplitAllocateTableFour {
	
	
	@ApiModelProperty(value = "仓库名称")
	private String whsName;
	
	@ApiModelProperty(value = "参与拆分商品的计划入库数")
    private BigDecimal joinSplitNum;
	
	@ApiModelProperty(value = "参与拆分商品的计划入库数占比")
	private BigDecimal joinSplitRatio;
	
	@ApiModelProperty(value = "不参与拆分商品的计划入库数")
	private BigDecimal joinNoSplitNum;

    
    public String getWhsName(){
        return whsName;
    }

    
    public void setWhsName(String whsName){
        this.whsName = whsName;
    }

    
    public BigDecimal getJoinSplitNum(){
        return joinSplitNum;
    }

    
    public void setJoinSplitNum(BigDecimal joinSplitNum){
        this.joinSplitNum = joinSplitNum;
    }

    
    public BigDecimal getJoinSplitRatio(){
        return joinSplitRatio;
    }

    
    public void setJoinSplitRatio(BigDecimal joinSplitRatio){
        this.joinSplitRatio = joinSplitRatio;
    }

    
    public BigDecimal getJoinNoSplitNum(){
        return joinNoSplitNum;
    }

    
    public void setJoinNoSplitNum(BigDecimal joinNoSplitNum){
        this.joinNoSplitNum = joinNoSplitNum;
    }
	
	
}

package com.lmis.pos.whsSkutypeOutrate.model;

import java.math.BigDecimal;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: PosWhsSkutypeOutrate
 * @Description: TODO(仓库-鞋服配出库占比)
 * @author codeGenerator
 * @date 2018-06-05 16:49:38
 * 
 */
public class PosWhsSkutypeOutrate extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "生成批次号 e.g.2018060101")
	private Integer batchId;
	public Integer getBatchId() {
		return batchId;
	}
	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}
	
    @ApiModelProperty(value = "仓库编码")
	private String whsCode;
	public String getWhsCode() {
		return whsCode;
	}
	public void setWhsCode(String whsCode) {
		this.whsCode = whsCode;
	}
	
	 @ApiModelProperty(value = "仓库名称")
	private String whsName;
    public String getWhsName() {
		return whsName;
	}
	public void setWhsName(String whsName) {
		this.whsName = whsName;
	}

	@ApiModelProperty(value = "SKU类型（鞋服配）")
	private String skuType;
	public String getSkuType() {
		return skuType;
	}
	public void setSkuType(String skuType) {
		this.skuType = skuType;
	}
	
    @ApiModelProperty(value = "近一个月仓库（鞋|服|配）出库量")
	private BigDecimal outBySkutype;
	public BigDecimal getOutBySkutype() {
		return outBySkutype;
	}
	public void setOutBySkutype(BigDecimal outBySkutype) {
		this.outBySkutype = outBySkutype;
	}
	
    @ApiModelProperty(value = "近一个月仓库（鞋|服|配）总出库量")
	private BigDecimal outByAll;
	public BigDecimal getOutByAll() {
		return outByAll;
	}
	public void setOutByAll(BigDecimal outByAll) {
		this.outByAll = outByAll;
	}
	
    @ApiModelProperty(value = "类型出库占比")
	private BigDecimal outrate;
	public BigDecimal getOutrate() {
		return outrate;
	}
	public void setOutrate(BigDecimal outrate) {
		this.outrate = outrate;
	}
	
}

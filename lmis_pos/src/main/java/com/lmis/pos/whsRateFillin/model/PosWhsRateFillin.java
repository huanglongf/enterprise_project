package com.lmis.pos.whsRateFillin.model;

import java.math.BigDecimal;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: PosWhsRateFillin
 * @Description: TODO(补货商品分配仓库比例分析)
 * @author codeGenerator
 * @date 2018-05-30 17:03:06
 * 
 */
public class PosWhsRateFillin extends PersistentObject {

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
	
    @ApiModelProperty(value = "SKU编码")
	private String skuCode;
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	
	@ApiModelProperty(value = "扩展编码")
	private String extCode;
	public String getExtCode() {
		return extCode;
	}
	public void setExtCode(String extCode) {
		this.extCode = extCode;
	}
	
    @ApiModelProperty(value = "近三个月仓库覆盖区域出库量")
	private BigDecimal outByWhs;
	public BigDecimal getOutByWhs() {
		return outByWhs;
	}
	public void setOutByWhs(BigDecimal outByWhs) {
		this.outByWhs = outByWhs;
	}
	
    @ApiModelProperty(value = "近三个月全国出库量")
	private BigDecimal outByAll;
	public BigDecimal getOutByAll() {
		return outByAll;
	}
	public void setOutByAll(BigDecimal outByAll) {
		this.outByAll = outByAll;
	}
	
    @ApiModelProperty(value = "出库占比")
	private BigDecimal outrate;
	public BigDecimal getOutrate() {
		return outrate;
	}
	public void setOutrate(BigDecimal outrate) {
		this.outrate = outrate;
	}
	
}

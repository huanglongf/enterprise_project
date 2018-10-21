package com.jumbo.wms.model.warehouse;

/**
 *  配货规则维护
 *  @author NCGZ-DZ-SJ
 */
public class DistributionRuleCommand extends DistributionRule {
	
	private static final long serialVersionUID = -7432121106442767576L;
   
    private String strStatus;//状态
    private String createName;//创建人

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
	
   
}

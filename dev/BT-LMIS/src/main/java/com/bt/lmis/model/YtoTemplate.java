package com.bt.lmis.model;

/**
* @ClassName: YtoTemplate
* @Description: TODO(YtoTemplate)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class YtoTemplate {
	
	
	private String bat_id;			//批次号
	
		return bat_id;
	}
	public void setBat_id(String bat_id) {
		this.bat_id = bat_id;
	}
	public Integer getId() {
	public YtoTemplate(String[] row,String bath_id) {
		this.bat_id = bath_id;
		this.transport_time = row[1];
		this.express_number = row[2];
		this.origin = row[3];
		this.province_dest = row[4];
		this.city_dist = row[5];
		this.weight = row[6];
		this.charge_weight = row[7];
		this.firstWeightPrice = row[8];
		this.addedWeightPrice = row[9];
		this.discount = row[10];
		this.standard_freight = row[11];
	}
	
	
}
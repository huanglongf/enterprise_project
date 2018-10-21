package com.jumbo.wms.model.warehouse;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Brand;

@Entity
@Table(name = "T_BI_CHANNEL_BRAND_REF")
public class BiChannelBrandRef extends BaseModel {

	private static final long serialVersionUID = 3364795663378009029L;
	
	/**
	 * 店铺
	 */
	private BiChannel biChannel;
	
	/**
	 * 品牌
	 */
	private Brand brand;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHANNEL_ID")
	public BiChannel getBiChannel() {
		return biChannel;
	}

	public void setBiChannel(BiChannel biChannel) {
		this.biChannel = biChannel;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRAND_ID")
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	
}

package com.jumbo.dao.baseinfo;

import java.util.List;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.BiChannelBrandRef;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface BiChannelBrandRefDao extends GenericEntityDao<BiChannelBrandRef, Long> {
	
	@NativeUpdate
	void insertBiChannelBrandRef(@QueryParam("channelId") Long shopId, @QueryParam("brandId") Long brandId);
	
	@NativeUpdate
	void deleteBiChannelBrandRef(@QueryParam("channelId") Long shopId);
	
	@NativeQuery
	List<Long> getBrandIdListByShopId(@QueryParam("channelId") Long id, SingleColumnRowMapper<Long> singleColumnRowMapper);
	
	@NativeQuery
	Long findBiChannelBrandRefExists(@QueryParam("channelId") Long shopId, @QueryParam("brandId") Long brandId, SingleColumnRowMapper<Long> singleColumnRowMapper);
	
}

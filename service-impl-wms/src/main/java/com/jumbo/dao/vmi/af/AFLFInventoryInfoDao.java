package com.jumbo.dao.vmi.af;



import java.util.Date;

import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.af.AFLFInventoryInfo;
@Transactional
public interface AFLFInventoryInfoDao extends GenericEntityDao<AFLFInventoryInfo, Long> {
	
	@NativeUpdate
	void insertAFLFInventoryInfoReport(@QueryParam("today") Date today,@QueryParam("vimCode") String vimSource,@QueryParam("ouid") Long ouid,@QueryParam("innerShopid") String innerShopid);
    
}

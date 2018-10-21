package com.jumbo.pms.manager.api;

import java.util.List;

import com.jumbo.pms.model.command.ParcelResult;
import com.jumbo.pms.model.command.cond.PgPackageCreateCommand;
import com.jumbo.pms.model.command.cond.PgPackageCreateCond;
import com.jumbo.pms.model.command.vo.CreateSfOrderVo;
import com.jumbo.pms.model.command.vo.GetParcelInfoVo;

public interface ApiPackageManager {
	
	/**
	 * 物流下单
	 * @param channelCode 渠道编码
	 * @param createSfOrderVo 物流下单面单内容
	 * @return
	 */
	ParcelResult createSFOrder(String channelCode, CreateSfOrderVo createSfOrderVo);
	   
	/**
	 * 获取包裹快照  
	 * @param getParcelInfoVo
	 * @return
	 */
	PgPackageCreateCond getParcelInfo(GetParcelInfoVo getParcelInfoVo);
	
    /**
     * 推送包裹出库信息至SD
     */
    List<PgPackageCreateCommand> getPackageInfo();
	
}

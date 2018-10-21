package com.jumbo.pms.manager.app;

import java.util.List;

import com.jumbo.pms.model.ParcelMailNoGettingCommand;
import com.jumbo.pms.model.AppParcelCommand;
import com.jumbo.pms.model.ParcelBill;
import com.jumbo.rmi.warehouse.PmsBaseResult;

public interface AppParcelManager {
	
	/**
	 * 包裹更新(批量)
	 * @param parcelCommands
	 * @return
	 */
	List<PmsBaseResult> updateSoParcel(List<AppParcelCommand> parcelCommands);
	
	/**
	 * WMS通知PMS包裹已出库
	 * @param parcelBill
	 */
	void staFinishNotifyPMS(ParcelBill parcelBill);
	
	/**
	 * APP获取运单号
	 * @param command
	 * @return
	 */
	PmsBaseResult getMailNoForStore(ParcelMailNoGettingCommand command);
	
	/**
	 * 查询当前门店是否可以签收扫描的包裹
	 * @param parcelCommand
	 * @return
	 */
	PmsBaseResult queryParcelExists(AppParcelCommand parcelCommand);
	
	/**
	 * 包裹更新(单个)
	 * @param parcelCommand
	 * @return
	 */
	PmsBaseResult updateParcel(AppParcelCommand parcelCommand);
}

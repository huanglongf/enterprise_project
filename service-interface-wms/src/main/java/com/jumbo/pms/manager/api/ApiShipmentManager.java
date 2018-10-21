package com.jumbo.pms.manager.api;

import java.util.List;
import java.util.Map;

import com.jumbo.pms.model.command.ParcelResult;
import com.jumbo.pms.model.command.cond.PgPackageCreateCond;
import com.jumbo.pms.model.command.vo.CreateLogisticsOrderVo;
import com.jumbo.pms.model.command.vo.GetParcelInfoVo;
import com.jumbo.pms.model.command.vo.ParcelUpdateMailNoVo;
import com.jumbo.pms.model.command.vo.ParcelUpdateStatusVo;
import com.jumbo.pms.model.command.vo.ShipmentInTransitVo;

public interface ApiShipmentManager {
	
	/**
	 * 包裹状态更新(批量)
	 * @param parcelUpdateStatusVos
	 * @return
	 */
	List<ParcelResult> updateParcelStatus(String channelCode, List<ParcelUpdateStatusVo> parcelUpdateStatusVos);
	
	/**
	 * create Shipment API
	 * @param opType
	 * @param shipmentCommand
	 * @return
	 */
	List<ParcelResult> createShipment(String opType, ShipmentInTransitVo shipmentInTransitVo) ;
	
	/**
	 * 物流下单
	 * @param channelCode 渠道编码
	 * @param opType 退货入口/门店配货入口
	 * @param createLogisticsOrderVo 物流下单面单内容
	 * @return
	 */
	Map<String, Object> createLogisticsOrder(String channelCode, String opType, CreateLogisticsOrderVo createLogisticsOrderVo);
	
	   
    /**
     * 异常包裹更新运单号
     * @param parcelUpdateMailVo
     * @return
     */
	ParcelResult updateParcelMailNo(ParcelUpdateMailNoVo parcelUpdateMailVo);
	
	/**
	 * 获取包裹快照
	 * @param vo
	 * @return
	 */
	PgPackageCreateCond getParcelInfo(GetParcelInfoVo vo);
	
}

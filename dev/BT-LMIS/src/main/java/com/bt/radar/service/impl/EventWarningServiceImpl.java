package com.bt.radar.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.radar.dao.RouteInfoMapper;
import com.bt.radar.dao.WarningLevelupRecordMapper;
import com.bt.radar.dao.WarningRoutestatusListMapper;
import com.bt.radar.dao.WarninginfoMaintainMasterMapper;
import com.bt.radar.dao.WarninglevelListMapper;
import com.bt.radar.dao.WaybillWarninginfoDetailMapper;
import com.bt.radar.dao.WaybillWarninginfoMasterMapper;
import com.bt.radar.model.RouteInfo;
import com.bt.radar.model.WarningLevelupRecord;
import com.bt.radar.model.WarningRoutestatusList;
import com.bt.radar.model.WarninginfoMaintainMaster;
import com.bt.radar.model.WarninglevelList;
import com.bt.radar.model.WaybillWarninginfoDetail;
import com.bt.radar.model.WaybillWarninginfoMaster;
import com.bt.radar.service.EventWarningService;
import com.bt.utils.CommonUtils;
/**
 * @Title:EventWarningServiceImpl
 * @Description: TODO(事件预警ServiceImpl)
 * @author Ian.Huang 
 * @date 2016年9月1日下午5:13:31
 */
@Service
public class EventWarningServiceImpl<T> extends ServiceSupport<T> implements EventWarningService<T> {
	
	@Autowired  
	WarninglevelListMapper  levelMapper;
	@Autowired
	private WarningLevelupRecordMapper<T> warningLevelupRecordMapper;
	@Autowired
	private WarninglevelListMapper<T> warninglevelListMapper;
	@Autowired
	private WaybillWarninginfoDetailMapper<T> waybillWarninginfoDetailMapper;
	@Autowired
	private WaybillWarninginfoMasterMapper<T> waybillWarninginfoMasterMapper;
	@Autowired
	private RouteInfoMapper<T> routeInfoMapper;
	@Autowired
	private WarningRoutestatusListMapper<T> warningRoutestatusListMapper;
	@Autowired
	private WarninginfoMaintainMasterMapper<T> warninginfoMaintainMasterMapper;
	
	public WarningLevelupRecordMapper<T> getWarningLevelupRecordMapper(){
		return warningLevelupRecordMapper;
	}
	public WarninglevelListMapper<T> getWarninglevelListMapper(){
		return warninglevelListMapper;
	}
	public WaybillWarninginfoDetailMapper<T> getWaybillWarninginfoDetailMapper(){
		return waybillWarninginfoDetailMapper;
	}
	public WaybillWarninginfoMasterMapper<T> getWaybillWarninginfoMasterMapper(){
		return waybillWarninginfoMasterMapper;
	}
	public RouteInfoMapper<T> getRouteInfoMapper(){
		return routeInfoMapper;
	}
	public WarningRoutestatusListMapper<T> getWarningRoutestatusListMapper(){
		return warningRoutestatusListMapper;
	}
	public WarninginfoMaintainMasterMapper<T> getWarninginfoMaintainMasterMapper(){
		return warninginfoMaintainMasterMapper;
	}
	
	
	@Override
	public void eventWarningLevelUp() throws Exception {
		WaybillWarninginfoDetail detail=new WaybillWarninginfoDetail();
		detail.setDel_flag("0");
		detail.setWarning_category("0");
		detail.setStop_watch("0");
	    List<WaybillWarninginfoDetail>	details =waybillWarninginfoDetailMapper.findRecords(detail);
	    for(WaybillWarninginfoDetail wd:details){
	    	eventWarningLevelUp(wd);
	    }
	}
	
	public  void  eventWarningLevelUp(WaybillWarninginfoDetail wd){
		Date baseDate=wd.getHappen_time();
		Map params=new HashMap();
		params.put("time",  getMinutesBetween(baseDate,new Date())); 
			params.put("warningtype_code",wd.getWarningtype_code());
			params=levelMapper.analyzeLevel(params);                               
			    WaybillWarninginfoDetail detail=new WaybillWarninginfoDetail();
			    if(params==null||params.get("min_num")==null){
			    	detail.setWarning_level(wd.getWarning_level());
			    }else{
			    	if(params.get("max_num")==null)detail.setStop_watch("1");
			    	detail.setWarning_level(params.get("min_num").toString());	
			    }
			    waybillWarninginfoDetailMapper.updateWaybillWarninginfoDetail(detail);
	}
	
	/*@Override
	public void eventWarningLevelUp() throws Exception {
		// 在运单预警信息主表中查询所有预警中的记录
		WaybillWarninginfoMaster waybillWarninginfoMaster = new WaybillWarninginfoMaster();
		waybillWarninginfoMaster.setWarining_status("0");
		List<WaybillWarninginfoMaster> waybillWarninginfoMasters = waybillWarninginfoMasterMapper.findRecords(waybillWarninginfoMaster);
		// 预警类型主表
		WarninginfoMaintainMaster warninginfoMaintainMaster = null;
		// 预警类型主表集合
		List<WarninginfoMaintainMaster> warninginfoMaintainMasters = null;
		// 预警升级规则集合
		List<WarninglevelList> warninglevelLists = null;
		// 预警升级规则
		WarninglevelList warninglevelList = null;
		// 相差分钟数
		BigDecimal minutes = null;
		//　预警级别
		String level = null;
		// 预警升级记录
		WarningLevelupRecord warningLevelupRecord = null;
		//　运单预警信息子表集合
		List<WaybillWarninginfoDetail> waybillWarninginfoDetails = null;
		//　运单预警信息子表
		WaybillWarninginfoDetail waybillWarninginfoDetail = null;
		// 级别集合
		List<String> levels = null;
		if(CommonUtils.checkExistOrNot(waybillWarninginfoMasters)){
			for(int i = 0; i < waybillWarninginfoMasters.size(); i++) {
				waybillWarninginfoMaster = waybillWarninginfoMasters.get(i);
				warninginfoMaintainMaster = new WarninginfoMaintainMaster();
				warninginfoMaintainMaster.setWarningtype_code(waybillWarninginfoMaster.getWarningtype_code());
				warninginfoMaintainMaster.setWarning_category("0");
				warninginfoMaintainMasters = warninginfoMaintainMasterMapper.findRecords(warninginfoMaintainMaster);
				// 存在则此运单预警信息主表记录为事件预警
				if(CommonUtils.checkExistOrNot(warninginfoMaintainMasters) && warninginfoMaintainMasters.size() == 1) {
					// 查询所有子表记录
					waybillWarninginfoDetail = new WaybillWarninginfoDetail();
					waybillWarninginfoDetail.setPid(waybillWarninginfoMaster.getId());
					waybillWarninginfoDetails = waybillWarninginfoDetailMapper.findRecords(waybillWarninginfoDetail);
					if(CommonUtils.checkExistOrNot(waybillWarninginfoDetails)){
						levels = new ArrayList<String>();
						for(int a = 0; a < waybillWarninginfoDetails.size(); a++) {
							waybillWarninginfoDetail = waybillWarninginfoDetails.get(a);
							// 子表数据是否需要预警升级
							warninglevelLists = warninglevelListMapper.selectRecord(waybillWarninginfoDetail.getWarning_level(), warninginfoMaintainMaster.getWarningtype_code());
							// 规则存在
							if(CommonUtils.checkExistOrNot(warninglevelLists)){
								for(int j = 0; j < warninglevelLists.size(); j++) {
									warninglevelList = warninglevelLists.get(j);
									minutes = new BigDecimal(
											new Date().getTime() - waybillWarninginfoDetail.getHappen_time().getTime()
											).divide(
													new BigDecimal(
															1000 * 60
															), 2);
									if(minutes.compareTo(new BigDecimal(warninglevelList.getLevelup_time())) == 1
											&& (Integer.parseInt(waybillWarninginfoDetail.getWarning_level()) < Integer.parseInt(warninglevelList.getLevelup_level()))){
										// 当满足升级条件，且升级等级大于当前等级
										level = warninglevelList.getLevelup_level();
									} else {
										break;
									}
								}
								if(CommonUtils.checkExistOrNot(level)){
									// 需更新等级存在则更新子表记录等级
									waybillWarninginfoDetail.setWarning_level(level);
									waybillWarninginfoDetailMapper.updateWaybillWarninginfoDetail(waybillWarninginfoDetail);
									// 
									levels.add(level);
								}
							}
						}
					}
					//　当子表升级等级存在
					if(CommonUtils.checkExistOrNot(levels)) {
						// 取最大等级
						String maxLevel = "0";
						for(int b = 0; b < levels.size(); b++){
							if(Integer.parseInt(levels.get(b)) > Integer.parseInt(maxLevel)){
								maxLevel = levels.get(b);
							}
						}
						//　判断预警主表是否去要升级
						if(Integer.parseInt(maxLevel) > Integer.parseInt(waybillWarninginfoMaster.getWarning_level())){
							//　新增升级记录
							warningLevelupRecord = new WarningLevelupRecord();
							warningLevelupRecord.setId(UUID.randomUUID().toString());
							warningLevelupRecord.setCreate_user("SYSTEM");
							warningLevelupRecord.setWaybill(waybillWarninginfoMaster.getWaybill());
							warningLevelupRecord.setPid(waybillWarninginfoMaster.getId());
							warningLevelupRecord.setBefore_level(waybillWarninginfoMaster.getWarning_level());
							warningLevelupRecord.setAfter_level(maxLevel);
							warningLevelupRecordMapper.insertWarningLevelupRecord(warningLevelupRecord);
							// 更新主表记录升级
							waybillWarninginfoMaster.setWarning_level(maxLevel);
							waybillWarninginfoMasterMapper.updateWaybillWarninginfoMaster(waybillWarninginfoMaster);
						}
					}
				}
			}
		}
	}*/
	
	@Override
	public void eventAutoWarning() throws Exception {
		// 查询所有预警类别为事件的预警类型
		WarninginfoMaintainMaster warninginfoMaintainMaster = new WarninginfoMaintainMaster();
		warninginfoMaintainMaster.setWarning_category("0");
		List<WarninginfoMaintainMaster> warninginfoMaintainMasters = warninginfoMaintainMasterMapper.findRecords(warninginfoMaintainMaster);
		// 预警状态集合
		List<WarningRoutestatusList> warningRoutestatusLists = null;
		//　预警状态
		WarningRoutestatusList warningRoutestatusList = null;
		// 路由信息集合
		List<RouteInfo> routeInfos = null;
		// 路由信息
		RouteInfo routeInfo = null;
		// 运单预警信息主表集合
		List<WaybillWarninginfoMaster> waybillWarninginfoMasters = null;
		// 运单预警信息主表
		WaybillWarninginfoMaster waybillWarninginfoMaster = null;
		//　运单预警信息明细表
		WaybillWarninginfoDetail waybillWarninginfoDetail = null;
		//　预警升级规则集合
		List<WarninglevelList> warninglevelLists = null;
		//　预警升级规则
		WarninglevelList warninglevelList = null;
		// 相差分钟数
		BigDecimal minutes = null;
		//　预警级别
		String level = null;
		// 预警级别升级记录
		WarningLevelupRecord warningLevelupRecord = null;
		for(int i = 0; i < warninginfoMaintainMasters.size(); i++) {
			//　所有预警类型
			warninginfoMaintainMaster = warninginfoMaintainMasters.get(i);
			// 预警状态
			warningRoutestatusList = new WarningRoutestatusList();
			warningRoutestatusList.setWarningtype_code(warninginfoMaintainMaster.getWarningtype_code());
			warningRoutestatusLists = warningRoutestatusListMapper.getRecords(warningRoutestatusList);
			// 当存在预警状态
			if(CommonUtils.checkExistOrNot(warningRoutestatusLists)) {
				for(int j = 0; j < warningRoutestatusLists.size(); j++) {
					warningRoutestatusList = warningRoutestatusLists.get(j);
					routeInfo = new RouteInfo();
					routeInfo.setTransport_code(warningRoutestatusList.getTransport_code());
					routeInfo.setRoutestatus_code(warningRoutestatusList.getRoutestatus_code());
					routeInfo.setEvent_warning_flag("0");
					routeInfos = routeInfoMapper.findRecords(routeInfo);
					// 当存在对应路由信息
					if(CommonUtils.checkExistOrNot(routeInfos)) {
						for(int k = 0; k < routeInfos.size(); k++) {
							routeInfo = routeInfos.get(k);
							waybillWarninginfoMaster = new WaybillWarninginfoMaster();
							waybillWarninginfoMaster.setWarningtype_code(warninginfoMaintainMaster.getWarningtype_code());
							waybillWarninginfoMaster.setWaybill(routeInfo.getWaybill());
							waybillWarninginfoMasters = waybillWarninginfoMasterMapper.findRecords(waybillWarninginfoMaster);
							//　当路由信息中运单已发生过此类预警类型
							if(CommonUtils.checkExistOrNot(waybillWarninginfoMasters)){
								// 存在则主表可能做更新操作子表做插入操作
								waybillWarninginfoMaster = waybillWarninginfoMasters.get(0);
								minutes = new BigDecimal(
										routeInfo.getRoute_time().getTime() - waybillWarninginfoMaster.getWarning_time().getTime()
										).divide(
												new BigDecimal(
														1000 * 60
														), 2);
								//　主表预警级别是否需要升级
								warninglevelLists = warninglevelListMapper.selectRecord(waybillWarninginfoMaster.getWarning_level(), warninginfoMaintainMaster.getWarningtype_code());
								// 规则存在
								if(CommonUtils.checkExistOrNot(warninglevelLists)){
									for(int a = 0; a < warninglevelLists.size(); a++) {
										warninglevelList = warninglevelLists.get(a);
										if(minutes.compareTo(new BigDecimal(warninglevelList.getLevelup_time())) == 1
												&& (Integer.parseInt(waybillWarninginfoMaster.getWarning_level()) < Integer.parseInt(warninglevelList.getLevelup_level()))){
											// 当满足升级条件，且升级等级大于当前等级
											level = warninglevelList.getLevelup_level();
										} else {
											break;
										}
									}
								}
								if(CommonUtils.checkExistOrNot(level)){
									//　当等级不为空
									//　新增升级记录
									warningLevelupRecord = new WarningLevelupRecord();
									warningLevelupRecord.setId(UUID.randomUUID().toString());
									warningLevelupRecord.setCreate_user("SYSTEM");
									warningLevelupRecord.setWaybill(routeInfo.getWaybill());
									warningLevelupRecord.setPid(waybillWarninginfoMaster.getId());
									warningLevelupRecord.setBefore_level(waybillWarninginfoMaster.getWarning_level());
									warningLevelupRecord.setAfter_level(level);
									warningLevelupRecordMapper.insertWarningLevelupRecord(warningLevelupRecord);
									// 更新主表记录升级
									waybillWarninginfoMaster.setWarning_level(level);
									waybillWarninginfoMasterMapper.updateWaybillWarninginfoMaster(waybillWarninginfoMaster);
								}
								// 插入子表数据
								waybillWarninginfoDetail = new WaybillWarninginfoDetail();
								waybillWarninginfoDetail.setId(UUID.randomUUID().toString());
								waybillWarninginfoDetail.setCreate_user("SYSTEM");
								waybillWarninginfoDetail.setHappen_time(routeInfo.getRoute_time());
								waybillWarninginfoDetail.setWarning_level(waybillWarninginfoMaster.getWarning_level());
								waybillWarninginfoDetail.setSource("SYSTEM");
								waybillWarninginfoDetail.setReason("发生"+ warninginfoMaintainMaster.getWarningtype_name()+"类预警！");
								waybillWarninginfoDetail.setPid(waybillWarninginfoMaster.getId());
								waybillWarninginfoDetail.setJkid("0");
								waybillWarninginfoDetailMapper.insertWaybillWarninginfoDetail(waybillWarninginfoDetail);
							} else {
								// 不存在则主表插入子表插入操作
								// 插入主表数据
								waybillWarninginfoMaster = new WaybillWarninginfoMaster();
								waybillWarninginfoMaster.setId(UUID.randomUUID().toString());
								waybillWarninginfoMaster.setCreate_user("SYSTEM");
								waybillWarninginfoMaster.setWarning_time(routeInfo.getRoute_time());
								waybillWarninginfoMaster.setWarningtype_code(warninginfoMaintainMaster.getWarningtype_code());
								waybillWarninginfoMaster.setWarning_level(warninginfoMaintainMaster.getInitial_level());
								waybillWarninginfoMaster.setWarining_status("0");
								waybillWarninginfoMaster.setWaybill(routeInfo.getWaybill());
								waybillWarninginfoMasterMapper.insertWaybillWarninginfoMaster(waybillWarninginfoMaster);
								// 插入子表数据
								waybillWarninginfoDetail = new WaybillWarninginfoDetail();
								waybillWarninginfoDetail.setId(UUID.randomUUID().toString());
								waybillWarninginfoDetail.setCreate_user("SYSTEM");
								waybillWarninginfoDetail.setHappen_time(routeInfo.getRoute_time());
								waybillWarninginfoDetail.setWarning_level(warninginfoMaintainMaster.getInitial_level());
								waybillWarninginfoDetail.setSource("SYSTEM");
								waybillWarninginfoDetail.setReason("发生"+ warninginfoMaintainMaster.getWarningtype_name()+"类预警！");
								waybillWarninginfoDetail.setPid(waybillWarninginfoMaster.getId());
								waybillWarninginfoDetail.setJkid("0");
								waybillWarninginfoDetailMapper.insertWaybillWarninginfoDetail(waybillWarninginfoDetail);
							}
							routeInfo.setEvent_warning_flag("1");
							routeInfoMapper.updateRouteInfo(routeInfo);
						}
					}
				}
			}
			// 不存在则下一个预警类型
		}
	}
	
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return null;
	}
	
	public long getMinutesBetween(Date base,Date d1){
		if(base==null)base=new Date();
		return 	(-d1.getTime()+
				base.getTime())/1000*60;
	}
}

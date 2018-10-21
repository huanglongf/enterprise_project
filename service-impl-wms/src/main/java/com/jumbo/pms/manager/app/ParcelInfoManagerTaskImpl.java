package com.jumbo.pms.manager.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.pms.parcel.BranchLibraryDao;
import com.jumbo.dao.pms.parcel.ParcelInfoDao;
import com.jumbo.dao.pms.parcel.SysInterfaceQueueDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.PMSPickUpBill;
import com.jumbo.pac.manager.extsys.wms.rmi.model.ParcelBill;
import com.jumbo.pac.manager.extsys.wms.rmi.model.PmsBaseResult;
import com.jumbo.pms.model.BranchLibrary;
import com.jumbo.pms.model.SysInterfaceQueue;
import com.jumbo.pms.model.SysInterfaceQueueStatus;
import com.jumbo.pms.model.SysInterfaceQueueSysType;
import com.jumbo.pms.model.SysInterfaceQueueType;
import com.jumbo.pms.model.command.ParcelInfoQueryCommand;
import com.jumbo.pms.model.command.cond.PgPackageCreateCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.wms.manager.BaseManagerImpl;


public class ParcelInfoManagerTaskImpl extends BaseManagerImpl implements ParcelInfoManagerTask {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final Logger log = LoggerFactory.getLogger(ParcelManagerTaskImpl.class);

	@Autowired
	private SysInterfaceQueueDao sysInterfaceQueueDao;
	@Autowired
	private ParcelInfoDao parcelInfoDao;
	@Autowired
	private Rmi4Wms rmi4Wms;
	@Autowired
	private BranchLibraryDao branchLibraryDao;
	@Autowired
	private PackageInfoDao packageInfoDao;
//	@Autowired
//	private PrimPackageManager primPackageManager;

	@Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
	public void parcelInfoChangeStatusNotifyPAC() {
		log.debug("--------------- [{}] parcelInfoChangeStatusNotifyPAC start -------------------",new Object[]{FormatUtil.formatDate(new Date(), "yyyyMMdd HH:mm:ss")});
		/**
		 * 1.获取未推送的包裹
		 * 2.封装数据通知PAC
		 * 3.PAC需要mail & lpCode 查询订单,然后刷订单完成
		 * 4.更新成已推送
		 */
		// 1
		List<SysInterfaceQueue> queueList = getSysInterfaceQueueList(SysInterfaceQueueStatus.STATUS_NEW, SysInterfaceQueueType.PARCEL_CHANGE_STATUS_NOTIFY_PAC, SysInterfaceQueueSysType.PACS);
		// 2
		List<ParcelBill> parcelList = new ArrayList<ParcelBill>();
		ParcelBill parcelBill = null;
		for (SysInterfaceQueue queue : queueList) {
			String code = queue.getCode();
			ParcelInfoQueryCommand parcelInfo = parcelInfoDao.findByCode(code);
			if(parcelInfo != null){
				parcelBill = new ParcelBill();
				parcelBill.setOrderCode(parcelInfo.getOmsOrderCode());
				parcelBill.setMailNo(parcelInfo.getMailNo());
				parcelBill.setLpcode(parcelInfo.getLpcode());
				parcelList.add(parcelBill);
			}
		}
		if(parcelList != null && parcelList.size() > 0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parcelBillList", parcelList);
			map.put("opType", new Integer(5));
			
			try{
				// 3
				List<PmsBaseResult> resultList = rmi4Wms.pmsOperations(map);
				// 4
				if(resultList == null || resultList.size() <= 0){
					return;
				}
				for (PmsBaseResult result : resultList) {
					String mailNo = result.getMailNo();
					String lpcode = result.getLpCode();
					int updateResult = sysInterfaceQueueDao.updateByLpcodeAndMailNo(mailNo, lpcode, result.getMsg(), PmsBaseResult.STATUS_ERROR == result.getStatus() ? SysInterfaceQueueStatus.STATUS_FAILED.getValue() : SysInterfaceQueueStatus.STATUS_SUCCESS.getValue(), null);
					if(updateResult < 0){
                        log.debug("parcelChangeStatusNotifyPAC update error , mail [" + mailNo + "], lpcode[" + lpcode + "]");
					}
				}
			} catch (Exception e){
				log.error("--------------- [{}] parcelChangeStatusNotifyPAC error -------------------",new Object[]{FormatUtil.formatDate(new Date(), "yyyyMMdd HH:mm:ss")});
				return;
			}
		}
		log.info("--------------- [{}] parcelChangeStatusNotifyPAC end -------------------",new Object[]{FormatUtil.formatDate(new Date(), "yyyyMMdd HH:mm:ss")});
	}

    private List<SysInterfaceQueue> getSysInterfaceQueueList(SysInterfaceQueueStatus status, SysInterfaceQueueType type, SysInterfaceQueueSysType targetSys) {
        List<SysInterfaceQueue> queueList = sysInterfaceQueueDao.findByStatusAndTypeWithSysAlsoCode(status, type, targetSys); 
		if(queueList == null || queueList.size() <= 0){
            log.warn("getSysInterfaceQueueList queueList.size<=0");
//            return null;
         }
        return queueList;
    }

    @Override
    public void parcelPickUpNotify() {
        /**
         * 1. 根据物流商获取待上门取件的包裹
         * 2. 封装数据
         * 3. PAC将数据保存至T_MQ_NIKE_DROPBOX_LOG中，定时任务通知SF上门取件
         * 4. 更新成已推送
         */
        // 1
        List<SysInterfaceQueue> queueList = getSysInterfaceQueueList(SysInterfaceQueueStatus.STATUS_NEW, SysInterfaceQueueType.PARCEL_PICK_UP_NOTIFY, SysInterfaceQueueSysType.LP);
        List<PMSPickUpBill> parcelList = new ArrayList<PMSPickUpBill>();
        PMSPickUpBill pmsPickUpBill = null;
        for (SysInterfaceQueue queue : queueList) {
            String code = queue.getCode();
            ParcelInfoQueryCommand parcel = parcelInfoDao.findByCode(code);
            if(parcel != null){
                //寄件方
                BranchLibrary originLibrary = branchLibraryDao.findBySlipCode(parcel.getOriginCode());
                pmsPickUpBill = new PMSPickUpBill();
                pmsPickUpBill.setSlipCode(parcel.getExtTransOrderId());
                pmsPickUpBill.setLpcode(parcel.getLpcode());
                pmsPickUpBill.setMailNo(parcel.getMailNo());
                pmsPickUpBill.setjAddress(originLibrary.getAddress());
                pmsPickUpBill.setjCity(originLibrary.getCity());
                pmsPickUpBill.setjCompany("");
                pmsPickUpBill.setjContact(originLibrary.getContact());
                pmsPickUpBill.setjProvince(originLibrary.getProvince());
                pmsPickUpBill.setjDistrict(originLibrary.getCounty());
                pmsPickUpBill.setjTel(originLibrary.getTelephone());
                pmsPickUpBill.setPayMothod(BranchLibrary.PAYMETHOD_THIRD_PARTY);
                pmsPickUpBill.setO2oShopCode(parcel.getOriginCode());
                pmsPickUpBill.setExpressType("1");//先定死
                pmsPickUpBill.setReturnReason(parcel.getRemark());
                parcelList.add(pmsPickUpBill);
            }
        }
        if(parcelList != null && parcelList.size() > 0){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pickUpBill", parcelList);
            map.put("lpcode", "SF");// TODO SG 代码迁出PMS后改成方法传参
            
            try{
                // 3
                List<PmsBaseResult> resultList = rmi4Wms.pmsNotifySFPickUp(map);
                // 4
                if(resultList == null || resultList.size() <= 0){
                    return;
                }
                for (PmsBaseResult result : resultList) {
                    String mailNo = result.getMailNo();
                    String lpcode = result.getLpCode();
//                    String extTransOrderId = result.getExtTransOrderId();
                    int updateResult = sysInterfaceQueueDao.updateByLpcodeAndMailNo(mailNo, lpcode, result.getMsg(), PmsBaseResult.STATUS_ERROR == result.getStatus() ? SysInterfaceQueueStatus.STATUS_FAILED.getValue() : SysInterfaceQueueStatus.STATUS_SUCCESS.getValue(), null);
                    if(updateResult < 0){
                        log.debug("parcelPickUpNotify update error , mail [" + mailNo + "], lpcode[" + lpcode + "]");
                    }
                }
            } catch (Exception e){
                log.error("--------------- [{}] parcelPickUpNotify error -------------------",new Object[]{FormatUtil.formatDate(new Date(), "yyyyMMdd HH:mm:ss")});
                return;
            }
        }
        log.info("--------------- [{}] parcelPickUpNotify end -------------------",new Object[]{FormatUtil.formatDate(new Date(), "yyyyMMdd HH:mm:ss")});
    }
    
	@Override
	public List<PgPackageCreateCommand> parcelInfoQuery() {
		log.debug("--------------- [{}] parcelInfoQuery start -------------------",new Object[]{FormatUtil.formatDate(new Date(), "yyyyMMdd HH:mm:ss")});
		/**
		 * 1.获取未推送的包裹
		 * 2.封装数据
		 */
		List<SysInterfaceQueue> queueList = getSysInterfaceQueueList(SysInterfaceQueueStatus.STATUS_NEW, SysInterfaceQueueType.PARCEL_DELIVERED_NOTIFY_SHOPDOG, SysInterfaceQueueSysType.G_SHOPDOG);
 		List<PgPackageCreateCommand> parcelInfoQueryCommands = new ArrayList<PgPackageCreateCommand>();
 		for (SysInterfaceQueue queue : queueList) {
 			String code = queue.getCode();
// 			ParcelInfoQueryCommand parcelInfo = parcelInfoDao.findByCode(code);
 	        List<PgPackageCreateCommand> packageInfos = packageInfoDao.findByParams(queue.getLpcode(), queue.getMailNo(), queue.getOmsOrderCode());
 			if( null == packageInfos || packageInfos.size() == 0 ){
 				int updateResult = 1;//sysInterfaceQueueDao.updateByCode(queue.getMailNo(), queue.getLpcode(), queue.getOmsOrderCode());
 				if(updateResult < 0){
 					log.debug("sendParcelToSD update error , code [" + code + "]");
 				}
 				continue;
 			}
 			parcelInfoQueryCommands.addAll(packageInfos);
 		}
 		log.debug("--------------- [{}] parcelInfoQuery end -------------------",new Object[]{FormatUtil.formatDate(new Date(), "yyyyMMdd HH:mm:ss")});
		return parcelInfoQueryCommands;
	}
	
}

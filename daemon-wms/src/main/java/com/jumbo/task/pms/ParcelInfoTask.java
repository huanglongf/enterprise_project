package com.jumbo.task.pms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.baozun.shopdog.primservice.command.pack.PackageKey;
import com.baozun.shopdog.primservice.command.pack.PgPackageCreateCommand;
import com.baozun.shopdog.primservice.constant.ResponseConstants;
import com.baozun.shopdog.primservice.manager.pack.PrimPackageManager;
import com.baozun.shopdog.primservice.response.BaseResult;
import com.baozun.shopdog.primservice.response.pack.PackageCreateResponse;
import com.jumbo.pms.manager.SysInterfaceQueueManager;
import com.jumbo.pms.manager.api.ApiPackageManager;
import com.jumbo.pms.model.SysInterfaceQueueStatus;

public class ParcelInfoTask {
    protected static final Logger log = LoggerFactory.getLogger(ParcelInfoTask.class);
    @Autowired
    private SysInterfaceQueueManager sysInterfaceQueueManager;
    @Autowired
    private PrimPackageManager primPackageManager;
    @Autowired
    private ApiPackageManager apiPackageManager;

    /**
     * 推送o2o订单包裹至SD
     */
    public void sendParcelToSD() {
    	log.error("Daemon sendParcelToSD begin.......");
        List<com.jumbo.pms.model.command.cond.PgPackageCreateCommand> queueList = apiPackageManager.getPackageInfo();
        if(null == queueList || queueList.size() == 0){
            log.error("Daemon sendParcelToSD queueList is null.......");
        	return;
        }
        // 2
 		List<PgPackageCreateCommand> packageCommandList = new ArrayList<PgPackageCreateCommand>();
 		PgPackageCreateCommand pgPackageCommand = null;
 		for (int i = 0; i < queueList.size(); i++) {
 		        pgPackageCommand = new PgPackageCreateCommand();
 		        BeanUtils.copyProperties(queueList.get(i), pgPackageCommand);
	 			Date deliveryTime = (pgPackageCommand.getShipTime());/** 出库时间 */
	            pgPackageCommand.setPlatCreateTime(deliveryTime);
	            pgPackageCommand.setCreateTime(deliveryTime);
	            pgPackageCommand.setRoCode(null);
	            pgPackageCommand.setExtCode(null);// 之前传的是PMS的唯一编码
	            pgPackageCommand.setSourceSys(null);
	 			packageCommandList.add(pgPackageCommand);
         }
 		
 		if(packageCommandList != null && packageCommandList.size() > 0){
 			try{
 				// 3
 				PackageCreateResponse response = primPackageManager.createPgPackages(packageCommandList);
 				// 4
 				if(response == null){
 					return;
 				}
 				Map<PackageKey, BaseResult> result = response.getCodeResult();
 				if(null ==result || result.isEmpty()){
 					return;
 				}
 				for (Entry<PackageKey, BaseResult> entry : result.entrySet()) {
 				    PackageKey key = entry.getKey();
 					BaseResult baseResult = entry.getValue();
 					if(baseResult != null){
 		                sysInterfaceQueueManager.updateByCode(key.getTrackNo(), key.getLpCode(), key.getOmsCode(), baseResult.getMessage(), StringUtils.hasText(baseResult.getErrorCode()) ? "" : baseResult.getErrorCode(), ResponseConstants.RESULT_OK.equals(baseResult.getResult()) ? SysInterfaceQueueStatus.STATUS_SUCCESS.getValue() : SysInterfaceQueueStatus.STATUS_FAILED.getValue());
 					}
 				}
 			} catch (Exception e){
 				return;
 			}
 		}
        log.error("Daemon sendParcelToSD end.......");
    }

}

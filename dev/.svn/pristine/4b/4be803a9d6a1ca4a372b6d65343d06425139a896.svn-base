package com.bt.lmis.summary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import com.bt.EPlatform;
import com.bt.OSinfo;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.core.business.ExcelExport;
import com.bt.lmis.dao.BalanceErrorLogMapper;
import com.bt.lmis.dao.BalanceSummaryUsedExMapper;
import com.bt.lmis.dao.CarrierUsedSummaryMapper;
import com.bt.lmis.dao.ContractBasicinfoMapper;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.service.CarrierUsedBalanceService;
import com.bt.lmis.service.ExpressUsedBalanceService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.DateUtil;
import com.bt.utils.FileUtil;
import com.bt.utils.ZipUtils;

/**
 * @Title:ClientSummary
 * @Description: TODO(客户账单)
 * @author Ian.Huang 
 * @date 2016年12月22日上午10:32:21
 */
@Service
public class ConsumerSummary {
	// 定义日志
	private static final Logger logger = Logger.getLogger(ExpressBalance.class);

	@SuppressWarnings("unchecked")
	private ContractBasicinfoMapper<T> contractBasicinfoMapper = (ContractBasicinfoMapper<T>)SpringUtils.getBean("contractBasicinfoMapper");
	
	/**
	 * 
	 * @Description: TODO(客户账单)
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月22日上午10:44:04
	 */
	@SuppressWarnings("unchecked")
	public void consumerSummary(){
		try {
			// 当前结算日期所在年月
			String ym= DateUtil.getMonth(DateUtil.getYesterDay());
			String[] temp= ym.split("-");
			String year= temp[0];
			String month= temp[1];
			String zip = "";
			// 压缩路径
			if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
				zip= CommonUtils.getAllMessage("config", "BALANCE_BILL_CUSTOMER_Linux");
			}else{
				zip= CommonUtils.getAllMessage("config", "BALANCE_BILL_CUSTOMER_" + OSinfo.getOSname());
			}
			
			// 文件生成路径
			String path= zip+ year+ "年"+ month+ "月客户结算报表";
			if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
				path+= "/";
				
			} else if(OSinfo.getOSname().equals(EPlatform.Windows)) {
				path+= "\\";
				
			}
			// 补全路径
			FileUtil.isExistPath(path);
			// 运费汇总是否存在
			boolean flag_1= ((BalanceSummaryUsedExMapper<T>)SpringUtils.getBean("balanceSummaryUsedExMapper")).judgeSummaryExistOrNot(ym) == 0? true: false;
			// 承运商汇总是否存在
			boolean flag_2= ((CarrierUsedSummaryMapper<T>)SpringUtils.getBean("carrierUsedSummaryMapper")).judgeSummaryExistOrNot(ym) == 0? true: false;
			// 所有客户/店铺合同
			List<ContractBasicinfo> cbList= contractBasicinfoMapper.find_by_cb();
			ContractBasicinfo cb= null;
			for(int i= 0; i< cbList.size(); i++) {
				cb= cbList.get(i);
				// 合同是否有效
				if(DateUtil.isTime(cb.getContract_start(), cb.getContract_end(), DateUtil.getCalendarToString(DateUtil.getYesterDay()))){
					// 合同是否需要汇总
					if(DateUtil.judgeSummaryOrNot(Integer.parseInt(cb.getSettle_date()))){
						// 仓储费汇总
						((StorageChargeSettlement)SpringUtils.getBean("storageChargeSettlement")).reckonStorageGroup(cb, ym);
						// 操作费汇总
						((OperatingCost)SpringUtils.getBean("operatingCost")).summary(cb, ym);
						// 耗材费汇总
						((ConsumableCost)SpringUtils.getBean("consumableCost")).summary(cb, ym);
						// 增值服务费汇总
						((ValueAddedBalance)SpringUtils.getBean("valueAddedBalance")).summary(cb, ym);
						// 打包费汇总
						((PackingChargeSettlement)SpringUtils.getBean("packingChargeSettlement")).packingChargeSummary(cb, ym);
						// 使用快递运费汇总
						if(flag_1) {
							((ExpressUsedBalanceService)SpringUtils.getBean("expressUsedBalanceServiceImpl")).expressUsedBalanceSummary(cb, ym);
							
						}
						// 总运费折扣&管理费
						if(flag_2) {
							((CarrierUsedBalanceService)SpringUtils.getBean("carrierUsedBalanceServiceImpl")).carrierUsedSummary(cb, ym);
							
						}
						// 输出EXCEL账单
						((ExcelExport)SpringUtils.getBean("excelExport")).exportSettlementForm(cb, path, year, month);
						
					}
					
				} else {
					// 合同失效错误日志新增
					BalanceErrorLog bEL = new BalanceErrorLog();
					bEL.setContract_id(Integer.valueOf(cbList.get(i).getId()));
					bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
					bEL.setPro_result_info(cbList.get(i).getId() + "合同已过期！");
					((BalanceErrorLogMapper<T>)SpringUtils.getBean("balanceErrorLogMapper")).addBalanceErrorLog(bEL);
					// 判断时间不在该生效合同周期内，合同修改为失效
					Map<String, Object> map= new HashMap<String, Object>();
					map.put("validity", 0);
					map.put("update_user", BaseConst.SYSTEM_JOB_CREATE);
					map.put("id", cbList.get(i).getId());
					contractBasicinfoMapper.update_cb_validity(map);
					
				}
				
			}
			// 压缩打包文件
			ZipUtils.zip(path, zip);
			// 将文件复制到下载路径
			if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
				FileUtil.copyFile(zip + year+ "年"+ month+ "月客户结算报表.zip", CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_Linux") + year+ "年"+ month+ "月客户结算报表.zip", true);
			}else{
				FileUtil.copyFile(zip + year+ "年"+ month+ "月客户结算报表.zip", CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname()) + year+ "年"+ month+ "月客户结算报表.zip", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		
	}
	
}
package com.bt.lmis.balance.service.impl;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.OSinfo;
import com.bt.lmis.balance.common.Constant;
import com.bt.lmis.balance.dao.ConsumerRevenueEstimateMapper;
import com.bt.lmis.balance.dao.EstimateMapper;
import com.bt.lmis.balance.estimate.CarrierFeeEstimate;
import com.bt.lmis.balance.estimate.ConsumableCostEstimate;
import com.bt.lmis.balance.estimate.ExpressFreightEstimate;
import com.bt.lmis.balance.estimate.OperatingCostEstimate;
import com.bt.lmis.balance.estimate.PackageFeeEstimate;
import com.bt.lmis.balance.estimate.StorageFeeEstimate;
import com.bt.lmis.balance.estimate.ValueAddedFeeEstimate;
import com.bt.lmis.balance.model.BatchEstimate;
import com.bt.lmis.balance.model.Contract;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.EstimateResult;
import com.bt.lmis.balance.service.EstimateService;
import com.bt.lmis.balance.util.EstimateUtil;
import com.bt.utils.CommonUtils;
import com.bt.utils.FileUtil;
import com.bt.utils.ZipUtils;

/** 
 * @ClassName: ConsumerRevenueEstimateServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月16日 下午3:01:28 
 * 
 */
@Service("consumerRevenueEstimateServiceImpl")
public class ConsumerRevenueEstimateServiceImpl implements EstimateService {

	@Autowired
	private EstimateMapper<T> estimateMapper;
	@Autowired
	private ConsumerRevenueEstimateMapper<T> consumerRevenueEstimateMapper;
	
	@Resource(name="expressFreightEstimate")
	private ExpressFreightEstimate expressFreightEstimate;
	@Resource(name="carrierFeeEstimate")
	private CarrierFeeEstimate carrierFeeEstimate;
	@Autowired
	private StorageFeeEstimate storageFeeEstimate;
	@Autowired
	private OperatingCostEstimate operatingCostEstimate;
	@Autowired
	private ConsumableCostEstimate consumableCostEstimate;
	@Autowired
	private ValueAddedFeeEstimate valueAddedFeeEstimate;
	@Autowired
	private PackageFeeEstimate packageFeeEstimate;
	
	@Override
	public void cleanEstimate(BatchEstimate batchEstimate) {
		consumerRevenueEstimateMapper.cleanEstimate("bal_estimate_express_freight",batchEstimate.getBatchNumber());
		consumerRevenueEstimateMapper.cleanEstimate("bal_estimate_carrier_fee",batchEstimate.getBatchNumber());
		consumerRevenueEstimateMapper.cleanEstimate("bal_invitation_useanmount_data_groupdetail_estimate",batchEstimate.getBatchNumber());
		consumerRevenueEstimateMapper.cleanEstimate("bal_invitationdata_estimate",batchEstimate.getBatchNumber());
		consumerRevenueEstimateMapper.cleanEstimate("bal_operationfee_data_daily_settlement_estimate",batchEstimate.getBatchNumber());
		consumerRevenueEstimateMapper.cleanEstimate("bal_operationfee_data_estimate",batchEstimate.getBatchNumber());
		consumerRevenueEstimateMapper.cleanEstimate("bal_storage_data_estimate",batchEstimate.getBatchNumber());
		consumerRevenueEstimateMapper.cleanEstimate("bal_storage_expenditures_data_settlement_estimate",batchEstimate.getBatchNumber());
		consumerRevenueEstimateMapper.cleanEstimate("bal_warehouse_express_data_store_settlement_estimate",batchEstimate.getBatchNumber());
		consumerRevenueEstimateMapper.cleanEstimate("bal_package_charage_estimate",batchEstimate.getBatchNumber());
		consumerRevenueEstimateMapper.cleanEstimate("bal_addservicefee_billdata_estimate",batchEstimate.getBatchNumber());
		consumerRevenueEstimateMapper.cleanEstimate("bal_addservicefee_settlement_estimate",batchEstimate.getBatchNumber());
		
	}
	
	@Override
	public EstimateResult batchEstimate(BatchEstimate batchEstimate) throws Exception {
		// 清空预估可能存在的脏数据
		cleanEstimate(batchEstimate);
		// 更新预估批次状态
		estimateMapper.ingEstimate(batchEstimate.getBatchNumber());
		//
		EstimateResult result= new EstimateResult(true);
		for(int i=0; i<batchEstimate.getContractId().size(); i++ ) {
			Contract contract=consumerRevenueEstimateMapper.ensureContractById(batchEstimate.getContractId().get(i));
			EstimateParam param=new EstimateParam(batchEstimate.getBatchNumber(), batchEstimate.getDomainFrom(), batchEstimate.getDomainTo(), contract);
			// 运费
			param.getParam().put("logTitle", "合同["+contract.getContractName()+"]"+Constant.EXPRESS_FREIGHT+"费用预估");
			expressFreightEstimate.estimate(param);
			// 总运费折扣及管理费
			param.getParam().put("logTitle", "合同["+contract.getContractName()+"]"+Constant.CARRIER_FEE+"费用预估");
			carrierFeeEstimate.estimate(param);
			//仓储费
			param.getParam().put("logTitle", "合同["+contract.getContractName()+"]"+Constant.STORAGE_FEE+"费用预估");
			storageFeeEstimate.estimate(param);
			//操作费
			param.getParam().put("logTitle", "合同["+contract.getContractName()+"]"+Constant.LABOUR_FEE_OR_HANDLING_CHARGE+"费用预估");
			operatingCostEstimate.estimate(param);
			//耗材费
			param.getParam().put("logTitle", "合同["+contract.getContractName()+"]"+Constant.CONSUMABLE_FEE+"费用预估");
			consumableCostEstimate.estimate(param);
			//增值服务费
			param.getParam().put("logTitle", "合同["+contract.getContractName()+"]"+Constant.VAS_FEE+"费用预估");
			valueAddedFeeEstimate.estimate(param);
			//打包费
			param.getParam().put("logTitle", "合同["+contract.getContractName()+"]"+Constant.BUYOUT_FEE+"费用预估");
			packageFeeEstimate.estimate(param);
			// 生成预估报表
			EstimateUtil.generateConsumerRevenueEstimate(param);
			
		}
		// 打包账单
		String zip=CommonUtils.getAllMessage("config", "BALANCE_ESTIMATE_CUSTOMER_"+OSinfo.getOSname());
		ZipUtils.zip(zip+batchEstimate.getBatchNumber(),zip);
		// 复制至下载路径
		FileUtil.copyFile(zip+batchEstimate.getBatchNumber()+Constant.SYM_POINT+Constant.FILE_TYPE_SUFFIX_ZIP, CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname())+batchEstimate.getBatchNumber()+Constant.SYM_POINT+Constant.FILE_TYPE_SUFFIX_ZIP, true);
		// 清空预估数据
		cleanEstimate(batchEstimate);
		return result;
		
	}

}
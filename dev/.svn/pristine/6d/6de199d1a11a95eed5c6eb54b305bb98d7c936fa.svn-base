package com.bt.lmis.balance.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.OSinfo;
import com.bt.lmis.balance.common.Constant;
import com.bt.lmis.balance.dao.EstimateMapper;
import com.bt.lmis.balance.dao.ExpressExpenditureEstimateMapper;
import com.bt.lmis.balance.estimate.ExpressExpenditureEstimate;
import com.bt.lmis.balance.model.BatchEstimate;
import com.bt.lmis.balance.model.Contract;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.EstimateResult;
import com.bt.lmis.balance.service.EstimateService;
import com.bt.utils.CommonUtils;
import com.bt.utils.FileUtil;
import com.bt.utils.ZipUtils;

/** 
 * @ClassName: ExpressExpenditureEstimateServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月15日 下午7:15:18 
 * 
 */
@Service("expressExpenditureEstimateServiceImpl")
public class ExpressExpenditureEstimateServiceImpl implements EstimateService {

	
	@Autowired
	private EstimateMapper<T> estimateMapper;
	@Autowired
	private ExpressExpenditureEstimateMapper<T> expressExpenditureEstimateMapper;
	
	@Resource(name="expressExpenditureEstimate")
	private ExpressExpenditureEstimate expressExpenditureEstimate;
	
	@Override
	public void cleanEstimate(BatchEstimate batchEstimate) {
		expressExpenditureEstimateMapper.cleanEstimate(batchEstimate.getBatchNumber());
		
	}
	
	@Override
	public EstimateResult batchEstimate(BatchEstimate batchEstimate) throws Exception {
		// 清空预估可能存在的脏数据
		cleanEstimate(batchEstimate);
		// 更新预估批次状态
		estimateMapper.ingEstimate(batchEstimate.getBatchNumber());
		//
		EstimateResult result= new EstimateResult(true);
		String info="";
		for(int i=0; i<batchEstimate.getContractId().size(); i++ ) {
			Contract contract=expressExpenditureEstimateMapper.ensureContractById(batchEstimate.getContractId().get(i));
			EstimateParam param=new EstimateParam(batchEstimate.getBatchNumber(), batchEstimate.getDomainFrom(), batchEstimate.getDomainTo(), contract);
			param.getParam().put("logTitle", "合同["+contract.getContractName()+"]"+Constant.EXPRESS_EXPENDITURE+"费用预估");
			EstimateResult estimateResult=expressExpenditureEstimate.estimate(param);
			if(estimateResult.isFlag()) {
				result.setFlag(false);
				info+=contract.getContractName()+"费用预估成功";
				
			} else {
				info+=contract.getContractName()+"费用预估失败，失败原因："+estimateResult.getMsg().get("errorInfo");
				
			}
			info+="；\n";
			
		}
		// 压缩打包文件
		String zip=CommonUtils.getAllMessage("config", "BALANCE_ESTIMATE_EXPRESS_"+OSinfo.getOSname());
		ZipUtils.zip(zip+batchEstimate.getBatchNumber(),zip);
		// 将文件复制到下载路径
		FileUtil.copyFile(zip+batchEstimate.getBatchNumber()+Constant.SYM_POINT+Constant.FILE_TYPE_SUFFIX_ZIP, CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname())+batchEstimate.getBatchNumber()+Constant.SYM_POINT+Constant.FILE_TYPE_SUFFIX_ZIP, true);
		// 返回信息
		Map<String, Object> msg= new HashMap<String, Object>();
		msg.put("info", info);
		result.setMsg(msg);
		return result;
		
	}

}
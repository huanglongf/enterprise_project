package com.bt.lmis.summary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.Client;
import com.bt.lmis.model.Store;
import com.bt.lmis.service.ClientService;
import com.bt.lmis.service.ExpressContractService;
import com.bt.lmis.service.StoreService;
import com.bt.utils.BaseConst;

/** 
* @ClassName: BaseSettlement 
* @Description: TODO(结算) 
* @author Yuriy.Jiang
* @date 2016年10月5日 下午3:28:04 
*  
*/
@SuppressWarnings("unchecked")
public class BaseSettlement {

	//店铺服务类
	StoreService<Store> storeService = (StoreService<Store>)SpringUtils.getBean("storeServiceImpl");
	//快递合同服务类 ［调用错误日志插入服务］
	ExpressContractService<T> expressContractService = (ExpressContractService<T>)SpringUtils.getBean("expressContractServiceImpl");
	//客户服务类
	ClientService<Client> clientService = (ClientService<Client>)SpringUtils.getBean("clientServiceImpl");
	
	public void getStoreList(String cbid,String contract_owner,String contract_type,List<Store> list){
		List<Store> storeList = new ArrayList<Store>();
		try {
			//主体
			if(contract_type.equals("3")){
				Store store = storeService.findByContractOwner(contract_owner);
				if(null!=store){
					storeList.add(store);
				}else{
					BalanceErrorLog bEL = new BalanceErrorLog();
					bEL.setContract_id(Integer.valueOf(cbid));
					bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
					bEL.setPro_result_info("合同ID["+cbid+"],店铺不存在！");
					expressContractService.addBalanceErrorLog(bEL);
				}
			}else if(contract_type.equals("4")){
				Client client = clientService.findByContractOwner(contract_owner);
				List<Store> sList = storeService.selectByClient(client.getId());
				if(null!=sList && sList.size()!=0){
					for (int j = 0; j < sList.size(); j++) {
						storeList.add(sList.get(j));
					}
				}else{
					expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbid), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cbid+"],"+client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
				}
			}
			for (int i = 0; i < storeList.size(); i++) {
				list.add(storeList.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}

package com.bt.lmis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.DiffBilldeatilsQueryParam;
import com.bt.lmis.controller.form.ExpressbillMasterQueryParam;
import com.bt.lmis.dao.DiffBilldeatilsMapper;
import com.bt.lmis.dao.ExpressContractMapper;
import com.bt.lmis.dao.ExpressbillMasterMapper;
import com.bt.lmis.model.DiffBilldeatils;
import com.bt.lmis.model.ExpressbillMaster;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ExpressbillMasterService;

@Service
public class ExpressbillMasterServiceImpl<T> extends ServiceSupport<T> implements ExpressbillMasterService<T> {

	@Autowired
	private ExpressContractMapper<T> expressContractMapper;
	
	ExpressbillMasterMapper  expressbillMasterMapper=
	(ExpressbillMasterMapper<T>)SpringUtils.getBean("expressbillMasterMapper");
	
	
	DiffBilldeatilsMapper  diffBilldeatilsMapper=
	(DiffBilldeatilsMapper<T>)SpringUtils.getBean("diffBilldeatilsMapper");
	
	@Autowired
    private ExpressbillMasterMapper<T> mapper;

	public ExpressbillMasterMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public QueryResult<ExpressbillMaster> selectExpressBill(ExpressbillMasterQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<ExpressbillMaster> qr=new QueryResult<ExpressbillMaster>();
		qr.setResultlist(mapper.pageQuery(queryParam));
		qr.setTotalrecord(mapper.CountMaster(queryParam));
		return qr;
	}

	@Override
	public void createExpressBill(ExpressbillMaster queryParam) {
		// TODO Auto-generated method stub
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("contractOwner", queryParam.getExpress_code());
		List<Map<String,Object>> list=expressContractMapper.findValidContract(param);
		queryParam.setCon_id(Integer.parseInt(list.get(0).get("id").toString()));
		queryParam.setStatus("1");
		mapper.createExpressBill(queryParam);
		
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		mapper.deleteById(id);
	}

	@Override
	public void backups() {
		// TODO Auto-generated method stub
		ExpressbillMasterQueryParam eqp=new ExpressbillMasterQueryParam();
		eqp.setBf_flag("0"); //初始值 ，未进行过备份标识作业操作
		eqp.setStatus("0");  //0 关账 1 为关账 2 处理中
		
		//获取所有符合条件账单
		List<ExpressbillMaster> listBills=	expressbillMasterMapper.findAll(eqp);
		
		for(ExpressbillMaster ebm:listBills){
			//遍历账单进行操作
			try{
			backupsProcess(ebm,expressbillMasterMapper);
			}catch(Exception e){
				ebm.setBf_flag("2");
				ebm.setBf_reason(e.getMessage());
				
			}
		}
	}

	private void backupsProcess(ExpressbillMaster ebm, ExpressbillMasterMapper expressbillMasterMapper2) {
		// TODO Auto-generated method stub
		if(expressbillMasterMapper2==null)
			expressbillMasterMapper2=(ExpressbillMasterMapper<T>)SpringUtils.getBean("expressbillMasterMapper");
		List<DiffBilldeatils> listdetail=new ArrayList<DiffBilldeatils>();
		DiffBilldeatilsQueryParam queryParam=new DiffBilldeatilsQueryParam();
		queryParam.setMaster_id(ebm.getId().toString());
		queryParam.setBf_flag("0");
		queryParam.setIs_verification("1");
		queryParam.setFirstResult(0);
		queryParam.setMaxResult(5000);
		 while(true){
			 listdetail= diffBilldeatilsMapper.selectMasterId(queryParam);
			 if(listdetail.size()==0)break;
			  for(DiffBilldeatils detail:listdetail){
				  diffBilldeatilsMapper.bf_settle(detail);
			  }
			 
		 }
		 queryParam=new DiffBilldeatilsQueryParam();
		 //退货入库数据
		 queryParam.setMaster_id(ebm.getId().toString());
		 queryParam.setFirstResult(0);
		 queryParam.setMaxResult(5000);
		 queryParam.setDetail_status("2"); //1 结算过的 2退货入库的3 其他
		 queryParam.setBf_flag("0");
		 while(true){
			 listdetail= diffBilldeatilsMapper.selectMasterId(queryParam); 
			 if(listdetail.size()==0)break;
			 for(DiffBilldeatils detail:listdetail){
				 diffBilldeatilsMapper.bf_return(detail);
			  }
			 
			 
		 }
		 
		
	}
		
	
	
	
}

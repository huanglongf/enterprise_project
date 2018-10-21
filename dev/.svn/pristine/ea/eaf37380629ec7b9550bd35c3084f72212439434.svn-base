package com.bt.lmis.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.service.BalanceService;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.ExpressbillMasterhxQueryParam;
import com.bt.lmis.dao.DiffBilldeatilsMapper;
import com.bt.lmis.dao.ExpressbillMasterhxMapper;
import com.bt.lmis.model.DiffBilldeatils;
import com.bt.lmis.model.ExpressbillMasterhx;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.DiffBilldeatilsService;
import com.bt.lmis.service.ExpressbillMasterhxService;
@Service
public class ExpressbillMasterhxServiceImpl<T> extends ServiceSupport<T> implements ExpressbillMasterhxService<T> {

	@Autowired
    private ExpressbillMasterhxMapper<T> mapper;

	@Autowired
    private DiffBilldeatilsMapper<T> diffBilldeatilsMapper;
	
	
	@Resource(name = "balanceServiceImpl")
	private BalanceService balanceService;
	public ExpressbillMasterhxMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public void updateById(String id, String file_name) {
		// TODO Auto-generated method stub
		mapper.updateById(id, file_name);
	}

	@Override
	public QueryResult<ExpressbillMasterhx> findAll(ExpressbillMasterhxQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<ExpressbillMasterhx> qr=new QueryResult<ExpressbillMasterhx>();
		qr.setResultlist(mapper.findAll(queryParam));
		qr.setTotalrecord(mapper.countNum(queryParam));
		return qr;
	}

	@Override
	public void deleteClose(Map<String, Object> param) throws NumberFormatException, Exception {
		// TODO Auto-generated method stub
		List<String> ids=(List<String>) param.get("ids");
		for(String id:ids){
			mapper.deleteID(id);	
			diffBilldeatilsMapper.deleteaccount(id,param.get("user").toString(),"1");
		}
		
	
		
		
	}

	@Override
	public void saveClose(Map<String, String> param)throws NumberFormatException, Exception{
		// TODO Auto-generated method stub
		
		    Map<String,Object> dismap= balanceService.getDiscount(param);
		    String str = (String) dismap.get("ALL");
		   if(str!=null){
				diffBilldeatilsMapper.updateDiffBilldeatils("",param.get("master_id").toString(),str);
			}else{
				for(Entry<String, Object> vo : dismap.entrySet()){ 
		            String procude_code = vo.getKey(); 
		            BigDecimal discount1 = (BigDecimal) vo.getValue(); 
		            diffBilldeatilsMapper.updateDiffBilldeatilsSF("",param.get("master_id").toString(),procude_code,discount1);
		        }
			}
		    
			//diffBilldeatilsMapper.upaccount(param.get("master_id"),param.get("user"),dismap.get("ALL").toString(),"1");
	}

	@Override
	public ExpressbillMasterhx selectByExpressbillMasterhxId(String id) {
		// TODO Auto-generated method stub
		return mapper.selectByExpressbillMasterhxId(id);
	}
		
	
}

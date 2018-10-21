package com.bt.lmis.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.TransOrderMapper;
import com.bt.lmis.dao.TransportVendorMapper;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.TransOrderService;
import com.bt.utils.IntervalValidationUtil;

/**
 * 
* @ClassName: TransOrderServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author likun
* @date 2016年6月20日 下午4:44:31 
* 
* @param <T>
 */
@Service
public class TransOrderServiceImpl<T> extends ServiceSupport<T> implements TransOrderService<T> {
	
	@Autowired
    private TransOrderMapper<T> mapper;
	
	@Autowired
    private TransportVendorMapper<T> mappers;

	
	
	public TransOrderMapper<T> getMapper() {
		return mapper;
	}

    
	public TransportVendorMapper<T> getMappers() {
		return mappers;
	}


	@Override
	public Integer saveMainData(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.saveMianData(param);
	}


	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void saveDetailData(Map<String, Object> param) throws RuntimeException{
		// TODO Auto-generated method stub
		    //检验收费项主信息是否存在 1:存在,否则不存在
			Integer result=mapper.checkContract(param); 
			//校验计费方式
			Integer count=mapper.checkData_td_formula(param);
			if(result==1){
		     mapper.updateCarriagelData(param);
			}else{
			 mapper.saveCarriageData(param);
			}

			//更新或者添加
			if(param.get("op_type")!=null && param.get("op_type").equals("1")){
				//param.get("id").equals(count.toString())
				if(count==null  ){
					mapper.updateDetailData(param);
				}
				else if(param.get("id").equals(count.toString())){
					mapper.updateDetailData(param);
				}
				else{
					throw new RuntimeException("计费方式已存在,无法更新该记录!");
				}
				 
			}else{
				if(count==null){
					 mapper.saveDetailData(param);
				}else{
					throw new RuntimeException("计费方式已存在,无法重复添加!");
				}
			   
			}
			
	}


	@Override
	public Integer saveCarriageData(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void saveDiscount(Map<String, Object> param) throws RuntimeException{
		// TODO Auto-generated method stub
		if(param.get("section")!=null){
			ArrayList result_section=mapper.getSection(param);
			for(int i=0;i<result_section.size();i++){
				
				if(((String)param.get("section")).equals((String)((HashMap)result_section.get(i)).get("section"))){
					throw new RuntimeException("区间价格已经存在,无法重复添加");
				}
				IntervalValidationUtil.isExist((String)((HashMap)result_section.get(i)).get("section"),(String)param.get("section"));
			}
		}
		
		if(param.get("discount")!=null){
			int count=mapper.getdiscount(param);
			if(count>1){
				throw new RuntimeException("区间价格已经存在,无法重复添加");
			}
		}
		
		
		
		if(param.get("cat_id")!=null && param.get("cat_id").equals("1")){
			Integer result=mapper.checkContract(param);
			if(result==1){
		    mapper.updateCarriagelData(param);
			}else{
			mapper.saveCarriageData(param);
			 }
		}else{
			Integer result=mapper.checkContractByOffer(param);
			if(result==1){
			mapper.updateOfferData(param);
			}else{
			mapper.saveOfferData(param);
			}
	 }
		Integer count=mapper.checkData_td_discount(param);
		if(count==null || param.get("interva_type_id").equals(count.toString())){
			mapper.saveDiscount(param);
		 }else{
		throw new RuntimeException("该产品类型的阶梯类型已存在,无法添加!");
		 }
		
	}


	@Override
	public ArrayList<T> getFormulaTd(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getFormulaTd(param);
	}


	@Override
	public ArrayList<T> getDiscountTd(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getDiscountTd(param);
	}


	@Override
	public void delFormulaTd(Map<String, Object> param) {
		// TODO Auto-generated method stub
		mapper.delFormulaTd(param);
	}

	@Override
	public void delDiscountTd(Map<String, Object> param) {
		// TODO Auto-generated method stub
		mapper.delDiscountTd(param);
	}


	@Override
	public Map saveSpecial(Map<String, Object> param) {
		// TODO Auto-generated method stub
		//校验收费项唯一
		Integer result=mapper.checkSpecialCat(param);
		if(result==null){
			//新增
			 mapper.saveSpecial(param);
		}
		
		else{
			//修改
			 mapper.updateSpecial(param);
		}
		
		return param;
	}


	@Override
	public void saveManager(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Integer result=mapper.checkManagerCat(param);
		if(result==null){
			//新增
			 mapper.saveManager(param);
		}
		else{
			 mapper.updateManager(param);
		}
		
	}


	@Override
	public ArrayList<T> getGoodType(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getGoodType(param)==null?new ArrayList<T>():mapper.getGoodType(param);
	}


	
	





//	public static void main(String[]args){
//		System.out.println("==========="+isExist("(21,25]","[20,21]"));
//	}


	@Override
	public List<Map<String, Object>> getExpress() {
		// TODO Auto-generated method stub
		return mappers.findExpress();
	}


	@Override
	public ArrayList<T> getExpressTab(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getExpressTab(param)==null?new ArrayList<T>():mapper.getExpressTab(param);
	}


	@Override
	public void addExpress(Map<String, Object> param) throws RuntimeException {
		// TODO Auto-generated method stub
		Integer result=mapper.checkexpress(param);
		if(result!=null && result>=1){
			throw new RuntimeException("数据已存在，不可重复添加");
		}
		mapper.addExpress(param);
	}


	@Override
	public void delExpressTab(Map<String, Object> param) {
		// TODO Auto-generated method stub
		mapper.delExpressTab(param);
	}


	@Override
	public void updateCarriagelData(Map<String, Object> param) {
		// TODO Auto-generated method stub
		mapper.updateCarriagelData(param);
	}


	@Override
	public HashMap getCarriageInfo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getCarriageInfo(param)==null?new HashMap():mapper.getCarriageInfo(param);
	}


	@Override
	public HashMap getSpecial(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getSpecial(param)==null?new HashMap():mapper.getSpecial(param);
	}


	@Override
	public HashMap<String,String>init_bj_data(Map<String, Object> param) {
		// TODO Auto-generated method stub
	
		if(param.get("type").equals("1")){
		 return mapper.init_bj_data(param)==null?new HashMap():mapper.init_bj_data(param);
		}
		if(param.get("type").equals("2")){
			mapper.updateOfferData(param);
			HashMap<String,String> result=new HashMap<String,String>();
			result.put("result_reason","成功");
			return	result;
		}
		return null;
	}


	@Override
	public HashMap initData_glf(Map<String, Object> param) {
		// TODO Auto-generated method stub
		if(param.get("type").equals("1")){
			 return mapper.initData_glf(param)==null?new HashMap():mapper.initData_glf(param);
			}
			if(param.get("type").equals("2")){
				mapper.updateManager(param);
				HashMap<String,String> result=new HashMap<String,String>();
				result.put("result_reason","成功");
				return	result;
			}
			return null;
	}


	@Override
	public void savegoods(Map<String, Object> param) {
		// TODO Auto-generated method stub
		mapper.savegoods(param);
	}


	@Override
	public ArrayList<T> initgoods(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.initgoods(param);
	}


	@Override
	public void delete_gs_tab(Map<String, Object> param) {
		// TODO Auto-generated method stub
		mapper.delete_gs_tab(param);
	}


	@Override
	public QueryResult<T> findAllData(QueryParameter queryParameter) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findAllData(queryParameter));
		queryResult.setTotalrecord(mapper.selectCount(queryParameter));
		return queryResult;
	}


	@Override
	public void saveWk(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Long count=mapper.checkData_td_wk(param);
		if(param.get("op_type").equals("update")){
			mapper.updateWk(param);
		}else{
		 if(count!=0){
		   throw new RuntimeException("计重方式已经存在，不可重复添加");
		  }
			mapper.saveWk(param);
		}
	}


	@Override
	public ArrayList<T> getWkData(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getWkData(param);
	}


	@Override
	public void del_wk(Map<String, Object> param) {
		// TODO Auto-generated method stub
		mapper.del_wk(param);
	}


}

package com.bt.lmis.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.page.QueryParameter;

/** 
* @ClassName: EmployeeMapper 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月23日 上午10:50:11 
* 
* @param <T> 
*/
public interface TransOrderMapper<T> extends BaseMapper<T>{
	public Integer saveMianData(Map<String, Object> param)throws RuntimeException;
	public void saveDetailData(Map<String, Object> param)throws RuntimeException;
	public void saveCarriageData(Map<String, Object> param)throws RuntimeException;
	public void updateCarriagelData(Map<String, Object> param)throws RuntimeException;
	public Integer checkContract(Map<String, Object> param)throws RuntimeException;
	public Integer checkContractByOffer(Map<String, Object> param)throws RuntimeException;
	public void saveDiscount(Map<String, Object> param)throws RuntimeException;
	public ArrayList<T>getFormulaTd(Map<String, Object> param)throws RuntimeException;
	public ArrayList<T>getDiscountTd(Map<String, Object> param)throws RuntimeException;
	public void delFormulaTd(Map<String, Object> param)throws RuntimeException;
	public void delDiscountTd(Map<String, Object> param)throws RuntimeException;
	public Integer checkData_td_formula(Map<String, Object> param)throws RuntimeException;
	public Integer checkData_td_discount(Map<String, Object> param)throws RuntimeException;
	public void updateOfferData(Map<String, Object> param)throws RuntimeException;
	public void saveOfferData(Map<String, Object> param)throws RuntimeException;
	public void updateDetailData(Map<String, Object> param)throws RuntimeException;
	public void saveSpecial(Map<String, Object> param)throws RuntimeException;
	public void updateSpecial(Map<String, Object> param)throws RuntimeException;
	public Integer checkSpecialCat(Map<String, Object> param)throws RuntimeException;
	public void saveManager(Map<String, Object> param)throws RuntimeException;
	public void updateManager(Map<String, Object> param)throws RuntimeException;
	public Integer checkManagerCat(Map<String, Object> param)throws RuntimeException;
	public ArrayList<T>getGoodType(Map<String, Object> param);
	public ArrayList<T>getSection(Map<String, Object> param);
	public ArrayList<T>getExpressTab(Map<String, Object> param);
	public void addExpress(Map<String, Object> param);
	public void delExpressTab(Map<String, Object> param);
	public Integer checkexpress(Map<String, Object> param)throws RuntimeException;
	public HashMap<String,Object>getCarriageInfo(Map<String, Object> param);
	public HashMap<String,Object>getSpecial(Map<String, Object> param);
	public HashMap<String,Object> init_bj_data(Map<String, Object> param);
	public HashMap<String,Object> initData_glf(Map<String, Object> param);
	public void savegoods(Map<String, Object> param)throws RuntimeException;
	public ArrayList<T>initgoods(Map<String,Object>param);
	public void delete_gs_tab(Map<String, Object> param);
	public List<T> findAllData(QueryParameter queryParameter);
	public Integer selectCount(QueryParameter queryParameter);
	public Integer getdiscount(Map<String, Object> param);
	public void saveWk(Map<String, Object> param)throws RuntimeException;
	public ArrayList<T>getWkData(Map<String, Object> param)throws RuntimeException;
	public void del_wk(Map<String, Object> param);
	public void updateWk(Map<String, Object> param)throws RuntimeException;
	public Long checkData_td_wk(Map<String,Object>param);
}

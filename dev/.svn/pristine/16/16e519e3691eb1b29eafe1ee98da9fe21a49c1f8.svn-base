package com.bt.lmis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;

/**
* @ClassName: TransOrderService 
* @Description: TODO
* @author likun
* @date 2016年6月20日 下午4:44:06 
* 
* @param <T>
 */
public interface TransOrderService<T> extends BaseService<T>{
	public Integer saveMainData(Map<String, Object> param);
	public void saveDetailData(Map<String, Object> param) throws RuntimeException;
	public Integer saveCarriageData(Map<String, Object> param);
	public void saveDiscount(Map<String, Object> param);
	public ArrayList<T>getFormulaTd(Map<String, Object> param);
	public ArrayList<T>getDiscountTd(Map<String, Object> param);
	public void delFormulaTd(Map<String, Object> param);
	public void delDiscountTd(Map<String, Object> param);
	public Map saveSpecial(Map<String, Object> param);
	public void saveManager(Map<String, Object> param);
	public ArrayList<T>getGoodType(Map<String, Object> param);
	public List<Map<String, Object>>getExpress();
	public ArrayList<T>getExpressTab(Map<String, Object> param);
	public void addExpress(Map<String, Object> param);
	public void delExpressTab(Map<String, Object> param);
	public void updateCarriagelData(Map<String, Object> param);
	public HashMap getCarriageInfo(Map<String, Object> param);
	public HashMap getSpecial(Map<String, Object> param);
	public HashMap init_bj_data(Map<String, Object> param);
	public HashMap initData_glf(Map<String, Object> param);
	public void savegoods(Map<String, Object> param);
	public void saveWk(Map<String, Object> param);
	public ArrayList<T>initgoods(Map<String, Object> param);
	public void delete_gs_tab(Map<String, Object> param);
	public QueryResult<T> findAllData(QueryParameter qr);
	public ArrayList<T>getWkData(Map<String, Object> param);
	public void del_wk(Map<String, Object> param);
}

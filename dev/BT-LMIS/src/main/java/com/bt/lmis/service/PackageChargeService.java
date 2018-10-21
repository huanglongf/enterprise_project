package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.PackagePrice;

/**
 * @Title:PackageChargeService
 * @Description: TODO(打包费Service)
 * @author Ian.Huang 
 * @date 2016年7月4日下午4:21:48
 */
@Service
public interface PackageChargeService<T> extends BaseService<T> {
	
	/**
	 * 
	 * @Description: TODO(获取打包价)
	 * @param con_id
	 * @return
	 * @return: PackagePrice  
	 * @author Ian.Huang 
	 * @date 2016年7月5日下午6:58:47
	 */
	public PackagePrice getPackagePrice(int con_id);
	
	/**
	 * 
	 * @Description: TODO(保存打包价规则)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年7月5日下午4:24:18
	 */
	public JSONObject savePackagePrice(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(删除阶梯)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年7月5日下午3:02:59
	 */
	public JSONObject delLadder(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(载入阶梯数据)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年7月5日下午2:01:22
	 */
	public JSONObject loadLadder(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(阶梯区间展现)
	 * @param regionList
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年7月5日下午2:00:54
	 */
	public List<Map<String, Object>> regionListShow(List<Map<String, Object>> regionList);
	
	/**
	 * 
	 * @Description: TODO(保存阶梯)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年7月5日上午9:12:01
	 */
	public JSONObject savePackagePriceLadder(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(校验阶梯类型是否已维护)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年7月4日下午4:20:53
	 */
	public JSONObject judgeExistRecord(HttpServletRequest request, JSONObject result) throws Exception;
	
}

package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.ExpressRawData;
import com.bt.lmis.model.LogisticsStandardData;

public interface RawDataService<T> extends BaseService<T> {
	
	/**
	 * 
	 * @Description: TODO(调用存储过程)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年9月22日下午12:39:43
	 */
	public JSONObject invoke(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(按批次删除)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年9月22日上午9:49:25
	 */
	public JSONObject del(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 * @return: ModelMap  
	 * @author Ian.Huang 
	 * @date 2016年9月21日下午3:34:49
	 */
	public ModelMap searchRawDataByBatId(ModelMap map, HttpServletRequest request) throws Exception;
	
	public List<Map<String, String>> getTemplet();
	
	/**
	* @Title: saveRawData
	* @Description: TODO(插入原始数据)
	* @param @param result
	* @param @param mapper
	* @param @param results
	* @param @param className
	* @param @throws Exception    设定文件
	* @return JSONObject    返回类型
	* @throws
	*/ 
	public JSONObject saveRawData(JSONObject result, Object mapper, List<Object> results, String className) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(保存数据)
	 * @param result
	 * @param filePath
	 * @param className
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年9月18日下午2:07:51
	 */
	public JSONObject saveData(JSONObject result, String filePath, String className) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(插入物流运单原始数据)
	 * @param logisticsStandardDatas
	 * @throws Exception
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年9月14日下午3:50:27
	 */
	public void saveLogisticsRawData(List<LogisticsStandardData> logisticsStandardDatas) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(上传物流运单原始数据)
	 * @param request
	 * @param result
	 * @param workbook
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年9月14日上午11:58:58
	 */
	public JSONObject uploadLogisticsWaybillExcel(HttpServletRequest request, JSONObject result, Workbook workbook) throws Exception;
	
	/**
	* @Title: saveExpressRawData
	* @Description: TODO(保存快递运单原始数据)
	* @param @param expressRawDatas
	* @param @throws Exception    设定文件
	* @return void    返回类型
	* @throws
	*/ 
	public void saveExpressRawData(List<ExpressRawData> expressRawDatas) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(上载数据)
	 * @param request
	 * @param result
	 * @param workbook
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年9月13日下午2:54:36
	 */
	public JSONObject uploadExpressWaybillExcel(HttpServletRequest request, JSONObject result, Workbook workbook) throws Exception;
	
}

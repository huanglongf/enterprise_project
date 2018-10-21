package com.bt.lmis.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;

/** 
* @ClassName: EmployeeMapper 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月23日 上午10:50:11 
* 
* @param <T> 
*/
public interface CommonDataMapper<T> extends BaseMapper<T>{
/**
 * 
* @Title: getTransportData 
* @Description: TODO(查询快递承运商信息 ) 
* @param @param param
* @param @return    设定文件 
* @author likun   
* @return ArrayList<T>    返回类型 
* @throws
 */
  ArrayList<T> getTransportData(HashMap<String,String>param);
  /**
   * 
  * @Title: getProductTypeData 
  * @Description: TODO(物流商产品类型  ) 
  * @param @param param
  * @param @return    设定文件 
  * @author likun   
  * @return ArrayList<T>    返回类型 
  * @throws
   */
  ArrayList<T> getProductTypeData(HashMap<String,String>param);
  /**
   * 
  * @Title: getAreaData 
  * @Description: TODO(省市区查询) 
  * @param @param param
  * @param @return    设定文件 
  * @author likun   
  * @return ArrayList<T>    返回类型 
  * @throws
   */
  ArrayList<T> getAreaData(HashMap<String,String>param);
  /**
   * 
  * @Title: getWarnMainData 
  * @Description: TODO(预警类型 ) 
  * @param @param param
  * @param @return    设定文件 
  * @author likun   
  * @return ArrayList<T>    返回类型 
  * @throws
   */
  ArrayList<T> getWarnMainData(HashMap<String,String>param);
  /**
   * 
  * @Title: getWarnDetailData 
  * @Description: TODO( 预警级别 ) 
  * @param @param param
  * @param @return    设定文件 
  * @author likun   
  * @return ArrayList<T>    返回类型 
  * @throws
   */
  ArrayList<T> getWarnDetailData(HashMap<String,String>param);
  
  
  /**
   * 获取业务流水编码
   */
  public Map<String,String> get_business_code(Map<String,String>param);
}

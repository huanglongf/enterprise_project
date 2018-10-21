package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.AddservicefeeData;

/** 
* @ClassName: AddservicefeeDataService 
* @Description: TODO(增值服务费服务类) 
* @author Yuriy.Jiang
* @date 2016年9月1日 上午11:04:11 
* 
* @param <T> 
*/
public interface AddservicefeeDataService<T> extends BaseService<T> {
	public List<AddservicefeeData> findByAll(AddservicefeeData afd);
	public List<AddservicefeeData> findBySection(AddservicefeeData afd);
	public List<Map<String, String>> findGroupByServiceName(AddservicefeeData afd);
	public void update_settleFlag(@Param("id")String id);
}

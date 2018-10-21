package com.bt.radar.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.page.QueryParameter;

/**
* @ClassName: RoutecodeMapper
* @Description: TODO(RoutecodeMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface RoutecodeMapper<T> extends BaseMapper<T> {
	
	public List<T> findAllData(QueryParameter queryParameter);
	
	public Integer selectCount(QueryParameter queryParameter);
	//获得tb_transport_vender 中 快递的原始数据
	public List<T> selectTransport_vender(QueryParameter queryParameter);
	//获得er_routecode中已有 快递状态的原始数据
	public List<T> getTransport_vender(QueryParameter queryParameter);
	
	public List<T> findAll(QueryParameter queryParameter);
	
	public	List<T> findAllRecord(QueryParameter queryParameter);
	
	public  List<T> selectRecordsByWID(Map param);
	
	public  List<T> selectRecordsByID(Map param);

	public List<T> getStatus(Map param);
	
}

package com.bt.lmis.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.ErrorAddressQueryParam;
import com.bt.lmis.controller.form.OperatorQueryParam;
import com.bt.lmis.dao.JkErrorMapper;
import com.bt.lmis.model.OperatorBean;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.JkErrorService;

/** 
* @ClassName: EmployeeServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月23日 上午10:51:08 
* 
* @param <T> 
*/
@Service
public class JkErrorServiceImpl<T> extends ServiceSupport<T> implements JkErrorService<T> {
	@Autowired
    private JkErrorMapper<T> mapper;
	public JkErrorMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return null;
	}

	@Override
	public QueryResult<T> findAllErrorData(QueryParameter queryParameter) {
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.getJkErrorOrder(queryParameter));
		queryResult.setTotalrecord(mapper.selectJkCount(queryParameter));
		return queryResult;
		
	}

	@Override
	public Map<String, String> updateErrorData(Map<String,String> param) {
		// TODO Auto-generated method stub
		mapper.dealErrorDataPro(param);
		return param;
	}
	
	@Override
	public Map<String, String> dealjkDataPro(Map<String,String> param) {
		// TODO Auto-generated method stub
		mapper.dealjkDataPro(param);
		return param;
	}
	
	@Override
	public QueryResult<T> findjKData(QueryParameter queryParameter) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.getJkDataInfo(queryParameter));
		queryResult.setTotalrecord(mapper.getJkDataCount(queryParameter));
		return queryResult;
	}

	@Override
	public void upStatus(Map<String, String> map) {
		// TODO Auto-generated method stub
		Map<String,Object>result=mapper.getStatus(map);
		if((Integer)result.get("newly_link_flag")==2){
			throw new RuntimeException("正在获取中，无法撤销获取");
		}else{
		    mapper.upStatus(map);
		}
	}

	/**操作费**/
	@Override
	public QueryResult<T> findOperatorData(QueryParameter qr) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findOperatorData(qr));
		queryResult.setTotalrecord(mapper.selecOperatorCount(qr));
		return queryResult;
	}

	@Override
	public List<Map<String, Object>> query_export(OperatorQueryParam queryParam) {
		// TODO Auto-generated method stub
		return mapper.exportData(queryParam);
	}

	@Override
	public List<Map<String, Object>> exportData(OperatorQueryParam queryParam) {
		// TODO Auto-generated method stub
		return mapper.exportData(queryParam);
	}

	@Override
	public QueryResult<T> getImportMainInfo(OperatorQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.getImportMainInfo(queryParam));
		queryResult.setTotalrecord(mapper.getImportMainInfoCount(queryParam));
		return queryResult;
	}

	
	@Override
	public void checkImport(String bat_id) {
		// TODO Auto-generated method stub
		Map<String,String>param=new HashMap<String,String>();
		param.put("bat_id", bat_id);
		mapper.update_iscomplete(param);
	}

	@Override
	public void insertOper(List<OperatorBean> sub_list) {
		// TODO Auto-generated method stub
		mapper.insertOper(sub_list);
	}
	
	@Override
	public QueryResult<T> getImportDetailInfo(OperatorQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findAllDetailData(queryParam));
		queryResult.setTotalrecord(mapper.selectDetailCount(queryParam));
		return queryResult;
	}
}

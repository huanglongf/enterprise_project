package com.bt.lmis.service.impl;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.TransPoolDetailMapper;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.TransPoolDetailService;

/** 
* @ClassName: EmployeeServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月23日 上午10:51:08 
* 
* @param <T> 
*/
@Service
public class TransPoolDetailServiceImpl<T> extends ServiceSupport<T> implements TransPoolDetailService<T> {
	
	@Autowired
    private TransPoolDetailMapper<T> mapper;

	public TransPoolDetailMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
	public HashMap getObject(HashMap param) {
		// TODO Auto-generated method stub
		return mapper.getObject(param);
	}

	@Override
	public QueryResult<T> findAllDataByGt(QueryParameter queryParameter) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findAllDataByGt(queryParameter));
		queryResult.setTotalrecord(mapper.selectCountByGt(queryParameter));
		return queryResult;
	}

	@Override
	public List<T> getExclData(Map<String, Object> param) {
		// TODO Auto-generated method stub
		 return mapper.getExclData(param);
	}

	@Override
	public List<T> getDetailData(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getDetailData(param);
	}

	@Override
	public List<T> getExclInvitation(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getExclInvitation(param);
	}

	@Override
	public List<T> getExeclOperator(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getExeclOperator(param);
	}

	@Override
	public List<T> getExeclOperatorTwo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getExeclOperatorTwo(param);
	}

	@Override
	public List<T> getExeclOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getExeclOrder(param);
	}

	@Override
	public List<T> getExeclOrderDetail(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getExeclOrderDetail(param);
	}

	@Override
	public List<T> getExeclStorage(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getExeclStorage(param);
	}

	@Override
	public List<T> getDetailUse(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getDetailUse(param);
	}



	
	
}

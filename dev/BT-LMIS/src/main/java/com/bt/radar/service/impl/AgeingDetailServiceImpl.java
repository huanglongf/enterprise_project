package com.bt.radar.service.impl;


import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bt.common.controller.result.Result;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.AgeingDetailQueryParam;
import com.bt.radar.dao.AgeingDetailMapper;
import com.bt.radar.model.AgeingDetail;
import com.bt.radar.model.AgeingDetailBackups;
import com.bt.radar.service.AgeingDetailService;

@Service
public class AgeingDetailServiceImpl<T> implements AgeingDetailService<T> {

	@Autowired
	private AgeingDetailMapper<AgeingDetail> ageingDetailMapper;
	
	public AgeingDetailMapper<AgeingDetail> getAgeingDetailMapper(){
		return ageingDetailMapper;
	}
	

	@Override
	public QueryResult<AgeingDetail> queryAgeingDetail(AgeingDetailQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<AgeingDetail> qr = new QueryResult<AgeingDetail>();
		qr.setResultlist((List<AgeingDetail>) ageingDetailMapper.queryAgeingDetail(queryParam));
		qr.setTotalrecord(ageingDetailMapper.countAllAgeingDetail(queryParam));
		return qr;
	}
	@Override
	public void insertList(List<AgeingDetailBackups> listageingDetailBackups) {
		// TODO Auto-generated method stub
		for (AgeingDetailBackups ageingDetailBackups : listageingDetailBackups) {
			ageingDetailMapper.insertAgeingDetailBackups(ageingDetailBackups);
		}
	}

	@Override
	public AgeingDetail selectByAgeingDetailBackups(AgeingDetailBackups ageingDetailBackups2) {
		// TODO Auto-generated method stub
		return ageingDetailMapper.selectByAgeingDetailBackups(ageingDetailBackups2);
	}	
	
	@Override
	public Result delAgeingDetail(AgeingDetailQueryParam queryParam) {
		String [] array= queryParam.getDetailIdStr().split(",");
		List<Integer> detailIdList= Lists.newArrayList();
		for(String str:array){
			if(StringUtils.isNotBlank(str)) detailIdList.add(Integer.parseInt(str));
		}
		if(detailIdList == null || detailIdList.isEmpty()) return  new Result(false,"params error");

		int count= ageingDetailMapper.delBatchAgeingDetailById(detailIdList);
		if(count>0) return new Result(true,"共有"+count+"行受影响");
		else return new Result(false,"删除失败");
	}

	@Override
	public int insert(AgeingDetailQueryParam queryParam) {
		// TODO Auto-generated method stub
		return ageingDetailMapper.insertAgeingDetailQueryParam(queryParam);
	}

	/**
	 * 根据id查询非当前id下明细信息相符的明细信息
	 */
	@Override
	public AgeingDetail selectByAgeingDetailQueryParam(AgeingDetailQueryParam queryParam) {
		// TODO Auto-generated method stub
		return ageingDetailMapper.selectByAgeingDetailQueryParam(queryParam);
	}

	@Override
	public AgeingDetail selectByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return ageingDetailMapper.selectByPrimaryKey(id);
	}

	@Override
	public int update(AgeingDetailQueryParam queryParam) {
		// TODO Auto-generated method stub
		return ageingDetailMapper.updateAgeingDetailQueryParam(queryParam);
	}

	@Override
	public List<Map<String, Object>> selectAgeingDetailQueryParam(AgeingDetailQueryParam queryParam) {
		// TODO Auto-generated method stub
		return ageingDetailMapper.selectAgeingDetailQueryParam(queryParam);
	}

	@Override
	public void uploadAgeingDetail(List<String[]> list, AgeingDetailQueryParam queryParam,String batId) {
			// TODO Auto-generated method stub
		    //插入临时表
		     //String  batId=UUID.randomUUID().toString();
			list.remove(0);
			ageingDetailMapper.insertTemp(list,batId,queryParam.getCreateUser(),queryParam.getAgeingId(),queryParam.getStoreName());
		
			//检验仓库是否存在
			ageingDetailMapper.checkWare(batId);
			//检验省市区
			ageingDetailMapper.checkAddress(batId);
			//承运商&产品类型
			ageingDetailMapper.checkExpress(batId);
			//校验重复性
			ageingDetailMapper.checkUnique(batId);
			//校验导入重复性
			List<?> listId=ageingDetailMapper.checkUniqueOther(batId);
			if(listId!=null&&listId.size()!=0)
				ageingDetailMapper.UpdateUniqueOther(listId);
			
			//插入正确的数据
			ageingDetailMapper.insertDetail(batId);
	}



}

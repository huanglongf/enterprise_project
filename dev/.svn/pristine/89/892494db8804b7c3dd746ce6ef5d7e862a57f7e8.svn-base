package com.bt.radar.service.impl;


import java.util.List;

import com.bt.common.controller.result.Result;
import com.bt.radar.dao.AgeingDetailMapper;
import com.bt.radar.model.AgeingDetail;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.AgeingMasterQueryParam;
import com.bt.radar.dao.AgeingMasterMapper;
import com.bt.radar.model.AgeingMaster;
import com.bt.radar.service.AgeingMasterService;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service
public class AgeingMasterServiceImpl<T> implements AgeingMasterService<T> {

	@Autowired
	private AgeingMasterMapper<AgeingMaster> ageingMasterMapper;

	@Autowired
	private AgeingDetailMapper<AgeingDetail> ageingDetailMapper;
	
	public AgeingMasterMapper<AgeingMaster> getAgeingMasterMapper(){
		return ageingMasterMapper;
	}

	@Override
	public QueryResult<AgeingMaster> queryAgeingMaster(AgeingMasterQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<AgeingMaster> qr = new QueryResult<AgeingMaster>();
		qr.setResultlist((List<AgeingMaster>) ageingMasterMapper.queryAgeingMaster(queryParam));
		qr.setTotalrecord(ageingMasterMapper.countAllAgeingMaster(queryParam));
		return qr;
	}

	@Override
	public AgeingMaster selectByAgeingName(String ageingName) {
		// TODO Auto-generated method stub
		return ageingMasterMapper.selectByAgeingName(ageingName);
	}

	@Override
	public void insertAgeingMaster(AgeingMasterQueryParam queryParam) {
		// TODO Auto-generated method stub
		ageingMasterMapper.insertAgeingMaster(queryParam);
	}

	@Override
	public AgeingMaster queryAgeingMasterById(String id) {
		// TODO Auto-generated method stub
		return ageingMasterMapper.queryAgeingMasterById(id);
	}

	@Override
	public void updateAgeingMaster(AgeingMasterQueryParam queryParam) {
		// TODO Auto-generated method stub
		ageingMasterMapper.updateByPrimaryKeySelective(queryParam);
	}

	@Override
	public Result delAgeingMaster(AgeingMasterQueryParam queryParam) throws Exception{
		String [] array= queryParam.getId().split(",");
		List<String> masterIdList= Lists.newArrayList();
		for(String str:array){
			if(StringUtils.isNotBlank(str)) masterIdList.add(str);
		}
		//先查询要删除的时效是否有明细
		List<AgeingDetail> detailList= ageingDetailMapper.queryByAgeingIds(masterIdList);
		int count= ageingMasterMapper.delBatchAgeingMaster(masterIdList);
		if(count>0){
			if(detailList!= null && !detailList.isEmpty()){
				int detailCount= ageingDetailMapper.delBatchAgeingDetailByAgeingId(masterIdList);
				if(detailCount>0) return new Result(true,"共有"+count+"行受影响");
				else throw new Exception("删除失败");
			}else return new Result(true,"共有"+count+"行受影响");

		}else return  new Result(false,"删除失败");
	}

	@Override
	public Result upStatus(AgeingMasterQueryParam queryParam) {
		// TODO Auto-generated method stub
		if(queryParam.getStatus().equals("1")){
			AgeingMaster selectByPrimaryKey = ageingMasterMapper.selectByPrimaryKey(queryParam.getId());
			AgeingMasterQueryParam ageingMaster1 =new AgeingMasterQueryParam();
			ageingMaster1.setStatus("1");
			ageingMaster1.setStoreCode(selectByPrimaryKey.getStoreCode());
			ageingMaster1.setStoreName(selectByPrimaryKey.getStoreName());
			ageingMaster1.setMaxResult(1);
			List<AgeingMaster> ageingMaster =ageingMasterMapper.queryAgeingMaster(ageingMaster1);
			if(ageingMaster.size()>0) return new Result(false,"该店铺存在正在运行的时效，无法启用！");
		}
		int count=ageingMasterMapper.updateByPrimaryKeySelective(queryParam);
		if(count>0) return new Result(true,"启用/禁用成功！");
		else return  new Result(false,"启用/禁用失败！");
	}


}

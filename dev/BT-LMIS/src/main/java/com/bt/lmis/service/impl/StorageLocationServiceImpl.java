package com.bt.lmis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.StorageLocationQueryParam;
import com.bt.lmis.dao.StorageLocationMapper;
import com.bt.lmis.dao.WarehouseMapper;
import com.bt.lmis.model.StorageLocation;
import com.bt.lmis.model.Warehouse;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.StorageLocationService;
@Service
public class StorageLocationServiceImpl<T> extends ServiceSupport<T> implements StorageLocationService<T> {

	@Autowired
    private StorageLocationMapper<T> mapper;

	@Autowired
	private WarehouseMapper warehouseMapper;
	public StorageLocationMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public boolean checkWarehouse(String warehouse_name,StorageLocation storageLocation) {
		// TODO Auto-generated method stub
       //检验仓库名称是否正确
		Warehouse  wParam= new Warehouse ();
		wParam.setValidity(1);
		wParam.setWarehouse_name(warehouse_name);
		Warehouse result=warehouseMapper.getByCondition(wParam);
		if(result==null)return true;
		storageLocation.setWarehouse_code(result.getWarehouse_code());
		
		//检验是否已经存在记录
		/*Map<String,String> param =new HashMap<String,String>();
		param.put("warehouse_name", warehouse_name);
	  List<StorageLocation>	list=mapper.findRecords(param);
		if(list!=null&&list.size()!=0)return true;*/
		return false;
	}

	@Override
	public void addBatch(List<StorageLocation> storageLocationList) {
		// TODO Auto-generated method stub
		mapper.addBatch(storageLocationList);
	}

	@Override
	public QueryResult<StorageLocation> findRecord(StorageLocationQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<StorageLocation> qr=new QueryResult<StorageLocation>();
		qr.setResultlist(mapper.findAll(queryParam));
		qr.setTotalrecord(mapper.Count(queryParam));
		return qr;
	}

	@Override
	public QueryResult<StorageLocation> findRecordNoCount(StorageLocationQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<StorageLocation> qr=new QueryResult<StorageLocation>();
		qr.setResultlist(mapper.findAll(queryParam));
		return qr;
	}
	
	@Override
	public void deleteListId(List<String> idList) {
		// TODO Auto-generated method stub
		mapper.deleteListId(idList);
	}

	@Override
	public boolean checkUpdateBf(StorageLocationQueryParam queryParam) {
		// TODO Auto-generated method stub
		
	int flag=	mapper.checkUpdateBf(queryParam);
	if(flag>0)return false;//不能添加
		
		return true;
	}

	@Override
	public void update(StorageLocation entity) {
		// TODO Auto-generated method stub
		mapper.updateById(entity);
	}

	@Override
	public boolean checkBtachinsertBf(StorageLocation storageLocation) {
		// TODO Auto-generated method stub
		int flag=	mapper.checkBtachinsertBf(storageLocation);
		if(flag>0)return false;//不能添加
			
			return true;
	}

	@Override
	public String checkRepeat(List<StorageLocation> storageLocationList) {
		// TODO Auto-generated method stub
		if(storageLocationList==null||storageLocationList.size()==0)return "";
		String log="";
		mapper.truncat();//清空数据
		mapper.deleteListIdRepeat(storageLocationList);//插入新的表
		//计算导入的数据是否有重复
		List<Map<String,Object>> list=mapper.getSame();
		if(list==null||list.size()==0)return "";
		for(Map<String,Object> map:list){
			log+="第"+map.get("row_no").toString()+"行"+map.get("warehouse_code").toString()+"--"+map.get("reservoir_code").toString()+"....存在重复数据;";
		}
		return log;
	}


	
}

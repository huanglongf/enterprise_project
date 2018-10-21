package com.bt.lmis.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.AreaQueryParam;
import com.bt.lmis.dao.AreaMapper;
import com.bt.lmis.model.Area;
import com.bt.lmis.service.AreaService;
@Service
public class AreaServiceImpl<T> extends ServiceSupport<T> implements AreaService<T> {

	@Autowired
    private AreaMapper<T> mapper;

	public AreaMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<Map<String, Object>> findChildren(Map<String, String> param) {
		
		return mapper.findChildren(param);
	}

	@Override
	public void deleteTree(int id) throws Exception {
		// TODO Auto-generated method stub
     Map <String,String> param=mapper.getChildLst(id);
     if(param==null||param.size()==0)return;
     String ids=param.get("ids");
     String ID[]=ids.split(",");
     Integer IID[]=new Integer[ID.length];
     for(int i=0;i<ID.length;i++){
    	 if(ID[i]==null||"".equals(ID[i]))continue;
    	 IID[i]=Integer.parseInt(ID[i]);
     }
        mapper.batchDelete(IID);
     
	}

	@Override
	public void updateTree(AreaQueryParam param) {
		// TODO Auto-generated method stub
		mapper.updateTree(param);
	}

	@Override
	public void addTree(Area area) throws Exception {
		area.setCreate_time(new Date());
	//zyz	area.setHaschild(0);
		area.setUpdate_time(new Date());
		mapper.insert((T) area);
		
	}

	@Override
	public Object selectOne(int id) throws Exception {
		// TODO Auto-generated method stub
		
		return mapper.selectById(id);
	}
		
	
}

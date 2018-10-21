package com.bt.lmis.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.dao.RoleMapper;
import com.bt.lmis.model.Role;
import com.bt.lmis.service.RoleService;

/** 
* @ClassName: RoleServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月27日 下午5:41:50 
* 
* @param <T> 
*/
@Service
public class RoleServiceImpl<T> implements RoleService<T> {


	@Autowired
    private RoleMapper<T> mapper;

	@Override
	public int save(T entity) throws Exception {
		return mapper.insert(entity);		
	}

	@Override
	public void update(T entity) throws Exception {
		mapper.update(entity);
	}

	@Override
	public void delete(Serializable id) throws Exception {
		mapper.delete(id);
	}

	@Override
	public void batchDelete(Integer[] ids) throws Exception {
		mapper.batchDelete(ids);
	}

	@Override
	public T selectById(Integer id) throws Exception {
		return mapper.selectById(id);
	}

	@Override
	public List<Map<String, Object>> findByList() {
		return mapper.findByList();
	}

	@Override
	public void upStatus(int id, int status) {
		mapper.upStatus(id, status);
	}

	@Override
	public List<Map<String, Object>> findRoleEmployeeByRoleId(int id) {
		return mapper.findRoleEmployeeByRoleId(id);
	}

	@Override
	public Long selectCount(Map<String, Object>  param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertRoleMenu(Role role, Integer[] ids)  throws Exception{
		//角色ID
		mapper.insert((T) role);
		for (int i = 0; i < ids.length; i++) {
			if(null!=ids[i]){
				int menuid = ids[i];
				mapper.insertMR(menuid, role.getId());
			}
		}
	}
	
	@Override
	public void updateRoleMenu(T entity, int id, Integer[] ids) throws Exception{
		//角色ID
		mapper.update(entity);
		mapper.deleteMR(id);
		for (int i = 0; i < ids.length; i++) {
			if(null!=ids[i]){
				int menuid = ids[i];
				mapper.insertMR(menuid, id);
			}
		}
	}

	@Override
	public List<Map<String, Object>> findByListSelective(T entity) {
		// TODO Auto-generated method stub
		return mapper.findByListSelective(entity);
	}
}

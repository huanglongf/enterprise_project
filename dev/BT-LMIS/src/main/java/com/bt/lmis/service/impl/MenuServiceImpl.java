package com.bt.lmis.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.dao.MenuMapper;
import com.bt.lmis.model.Menu;
import com.bt.lmis.service.MenuService;

/** 
* @ClassName: MenuServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月24日 下午3:25:46 
* 
* @param <T> 
*/
@Service
public class MenuServiceImpl<T> implements MenuService<T> {

	@Autowired
    private MenuMapper<T> mapper;
	
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
	public void upStatus(int id, int status) {
		mapper.upStatus(id, status);
	}

	@Override
	public List<Map<String, Object>> getMenuTree(Menu menu) {
		return mapper.getMenuTree(menu);
	}
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getRoleMenuTree(int roleid) {
		return mapper.getRoleMenuTree(roleid);
	}

	@Override
	public List<Map<String, Object>> getMenu(Menu menu) {
		return mapper.getMenu(menu);
	}


}

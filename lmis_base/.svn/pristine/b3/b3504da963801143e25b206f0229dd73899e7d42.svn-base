package com.lmis.jbasis.store.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lmis.jbasis.store.model.JbasisStore;
import com.lmis.jbasis.store.model.ViewJbasisStore;

/**
* @ClassName: JbasisStoreMapperTest  
* @Description: TODO(店铺管理测试类)  
* @author fxqiang
* @date 2018年4月16日  
*
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JbasisStoreMapperTest {

	@Autowired
	JbasisStoreMapper<JbasisStore> jbasisStoreMapper;

	/**
	* @Title: retrieveViewJbasisStoreTest  
	* @Description: TODO(查询集合)  
	* @param     参数  
	* @return void    返回类型  
	* @throws
	 */
	@Test
	public void retrieveViewJbasisStoreTest() {
		ViewJbasisStore s=new ViewJbasisStore();
		s.setId("00fdddc4-4121-11e8-bd1b-005056952d2b");
		List<ViewJbasisStore> list=jbasisStoreMapper.retrieveViewJbasisStore(s);
		System.out.println("店铺管理测试类-查询(retrieveViewJbasisStore):"+list.size());
	}
	
}

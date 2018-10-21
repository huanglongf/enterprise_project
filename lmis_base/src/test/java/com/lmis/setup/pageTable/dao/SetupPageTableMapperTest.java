package com.lmis.setup.pageTable.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lmis.setup.pageTable.model.SetupPageTable;
import com.lmis.setup.pageTable.model.ViewSetupPageTable;

/** 
 * @ClassName: SetupPageTableMapperTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年12月8日 下午6:32:01 
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SetupPageTableMapperTest {

	/**
     * Spring RestTemplate的便利替代。你可以获取一个普通的或发送基本HTTP认证（使用用户名和密码）的模板
     * 这里不使用
     */
//    @Autowired
//    private TestRestTemplate testRestTemplate;

    @Autowired
    SetupPageTableMapper<SetupPageTable> setupPageTableMapper;

    /**
     * 2017-06-16 14:08:09.884  INFO 13803 --- [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
     size:5
     -----测试完毕-------
     2017-06-16 14:08:09.955  INFO 13803 --- [       Thread-4] ationConfigEmbeddedWebApplicationContext : Closing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@fd07cbb: startup date [Fri Jun 16 14:08:04 CST 2017]; root of context hierarchy
     */
    @Test
    public void test() {
    	
    	// 单条新增
    	SetupPageTable test = new SetupPageTable(null, null, "hhy", null, "hhy", null, null, null, "0", "hhy0", "hhy0", "hhy0");
    	test.setColumnHide(true);
    	test.setListType("lt_plain");
    	setupPageTableMapper.create(test);
    	
    	// 批量新增
        List<SetupPageTable> testlist = new ArrayList<SetupPageTable>();
        for(int i=1; i < 3; i++) {
        	SetupPageTable setupPageTable = new SetupPageTable(null, null, "hhy", null, "hhy", null, null, null, "0", "hhy" + i, "hhy" + i, "hhy" + i);
        	setupPageTable.setColumnHide(false);
        	test.setListType("lt_plain");
        	testlist.add(setupPageTable);
        }
        setupPageTableMapper.createBatch(testlist);
        //
        testlist.add(0, test);
        
        // 查询
        List<SetupPageTable> resultlist = new ArrayList<SetupPageTable>();
        for(int i=0; i < testlist.size(); i++) {
        	resultlist.addAll(setupPageTableMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 更新
        for(int i=0; i < testlist.size(); i++) {
        	testlist.get(i).setColumnName(testlist.get(i).getColumnName()+"更新");;
        	testlist.get(i).setColumnHide(true);
        	test.setListType("lt_plain");
        	setupPageTableMapper.update(testlist.get(i));
        }
        resultlist = new ArrayList<SetupPageTable>();
        for(int i=0; i < testlist.size(); i++) {
        	resultlist.addAll(setupPageTableMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;

        // 更新记录
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setColumnName(testlist.get(i).getColumnName() + "【更新记录】");;
        	testlist.get(i).setColumnHide(false);
        	testlist.get(i).setListType("lt_plain");
        	setupPageTableMapper.updateRecord(testlist.get(i));
        }
        resultlist = new ArrayList<SetupPageTable>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupPageTableMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 切换有效性
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setIsDisabled(true);
        	setupPageTableMapper.shiftValidity(testlist.get(i));
        }
        resultlist = new ArrayList<SetupPageTable>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupPageTableMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 逻辑删除
        for(int i = 0; i < testlist.size(); i++) {
        	setupPageTableMapper.logicalDelete(testlist.get(i));
        }
        resultlist = new ArrayList<SetupPageTable>();
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setIsDeleted(null);
        	resultlist.addAll(setupPageTableMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 删除
        for(int i = 0; i < testlist.size(); i++) {
        	setupPageTableMapper.delete(testlist.get(i));
        }
    }
    
    @Test
    public void testListSetupPageTableSimpleBySeq() {
    	// 测试查询获取简单的查询列表
    	ViewSetupPageTable viewSetupPageTable = new ViewSetupPageTable();
    	viewSetupPageTable.setIsDeleted(false);
    	viewSetupPageTable.setLayoutId("P_YMBJ2_P02");
		List<ViewSetupPageTable> result = setupPageTableMapper.listSetupPageTableSimpleBySeq(viewSetupPageTable);
		assertTrue(result.size() > 0);
    }
    @Test
    public void testListValidateSetupPageTableByLayoutIds() throws Exception{
    	List<String> layoutIds = setupPageTableMapper.listAllValidateLayoutId();
    	List<ViewSetupPageTable> setupPageTables = setupPageTableMapper.listValidateSetupPageTableByLayoutIds(layoutIds);
    	assertNotNull(setupPageTables);
    }
}

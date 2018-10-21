package com.lmis.setup.pageLayout.dao;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.setup.pageLayout.model.SetupPageLayout;
import com.lmis.setup.pageLayout.model.ViewSetupPageLayout;

/** 
 * @ClassName: SetupPageLayoutMapperTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年12月7日 上午11:32:23 
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SetupPageLayoutMapperTest {

	/**
     * Spring RestTemplate的便利替代。你可以获取一个普通的或发送基本HTTP认证（使用用户名和密码）的模板
     * 这里不使用
     */
//    @Autowired
//    private TestRestTemplate testRestTemplate;

    @Autowired
    SetupPageLayoutMapper<SetupPageLayout> setupPageLayoutMapper;

    /**
     * 2017-06-16 14:08:09.884  INFO 13803 --- [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
     size:5
     -----测试完毕-------
     2017-06-16 14:08:09.955  INFO 13803 --- [       Thread-4] ationConfigEmbeddedWebApplicationContext : Closing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@fd07cbb: startup date [Fri Jun 16 14:08:04 CST 2017]; root of context hierarchy
     */
    @Test
    public void test() {
    	
    	// 单条新增
    	SetupPageLayout test = new SetupPageLayout(null, null, "hhy", null, "hhy", null, null, null, "0", "hhy0", "hhy0", "hhy0", "hhy0");
    	setupPageLayoutMapper.create(test);
    	
    	// 批量新增
        List<SetupPageLayout> testlist = new ArrayList<SetupPageLayout>();
        for(int i=1; i < 3; i++) {
        	testlist.add(new SetupPageLayout(null, null, "hhy", null, "hhy", null, null, null, "0", "hhy" + i, "hhy" + i, "hhy" + i, "hhy" + i));
        }
        setupPageLayoutMapper.createBatch(testlist);
        //
        testlist.add(0, test);
        
        // 查询
        List<SetupPageLayout> resultlist = new ArrayList<SetupPageLayout>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupPageLayoutMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).getLayoutId() + "[" + resultlist.get(i).getIsDeleted() + "|" + resultlist.get(i).getVersion() + "]:" + resultlist.get(i).getLayoutName());
        }
        testlist = resultlist;
        
        // 更新
        for(int i=0; i < testlist.size(); i++) {
        	testlist.get(i).setLayoutName(testlist.get(i).getLayoutName() + "【更新】");;
        	setupPageLayoutMapper.update(testlist.get(i));
        }
        resultlist = new ArrayList<SetupPageLayout>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupPageLayoutMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 更新记录
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setLayoutName(testlist.get(i).getLayoutName() + "【更新记录】");;
        	setupPageLayoutMapper.updateRecord(testlist.get(i));
        }
        resultlist = new ArrayList<SetupPageLayout>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupPageLayoutMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 切换有效性
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setIsDisabled(true);
        	setupPageLayoutMapper.shiftValidity(testlist.get(i));
        }
        resultlist = new ArrayList<SetupPageLayout>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupPageLayoutMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 逻辑删除
        for(int i = 0; i < testlist.size(); i++) {
        	setupPageLayoutMapper.logicalDelete(testlist.get(i));
        }
        resultlist = new ArrayList<SetupPageLayout>();
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setIsDeleted(null);
        	resultlist.addAll(setupPageLayoutMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 删除
        for(int i = 0; i < testlist.size(); i++) {
        	setupPageLayoutMapper.delete(testlist.get(i));
        }
    }
    
    @Test
    public void testListSetupPageLayoutBySeq() {
    	 // 页面布局查询测试
        ViewSetupPageLayout viewSetupPageLayout = new ViewSetupPageLayout();
        viewSetupPageLayout.setIsDeleted(false);
        viewSetupPageLayout.setPageId("YMBJ");
        viewSetupPageLayout.setParentLayoutId("");
        List<ViewSetupPageLayout> viewSetupPageLayouts  = setupPageLayoutMapper.listSetupPageLayoutBySeq(viewSetupPageLayout);
        assertNotNull(viewSetupPageLayouts);
    }
    
    @Test
    public void testListSetupPageLayoutSimpleBySeq() {
    	 // 页面布局简单查询测试
        ViewSetupPageLayout viewSetupPageLayout = new ViewSetupPageLayout();
        viewSetupPageLayout.setIsDeleted(false);
        viewSetupPageLayout.setPageId("YMBJ");
        viewSetupPageLayout.setParentLayoutId("");
        List<ViewSetupPageLayout> viewSetupPageLayouts  = setupPageLayoutMapper.listSetupPageLayoutSimpleBySeq(viewSetupPageLayout);
        assertNotNull(viewSetupPageLayouts);
    }
    
    @Test
    public void testLayoutIdIsEmpty() throws Exception{
    	//String generateId = baseUtils.GetCodeRule("PAGE_NUM");
    	SetupPageLayout setupPageLayout = new SetupPageLayout();
		setupPageLayout.setLayoutId("PN100023");
		// 唯一校验
		
		boolean result = ObjectUtils.isNull(setupPageLayoutMapper.retrieve(new SetupPageLayout(null, false, setupPageLayout.getLayoutId())));
		System.out.println("result:" + result);
    }
}

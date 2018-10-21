package com.lmis.setup.pageElement.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.validator.PublicClassValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.pageElement.model.SetupPageElement;
import com.lmis.setup.pageElement.model.ViewSetupPageElement;

/** 
 * @ClassName: SetupPageElementMapperTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年12月7日 上午11:33:04 
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SetupPageElementMapperTest {

	/**
     * Spring RestTemplate的便利替代。你可以获取一个普通的或发送基本HTTP认证（使用用户名和密码）的模板
     * 这里不使用
     */
//    @Autowired
//    private TestRestTemplate testRestTemplate;

    @Autowired
    SetupPageElementMapper<SetupPageElement> setupPageElementMapper;

    /**
     * 2017-06-16 14:08:09.884  INFO 13803 --- [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
     size:5
     -----测试完毕-------
     2017-06-16 14:08:09.955  INFO 13803 --- [       Thread-4] ationConfigEmbeddedWebApplicationContext : Closing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@fd07cbb: startup date [Fri Jun 16 14:08:04 CST 2017]; root of context hierarchy
     */
    @Test
    public void test() {
    	// 单条新增
    	SetupPageElement test = new SetupPageElement(null, null, "hhy", null, "hhy", null, null, null, "0", "hhy0", "hhy0", "hhy0", "hhy0",100);
    	test.setElementHide(false);
    	test.setIsFilt(true);
    	setupPageElementMapper.create(test);
    	
    	// 批量新增
        List<SetupPageElement> testlist = new ArrayList<SetupPageElement>();
        for(int i = 1; i < 3; i++) {
        	SetupPageElement  setupPageElement  =  new SetupPageElement(null, null, "hhy", null, "hhy", null, null, null, "0", "hhy" + i, "hhy" + i, "hhy" + i, "hhy" + i, 100);
        	setupPageElement.setElementHide(true);
        	test.setIsFilt(true);
        	testlist.add(setupPageElement);
        }
        setupPageElementMapper.createBatch(testlist);
        //
        testlist.add(0, test);
       
        // 查询
        List<SetupPageElement> resultlist = new ArrayList<SetupPageElement>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupPageElementMapper.retrieve(testlist.get(i)));
        	System.out.println("retrieve: " + resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 更新
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setElementName(testlist.get(i).getElementName() + "【更新】");;
        	testlist.get(i).setColumnLen(100);
        	testlist.get(i).setElementHide(true);
        	testlist.get(i).setIsFilt(true);
        	setupPageElementMapper.update(testlist.get(i));
        }
        resultlist = new ArrayList<SetupPageElement>();
        for(int i=0; i < testlist.size(); i++) {
        	resultlist.addAll(setupPageElementMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 更新记录
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setElementName(testlist.get(i).getElementName() + "【更新记录】");
        	testlist.get(i).setColumnLen(100);
        	testlist.get(i).setIsFilt(false);
        	setupPageElementMapper.updateRecord(testlist.get(i));
        }
        resultlist = new ArrayList<SetupPageElement>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupPageElementMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 切换有效性
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setIsDisabled(true);
        	setupPageElementMapper.shiftValidity(testlist.get(i));
        }
        resultlist = new ArrayList<SetupPageElement>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupPageElementMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 逻辑删除
        for(int i = 0; i < testlist.size(); i++) {
        	setupPageElementMapper.logicalDelete(testlist.get(i));
        }
        resultlist = new ArrayList<SetupPageElement>();
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setIsDeleted(null);
        	resultlist.addAll(setupPageElementMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 删除
        for(int i = 0; i < testlist.size(); i++) {
        	setupPageElementMapper.delete(testlist.get(i));
        }
    }
    
    @Test
    public void testListSetupPageElementBySeq() throws Exception{
    	// 查询简单视图获取元素结果集
    	ViewSetupPageElement param = new ViewSetupPageElement();
		param.setIsDeleted(false);
		param.setLayoutId("P_YMGX");
		List<ViewSetupPageElement> result = setupPageElementMapper.listSetupPageElementBySeq(param);
		System.out.println(result);
		assertNotNull("result is not null :" + result);
    }
    
    @Test
    public void testListSetupPageElementSimpleBySeq() throws Exception{
    	// 查询简单视图获取元素结果集
    	ViewSetupPageElement param = new ViewSetupPageElement();
		param.setIsDeleted(false);
		param.setLayoutId("P_YMGX");
		List<ViewSetupPageElement> result = setupPageElementMapper.listSetupPageElementSimpleBySeq(param);
		System.out.println(result);
		assertNotNull("result is not null :" + result);
    }
}

package com.lmis.setup.constant.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lmis.setup.constant.model.SetupConstant;

/** 
 * @ClassName: SetupConstantMapperTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年12月6日 下午8:29:15 
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SetupConstantMapperTest {

	/**
     * Spring RestTemplate的便利替代。你可以获取一个普通的或发送基本HTTP认证（使用用户名和密码）的模板
     * 这里不使用
     */
//    @Autowired
//    private TestRestTemplate testRestTemplate;

    @Autowired
    SetupConstantMapper<SetupConstant> setupConstantMapper;

    /**
     * 2017-06-16 14:08:09.884  INFO 13803 --- [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
     size:5
     -----测试完毕-------
     2017-06-16 14:08:09.955  INFO 13803 --- [       Thread-4] ationConfigEmbeddedWebApplicationContext : Closing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@fd07cbb: startup date [Fri Jun 16 14:08:04 CST 2017]; root of context hierarchy
     */
    @Test
    public void test() {
    	
    	// 单条新增
    	SetupConstant test = new SetupConstant(null, null, "hhy", null, "hhy", null, null, null, "0", "hhy0", "hhy0", "hhy0", "hhy0");
    	test.setRemark("新增");
    	setupConstantMapper.create(test);
    	
    	// 批量新增
        List<SetupConstant> testlist = new ArrayList<SetupConstant>();
        for(int i = 1; i < 3; i++) {
        	SetupConstant tmp = new SetupConstant(null, null, "hhy", null, "hhy", null, null, null, "0", "hhy" + i, "hhy" + i, "hhy" + i, "hhy" + i);
        	tmp.setRemark("新增");
        	testlist.add(tmp);
        }
        setupConstantMapper.createBatch(testlist);
        //
        testlist.add(0, test);
        
        // 查询
        List<SetupConstant> resultlist = new ArrayList<SetupConstant>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupConstantMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 更新
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setRemark("更新");
        	setupConstantMapper.update(testlist.get(i));
        }
        resultlist = new ArrayList<SetupConstant>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupConstantMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 更新记录
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setRemark("更新记录");
        	setupConstantMapper.updateRecord(testlist.get(i));
        }
        resultlist = new ArrayList<SetupConstant>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupConstantMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 切换有效性
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setIsDisabled(true);
        	setupConstantMapper.shiftValidity(testlist.get(i));
        }
        resultlist = new ArrayList<SetupConstant>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupConstantMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 逻辑删除
        for(int i = 0; i < testlist.size(); i++) {
        	setupConstantMapper.logicalDelete(testlist.get(i));
        }
        resultlist = new ArrayList<SetupConstant>();
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setIsDeleted(null);
        	resultlist.addAll(setupConstantMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 删除
        for(int i = 0; i < testlist.size(); i++) {
        	setupConstantMapper.delete(testlist.get(i));
        }
    }
}

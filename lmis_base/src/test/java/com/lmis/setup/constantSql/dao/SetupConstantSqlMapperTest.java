package com.lmis.setup.constantSql.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lmis.setup.constantSql.model.SetupConstantSql;

/** 
 * @ClassName: SetupConstantSqlMapperTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年12月8日 下午5:14:09 
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SetupConstantSqlMapperTest {

	/**
     * Spring RestTemplate的便利替代。你可以获取一个普通的或发送基本HTTP认证（使用用户名和密码）的模板
     * 这里不使用
     */
//    @Autowired
//    private TestRestTemplate testRestTemplate;

    @Autowired
    SetupConstantSqlMapper<SetupConstantSql> setupConstantSqlMapper;

    /**
     * 2017-06-16 14:08:09.884  INFO 13803 --- [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
     size:5
     -----测试完毕-------
     2017-06-16 14:08:09.955  INFO 13803 --- [       Thread-4] ationConfigEmbeddedWebApplicationContext : Closing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@fd07cbb: startup date [Fri Jun 16 14:08:04 CST 2017]; root of context hierarchy
     */
    @Test
    public void test() {
    	
    	// 单条新增
    	SetupConstantSql test = new SetupConstantSql(null, null, "hhy", null, "hhy", null, null, null, "0", "hhy0", "hhy0", "新增");
    	setupConstantSqlMapper.create(test);
    	
    	// 批量新增
        List<SetupConstantSql> testlist = new ArrayList<SetupConstantSql>();
        for(int i = 1; i < 3; i++) {
        	testlist.add(new SetupConstantSql(null, null, "hhy", null, "hhy", null, null, null, "0", "hhy" + i, "hhy" + i, "新增"));
        }
        setupConstantSqlMapper.createBatch(testlist);
        //
        testlist.add(0, test);
        
        // 查询
        List<SetupConstantSql> resultlist = new ArrayList<SetupConstantSql>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupConstantSqlMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 更新
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setSqlRemark("更新");;
        	setupConstantSqlMapper.update(testlist.get(i));
        }
        resultlist = new ArrayList<SetupConstantSql>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupConstantSqlMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 更新记录
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setSqlRemark("更新记录");
        	setupConstantSqlMapper.updateRecord(testlist.get(i));
        }
        resultlist = new ArrayList<SetupConstantSql>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupConstantSqlMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 切换有效性
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setIsDisabled(true);
        	setupConstantSqlMapper.shiftValidity(testlist.get(i));
        }
        resultlist = new ArrayList<SetupConstantSql>();
        for(int i = 0; i < testlist.size(); i++) {
        	resultlist.addAll(setupConstantSqlMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 逻辑删除
        for(int i = 0; i < testlist.size(); i++) {
        	setupConstantSqlMapper.logicalDelete(testlist.get(i));
        }
        resultlist = new ArrayList<SetupConstantSql>();
        for(int i = 0; i < testlist.size(); i++) {
        	testlist.get(i).setIsDeleted(null);
        	resultlist.addAll(setupConstantSqlMapper.retrieve(testlist.get(i)));
        	System.out.println(resultlist.get(i).toString());
        }
        testlist = resultlist;
        
        // 删除
        for(int i=0; i < testlist.size(); i++) {
        	setupConstantSqlMapper.delete(testlist.get(i));
        }
    }
}

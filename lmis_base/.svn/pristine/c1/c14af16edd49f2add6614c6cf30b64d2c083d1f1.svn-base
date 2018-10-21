package com.lmis.setup.pageTable.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.pageTable.dao.SetupPageTableMapper;
import com.lmis.setup.pageTable.model.SetupPageTable;

/**
 * @ClassName: SetupPageTableMapperTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年12月8日 下午6:32:01
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SetupPageTableServiceTest {
	@Autowired
	SetupPageTableServiceInterface<SetupPageTable> setupPageTableService;
	
	@Autowired
	ObjectMapper mapper;
	
	@Value("${base.page.pageNum}")
    int defPageNum;

    @Value("${base.page.pageSize}")
    int defPageSize;
    
    @Autowired
    SetupPageTableMapper<SetupPageTable> setupPageTableMapper;
    
    private static final String COLUMNNAME = UUID.randomUUID().toString();
    
    /**
     * 测试新增查询列表
     * @throws Exception
     */
	public void testExecuteInsert() throws Exception {
    	DynamicSqlParam<SetupPageTable> dynamicSqlParam = new DynamicSqlParam<SetupPageTable>();
    	dynamicSqlParam.setLayoutId("P_YMBJ2_ADDTABLE_P1");
    	String jsonStr = "[{\"elementId\":\"P_YMBJ2_ADDTABLE_P1_E02\",\"value\":\""+COLUMNNAME+"\"},{\"elementId\":\"P_YMBJ2_ADDTABLE_P1_E03\",\"value\":\"P_YMBJ2_P02\"},{\"elementId\":\"P_YMBJ2_ADDTABLE_P1_E04\",\"value\":\"222\"},{\"elementId\":\"P_YMBJ2_ADDTABLE_P1_E08\",\"value\":\"0\"}]";
    	dynamicSqlParam.setElements(jsonStr);
    	LmisResult<?> result = setupPageTableService.executeInsert(dynamicSqlParam);
		assertTrue(result.getCode().equals(LmisConstant.RESULT_CODE_SUCCESS));
	}
    
	/**
	 * 测试修改查询列表
	 * @throws Exception
	 */
    @SuppressWarnings("rawtypes")
	@Transactional  
    @Rollback(true)
    @Test
	public void testUpdateSetupPageLayoutDynamic() throws Exception {
    	// 测试新增
    	testExecuteInsert();
    	
    	SetupPageTable setupPageTable = new SetupPageTable();
    	setupPageTable.setColumnName(COLUMNNAME);
    	
    	// 测试查询
    	DynamicSqlParam<SetupPageTable> dynamicSqlParam = new DynamicSqlParam<SetupPageTable>();
    	dynamicSqlParam.setLayoutId("P_YMBJ2_ADDTABLE_P1");
    	dynamicSqlParam.setId(setupPageTableMapper.retrieve(setupPageTable).get(0).getId());
    	Map map =  (Map) testCheckSetupPageLayoutDynamic(dynamicSqlParam).getData();
    	
    	System.out.println("map: " + JSON.toJSONString(map));
    	dynamicSqlParam.setId((String) map.get("id"));
    	String jsonStr = "[{\"elementId\":\"P_YMBJ2_ADDTABLE_P1_E01\",\"value\":\""+map.get("column_id")+"\"},{\"elementId\":\"P_YMBJ2_ADDTABLE_P1_E02\",\"value\":\""+COLUMNNAME+"\"},{\"elementId\":\"P_YMBJ2_ADDTABLE_P1_E03\",\"value\":\"P_YMBJ2_P02\"},{\"elementId\":\"P_YMBJ2_ADDTABLE_P1_E04\",\"value\":10},{\"elementId\":\"P_YMBJ2_ADDTABLE_P1_E05\",\"value\":\"300px\"}]";
    	dynamicSqlParam.setElements(jsonStr);
    	
    	// 修改
    	LmisResult<?> result = setupPageTableService.executeUpdate(dynamicSqlParam);
		assertNotNull(result);
		assertTrue(result.getCode().equals(LmisConstant.RESULT_CODE_SUCCESS));
	}
    
    @Transactional  
    @Rollback(true)
    @Test(expected = Exception.class)
	public void testInsertSetupPageTableHasIdDynamic() throws Exception {
		// 测试新增查询列表手动指定 ID
    	DynamicSqlParam<SetupPageTable> dynamicSqlParam = new DynamicSqlParam<SetupPageTable>();
    	dynamicSqlParam.setLayoutId("P_YMBJ2_ADDTABLE_P1");
    	
    	String jsonStr = "[{\"elementId\":\"P_YMBJ2_ADDTABLE_P1_E01\",\"value\":\"T_111\"},{\"elementId\":\"P_YMBJ2_ADDTABLE_P1_E02\",\"value\":\""+COLUMNNAME+"\"},{\"elementId\":\"P_YMBJ2_ADDTABLE_P1_E03\",\"value\":\"P_YMBJ2_P02\"},{\"elementId\":\"P_YMBJ2_ADDTABLE_P1_E04\",\"value\":\"50\"},{\"elementId\":\"P_YMBJ2_ADDTABLE_P1_E08\",\"value\":\"0\"}]";
    	dynamicSqlParam.setElements(jsonStr);
    	LmisResult<?> result = setupPageTableService.executeInsert(dynamicSqlParam);
		assertNotNull(result);
		assertTrue(result.getCode().equals(LmisConstant.RESULT_CODE_SUCCESS));
	}
    
    
    /**
     * 测试获取查询列表
     * @param dynamicSqlParam
     * @return
     * @throws Exception
     */
	public LmisResult<?> testCheckSetupPageLayoutDynamic(DynamicSqlParam<SetupPageTable> dynamicSqlParam) throws Exception {
    	return setupPageTableService.executeSelect(dynamicSqlParam);
	}
    
    @Test
	public void testRedisForPageTablesPipeline() throws Exception {
    	// 测试批量同步查询结果
    	LmisResult<?> result = setupPageTableService.redisForPageTablesPipeline();
    	assertTrue("sync success: ", LmisConstant.RESULT_CODE_SUCCESS.equals(result.getCode()));
	}
    
    @Test
    public void testGetSetupPageTablesCount()  throws Exception{
    	// 测试查询结果统计
    	DynamicSqlParam<SetupPageTable> dynamicSqlParam = new DynamicSqlParam<SetupPageTable>();
    	dynamicSqlParam.setLayoutId("P_YMBJ2_P02");
    	dynamicSqlParam.setElements("[{\"elementId\":\"P_YMBJ2_P01_E01\",\"value\":\"P_YMBJ2\"}]");
    	dynamicSqlParam.setIsDeleted(false);
    	LmisResult<?> result = setupPageTableService.getSetupPageTablesCount(dynamicSqlParam);
    	assertTrue("count success: ", LmisConstant.RESULT_CODE_SUCCESS.equals(result.getCode()));
    }
}

package com.lmis.setup.pageElement.service;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.pageElement.dao.SetupPageElementMapper;
import com.lmis.setup.pageElement.model.SetupPageElement;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SetupPageElementServiceInterfaceTest {
	
	@Autowired
	SetupPageElementServiceInterface<SetupPageElement> setupPageElementService;
	
	@Autowired
    SetupPageElementMapper<SetupPageElement> setupPageElementMapper;
	
	@Autowired
	ObjectMapper mapper;
	
	@Value("${base.page.pageNum}")
    int defPageNum;

    @Value("${base.page.pageSize}")
    int defPageSize;
    
    private static final String NEWSETUPPAGEELEMENTNAME = UUID.randomUUID().toString();
    
    /**
     * 测试新增页面元素
     * @throws Exception
     */
    @Before
    @Transactional  
    @Rollback(true)
    @Test
    public void mockCreateSetupPageElement() throws Exception {
		DynamicSqlParam<SetupPageElement> dynamicSqlParam = new DynamicSqlParam<SetupPageElement>();
		dynamicSqlParam.setLayoutId("P_YMBJ2_ADDELE_P01");
		String jsonStr = "[{\"elementId\":\"P_YMBJ2_ADDELE_P01_E02\",\"value\":\"" + NEWSETUPPAGEELEMENTNAME
				+ "\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E03\",\"value\":\"C_S_RIKI_0_T_1\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E04\",\"value\":\"100\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E05\",\"value\":\"10%\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E07\",\"value\":\"10\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E09\",\"value\":\"h_e_input\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E13\",\"value\":\"bt_right\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E21\",\"value\":\"0\"}]";
		dynamicSqlParam.setElements(jsonStr);
		setupPageElementService.executeInsert(dynamicSqlParam);
    }
	
    /**
     * 获取页面元素分页数据
     * @throws Exception
     */
	@Test
	public void testExecuteSelect() throws Exception {
		DynamicSqlParam<SetupPageElement> dynamicSqlParam = new DynamicSqlParam<SetupPageElement>();
		dynamicSqlParam.setLayoutId("P_YMBJ2_P02");
		String queryJsonStr = "[{\"elementId\":\"P_YMBJ2_P01_E01\",\"value\":\"P_YMBJ\"}]";
		dynamicSqlParam.setIsDeleted(false);
		dynamicSqlParam.setElements(queryJsonStr);
		LmisPageObject pageObject = new LmisPageObject();
		pageObject.setDefaultPage(defPageNum,defPageSize);
		LmisResult<?> result = setupPageElementService.executeSelect(dynamicSqlParam, pageObject);
		assertTrue("success", result.getCode().equals(LmisConstant.RESULT_CODE_SUCCESS));
	}
	
	@Transactional  
    @Rollback(true)
	@Test(expected = Exception.class)
	public void testAddSetupPageElementNoIdDynamic() throws Exception {
		// 测试公共传参新增页面元素手动赋值 ID
		DynamicSqlParam<SetupPageElement> dynamicSqlParam = new DynamicSqlParam<SetupPageElement>();
		dynamicSqlParam.setLayoutId("P_YMBJ2_ADDELE_P01");
		String jsonStr = "[{\"elementId\":\"P_YMBJ2_ADDELE_P01_E01\",\"value\":\"P4\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E02\",\"value\":\"测试元素3\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E03\",\"value\":\"C_S_RIKI_0_T_1\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E04\",\"value\":\"100\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E05\",\"value\":\"10%\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E07\",\"value\":\"10\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E09\",\"value\":\"h_e_input\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E13\",\"value\":\"bt_right\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E21\",\"value\":\"0\"}]";
		dynamicSqlParam.setElements(jsonStr);
		setupPageElementService.executeInsert(dynamicSqlParam);
	}
	
	/**
	 * 测试公共传参修改页面元素
	 * @throws Exception
	 */
	@Transactional  
    @Rollback(true)
	@Test
	public void testUpdateSetupPageElementDynamic() throws Exception {
		
		// 先查询出之前新创建的元素
		DynamicSqlParam<SetupPageElement> dynamicSqlParam = new DynamicSqlParam<SetupPageElement>();
		dynamicSqlParam.setLayoutId("P_YMBJ2_ADDELE_P01");
		SetupPageElement setupPageElement = new SetupPageElement();
		setupPageElement.setElementName(NEWSETUPPAGEELEMENTNAME);
		
		SetupPageElement searchElement = setupPageElementMapper.retrieve(setupPageElement).get(0);
		dynamicSqlParam.setId(searchElement.getId());
		
		String updateElementName = UUID.randomUUID().toString();
		// 公共传参修改页面元素名称
		String updateStr = "[{\"elementId\":\"P_YMBJ2_ADDELE_P01_E01\",\"value\":\""+searchElement.getElementId()+"\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E02\",\"value\":\""+updateElementName+"\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E03\",\"value\":\"C_S_RIKI_0_T_1\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E04\",\"value\":\"100\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E05\",\"value\":\"10%\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E07\",\"value\":\"10\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E09\",\"value\":\"h_e_input\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E13\",\"value\":\"bt_right\"},{\"elementId\":\"P_YMBJ2_ADDELE_P01_E21\",\"value\":\"0\"}]";
		dynamicSqlParam.setElements(updateStr);
		LmisResult<?> result = setupPageElementService.executeUpdate(dynamicSqlParam);
		assertTrue(result.getCode().equals(LmisConstant.RESULT_CODE_SUCCESS));
		// 验证是否修改成功
		setupPageElement.setElementName(updateElementName);
		assertTrue(setupPageElement.getElementName().equals(setupPageElementMapper.retrieve(setupPageElement).get(0).getElementName()));
	}
	
	/**
	 * 测试获取单个页面元素
	 * @throws Exception
	 */
	@Test
	public void testCheckSetupPageElement() throws Exception {
		DynamicSqlParam<SetupPageElement> dynamicSqlParam = new DynamicSqlParam<SetupPageElement>();
		dynamicSqlParam.setLayoutId("P_YMBJ2_ADDELE_P01");
		SetupPageElement setupPageElement = new SetupPageElement();
		setupPageElement.setElementName(NEWSETUPPAGEELEMENTNAME);
		
		SetupPageElement searchElement = setupPageElementMapper.retrieve(setupPageElement).get(0);
		dynamicSqlParam.setId(searchElement.getId());
		
		LmisResult<?> result = setupPageElementService.executeSelect(dynamicSqlParam);
		assertTrue(result.getCode().equals(LmisConstant.RESULT_CODE_SUCCESS));
	}
	
	@Test
	@Ignore
	public void testSync() throws Exception {
		// 测试同步页面元素数据
		LmisResult<?> result = setupPageElementService.redisForPageElements();
		assertTrue("success: ", LmisConstant.RESULT_CODE_SUCCESS.equals(result.getCode()));
	}
	
	@Test
	public void testPipeline() throws Exception{
		// 测试  Mset 操作，减少同步耗费时间
		setupPageElementService.redisForPageElementsMset();
	}
}

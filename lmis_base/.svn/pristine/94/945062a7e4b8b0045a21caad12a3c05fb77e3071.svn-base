package com.lmis.setup.pageLayout.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
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
import com.lmis.common.util.BaseUtils;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.pageLayout.dao.SetupPageLayoutMapper;
import com.lmis.setup.pageLayout.model.SetupPageLayout;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SetupPageLayoutServiceInterfaceTest {	
	@Autowired
	SetupPageLayoutServiceInterface<SetupPageLayout> setupPageLayoutService;

	@Autowired
	SetupPageLayoutMapper<SetupPageLayout> setupPageLayoutMapper;
	
	@Autowired
	ObjectMapper mapper;
	
	@Value("${base.page.pageNum}")
    int defPageNum;

    @Value("${base.page.pageSize}")
    int defPageSize;
    
    @Autowired
    BaseUtils baseUtils;
    
    private static final String NEW_SETUP_PAGE_LAYOUTNAME = UUID.randomUUID().toString();
    
    /**
     * 新增页面布局
     * @throws Exception
     */
	public void testAddSetupPageLayoutDynamic() throws Exception {
		DynamicSqlParam<SetupPageLayout> dynamicSqlParam = new DynamicSqlParam<SetupPageLayout>();
		dynamicSqlParam.setLayoutId("P_YMBJ2_ADD_P01");
		String jsonStr = "[{\"elementId\":\"P_YMBJ2_ADD_P01_E02\",\"value\":\"P_YMBJ2\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E03\",\"value\":\""+NEW_SETUP_PAGE_LAYOUTNAME+"\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E04\",\"value\":\"111\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E09\",\"value\":\"layout_a\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E11\",\"value\":\"0\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E12\",\"value\":\"0\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E13\",\"value\":\"0\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E14\",\"value\":\"0\"}]";
		dynamicSqlParam.setIsDeleted(false);
		dynamicSqlParam.setElements(jsonStr);
		LmisResult<?> result = setupPageLayoutService.executeInsert(dynamicSqlParam);
		assertNotNull(result);
		assertTrue(result.getCode().equals(LmisConstant.RESULT_CODE_SUCCESS));
	}
	
    /**
     * 查询页面布局
     * @throws Exception
     */
	@Test
	public void testSelectSetupPageLayoutDynamic() throws Exception {
		DynamicSqlParam<SetupPageLayout> dynamicSqlParam = new DynamicSqlParam<SetupPageLayout>();
		dynamicSqlParam.setLayoutId("P_YMBJ2_P02");
		String queryJsonStr = "[{\"elementId\":\"P_YMBJ2_P01_E01\",\"value\":\"P_YMBJ\"}]";
		dynamicSqlParam.setIsDeleted(false);
		dynamicSqlParam.setElements(queryJsonStr);
		LmisPageObject pageObject = new LmisPageObject();
		pageObject.setDefaultPage(0,10000);
		LmisResult<?> result = setupPageLayoutService.executeSelect(dynamicSqlParam,pageObject);
		assertNotNull(result);
		assertTrue(result.getCode().equals(LmisConstant.RESULT_CODE_SUCCESS));
	}
	
	@Transactional  
    @Rollback(true)
	@Test
	public void testAddSetupPageLayoutNoIdDynamic() throws Exception {
		DynamicSqlParam<SetupPageLayout> dynamicSqlParam = new DynamicSqlParam<SetupPageLayout>();
		dynamicSqlParam.setLayoutId("P_YMBJ2_ADD_P01");
		String jsonStr = "[{\"elementId\":\"P_YMBJ2_ADD_P01_E02\",\"value\":\"P_YMBJ2\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E03\",\"value\":\""+NEW_SETUP_PAGE_LAYOUTNAME+"\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E04\",\"value\":\"30\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E05\",\"value\":\"100%\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E07\",\"value\":\"100%\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E09\",\"value\":\"layout_a\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E11\",\"value\":\"0\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E12\",\"value\":\"0\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E13\",\"value\":\"0\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E14\",\"value\":\"0\"}]";
		dynamicSqlParam.setIsDeleted(false);
		dynamicSqlParam.setElements(jsonStr);
		setupPageLayoutService.executeInsert(dynamicSqlParam);
	}
	
	/**
	 * 公共传参修改页面布局
	 * @throws Exception
	 */
	@Transactional  
    @Rollback(true)
	@Test
	public void testUpdateSetupPageLayoutDynamic() throws Exception {
		testAddSetupPageLayoutDynamic();
		
		DynamicSqlParam<SetupPageLayout> dynamicSqlParam = new DynamicSqlParam<SetupPageLayout>();
		dynamicSqlParam.setLayoutId("P_YMBJ2_ADD_P01");
		String updateLayoutName = UUID.randomUUID().toString();
		SetupPageLayout searchParam = new SetupPageLayout();
		searchParam.setLayoutName(NEW_SETUP_PAGE_LAYOUTNAME);
		String existsLayotuId = setupPageLayoutMapper.retrieve(searchParam).get(0).getLayoutId();
		assertNotNull(existsLayotuId);
		
		String jsonStr = "[{\"elementId\":\"P_YMBJ2_ADD_P01_E01\",\"value\":\""+existsLayotuId+"\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E02\",\"value\":\"P_YMBJ2\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E03\",\"value\":\""+updateLayoutName+"\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E04\",\"value\":\"30\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E05\",\"value\":\"100%\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E07\",\"value\":\"100%\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E09\",\"value\":\"layout_a\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E11\",\"value\":\"0\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E12\",\"value\":\"0\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E13\",\"value\":\"0\"},{\"elementId\":\"P_YMBJ2_ADD_P01_E14\",\"value\":\"0\"}]";
		dynamicSqlParam.setIsDeleted(false);
		dynamicSqlParam.setElements(jsonStr);
		searchParam.setLayoutName(NEW_SETUP_PAGE_LAYOUTNAME);
		List<SetupPageLayout> setupPageLayouts = setupPageLayoutMapper.retrieve(searchParam);
		dynamicSqlParam.setId(setupPageLayouts.get(0).getId());
		
		LmisResult<?> result = setupPageLayoutService.executeUpdate(dynamicSqlParam);
		assertNotNull(result);
		assertTrue(result.getCode().equals(LmisConstant.RESULT_CODE_SUCCESS));
		
		searchParam.setLayoutName(updateLayoutName);
		// 验证修改成功
		List<SetupPageLayout> updateSetupPageLayouts = setupPageLayoutMapper.retrieve(searchParam);
		assertNotNull(updateSetupPageLayouts.get(0).getLayoutName().equals(updateLayoutName));
	}
	
	/**
	 * 测试获取页面布局
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testPreviewSetupPageLayout() throws Exception {
		SetupPageLayout setupPageLayout = new SetupPageLayout();
		setupPageLayout.setPageId("P_YMGX");
		LmisResult<?> result = setupPageLayoutService.previewSetupPageLayout(setupPageLayout);
		assertTrue(result.getCode().equals(LmisConstant.RESULT_CODE_SUCCESS));
	}
	
	/**
	 * 测试获取生成的页面编号
	 */
	@Transactional  
    @Rollback(true)
	@Test
	public void testGenerateId() {
		String id = baseUtils.GetCodeRule("PAGE_NUM");
		assertNotNull(id);
	}
}

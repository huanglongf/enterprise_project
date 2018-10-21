package com.lmis.setup.page;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.page.model.SetupPage;
import com.lmis.setup.page.service.SetupPageServiceInterface;
import com.lmis.setup.page.service.impl.SetupPageServiceImpl;
import com.lmis.setup.pageElement.model.SetupPageElement;
import com.lmis.setup.pageElement.service.SetupPageElementServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SetupPageServiceInterfaceTest {

	@Autowired
	SetupPageServiceInterface<SetupPage> setupPageService;
	
	@Transactional  
    @Rollback(true)
	@Test
	public void testCopySetupPage() throws Exception {
		// 测试复制页面布局
		LmisResult<?> result = setupPageService.copySetupPage("P_YMBJ", "P_YMBJ2");
		assertTrue("success:", result.getCode().equals(LmisConstant.RESULT_CODE_SUCCESS));
	}

}

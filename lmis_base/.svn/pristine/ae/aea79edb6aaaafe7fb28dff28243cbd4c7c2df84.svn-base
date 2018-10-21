package com.lmis.setup.constantSql.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.pageElement.model.SetupPageElement;
import com.lmis.setup.pageElement.service.SetupPageElementServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SetupConstantSqlServiceImplTest {
	
	@Autowired
	SetupConstantSqlServiceInterface<SetupPageElement> setupConstantSqlService;
	
	@Test
	public void checkValidSQLTest() throws Exception {
		// select 语句中 表不存在，也可以保存成功，不做限制,sql 语句错误也可以保存成功
		String sql = "select * from user where a.name = ? and 1=1；";
		LmisResult<?> result = setupConstantSqlService.checkValidSQL(sql);
		assertTrue(LmisConstant.RESULT_CODE_SUCCESS.equals(result.getCode()));
	}

}

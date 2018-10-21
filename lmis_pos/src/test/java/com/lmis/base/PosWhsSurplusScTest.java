package com.lmis.base;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.whsSurplusSc.model.PosWhsSurplusSc;
import com.lmis.pos.whsSurplusSc.service.PosWhsSurplusScServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PosWhsSurplusScTest {

    /**
     * Spring RestTemplate的便利替代。你可以获取一个普通的或发送基本HTTP认证（使用用户名和密码）的模板
     * 这里不使用
     */
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Resource(name = "posWhsSurplusScServiceImpl")
    PosWhsSurplusScServiceInterface<PosWhsSurplusSc> posWhsSurplusScServiceInterface;


    /**
     * 2017-06-16 14:08:09.884  INFO 13803 --- [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
     size:5
     -----测试完毕-------
     2017-06-16 14:08:09.955  INFO 13803 --- [       Thread-4] ationConfigEmbeddedWebApplicationContext : Closing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@fd07cbb: startup date [Fri Jun 16 14:08:04 CST 2017]; root of context hierarchy
     */
    @Test
    public void test(){
    	LmisResult<?> result = null;
    	try {
    		result = posWhsSurplusScServiceInterface.updatePlannedInAbility(
					"BJBZ", "10", "2018-06-02", 1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("-------------------");
        System.out.println("返回结果:result=" + result);
        System.out.println("------测试完毕-------");

    }
}

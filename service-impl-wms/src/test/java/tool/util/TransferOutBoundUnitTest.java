/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package tool.util;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.test.wms.manager.TestOutboundManager;
import com.jumbo.dao.StaTestDao;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * 
 * @author lzb nikeCRW转店定制 测试点 1:转店单据创建 2：执行出库数据定制生成 3：定制数据生成文件
 */
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:test/spring-test.xml"})
public class TransferOutBoundUnitTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    TestOutboundManager testOutboundManager;
    @Autowired
    StaTestDao staTestDao;

    private StockTransApplication staData;


    /**
     * 
     * @author LZB
     * @throws Exception 描述：转店单据创建
     */
    @Before
    public void befortGetSta() throws Exception {
        System.out.println("before");
        staData = testOutboundManager.createStaForVMITransfer("1Nike官方旗舰店", "1NIKE中国官方商城", 1061L, 9L, 12727L, 123L);

    }

    /**
     * 
     * @author LZB
     * @throws Exception 描述：执行出库数据定制生成 并验证是否正确 assertEquals:抛异常（数据异常）否：成功
     */
    @Test
    public void testGenerateRtwData() throws Exception {
        System.out.println("getSta");
        System.out.println("staCode:" + staData.getCode());
        testOutboundManager.executeVmiTransferOutBound(staData.getId(), 12727L, 1061L);
        boolean b = testOutboundManager.checkResult(staData.getId());
        assertEquals("ERROR", b, true);
    }

    /**
     * 
     * @author LZB
     * @throws Exception 描述：定制数据生成文件
     */
    @After
    public void afrerGetSta() throws Exception {
        System.out.println("after");
        testOutboundManager.updateNikeFile();
        // testOutboundManager.removeTestData(staData.getCode());
    }

}

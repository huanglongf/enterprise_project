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
package tool;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jumbo.dao.pickZone.WhOcpOrderLineDao;
import com.jumbo.wms.manager.pickZone.NewInventoryOccupyManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.AreaOcpStaManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.automaticEquipment.ZoonOcpSort;

/**
 * 
 * @author dailingyun
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:spring.xml"})
public class TaskTest extends AbstractJUnit4SpringContextTests {

    /*
     * @Autowired private CaiNiaoConsignmentSnTask caiNiaoConsignmentSnTask;
     */
    /*
     * @Autowired private YamatoConfirmOrderQueueDao dao;
     */

    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private AreaOcpStaManager areaOcpStaManager;
    @Autowired
    private NewInventoryOccupyManager newInventoryOccupyManager;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private WhOcpOrderLineDao whOcpOrderLineDao;

    @Test
    public void testExecute() {

    }
    // areaOcpStaManager.createMongDbTableForOcp();
    // areaOcpStaManager.deleteInventoryForAreaOcpInv();
    // areaOcpStaManager.initInventoryForOnceUseByOwner("1飞利浦官方旗舰店");
    // areaOcpStaManager.initConfigByStaId(71965030l);
    // List<Long> staList = wareHouseManager.ocpStaByOcpCode(5933l, null, 20000, null, true,
    // null);
    // for (Long staId : staList) {
    // areaOcpStaManager.initConfigByStaId(staId);
    // }
    // areaOcpStaManager.ocpInvByStaId(61314490l);
    /**
     * mongdb初始化
     */
    // List<Long> staList = areaOcpStaManager.findOcpStaByOuId(10000, 5933l);
    // if (staList == null || staList.size() == 0) { // 如没有需占用的订单，则结束
    // return;
    // }
    // List<String> areaList = areaOcpStaManager.findAllAreaCode(5933l);
    // if (areaList == null || areaList.size() == 0) { // 如没有可用的区域，则结束
    // return;
    // }
    // // 仓库区域占用库存的线程数
    // ChooseOption co =
    // chooseOptionManager.findChooseOptionByCategoryCodeAndKey(OcpInvConstants.OCP_CHOOSE_GROUP_SYS_WH,
    // OcpInvConstants.AREA_OCP_THREAD_COUNT + 5933l);
    // // MongDB计算库存线程数
    // ChooseOption co2 =
    // chooseOptionManager.findChooseOptionByCategoryCodeAndKey(OcpInvConstants.OCP_CHOOSE_GROUP_SYS_WH,
    // OcpInvConstants.AREA_OCP_MONGDB_COUNT + 5933l);
    // Integer mongDbamount = 200;
    // Integer amount = 200;
    // if (co != null) {
    // amount = Integer.parseInt(co.getOptionValue());
    // }
    // if (co2 != null) {
    // mongDbamount = Integer.parseInt(co2.getOptionValue());
    // }
    // areaOcpStaManager.deleteInventoryForAreaOcpInv(5933l); // MongDb删库
    // areaOcpStaManager.createMongDbTableForOcp(5933l); // MongDb建库
    // /**
    // * 初始化MongDb库存， by 区域多线程， 再by库存行多线程
    // */
    // for (String area : areaList) {
    // // 每区域一个线程
    // List<MongoDBInventoryOcp> mongDbList = areaOcpStaManager.findMongDbInvList(area, 5933l);
    // for (MongoDBInventoryOcp ocp : mongDbList) {
    // areaOcpStaManager.initInventoryForOcpAreaByOwner(ocp, 5933l);
    // }
    // }
    // areaOcpStaManager.ocpInvByStaId(71965030l);
    /*
     * Long sum = 0L; Long cd = null; StringBuffer sb=new StringBuffer(); // 初始化邮件分发码字符数组 String[]
     * mailCode = new String[] {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"}; // 初始化权重码
     * String[] wight = new String[] {"4", "6", "2", "3", "1", "5", "4", "6", "2", "3", "1"}; String
     * ymtTrackingNo = null; // 得到宝尊系统邮件分发码 // YamatoTransNoCache yamatoTransNoCache =
     * yamatoTransNoCacheDao.findmileCode(0,new
     * BeanPropertyRowMapper<YamatoTransNoCache>(YamatoTransNoCache.class)); YamatoTransNoCache
     * yamatoTransNoCache=null; //缓存单号使用完毕，邮件通知开发手动维护 if (yamatoTransNoCache==null){
     * MailUtil.sendMail("香港yamato缓存单号不足提醒", "jinggang.chen@baozun.cn", "", "黑猫物流缓存单号使用完毕，请手动维护",
     * false, null);
     * 
     * } String code=yamatoTransNoCache.getMailCode(); for (int i = 0; i < code.length(); i++) {
     * mailCode[mailCode.length - 1 - i] = String.valueOf(code.charAt(code.length() - 1 - i)); } //
     * 得到邮件分发码和终止码之间的cd码，权重和邮件分发码对应位相乘相加在余7得到的数即cd码。 for (int j = 0; j < wight.length; j++) { sum =
     * sum + Integer.parseInt(mailCode[j]) * Integer.parseInt(wight[j]); sb.append(mailCode[j]); }
     * cd = sum % 7; // 得到最终yamato运单号 ymtTrackingNo = "a" +sb+ String.valueOf(cd) + "a";
     * yamatoTransNoCache.setIsCod(true);
     */
    // yamatoTransNoCacheDao.save(yamatoTransNoCache);
    // yamatoTransNoCache.setMailCode(ymtTrackingNo);
    // .exeSetAllTransNo();
    // yamatoTransInfoToHub.sendYamatoTransInfoToHub();

    // sfOrderTaskManager.matchingTransNo("YAMATO", 4837L, 29907137L);
    /*
     * public static void main(String args[]) { System.out.println(!StringUtil.isEmpty("we"));
     * System.out.println("ceshi "); }
     */
}

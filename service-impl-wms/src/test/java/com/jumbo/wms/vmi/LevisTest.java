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
package com.jumbo.wms.vmi;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.jumbo.dao.vmi.levis.LevisDeliveryOrderDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.daemon.IdsTask;
import com.jumbo.wms.manager.task.vmi.LevisTaskImpl;
import com.jumbo.wms.manager.vmi.levisData.LevisManager;

/**
 * 
 * @author dailingyun
 * 
 */

@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:spring.xml"})
public class LevisTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private LevisDeliveryOrderDao levisDeliveryOrderDao;
    @Autowired
    private LevisManager levisManager;
    @Autowired
    protected IdsTask IdsTask;

    @Autowired
    @Test
    public void testExecute() {
        // 文件下载
        // levisTask.downloadFile();


        /*** 文件读取 此处文件读取 需要手动从ftp将文件下载下来再进行读取 **/
        // fileRead("E:/file/", "E:/file/backup/");

        // 数据生成
        // createSta();
        levisManager.generateInboundStaByCode("41261723");
        // 等待创建SKU数据
        // try {
        // Thread.sleep(120000L);
        // } catch (InterruptedException e) {
        // }
        // 数据生成
        // createSta();
        // levisManager.createSkmrData("2015-02-06");
        // levisManager.createSkmrData("2015-02-07");
        // levisManager.createSkmrData("2015-02-08");
        // levisManager.createSkmrData("2015-02-09");whe
        //
        // IdsTask.feedbackIds();
    }

    /**
     * 报表生成
     */
    protected void createSkmr() {
        levisManager.createSkmrData("");
    }

    /**
     * 执行未操作的数据
     */
    protected void createSta() {
        List<String> pocodes = levisDeliveryOrderDao.findUnDoPoCode(new SingleColumnRowMapper<String>(String.class));
        for (String poCode : pocodes) {
            try {
                // if (poCode.equals("41218437"))
                levisManager.generateInboundStaByCode(poCode);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 文件数据读取到中间表
     * 
     * @param localPath 文件目录
     * @param localBackupPath 备份目录
     */
    protected void fileRead(String localPath, String localBackupPath) {
        if (!StringUtil.isEmpty(localPath)) {
            File f = new File(localPath);
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                if (files != null && files.length > 0) {
                    for (File file : files) {
                        if (file.getName().startsWith(LevisTaskImpl.LEVIS_STOCKIN_FILE_NAME_START)) {
                            levisManager.readStockInFile(file, localBackupPath);
                        }
                    }
                }
            }
        }
    }
}

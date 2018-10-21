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
 */
package com.jumbo.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.wms.daemon.JdOrderTask;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;

/**
 * @author lichuan
 * 
 */
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:spring.xml"})
public class JdOrderTaskTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private JdOrderTask jdOrderTask;
    @Autowired
    private WhTransProvideNoDao whTransProvideNoDao;
    @Autowired
    private BiChannelDao biChannelDao;

    @Test
    public void toHubGetJdTransNoTest() {
        jdOrderTask.toHubGetJdTransNo();
    }
    
    @Test
    public void insertTransTest(){
        List<String> trans = new ArrayList<String>();
        trans.add("554371");
        trans.add("554372");
        trans.add("554373");
        trans.add("554374");
        trans.add("554375");
        trans.add("554376");
        trans.add("554377");
        trans.add("554378");
        trans.add("554379");
        trans.add("554380");
        trans.add("554381");
        trans.add("554382");
        trans.add("554383");
        trans.add("554384");
        trans.add("554385");
        for(String t : trans){
            BiChannel biChannel= biChannelDao.getByPrimaryKey(702L);
            WhTransProvideNo whTransProvideNo = new WhTransProvideNo();
            whTransProvideNo.setLpcode("JD");
            whTransProvideNo.setTransno(t);
            whTransProvideNo.setOwner(biChannel.getCode());
            Date createTime = new Date();
            whTransProvideNo.setCreateTime(createTime);
            //75天后过期
            long cTime = createTime.getTime();
            long eTime = cTime + 1000*60*60*24*75L;
            Date expTime = new Date(eTime);
            whTransProvideNo.setExpTime(expTime);
            try {
                whTransProvideNoDao.save(whTransProvideNo);
            } catch (Exception e) {
            }
        }
    }
}

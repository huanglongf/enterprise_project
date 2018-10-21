package com.lmis.sys.codeRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.sys.codeRule.dao.SysCoderuleDataMapper;
import com.lmis.sys.codeRule.dao.SysCoderuleInfoMapper;
import com.lmis.sys.codeRule.model.SysCoderuleData;
import com.lmis.sys.codeRule.model.SysCoderuleInfo;

import cn.hutool.core.util.RandomUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InfoTest {

	
    @Autowired
    private SysCoderuleInfoMapper<SysCoderuleInfo> sysCoderuleInfoMapper;
    @Autowired
    private SysCoderuleDataMapper<SysCoderuleData> sysCoderuleDataMapper;

    /**
     * 保存格式为 常量+日期+自增
     */
    @Transactional  
    @Rollback(true)
    @Test
    public void testSave1()
    {
        SysCoderuleInfo sysCoderuleInfo=new SysCoderuleInfo();
        sysCoderuleInfo.setId(RandomUtil.randomUUID());
        sysCoderuleInfo.setConfigName("A01");
        sysCoderuleInfo.setConfigCode("A01");
        //模拟配置多个数据
        List<SysCoderuleData> list=new ArrayList<>();
        SysCoderuleData sysCoderuleData=new SysCoderuleData();
        sysCoderuleData.setConfigCode(sysCoderuleInfo.getConfigCode());
        sysCoderuleData.setRuleCode("AAA");
        sysCoderuleData.setStartValue("LMIX");
        sysCoderuleData.setDataValuelg(sysCoderuleData.getStartValue().length());
        sysCoderuleData.setDataOrder(1);
        list.add(sysCoderuleData);
        SysCoderuleData sysCoderuleData2=new SysCoderuleData();
        sysCoderuleData2.setConfigCode(sysCoderuleInfo.getConfigCode());
        sysCoderuleData2.setRuleCode("BBB");
        sysCoderuleData2.setDataOrder(2);
        list.add(sysCoderuleData2);
        SysCoderuleData sysCoderuleData3=new SysCoderuleData();
        sysCoderuleData3.setConfigCode(sysCoderuleInfo.getConfigCode());
        sysCoderuleData3.setRuleCode("CCC");
        sysCoderuleData3.setDataOrder(3);
        list.add(sysCoderuleData3);
        sysCoderuleInfoMapper.create(sysCoderuleInfo);
        sysCoderuleDataMapper.createBatch(list);
        System.out.println("ok");
    }
    
    /**
     * 保存格式为 常量+自增+日期+随机值
     */
    @Transactional  
    @Rollback(true)
    @Test
    public void testSave2()
    {
        SysCoderuleInfo sysCoderuleInfo=new SysCoderuleInfo();
        sysCoderuleInfo.setId(RandomUtil.randomUUID());
        sysCoderuleInfo.setConfigName("A02");
        sysCoderuleInfo.setConfigCode("A02");
        //模拟配置多个数据
        List<SysCoderuleData> list=new ArrayList<>();
        SysCoderuleData sysCoderuleData=new SysCoderuleData();
        sysCoderuleData.setConfigCode(sysCoderuleInfo.getConfigCode());
        sysCoderuleData.setRuleCode("AAA");
        sysCoderuleData.setStartValue("LMIX");
        sysCoderuleData.setDataValuelg(sysCoderuleData.getStartValue().length());
        sysCoderuleData.setDataOrder(1);
        list.add(sysCoderuleData);
        SysCoderuleData sysCoderuleData2=new SysCoderuleData();
        sysCoderuleData2.setConfigCode(sysCoderuleInfo.getConfigCode());
        sysCoderuleData2.setRuleCode("CCC");
        sysCoderuleData2.setDataOrder(2);
        list.add(sysCoderuleData2);
        SysCoderuleData sysCoderuleData3=new SysCoderuleData();
        sysCoderuleData3.setConfigCode(sysCoderuleInfo.getConfigCode());
        sysCoderuleData3.setRuleCode("BBB");
        sysCoderuleData3.setDataOrder(3);
        list.add(sysCoderuleData3);
        SysCoderuleData sysCoderuleData4=new SysCoderuleData();
        sysCoderuleData4.setConfigCode(sysCoderuleInfo.getConfigCode());
        sysCoderuleData4.setRuleCode("DDD");
        sysCoderuleData4.setDataOrder(4);
        list.add(sysCoderuleData4);
        sysCoderuleInfoMapper.create(sysCoderuleInfo);
        sysCoderuleDataMapper.createBatch(list);
        System.out.println("ok");
    }
    
    @Test
    public void select()
    {
//        int dataCountByConfigCode = sysCoderuleDataMapper.getDataCountByConfigCode("A01");
//        System.out.println(dataCountByConfigCode);
    }
    
}

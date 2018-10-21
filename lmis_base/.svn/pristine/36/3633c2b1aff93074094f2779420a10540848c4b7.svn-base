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

import com.lmis.sys.codeRule.dao.SysCoderuleRuleMapper;
import com.lmis.sys.codeRule.enums.RuleConfigEnum;
import com.lmis.sys.codeRule.model.SysCoderuleRule;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RuleTest {

	
//    @Autowired
//    private SysCoderuleConfigServiceInterface<SysCoderuleConfig>  sysCoderuleConfigService;
    
    @Autowired
    private SysCoderuleRuleMapper<SysCoderuleRule>  sysCoderuleConfigMapper;
    @Transactional  
    @Rollback(true)
    @Test
    public void testSave()
    {
        List<SysCoderuleRule> list=new ArrayList<>();
        SysCoderuleRule sysCoderuleConfig=new SysCoderuleRule();
        sysCoderuleConfig.setRuleName("时间");
        sysCoderuleConfig.setRuleOrder(5);
        sysCoderuleConfig.setDataType(RuleConfigEnum.时间.getIndex());
        sysCoderuleConfig.setRuleCode("EEE");
        list.add(sysCoderuleConfig);
        SysCoderuleRule sysCoderuleConfig2=new SysCoderuleRule();
        sysCoderuleConfig2.setRuleName("日期时间");
        sysCoderuleConfig2.setRuleOrder(6);
        sysCoderuleConfig2.setDataType(RuleConfigEnum.日期时间.getIndex());
        sysCoderuleConfig2.setRuleCode("BBB");
        list.add(sysCoderuleConfig2);
//        SysCoderuleRule sysCoderuleConfig3=new SysCoderuleRule();
//        sysCoderuleConfig3.setRuleName("自增值");
//        sysCoderuleConfig3.setRuleOrder(3);
//        sysCoderuleConfig3.setDataType(RuleConfigEnum.自增长.getIndex());
//        sysCoderuleConfig3.setRuleCode("CCC");
//        list.add(sysCoderuleConfig3);
//        SysCoderuleRule sysCoderuleConfig4=new SysCoderuleRule();
//        sysCoderuleConfig4.setRuleName("随机值");
//        sysCoderuleConfig4.setRuleOrder(4);
//        sysCoderuleConfig4.setDataType(RuleConfigEnum.随机数.getIndex());
//        sysCoderuleConfig4.setRuleCode("DDD");
//        list.add(sysCoderuleConfig4);
        sysCoderuleConfigMapper.createBatch(list);
        System.out.println("ok");
    }
    
}

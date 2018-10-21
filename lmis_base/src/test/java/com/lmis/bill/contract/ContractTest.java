package com.lmis.bill.contract;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lmis.sys.sequence.service.SysSequenceServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContractTest {

	@Resource(name="sysSequenceServiceImpl")
	SysSequenceServiceInterface sysSequenceService;
	
	@Test
    public void test(){
		for(int i=0;i<3;i++){			
			System.out.println("~~~~~~~~~~"+sysSequenceService.getSeqNum("budget:item:uuid:1"));
		}		
	}
}

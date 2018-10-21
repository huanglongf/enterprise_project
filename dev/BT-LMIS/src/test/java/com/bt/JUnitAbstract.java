package com.bt;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/** 
 * @ClassName: JUnitAbstract
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年6月28日 下午5:22:24 
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"/spring.xml","/spring-mvc.xml","/spring-mybatis.xml"})
public class JUnitAbstract extends AbstractTransactionalJUnit4SpringContextTests {

}

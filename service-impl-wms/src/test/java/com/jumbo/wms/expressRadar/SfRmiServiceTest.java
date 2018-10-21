package com.jumbo.wms.expressRadar;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import cn.baozun.bh.connector.sf.rmi.RmiService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:spring.xml"})
public class SfRmiServiceTest {
    protected static final Logger log = LoggerFactory.getLogger(SfRmiServiceTest.class);

	@Autowired
	private RmiService sfRmiService;
	
	@SuppressWarnings("rawtypes")
    @Test
	public void sfTest() {
		try {
			String string=sfRmiService.sfexpressService("589760954975");
			Document doc=DocumentHelper.parseText(string);
			Element root=doc.getRootElement(); 
			Element element=root.element("Body").element("RouteResponse");
			List nodes=element.elements();
			for (Iterator it = nodes.iterator(); it.hasNext();) {  
			      Element elm = (Element) it.next();
			      System.out.println(elm.attributeValue("remark"));
			} 
		} catch (Exception e) {
		    log.error("",e);
		}
	}
}

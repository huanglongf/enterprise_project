package com.lmis.pos.common.controller;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.pos.common.model.Demo;
import com.lmis.pos.common.service.DemoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/** 
 * @ClassName: DemoController
 * @Description: TODO(DEMO)
 * @author Ian.Huang
 * @date 2017年10月23日 下午12:12:34 
 * 
 */
@Api(value = "单条查询demo")
@RestController
@RequestMapping(value="/demo")
public class DemoController extends BaseController{

	/** 
	 * @Fields demoService : TODO(DEMO) 
	 */
	@Resource(name="demoServiceImpl")
	DemoService<Demo> demoService;
	
	@Value("${server.port}")
    String port;
	
	@ApiOperation(value="查询demo表")
	@RequestMapping(value = "/demo", method = RequestMethod.POST)
    public String demo(@RequestParam @ApiParam(value = "Demo id", required = true) String id) {
		Demo demo = new Demo();
		demo.setId(id);
        return JSON.toJSONString(demoService.queryPage(demo, 1, 20));
    }
	
//	@ApiOperation(value="查询demo表", httpMethod="POST")
//	@RequestMapping("/invoke/demo")
//    public String rpcdemo(@RequestParam String id) {
//		Demo demo = new Demo();
//		demo.setId(id);
//        return JSON.toJSONString(demoService.queryPage(demo, 1, 20));
//    }
	
	@ApiOperation(value="抛出异常处理")
	@RequestMapping("/testException")
	public String testException() throws Exception {
	    throw new Exception("this is exception!");
	}
	
	@ApiOperation(value="抛出数据库异常处理")
	@RequestMapping("/testSQLException")
	public String testSQLException() throws Exception {
	    throw new SQLException("this is sql exception!");
	}
	
}

package com.baozun.easytask.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baozun.easytask.service.SkxService;
import com.baozun.easytask.util.BigExcelExport;
import com.baozun.easytask.util.DateUtil;
import com.baozun.easytask.util.ExportExcelUtils;
import com.baozun.easytask.util.MailUtils;
import com.baozun.easytask.util.SendExcelMailUtil;

@Controller
@RequestMapping("/test")
public class TestController2 {
	private final static String  path = "/home/tomcat_lmis_pe/order/";//文件产生的地址
	private final static String email = "jinggong.li@baozun.com";//收件人邮箱
	private final static String cc = "jinggong.li@baozun.com";//抄送人邮箱
	
	@Resource(name = "skxServiceImpl")
	private SkxService skxServiceImpl;
	
	@RequestMapping("/test")
    @ResponseBody
	public String wmsSearch(@RequestParam("sheetName") String sheetName) {
		
		return JSON.toJSONString(sheetName);	
	}
 }
